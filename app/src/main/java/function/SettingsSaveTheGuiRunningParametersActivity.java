package function;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psilos.automationsystem.R;

import service.BackgroundService;
import settings.AutomationSystemSettings;

/**
 * The type Settings save the gui running parameters activity.
 */
public class SettingsSaveTheGuiRunningParametersActivity {
    private static final String Tag = SettingsSaveTheGuiRunningParametersActivity.class.getName();
    private AutomationSystemSettings settings = AutomationSystemSettings.getInstance();
    private Activity activity;

    /**
     * Instantiates a new Settings save the gui running parameters activity.
     *
     * @param activity the activity
     */
    public SettingsSaveTheGuiRunningParametersActivity(Activity activity) {
        this.activity = activity;
    }


    /**
     * Save the gui running parameters.
     *
     */
    public void saveTheGuiRunningParameters() {

        //TextView
        TextView frequency_value = (TextView) activity.findViewById(R.id.Setting_Frequency_Value);
        TextView threshold_sensor_proximity = (TextView) activity.findViewById(R.id.Setting_Threshold_Proximity_Value);
        TextView threshold_sensor_acceleration_x = (TextView)activity.findViewById(R.id.Setting_Threshold_Acceleration_X_Value);
        TextView threshold_sensor_acceleration_y = (TextView)activity.findViewById(R.id.Setting_Threshold_Acceleration_Y_Value);
        TextView threshold_sensor_acceleration_z = (TextView)activity.findViewById(R.id.Setting_Threshold_Acceleration_Z_Value);
        TextView network_ip = (TextView)activity.findViewById(R.id.Setting_Ip_Value);
        TextView network_port = (TextView)activity.findViewById(R.id.Setting_Port_Value);

        //___________________________switches
        SwitchCompat alarm_when_above1 = (SwitchCompat) activity.findViewById(R.id.Settings_Threshold_Proximity_Switcher);
        SwitchCompat alarm_when_above_x = (SwitchCompat) activity.findViewById(R.id.Settings_Threshold_Accelerator_X_Swicher);
        SwitchCompat alarm_when_above_y = (SwitchCompat) activity.findViewById(R.id.Settings_Threshold_Accelerator_Y_Swicher);
        SwitchCompat alarm_when_above_z = (SwitchCompat) activity.findViewById(R.id.Settings_Threshold_Accelerator_Z_Swicher);
        SwitchCompat mode = (SwitchCompat) activity.findViewById(R.id.Settings_Mode_Switcher);

        BackgroundService.automaticModeEnabled = !mode.isChecked();
        int options;



        if(BackgroundService.onlineModeEnabled==true){
            Boolean errorMessage=false;
            if(settings.getThresholdProximitySensor()!=Float.parseFloat(threshold_sensor_proximity.getText().toString())){
                threshold_sensor_proximity.setText(Float.toString(settings.getThresholdProximitySensor()));
                errorMessage=true;
            }
            if(settings.getThreshold_sensor_acceleration_x()!=Float.parseFloat(threshold_sensor_acceleration_x.getText().toString())){
                threshold_sensor_acceleration_x.setText(Float.toString(settings.getThreshold_sensor_acceleration_x()));
                errorMessage=true;
            }
            if(settings.getThreshold_sensor_acceleration_y()!=Float.parseFloat(threshold_sensor_acceleration_y.getText().toString())){
                threshold_sensor_acceleration_y.setText(Float.toString(settings.getThreshold_sensor_acceleration_y()));
                errorMessage=true;
            }
            if(settings.getThreshold_sensor_acceleration_z()!=Float.parseFloat(threshold_sensor_acceleration_z.getText().toString())){
                threshold_sensor_acceleration_z.setText(Float.toString(settings.getThreshold_sensor_acceleration_z()));
                errorMessage=true;
            }

            boolean proxValue;
            if(alarm_when_above1.isChecked()){
                proxValue=false;
            }
            else{
                proxValue=true;
            }
            if(settings.isAlarm_when_below_p()!=proxValue){
                if (settings.isAlarm_when_below_p()) {
                    alarm_when_above1.setChecked(false);
                } else {
                    alarm_when_above1.setChecked(true);
                }
                errorMessage=true;
            }


            boolean accXValue;
            if(alarm_when_above_x.isChecked()){
                accXValue=false;
            }
            else{
                accXValue=true;
            }
            if(settings.isAlarm_when_below_x()!=accXValue){
                if (settings.isAlarm_when_below_x()) {
                    alarm_when_above_x.setChecked(false);
                } else {
                    alarm_when_above_x.setChecked(true);
                }
                errorMessage=true;
            }


            boolean accYValue;
            if(alarm_when_above_y.isChecked()){
                accYValue=false;
            }
            else{
                accYValue=true;
            }
            if(settings.isAlarm_when_below_y()!=accYValue){
                if (settings.isAlarm_when_below_y()) {
                    alarm_when_above_y.setChecked(false);
                } else {
                    alarm_when_above_y.setChecked(true);
                }
                errorMessage=true;
            }


            boolean accZValue;
            if(alarm_when_above_z.isChecked()){
                accZValue=false;
            }
            else{
                accZValue=true;
            }
            if(settings.isAlarm_when_below_z()!=accZValue){
                if (settings.isAlarm_when_below_z()) {
                    alarm_when_above_z.setChecked(false);
                } else {
                    alarm_when_above_z.setChecked(true);
                }
                errorMessage=true;
            }

            if(errorMessage){
                Toast.makeText(activity, "eisai online! den mporeis na peirakseis tis rythmiseis gia proximity kai accelerometer, opoiadipote ali allagi tha apothikeutei kanonika", Toast.LENGTH_LONG).show();
            }
        }

        if(BackgroundService.onlineModeEnabled==false){
            try {
                settings.setThresholdProximitySensor(Float.parseFloat(threshold_sensor_proximity.getText().toString()));
            } catch (Exception exception) {
                options = 1;
                messageFromUserToFixSettings(options);
            }

            try{
                settings.setThreshold_sensor_acceleration_x(Float.parseFloat(threshold_sensor_acceleration_x.getText().toString()));
            }catch (Exception exception){
                options = 2;
                messageFromUserToFixSettings(options);
            }

            try{
                settings.setThreshold_sensor_acceleration_y(Float.parseFloat(threshold_sensor_acceleration_y.getText().toString()));
            }catch (Exception exception){
                options = 3;
                messageFromUserToFixSettings(options);
            }

            try{
                settings.setThreshold_sensor_acceleration_z(Float.parseFloat(threshold_sensor_acceleration_z.getText().toString()));
            }catch (Exception exception){
                options = 4;
                messageFromUserToFixSettings(options);
            }


            if(alarm_when_above1.isChecked()){
                settings.setAlarm_when_below_p(false);
            }
            else{
                settings.setAlarm_when_below_p(true);

            }
            //__________switchCompat____________________________________________________________________
            if(alarm_when_above_x.isChecked()){
                settings.setAlarm_when_below_x(false);
            }
            else{
                settings.setAlarm_when_below_x(true);

            }

            if(alarm_when_above_y.isChecked()){
                settings.setAlarm_when_below_y(false);
            }
            else{
                settings.setAlarm_when_below_y(true);
            }

            if(alarm_when_above_z.isChecked()){
                settings.setAlarm_when_below_z(false);
            }
            else{
                settings.setAlarm_when_below_z(true);

            }
        }

        try {
            int frequencyValue = Integer.parseInt(frequency_value.getText().toString());
            if (frequencyValue == 0) {
                myException();
            }
            if (settings.getFrequency() != frequencyValue) {
                settings.setFrequency(frequencyValue);
                Toast.makeText(activity, "You have changed the frequency. You need to restart monitoring if active", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            options = 0;
            messageFromUserToFixSettings(options);
        }

        try{
            String temp = network_ip.getText().toString();
            if(temp.length() == 0){
                myException();
            }else {
                settings.setNetwork_ip(temp);
                Log.w(Tag,temp);
            }
        }catch (Exception exception){
            Log.w(Tag,"error in the input of ip");
            options = 5;
            messageFromUserToFixSettings(options);
        }

        try{
            int port = Integer.parseInt(network_port.getText().toString());
            if (port == 0) {
                myException();
            }
            settings.setNetwork_port(port);
        }catch (Exception exception){
            options=6;
            messageFromUserToFixSettings(options);
        }
    }

    private void messageFromUserToFixSettings(int options) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);

        switch (options) {
            case 0:
                builder.setMessage("Frequency is not a valid integer");
                builder.setNeutralButton("Fix Frequency", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case 1:
                builder.setMessage("Proximity error Insert Correct Values");
                builder.setNeutralButton("Fix Proximity", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertProximity = builder.create();
                alertProximity.show();
                break;
            case 2:
                builder.setMessage("Acceleration X error Insert Correct Values");
                builder.setNeutralButton("Fix X", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertAccX = builder.create();
                alertAccX.show();
                break;
            case 3:
                builder.setMessage("Acceleration Y error Insert Correct Values");
                builder.setNeutralButton("Fix Y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertAccY = builder.create();
                alertAccY.show();
                break;
            case 4:
                builder.setMessage("Acceleration Z error Insert Correct Values");
                builder.setNeutralButton("Fix Z", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertAccZ = builder.create();
                alertAccZ.show();
                break;
            case 5:
                builder.setMessage("Ip error Insert Correct Values");
                builder.setNeutralButton("Fix Ip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog network_ip = builder.create();
                network_ip.show();
                break;
            case 6:
                builder.setMessage("Port error Insert Correct Values");
                builder.setNeutralButton("Fix Ip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog network_port = builder.create();
                network_port.show();
                break;
            default:
                builder.setMessage("SOMETHING GO WRONG");
                builder.setNeutralButton("God help you", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertWrong = builder.create();
                alertWrong.show();
        }
    }

    private void myException() throws Exception{
        throw new Exception();
    }

}