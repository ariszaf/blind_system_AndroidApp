package model;

import android.hardware.Sensor;

/**
 *  store the data for the sensor acceleration
 *
 * */

public class StatisticsAccelerometer extends StatisticsContent {
    private final float x;
    private final float y;
    private final float z;

    /**
     *@param sensor this represent the type of sensor acceleration
     *@param message_content this represent the value of sensor casted in string
     *@param x this represent the value of the x axes
     *@param y this represent the value of the y axes
     *@param z this represent the value of the z axes
     */

    public StatisticsAccelerometer(Sensor sensor, String message_content, float x, float y, float z) {
        super(sensor, message_content);
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     *@return current value of the x
     * */
    public float getX() {
        return x;
    }

    /**
     *@return current value of the y
     * */
    public float getY() {
        return y;
    }

    /**
     *@return current value of the z
     * */
    public float getZ() {
        return z;
    }
}
