package sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Message;

import model.StatisticsContent;
import model.StatisticsProximity;
import samples.SingletonSensorSample;

import static android.hardware.Sensor.TYPE_PROXIMITY;


/**
 * The type Proximity sensor listener.
 * Take data from the sensor Proximity and send to the MainActivity and update the singleton Sample
 */
public class ProximitySensorListener implements SensorEventListener{
    private final SingletonSensorSample singleton = SingletonSensorSample.getInstance();

    /**
     * The constant handler.
     * The handler of main thread
     */
    public static Handler handler = null;

    /**
     * Instantiates a new Proximity sensor listener.
     *
     * @param handler the handler of the main thread
     */
    public ProximitySensorListener(Handler handler) {
        this.handler = handler;
    }

    /**
     * take the values from Proximity Sensor.
     * Update the Singleton sample.*/

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == TYPE_PROXIMITY) {
            Float proximitySensorValue = event.values[0];

            float currentProximityValue = proximitySensorValue;

            String content = String.valueOf(currentProximityValue);

            StatisticsContent statisticsContent = new StatisticsProximity(event.sensor, content, currentProximityValue);

            synchronized (singleton) {
                singleton.setProximityValue(proximitySensorValue);
            }

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
