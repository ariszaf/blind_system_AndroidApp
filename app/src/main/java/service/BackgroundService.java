package service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import locetionlisener.LocationListeners;
import sensors.AccelerometerSensorListener;
import sensors.ProximitySensorListener;
import settings.AutomationSystemSettings;


public class BackgroundService extends Service {
    private static final String Tag = BackgroundService.class.getName();
    private AutomationSystemSettings settings = AutomationSystemSettings.getInstance();
    private int frequency;
    private SensorManager sensorManager = null;
    private Sensor sensorAcceleration = null;
    private Sensor sensorProximity = null;
    private SensorEventListener sensorEventListenerProximity = null;
    private SensorEventListener sensorEventListenerAccelerometer = null;


    public static Handler handler = null;

    public static boolean serviceRunning = false;//flag too check if a service is running
    public static volatile boolean flagAlarm = false;

    public static volatile boolean automaticModeEnabled = false;//Manual
    public static volatile boolean onlineModeEnabled = false;//Offline
    private ThreadAlarm monitorThread;
    private ThreadMonitorNetwork monitorNetworkThread;

    //_____________________________________________________________________________________location

    private LocationManager locationManager = null;
    private LocationListeners locationListeners = null;




    @Override
    public void onCreate() {

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        frequency = settings.getFrequency();

        //create thread
        monitorThread = new ThreadAlarm(getApplicationContext());
        monitorNetworkThread = new ThreadMonitorNetwork(getApplicationContext());

        //__________________________________________________________________________________________

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListeners = new LocationListeners();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        sensorEventListenerProximity = new ProximitySensorListener(handler);
        sensorEventListenerAccelerometer = new AccelerometerSensorListener(handler);
        //register the listener with a periode that user wont
        sensorManager.registerListener(sensorEventListenerProximity, sensorProximity, frequency);
        sensorManager.registerListener(sensorEventListenerAccelerometer, sensorAcceleration, frequency);

        //start thread
        flagAlarm = true;
        monitorThread.start();
        monitorNetworkThread.start();

        serviceRunning = true;


        //________________________________________________________________________________location
        //the locationManager is open if we are or not in internet or the gps is enable
        try {
            boolean isGpsEnableGPSProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListeners);

            boolean isGpsEnableNETProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListeners);

            if (!isGpsEnableGPSProvider && !isGpsEnableNETProvider) {
                Log.w(Tag," gps is disabled , please enable");
                Toast.makeText(this, "NO GPS! please activate", Toast.LENGTH_LONG).show();
            }
        } catch (java.lang.SecurityException ex) {
            Log.w(Tag, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.w(Tag, "gps provider does not exist " + ex.getMessage());
        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            //interrupt the thread to stop an wait to terminate
            if (serviceRunning ) {
                monitorThread.interrupt();
                monitorNetworkThread.interrupt();
                flagAlarm = false;
                monitorThread.join();
                monitorNetworkThread.join();
            }
        } catch (InterruptedException e) {
            Log.i("BackgroundService", "InterruptedException" );
        }

        if(sensorManager != null) {
            //unregister the listeners
            sensorManager.unregisterListener(sensorEventListenerAccelerometer);
            sensorManager.unregisterListener(sensorEventListenerProximity);
            sensorEventListenerProximity = null;
            sensorEventListenerAccelerometer = null;
            sensorManager = null;
            sensorProximity = null;
            sensorAcceleration = null;
        }

        //http://stackoverflow.com/questions/32715189/location-manager-remove-updates-permission
        if (locationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.removeUpdates(locationListeners);
                }
            }
            locationManager = null;
        }
        serviceRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
