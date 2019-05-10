package function;


import service.BackgroundService;
import settings.AutomationSystemSettings;

/**
 * The type Settings activity reset button.
 * Set the default values to the sensor fields and the Switchers.
 */
public class SettingsActivityResetButton {

    private final AutomationSystemSettings settings = AutomationSystemSettings.getInstance();
    private SettingsActivityShowCurrentRunningParameters settingsActivityShowCurrentRunningParameters;


    /**
     * Instantiates a new Settings activity reset button.
     *
     * @param settingsActivityShowCurrentRunningParameters the settings activity show current running parameters taken from the setting singleton
     */
    public SettingsActivityResetButton( SettingsActivityShowCurrentRunningParameters settingsActivityShowCurrentRunningParameters) {
        this.settingsActivityShowCurrentRunningParameters = settingsActivityShowCurrentRunningParameters;
    }

    /**
     * Reset changes.
     * Set the switches and the sensor fields with the default values
     */

    public void resetChanges(){
        if(BackgroundService.onlineModeEnabled==false) {
            synchronized (settings) {
                settings.setFrequency(settings.getDEFAULT_FREQUENCY());
                settings.setThresholdProximitySensor(settings.getDEFAULT_THRESHOLD_PROXIMITY());
                settings.setThreshold_sensor_acceleration_x(settings.getDEFAULT_THRESHOLD_ACCELERATION_X());
                settings.setThreshold_sensor_acceleration_y(settings.getDEFAULT_THRESHOLD_ACCELERATION_Y());
                settings.setThreshold_sensor_acceleration_z(settings.getDEFAULT_THRESHOLD_ACCELERATION_Z());
                settings.setAlarm_when_below_p(settings.isDEFAULT_ALARM_WHEN_BELOW_P());
                settings.setAlarm_when_below_x(settings.isDEFAULT_ALARM_WHEN_BELOW_X());
                settings.setAlarm_when_below_y(settings.isDEFAULT_ALARM_WHEN_BELOW_Y());
                settings.setAlarm_when_below_z(settings.isDEFAULT_ALARM_WHEN_BELOW_Z());
            }
            settingsActivityShowCurrentRunningParameters.showTheCurrentRunningParameters();
        }
    }
}