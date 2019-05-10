package com.example.psilos.automationsystem;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import backButton.BackPressed;
import deviceid.UniqueId;
import function.MainActivityFunction;
import model.StatisticsContent;
import sensors.AccelerometerSensorListener;
import sensors.ProximitySensorListener;
import service.BackgroundService;
import service.ThreadMonitorNetwork;
import settings.AutomationSystemSettings;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String Tag = MainActivity.class.getCanonicalName();
    private AutomationSystemSettings settings = AutomationSystemSettings.getInstance();
    private Button buttons[] = new Button[4];
    private TextView textViews[] = new TextView[4];
    private MainActivityFunction mainActivityFunction;
    private UniqueId uniqueId;
    private SwitchCompat wifiSwitcher;

    //http://idlesun.blogspot.gr/2012/12/android-handler-and-message-tutorial.html


    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                StatisticsContent statisticsContent = (StatisticsContent) msg.obj;
                if (statisticsContent != null) {
                    Log.i("NOTIFICATION:", statisticsContent.toString());
                }
                //elenxi ton tipo tou sensora apo tin domi statisticContex
                switch (statisticsContent.getSensor().getType()) {
                    case Sensor.TYPE_PROXIMITY:
                        textViews[0].setText(statisticsContent.getMessage_content() + " cm");
                        break;
                    case Sensor.TYPE_ACCELEROMETER:
                        String messageGui = statisticsContent.getMessage_content();
                        String[] parser = messageGui.split(" "); //pinakas xoris ta kena
                        updateAccelerationGui(parser);
                        break;
                }
            } else { // message from monitoring thread ...
                switch (msg.arg1) {
                    case 11:
                        wifiSwitcher.setChecked(true);
                        wifiSwitcher.setEnabled(false);
                        break;
                    case 13:
                        wifiSwitcher.setChecked(false);
                        wifiSwitcher.setEnabled(false);
                        break;
                    case 15:
                        wifiSwitcher.setChecked(true);
                        wifiSwitcher.setEnabled(false);
                        break;
                    case 17:
                        wifiSwitcher.setEnabled(true);
                        break;
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BackgroundService.serviceRunning = false;
        BackgroundService.automaticModeEnabled = false;
        BackgroundService.onlineModeEnabled = false;

        //______________check if the device support the app ________________________________________
        SensorManager sensorManager;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if ((sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) || (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null)) {
            Toast.makeText(this, "THE DEVICE NOT SUPPORT THE APP", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "YOUR DEVICE SUPPORT THE APP", Toast.LENGTH_SHORT).show();
        }

        //____________action bar____________________________________________________________________
        //http://stackoverflow.com/questions/29786011/how-to-fix-getactionbar-method-may-produce-java-lang-nullpointerexception
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);

        //__________onClickListener Button__________________________________________________________
        wifiSwitcher = (SwitchCompat) findViewById(R.id.wifi_switcher);

        buttons[0] = (Button) findViewById(R.id.start_button);
        buttons[1] = (Button) findViewById(R.id.stop_button);
        buttons[2] = (Button) findViewById(R.id.settings_button);
        buttons[3] = (Button) findViewById(R.id.exit_button);

        textViews[0] = (TextView) findViewById(R.id.proximity_value);
        textViews[1] = (TextView) findViewById(R.id.acceleration_x);
        textViews[2] = (TextView) findViewById(R.id.acceleration_y);
        textViews[3] = (TextView) findViewById(R.id.acceleration_z);

        for (int i = 0; i < 4; i++) {
            buttons[i].setOnClickListener(this);
        }

        wifiSwitcher.setOnCheckedChangeListener(this);

        //___________load preferences_______________________________________________________________

        settings.loadFromPreferences(this);
        BackgroundService.handler = handler;
        ProximitySensorListener.handler = handler;
        AccelerometerSensorListener.handler = handler;
        ThreadMonitorNetwork.mainActivityHandler = handler;
        //__________________________________________________________________________________________

        mainActivityFunction = new MainActivityFunction(this, getApplicationContext());


        //______________________________________________________________find unique id______________

        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        int address = info.getMacAddress().hashCode();
        Log.w(Tag, " mac address " + Integer.toString(address));

        uniqueId = new UniqueId(getApplicationContext());
        String uniqueName = uniqueId.UniqueIdName();
        Log.w(Tag,uniqueName);
        settings.setUuid(uniqueName);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mainActivityFunction.showTheCurrentRunningParameters();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        //check if has connection in internet
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (BackgroundService.serviceRunning) {
            if (BackgroundService.automaticModeEnabled) { // auto
                wifiSwitcher.setEnabled(false);
            } else { // manual
                if (isConnected) {
                    wifiSwitcher.setEnabled(true);
                } else {
                    wifiSwitcher.setEnabled(false);
                    BackgroundService.onlineModeEnabled = false;
                }
            }
        } else {
            wifiSwitcher.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_settings:
                mainActivityFunction.createActivitySettings();
                return true;
            case R.id.id_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                return true;
            case R.id.id_help:
                Intent intentHelp = new Intent(this, HelpActivity.class);
                startActivity(intentHelp);
                return true;
            case R.id.id_exit:
                mainActivityFunction.exitFromTheApplication();
                return true;
            case R.id.id_pub:
            {
                String topic        = "bill";
                String content      = "Message from Java project";
                int qos             = 2;
                String broker       = "tcp://192.168.1.66:1883";
                String clientId     = "JavaSample";
                MemoryPersistence persistence = new MemoryPersistence();

                try {
                    MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
                    MqttConnectOptions connOpts = new MqttConnectOptions();
                    connOpts.setCleanSession(true);
                    System.out.println("Connecting to broker: "+broker);
                    sampleClient.connect(connOpts);
                    System.out.println("Connected");
                    System.out.println("Publishing message: "+content);
                    MqttMessage message = new MqttMessage(content.getBytes());
                    message.setQos(qos);
                    sampleClient.publish(topic, message);
                    System.out.println("Message published");
                    sampleClient.disconnect();
                    System.out.println("Disconnected");
                } catch(MqttException me) {
                    System.out.println("reason "+me.getReasonCode());
                    System.out.println("msg "+me.getMessage());
                    System.out.println("loc "+me.getLocalizedMessage());
                    System.out.println("cause "+me.getCause());
                    System.out.println("excep "+me);
                    me.printStackTrace();
                }
            }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        settings.saveToPreferences(this);
    }

    @Override
    public void onBackPressed() {

        BackPressed backPressed = new BackPressed(this, mainActivityFunction);
        backPressed.createExitDialog();

    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.start_button)) {
            if (!BackgroundService.serviceRunning) {
                buttons[0].setEnabled(false);
                buttons[1].setEnabled(true);
                Intent intent = new Intent(this, BackgroundService.class);
                startService(intent);
            }
        } else if (v == findViewById(R.id.stop_button)) {
            if (BackgroundService.serviceRunning) {
                buttons[0].setEnabled(true);
                buttons[1].setEnabled(false);
                Intent intent = new Intent(this, BackgroundService.class);
                stopService(intent);
            }
        } else if (v == findViewById(R.id.settings_button)) {
            mainActivityFunction.createActivitySettings();
        } else if (v == findViewById(R.id.exit_button)) {
            mainActivityFunction.exitFromTheApplication();
        }
    }


    //______________________________________________________________________________________________
    //update gui Acceleration
    private void updateAccelerationGui(String[] parser) {
        for (int i = 0; i < parser.length; i++) {
            switch (i) {
                case 0:
                    textViews[1].setText(parser[0] + "  m/s²");
                    break;
                case 1:
                    textViews[2].setText(parser[1] + "  m/s²");
                    break;
                case 2:
                    textViews[3].setText(parser[2] + "  m/s²");
                    break;
                default:
                    Log.w(Tag, "Something went wrong");
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        BackgroundService.onlineModeEnabled = !wifiSwitcher.isChecked();
    }
}
