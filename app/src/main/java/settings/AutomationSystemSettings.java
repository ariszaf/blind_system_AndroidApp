package settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * The type Automation system settings.
 */
public class AutomationSystemSettings {

    //___DEFAULT_VALUES_WHEN_APP_FIRST_START
    private final int DEFAULT_FREQUENCY = 2000000;
    private final float DEFAULT_THRESHOLD_PROXIMITY = 0;
    private final float DEFAULT_THRESHOLD_ACCELERATION_X = 20.0f;
    private final float DEFAULT_THRESHOLD_ACCELERATION_Y = 20.0f;
    private final float DEFAULT_THRESHOLD_ACCELERATION_Z = 20.0f;

    private final boolean DEFAULT_ALARM_WHEN_BELOW_P = true;
    private final boolean DEFAULT_ALARM_WHEN_BELOW_X = false;
    private final boolean DEFAULT_ALARM_WHEN_BELOW_Y = false;
    private final boolean DEFAULT_ALARM_WHEN_BELOW_Z = false;


    // "192.168.1.66"
    private final String DEFAULT_IP = "192.168.43.168";
    private final int DEFAULT_PORT = 1883;
    private final boolean DEFAULT_MODE = false;


    //_________REAL_TIME_VALUE______________________________________________________________________
    private int frequency;
    private float thresholdProximitySensor;
    private float threshold_sensor_acceleration_x;
    private float threshold_sensor_acceleration_y;
    private float threshold_sensor_acceleration_z;

    private boolean alarm_when_below_p = true;
    private boolean alarm_when_below_x = false;
    private boolean alarm_when_below_y = false;
    private boolean alarm_when_below_z = false;
    //___________________________________________________________________________________________new

    private String network_ip ;
    private int network_port;

    private String uuid = null;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets default frequency.
     *
     * @return the default frequency
     */
    public int getDEFAULT_FREQUENCY() {
        return DEFAULT_FREQUENCY;
    }

    /**
     * Gets default threshold acceleration x.
     *
     * @return the default threshold acceleration x
     */
    public float getDEFAULT_THRESHOLD_ACCELERATION_X() {
        return DEFAULT_THRESHOLD_ACCELERATION_X;
    }

    /**
     * Gets default threshold proximity.
     *
     * @return the default threshold proximity
     */
    public float getDEFAULT_THRESHOLD_PROXIMITY() {
        return DEFAULT_THRESHOLD_PROXIMITY;
    }

    /**
     * Gets default threshold acceleration y.
     *
     * @return the default threshold acceleration y
     */
    public float getDEFAULT_THRESHOLD_ACCELERATION_Y() {
        return DEFAULT_THRESHOLD_ACCELERATION_Y;
    }

    /**
     * Gets default threshold acceleration z.
     *
     * @return the default threshold acceleration z
     */
    public float getDEFAULT_THRESHOLD_ACCELERATION_Z() {
        return DEFAULT_THRESHOLD_ACCELERATION_Z;
    }

    /**
     * Is default alarm when below p boolean.
     *
     * @return the boolean
     */
    public boolean isDEFAULT_ALARM_WHEN_BELOW_P() {
        return DEFAULT_ALARM_WHEN_BELOW_P;
    }

    /**
     * Is default alarm when below x boolean.
     *
     * @return the boolean
     */
    public boolean isDEFAULT_ALARM_WHEN_BELOW_X() {
        return DEFAULT_ALARM_WHEN_BELOW_X;
    }

    /**
     * Is default alarm when below y boolean.
     *
     * @return the boolean
     */
    public boolean isDEFAULT_ALARM_WHEN_BELOW_Y() {
        return DEFAULT_ALARM_WHEN_BELOW_Y;
    }

    /**
     * Is default alarm when below z boolean.
     *
     * @return the boolean
     */
    public boolean isDEFAULT_ALARM_WHEN_BELOW_Z() {
        return DEFAULT_ALARM_WHEN_BELOW_Z;
    }

    /**
     * Gets threshold sensor acceleration y.
     *
     * @return the threshold sensor acceleration y
     */


    public String getDEFAULT_IP() {
        return DEFAULT_IP;
    }

    public int getDEFAULT_PORT() {
        return DEFAULT_PORT;
    }

    public boolean isDEFAULT_MODE() {
        return DEFAULT_MODE;
    }
//__________SET_GET_____________________________________________________________________________

    public int getNetwork_port() {
        return network_port;
    }

    public void setNetwork_port(int network_port) {
        this.network_port = network_port;
    }

    public String getNetwork_ip() {
        return network_ip;
    }

    public void setNetwork_ip(String network_ip) {
        this.network_ip = network_ip;
    }
    public float getThreshold_sensor_acceleration_y() {
        return threshold_sensor_acceleration_y;
    }

    /**
     * Sets threshold sensor acceleration y.
     *
     * @param threshold_sensor_acceleration_y the threshold sensor acceleration y
     */
    public void setThreshold_sensor_acceleration_y(float threshold_sensor_acceleration_y) {
        this.threshold_sensor_acceleration_y = threshold_sensor_acceleration_y;
    }


    /**
     * Gets threshold sensor acceleration z.
     *
     * @return the threshold sensor acceleration z
     */
    public float getThreshold_sensor_acceleration_z() {
        return threshold_sensor_acceleration_z;
    }

    /**
     * Sets threshold sensor acceleration z.
     *
     * @param threshold_sensor_acceleration_z the threshold sensor acceleration z
     */
    public void setThreshold_sensor_acceleration_z(float threshold_sensor_acceleration_z) {
        this.threshold_sensor_acceleration_z = threshold_sensor_acceleration_z;
    }


    /**
     * Is alarm when below p boolean.
     *
     * @return the boolean
     */
    public boolean isAlarm_when_below_p() {
        return alarm_when_below_p;
    }

    /**
     * Sets alarm when below p.
     *
     * @param alarm_when_below1 the alarm when below p
     */
    public void setAlarm_when_below_p(boolean alarm_when_below1) {
        this.alarm_when_below_p = alarm_when_below1;
    }

    /**
     * Is alarm when below z boolean.
     *
     * @return the boolean
     */
    public boolean isAlarm_when_below_z() {
        return alarm_when_below_z;
    }

    /**
     * Sets alarm when below z.
     *
     * @param alarm_when_below_z the alarm when below z
     */
    public void setAlarm_when_below_z(boolean alarm_when_below_z) {
        this.alarm_when_below_z = alarm_when_below_z;
    }

    /**
     * Is alarm when below y boolean.
     *
     * @return the boolean
     */
    public boolean isAlarm_when_below_y() {
        return alarm_when_below_y;
    }

    /**
     * Sets alarm when below y.
     *
     * @param alarm_when_below_y the alarm when below y
     */
    public void setAlarm_when_below_y(boolean alarm_when_below_y) {
        this.alarm_when_below_y = alarm_when_below_y;
    }

    /**
     * Is alarm when below x boolean.
     *
     * @return the boolean
     */
    public boolean isAlarm_when_below_x() {
        return alarm_when_below_x;
    }

    /**
     * Sets alarm when below x.
     *
     * @param alarm_when_below_x the alarm when below x
     */
    public void setAlarm_when_below_x(boolean alarm_when_below_x) {
        this.alarm_when_below_x = alarm_when_below_x;
    }

    /**
     * Gets frequency.
     *
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Sets frequency.
     *
     * @param frequency the frequency
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * Gets threshold proximity sensor.
     *
     * @return the threshold proximity sensor
     */
    public float getThresholdProximitySensor() {
        return thresholdProximitySensor;
    }

    /**
     * Sets threshold proximity sensor.
     *
     * @param thresholdProximitySensor the threshold proximity sensor
     */
    public void setThresholdProximitySensor(float thresholdProximitySensor) {
        this.thresholdProximitySensor = thresholdProximitySensor;
    }

    /**
     * Gets threshold sensor acceleration x.
     *
     * @return the threshold sensor acceleration x
     */
    public float getThreshold_sensor_acceleration_x() {
        return threshold_sensor_acceleration_x;
    }

    /**
     * Sets threshold sensor acceleration x.
     *
     * @param threshold_sensor_acceleration_x the threshold sensor acceleration x
     */
    public void setThreshold_sensor_acceleration_x(float threshold_sensor_acceleration_x) {
        this.threshold_sensor_acceleration_x = threshold_sensor_acceleration_x;
    }

    //_____________________SINGLETON________________________________________________________________
    private static AutomationSystemSettings instance = null;

    private AutomationSystemSettings() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized AutomationSystemSettings getInstance() {
        if (instance == null) {
            instance = new AutomationSystemSettings();
        }
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        return new CloneNotSupportedException();
    }

    /**
     * Load from preferences.
     *
     * @param activity the activity
     */
//______________________________________________________________________________________________
    //http://stackoverflow.com/questions/23024831/android-shared-preferences-example
    //https://www.tutorialspoint.com/android/android_shared_preferences.htm
    //http://stackoverflow.com/questions/3624280/how-to-use-sharedpreferences-in-android-to-store-fetch-and-edit-values
    //Preferences
    public void loadFromPreferences(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("AutomationSystem", Context.MODE_PRIVATE);//only this app can share

        frequency = sharedPreferences.getInt("frequency", DEFAULT_FREQUENCY);
        thresholdProximitySensor = sharedPreferences.getFloat("thresholdProximitySensor", DEFAULT_THRESHOLD_PROXIMITY);
        threshold_sensor_acceleration_x = sharedPreferences.getFloat("threshold_sensor_acceleration_x", DEFAULT_THRESHOLD_ACCELERATION_X);
        threshold_sensor_acceleration_y = sharedPreferences.getFloat("threshold_sensor_acceleration_y", DEFAULT_THRESHOLD_ACCELERATION_Y);
        threshold_sensor_acceleration_z = sharedPreferences.getFloat("threshold_sensor_acceleration_z", DEFAULT_THRESHOLD_ACCELERATION_Z);

        alarm_when_below_p = sharedPreferences.getBoolean("alarm_when_below_p", DEFAULT_ALARM_WHEN_BELOW_P);
        alarm_when_below_x = sharedPreferences.getBoolean("alarm_when_below_x", DEFAULT_ALARM_WHEN_BELOW_X);
        alarm_when_below_y = sharedPreferences.getBoolean("alarm_when_below_y", DEFAULT_ALARM_WHEN_BELOW_Y);
        alarm_when_below_z = sharedPreferences.getBoolean("alarm_when_below_z", DEFAULT_ALARM_WHEN_BELOW_Z);

        network_ip = sharedPreferences.getString("network_ip",DEFAULT_IP);
        network_port = sharedPreferences.getInt("network_port",DEFAULT_PORT);
    }


    /**
     * Save to preferences.
     *
     * @param activity the activity
     */
    public void saveToPreferences(Activity activity) {
        //object that helps as to edit a file
        SharedPreferences.Editor editor = activity.getSharedPreferences("AutomationSystem", Context.MODE_PRIVATE).edit();
        editor.putInt("frequency", frequency);
        editor.putFloat("thresholdProximitySensor", thresholdProximitySensor);
        editor.putFloat("threshold_sensor_acceleration_x", threshold_sensor_acceleration_x);
        editor.putFloat("threshold_sensor_acceleration_y", threshold_sensor_acceleration_y);
        editor.putFloat("threshold_sensor_acceleration_z", threshold_sensor_acceleration_z);


        editor.putBoolean("alarm_when_below_p", alarm_when_below_p);
        editor.putBoolean("alarm_when_below_x", alarm_when_below_x);
        editor.putBoolean("alarm_when_below_y", alarm_when_below_y);
        editor.putBoolean("alarm_when_below_z", alarm_when_below_z);

        editor.putString("network_ip",network_ip);
        editor.putInt("network_port",network_port);
//        editor.putBoolean("mode",mode);
        editor.apply();
    }

    //______________________________________________________________________________________________

}
