package model;

import android.hardware.Sensor;


/**
 * The type Statistics content.
 */
public abstract class StatisticsContent {
    private final Sensor sensor;
    private final String message_content;


    /**
     * Instantiates a new Statistics content.
     *
     * @param sensor          the sensor
     * @param message_content the message content
     */
    public StatisticsContent(Sensor sensor, String message_content) {
        this.sensor = sensor;
        this.message_content = message_content;

    }

    /**
     * Gets sensor.
     *
     * @return the sensor
     */
    public Sensor getSensor() {
        return sensor;
    }

    /**
     * Gets message content.
     *
     * @return the message content
     */
    public String getMessage_content() {
        return message_content;
    }
}
