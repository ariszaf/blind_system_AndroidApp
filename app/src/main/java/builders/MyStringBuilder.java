package builders;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type My string builder.
 */
//http://stackoverflow.com/questions/30675913/using-findviewbyid-in-an-external-class
public class MyStringBuilder {


    /**
     * take three float number and united with a (X,Y,Z) in a start and a space in the middle of ic one
     *
     * @param x the x axes
     * @param y the y axes
     * @param z the z axes
     * @return Return a String with the axes and float number with a space in the middle
     */
    public String createContentWithLabels(float x, float y, float z){
        String acceleration_X = String.valueOf(x);
        String acceleration_Y = String.valueOf(y);
        String acceleration_Z = String.valueOf(z);
        StringBuilder str = new StringBuilder("X: ").append(acceleration_X).append("  ").append("Y: " ).append(acceleration_Y).append("  ").append("Z: ").append(acceleration_Z);
        return str.toString();
    }

    /**
     * Create content without labels string.
     *
     * @param currentAcceleration_X the current acceleration x axes
     * @param currentAcceleration_Y the current acceleration y axes
     * @param currentAcceleration_Z the current acceleration z axes
     * @return the string with a space in the middle
     */
    public String createContentWithoutLabels(float currentAcceleration_X, float currentAcceleration_Y, float currentAcceleration_Z){
        String accelerationX = String.valueOf(currentAcceleration_X);
        String accelerationY = String.valueOf(currentAcceleration_Y);
        String accelerationZ = String.valueOf(currentAcceleration_Z);
        StringBuilder str = new StringBuilder(accelerationX).append(' ').append(accelerationY).append(' ').append(accelerationZ);
        return str.toString();
    }

    public String createMessage(String terminal_name, double latitude, double longitude, String proximity_name, float proximity_value, String accelerator_name, float acc_x, float acc_y, float acc_z,Date date) {
        String lat = String.valueOf(latitude);
        String longt = String.valueOf(longitude);
        String prox_value = String.valueOf(proximity_value);
        String acceleration_x = String.valueOf(acc_x);
        String acceleration_y = String.valueOf(acc_y);
        String acceleration_z = String.valueOf(acc_z);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        StringBuilder stringBuilder = new StringBuilder(terminal_name).append('#').append(lat).append('#').append(longt).append('#').append(proximity_name).append('#');
        stringBuilder.append(prox_value).append('#').append(accelerator_name).append('#').append(acceleration_x).append('#').append(acceleration_y).append('#');
        stringBuilder.append(acceleration_z).append('#').append(format.format(date));
        return stringBuilder.toString();
    }
}
