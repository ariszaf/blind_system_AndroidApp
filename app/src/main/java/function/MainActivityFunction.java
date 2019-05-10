package function;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.psilos.automationsystem.R;
import com.example.psilos.automationsystem.SettingsActivity;

import builders.MyStringBuilder;
import mymath.MyMath;
import service.BackgroundService;
import settings.AutomationSystemSettings;


/**
 * The type Main activity function.
 * Update the gui of MainActivity.
 * Send Intent to create SettingsActivity.
 * Send Intent to stop the service and finish the app.
 */

//http://stackoverflow.com/questions/10847526/what-exactly-activity-finish-method-is-doing
public class MainActivityFunction {

    private AutomationSystemSettings settings = AutomationSystemSettings.getInstance();
    private Activity activity;
    private Context context;

    /**
     * Instantiates a new Main activity function.
     *
     * @param activity the activity is the the MainActivity
     * @param context  the context is the getApplicationContext
     */
    public MainActivityFunction(Activity activity,Context context) {
        this.activity = activity;
        this.context = context;
    }

    /**
     * Show the current running parameters.
     * Update the MainActivity Running parameters from the settings
     */
    public void showTheCurrentRunningParameters() {
     
        TextView frequency_value = (TextView) activity.findViewById(R.id.frequency_value);
        frequency_value.setText(Integer.toString(settings.getFrequency()) + " μs");

        TextView threshold_sensor_proximity = (TextView) activity.findViewById(R.id.proximity_value_from_settings);
        threshold_sensor_proximity.setText(Float.toString(settings.getThresholdProximitySensor()) + " cm");

        float x = MyMath.round( settings.getThreshold_sensor_acceleration_x());
        float y = MyMath.round( settings.getThreshold_sensor_acceleration_y());
        float z = MyMath.round( settings.getThreshold_sensor_acceleration_z());

        MyStringBuilder msb = new MyStringBuilder();

        String content = msb.createContentWithLabels(x,y,z);

        TextView threshold_sensor_Acceleration =(TextView)activity.findViewById(R.id.acceleration_value_from_settings );
        threshold_sensor_Acceleration.setText(content);
    }

    /**
     * Create activity settings.
     * Send Intent to start Activity Settings.
     */
    public void createActivitySettings(){
        Intent intentSettings = new Intent(context, SettingsActivity.class);
        activity.startActivity(intentSettings);
    }

    /**
     * Exit from the application.
     * Send Intent to the service to stop.
     */
    public void exitFromTheApplication(){
        if (BackgroundService.serviceRunning) {
            Intent intent = new Intent(context, BackgroundService.class);
            activity.stopService(intent);
        }
        activity.finish();
    }

}
