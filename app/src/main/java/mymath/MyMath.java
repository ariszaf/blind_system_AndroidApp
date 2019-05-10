package mymath;

import java.math.BigDecimal;


public class MyMath {

    //http://stackoverflow.com/questions/8911356/whats-the-best-practice-to-round-a-float-to-2-decimals
    public static float round(float d) {
        int decimalPlace = 2;
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static double doubleRound(double d) {
        int decimalPlace = 2;
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
