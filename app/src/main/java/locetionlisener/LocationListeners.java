package locetionlisener;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import mymath.MyMath;
import samples.SingletonSensorSample;

public class LocationListeners implements LocationListener{
    private final SingletonSensorSample singleton = SingletonSensorSample.getInstance();
    private static final String Tag = LocationListeners.class.getName();

    @Override
    public void onLocationChanged(Location location) {

            double latitude = MyMath.doubleRound(location.getLatitude());
            double longitude = MyMath.doubleRound(location.getLongitude());

            synchronized (singleton){
                singleton.setLongitude(longitude);
                singleton.setLatitude(latitude);
            }

            Log.w(Tag, Double.toString(latitude) + " latitude");
            Log.w(Tag, Double.toString(longitude) + " longitude");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.w(Tag,"provider is enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.w(Tag,"provider is disable");
        synchronized (singleton) {
            singleton.setLatitude(0);
            singleton.setLongitude(0);
        }
    }
}
