package service;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.util.Date;

import builders.MyStringBuilder;
import samples.SingletonSensorSample;
import settings.AutomationSystemSettings;

import static service.BackgroundService.flagAlarm;

//http://stackoverflow.com/questions/3289038/play-audio-file-from-the-assets-directory
//http://stackoverflow.com/questions/16795600/can-you-play-a-mp3-file-from-the-assets-folder
/**
 * The type Thread alarm.
 */
class ThreadAlarm extends Thread implements MqttCallback {
    private final AutomationSystemSettings settings = AutomationSystemSettings.getInstance();
    private final SingletonSensorSample singleton = SingletonSensorSample.getInstance();
    private final static String Tag = ThreadAlarm.class.getCanonicalName();

    //_____________visual alarm_____________________________________________________________________
    private final Toast alarmToast;
    private final Toast alarmToastOnline;
    private final Toast bothAlarmToastOnline;
    private AudioManager myAudioManager = null;

    //_____________sound alarm______________________________________________________________________
    private MediaPlayer mMediaPlayer = null;
    private  AssetFileDescriptor afd = null;
    //______________________________________________________________________________________________
    private MemoryPersistence persistence = new MemoryPersistence();

    private MqttClient subscribeClient = null;
    /**
     * The Application context.
     */
    private Context applicationContext;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("HANDLER",msg.toString());
            if (msg.obj == null) {
                if (msg.arg1 == 1) {
                    alarmToastOnline.cancel();
                    bothAlarmToastOnline.cancel();
                    alarmToast.show();

                    try {
                        if (!mMediaPlayer.isPlaying()) {
                            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                            mMediaPlayer.prepare();
                            mMediaPlayer.start();
                        }
                    } catch(Exception e) {
                        Log.i("ThreadAlarm", "Exception");
                    }
                } else if (msg.arg1 == 2) {
                    alarmToast.cancel();
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                    }
                } else if (msg.arg1 == 3 || msg.arg1 == 4) {

                    if(msg.arg1 == 3){
                        if (!bothAlarmToastOnline.getView().isShown()) {
                            alarmToastOnline.show();
                        }
                        alarmToast.cancel();
                    }else if(msg.arg1 == 4){
                        alarmToastOnline.cancel();
                        bothAlarmToastOnline.show();
                        alarmToast.cancel();
                    }
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    int volume =myAudioManager.getStreamMaxVolume(AudioManager.ADJUST_RAISE);
                    myAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,volume,AudioManager.FLAG_PLAY_SOUND);
                    Log.w(Tag, msg.toString());

                }
            }
        }
    };


    /**
     * Instantiates a new Thread alarm.
     *
     * @param applicationContext the application context
     *
     */
    public ThreadAlarm(Context applicationContext) {
        this.applicationContext = applicationContext;

        alarmToastOnline = Toast.makeText(applicationContext,"AlarmOnline",Toast.LENGTH_LONG);
        alarmToast = Toast.makeText(applicationContext, "Alarm", Toast.LENGTH_LONG);
        bothAlarmToastOnline = Toast.makeText(applicationContext,"BothAlarm",Toast.LENGTH_LONG);
        mMediaPlayer = new MediaPlayer();
        myAudioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);

        try {
            afd = applicationContext.getAssets().openFd("siren.mp3");
            mMediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mMediaPlayer.setLooping(true);
        } catch (Exception e) {
            Log.i("ThreadAlarm", "Exception");
        }
    }

    private void subscribe() {
        String device_name ;
        int port ;
        String network_ip;
        String topic = "COMMON_ALERTS";

        synchronized (settings){
            device_name = settings.getUuid();
            network_ip = settings.getNetwork_ip();
            port = settings.getNetwork_port();
        }

        String broker = "tcp://" + network_ip + ":" + port;
        String clientId     = "JavaAdnroidSubscriber" + device_name;
        int qos = 2;

        if (subscribeClient == null) {
            try {
                subscribeClient = new MqttClient(broker, clientId, persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                subscribeClient.setCallback(this);
                subscribeClient.connect(connOpts);
                subscribeClient.subscribe(topic, qos);
            } catch (MqttException e) {
               Log.w(Tag, " MqttException subscribe");
            }
        }
    }

    private void unsubcribe() {
        String topic = "COMMON_ALERTS";
        if (subscribeClient != null) {
            try {
                subscribeClient.unsubscribe(topic);
                subscribeClient.disconnect();
            } catch (MqttException e) {
             Log.w(Tag,"MqttException in unsubscribe");
            }
            subscribeClient =null;
        }
    }

    public void run() {
        float currentProximityValue;
        float x;
        float y;
        float z;
        //from settings
        boolean is_alarm_when_below_p;
        boolean is_alarm_when_below_x;
        boolean is_alarm_when_below_y;
        boolean is_alarm_when_below_z;
        float threshold_proximity;
        float threshold_acc_x;
        float threshold_acc_y;
        float threshold_acc_z;
        //_________________________________________________________________________________net
        double latitude;
        double longitude;
        String device_name;
        int port;
        String network_ip;




        try {
            while (!isInterrupted() && flagAlarm){
                Log.w(Tag, " Tick from thread alarm");
                Thread.sleep(250);

                if (BackgroundService.onlineModeEnabled) {
                    subscribe();
                } else {
                    unsubcribe();
                }

                boolean alarm = false;
                //from samples
                synchronized (singleton) {
                    currentProximityValue = singleton.getProximityValue();
                    x = singleton.getAccelerationXValue();
                    y = singleton.getAccelerationYValue();
                    z = singleton.getAccelerationZValue();
                    latitude = singleton.getLatitude();
                    longitude = singleton.getLongitude();
                }

                //for settings
                synchronized (settings) {
                    is_alarm_when_below_p = settings.isAlarm_when_below_p();
                    is_alarm_when_below_x = settings.isAlarm_when_below_x();
                    is_alarm_when_below_y = settings.isAlarm_when_below_y();
                    is_alarm_when_below_z = settings.isAlarm_when_below_z();
                    threshold_proximity = settings.getThresholdProximitySensor();
                    threshold_acc_x = settings.getThreshold_sensor_acceleration_x();
                    threshold_acc_y = settings.getThreshold_sensor_acceleration_y();
                    threshold_acc_z = settings.getThreshold_sensor_acceleration_z();
                }

                //______________________proximity___________________________________________________
                if (is_alarm_when_below_p) {
                    if (currentProximityValue <= threshold_proximity) {
                        alarm = true;
                    }
                } else {
                    if (currentProximityValue >= threshold_proximity) {
                        alarm = true;
                    }
                }
                //_________________acceleration_____________________________________________________
                if (is_alarm_when_below_x) {
                    if (x <= threshold_acc_x) {
                        alarm = true;
                    }
                } else {
                    if (x >= threshold_acc_x) {
                        alarm = true;
                    }
                }

                if (is_alarm_when_below_y) {
                    if (y <= threshold_acc_y) {
                        alarm = true;
                    }
                } else {
                    if (y >= threshold_acc_y) {
                        alarm = true;
                    }
                }

                if (is_alarm_when_below_z) {
                    if (z <= threshold_acc_z) {
                        alarm = true;
                    }
                } else {
                    if (z >= threshold_acc_z) {
                        alarm = true;
                    }
                }
                //__________________________________________________________________________________

                Message message = new Message();
                if (alarm) {
                    message.arg1 = 1;
                } else {
                    message.arg1 = 2;
                }

                //no internet connection
                if (!BackgroundService.onlineModeEnabled ) {//false offline mode
                    // 1st assignment
                    Log.w("********", "first assignment");
                    handler.sendMessage(message);
                } else {
                    // 2nd assignment online
                    Log.w("********", "second assignment");
                    Log.w("network_state", Boolean.toString(BackgroundService.onlineModeEnabled));

                    if (mMediaPlayer.isPlaying()) { //case from alarm in the offline is rining and now we are online
                        message.arg1 = 2;
                        handler.sendMessage(message);
                    }

                    if (latitude != 0 && longitude != 0) { //if the latitude  && longitude flag tou publish
                        try {
                            synchronized (settings){
                                device_name =settings.getUuid();
                                network_ip = settings.getNetwork_ip();
                                port = settings.getNetwork_port();
                            }

                            //publisher mqtt
                            int  qos             = 2;
                            String clientId     = "AndroidSample" + device_name;
                            String topic = device_name;
                            String broker = "tcp://" + network_ip + ":" + Integer.toString(port);

                            MqttConnectOptions connOpts = new MqttConnectOptions();
                            connOpts.setCleanSession(true);
                            MyStringBuilder myStringBuilder = new  MyStringBuilder();
                            String message_all_data= myStringBuilder.createMessage(device_name ,latitude ,longitude ,"Proximity",currentProximityValue,"Acceleration",x,y,z,new Date());
                            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);

                            if(sampleClient != null){
                                sampleClient.connect(connOpts);
                                MqttMessage message_mqtt = new MqttMessage(message_all_data.getBytes());
                                message_mqtt.setQos(qos);
                                sampleClient.publish(topic, message_mqtt);
                                sampleClient.disconnect();
                            }
                        }catch (MqttException e) {
                            Log.w("MQTT:", "exception");
                        }
                    } else {
                        Log.w("MQTT:", "GPS: has no location data");
                    }
                }
            }
        } catch (InterruptedException e) {

        }finally {
            Log.i(Tag,"InterruptedException");

            if (alarmToast != null) {
                alarmToast.cancel();
            }
            if (alarmToastOnline != null) {
                alarmToastOnline.cancel();
            }
            if(bothAlarmToastOnline != null){
                bothAlarmToastOnline.cancel();
            }

            if(alarmToastOnline != null){
                alarmToastOnline.cancel();
            }
            if (mMediaPlayer.isPlaying()) {
                Log.i(Tag,"stop music");
                mMediaPlayer.stop();
                mMediaPlayer = null;
            }
            try {
                Log.i(Tag,"close fd music");
                if (afd.getFileDescriptor().valid()) {
                    afd.close();
                }
                Log.w(Tag,"IOException");
            } catch (IOException e1) {
            }
        }
        try{
        unsubcribe();
        }catch (Exception e){

        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        Log.d(Tag, "connection Lost **********************************");
//        subscribeClient = null;
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String msg = new String(mqttMessage.getPayload());
        String dev_name;
        synchronized (settings){
            dev_name = settings.getUuid();
        }

        Log.w(Tag, " message" + msg);
        if (BackgroundService.onlineModeEnabled) {
            if (msg.equals(dev_name)) {
                Message m = new Message();
                m.arg1 = 3;
                Log.w(Tag, Integer.toString( m.arg1));
                handler.sendMessage(m);//to this device
                Log.w(Tag, String.valueOf(m));
            } else if (msg.equals("0")) {
                Message m = new Message();
                m.arg1 = 4;
                handler.sendMessage(m);//to both devices
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}




















