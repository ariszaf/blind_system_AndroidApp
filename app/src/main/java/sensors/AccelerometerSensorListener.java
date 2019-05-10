package sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Message;

import builders.MyStringBuilder;
import model.StatisticsAccelerometer;
import model.StatisticsContent;
import samples.SingletonSensorSample;
import settings.AutomationSystemSettings;


/**
 * The type Accelerometer sensor listener.
 * Take data from the sensor Acceleration and send to the MainActivity and update the singleton Sample
 */
public class AccelerometerSensorListener implements SensorEventListener{
    private final AutomationSystemSettings settings = AutomationSystemSettings.getInstance();
    private final SingletonSensorSample singleton = SingletonSensorSample.getInstance();

    /**
     * The constant handler.
     * The handler of main thread
     */
    public static Handler handler = null;

    /**
     * Instantiates a new Accelerometer sensor listener.
     *
     * @param handler the handler of the main thread
     */
    public AccelerometerSensorListener(Handler handler) {
        this.handler = handler;
        //singleton = SingletonSensorSample.getInstance();
    }

    /**
     * take the values from Acceleration Sensor.
     * Update the Singleton sample.*/

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            //_________________take the values from sensor _________________________________________
            Float accelerationXSensorValue = event.values[0];
            Float accelerationYSensorValue = event.values[1];
            Float accelerationZSensorValue = event.values[2];

            //________________float with tow decimal________________________________________________

            float currentAcceleration_X = Math.round(accelerationXSensorValue);
            float currentAcceleration_Y = Math.round(accelerationYSensorValue);
            float currentAcceleration_Z = Math.round(accelerationZSensorValue);

            //_______________make a Sting from values with a space in the middle____________________

            MyStringBuilder msb = new MyStringBuilder();

            String content = msb.createContentWithoutLabels(currentAcceleration_X,currentAcceleration_Y,currentAcceleration_Z);
            StatisticsContent statisticsContent = new StatisticsAccelerometer(event.sensor,content,currentAcceleration_X,currentAcceleration_Y,currentAcceleration_Z) ;


            synchronized (singleton){
                singleton.setAccelerationXValue(Float.valueOf(currentAcceleration_X));
                singleton.setAccelerationYValue(Float.valueOf(currentAcceleration_Y));
                singleton.setAccelerationZValue(Float.valueOf(currentAcceleration_Z));
            }

            //______________paste message to the mainActivity_______________________________________
            if (handler != null) {
                Message message = new Message();
                message.obj = statisticsContent;
                handler.sendMessage(message);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }



}
