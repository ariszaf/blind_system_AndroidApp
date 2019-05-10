package model;

import android.hardware.Sensor;

/**
 * The type Statistics proximity.
 *
 *  store the data for the sensor proximity
 */


public class StatisticsProximity extends StatisticsContent {
    private final float value;

    /**
     * create an new object StatisticsProximity
     *
     * @param sensor          this represent the type of sensor proximity
     * @param message_content this represent the value of sensor casted in string
     * @param value           this represent the value in int of the sensor
     */
    public StatisticsProximity(Sensor sensor, String message_content, float value) {
        super(sensor, message_content);
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return current value of the sensor Proximity
     */
    public float getValue() {
        return value;
    }
}
