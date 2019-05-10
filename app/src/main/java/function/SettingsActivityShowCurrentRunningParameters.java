package function;

import android.app.Activity;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;

import com.example.psilos.automationsystem.R;

import service.BackgroundService;
import settings.AutomationSystemSettings;

/**
 * The type Settings activity show current running parameters.
 * Update the gui of ActivitySettings.
 * Read data from Singleton Settings
 */
public class SettingsActivityShowCurrentRunningParameters {
    private AutomationSystemSettings settings = AutomationSystemSettings.getInstance();
    private Activity activity;


    /**
     * Instantiates a new Settings activity show current running parameters.
     *
     * @param activity the activity Settings
     */
    public SettingsActivityShowCurrentRunningParameters(Activity activity) {
        this.activity = activity;
    }

    /**
     * Show the current running parameters.
     * Update the gui of Activity Setting.
     */
    public void showTheCurrentRunningParameters() {

        TextView frequency_value = (TextView) activity.findViewById(R.id.Setting_Frequency_Value);
        frequency_value.setText(Integer.toString(settings.getFrequency()));

        TextView threshold_sensor_proximity = (TextView) activity.findViewById(R.id.Setting_Threshold_Proximity_Value);
        threshold_sensor_proximity.setText(Float.toString(settings.getThresholdProximitySensor()));

        TextView threshold_sensor_acceleration_x = (TextView) activity.findViewById(R.id.Setting_Threshold_Acceleration_X_Value);
        threshold_sensor_acceleration_x.setText(Float.toString(settings.getThreshold_sensor_acceleration_x()));

        TextView threshold_sensor_acceleration_y = (TextView) activity.findViewById(R.id.Setting_Threshold_Acceleration_Y_Value);
        threshold_sensor_acceleration_y.setText(Float.toString(settings.getThreshold_sensor_acceleration_y()));

        TextView threshold_sensor_acceleration_z = (TextView) activity.findViewById(R.id.Setting_Threshold_Acceleration_Z_Value);
        threshold_sensor_acceleration_z.setText(Float.toString(settings.getThreshold_sensor_acceleration_z()));



        //_______________________________________________________________________________________new

        TextView network_ip = (TextView) activity.findViewById(R.id.Setting_Ip_Value) ;
        network_ip.setText(settings.getNetwork_ip());

        TextView network_port = (TextView) activity.findViewById(R.id.Setting_Port_Value);
        network_port.setText(Integer.toString(settings.getNetwork_port()));

        SwitchCompat mode = (SwitchCompat) activity.findViewById(R.id.Settings_Mode_Switcher);
        mode.setChecked(!BackgroundService.automaticModeEnabled);

        //_________________Switches_________________________________________________________________
        SwitchCompat alarm_when_above_p = (SwitchCompat) activity.findViewById(R.id.Settings_Threshold_Proximity_Switcher);
        if (settings.isAlarm_when_below_p()) {
            alarm_when_above_p.setChecked(false);
        } else {
            alarm_when_above_p.setChecked(true);
        }

        SwitchCompat alarm_when_above_x = (SwitchCompat) activity.findViewById(R.id.Settings_Threshold_Accelerator_X_Swicher);
        if (settings.isAlarm_when_below_x()) {
            alarm_when_above_x.setChecked(false);
        } else {
            alarm_when_above_x.setChecked(true);
        }

        SwitchCompat alarm_when_above_y = (SwitchCompat) activity.findViewById(R.id.Settings_Threshold_Accelerator_Y_Swicher);
        if (settings.isAlarm_when_below_y()) {
            alarm_when_above_y.setChecked(false);
        } else {
            alarm_when_above_y.setChecked(true);
        }

        SwitchCompat alarm_when_above_z = (SwitchCompat) activity.findViewById(R.id.Settings_Threshold_Accelerator_Z_Swicher);
        if (settings.isAlarm_when_below_z()) {
            alarm_when_above_z.setChecked(false);
        } else {
            alarm_when_above_z.setChecked(true);
        }
    }
}
