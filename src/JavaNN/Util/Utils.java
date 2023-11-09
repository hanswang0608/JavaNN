package JavaNN.Util;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Random;

public class Utils {
    private static final DecimalFormat df = new DecimalFormat("+0.00;-0.00");
    private static final Random rand = new Random();
    
    public static double roundDouble(double x, int places) {
        return Math.round(x * Math.pow(10, places))/Math.pow(10, places);
    }

    public static String formatDouble(double x) {
        df.applyPattern("0.00");
        return df.format(x);
    }

    public static String formatDouble(double x, int places) {
        String pattern = "0.";
        for (int i = 0; i < places; i++) pattern += "0";
        df.applyPattern(pattern);
        return df.format(x);
    }

    public static String prettifyDouble(double x) {
        df.applyPattern("+0.00;-0.00");
        if (x == 10.0) return "+10.0";
        if (x == -10.0) return "-10.0";
        return df.format(x);
    }


    public static double randDouble(double lower, double upper) {
        return rand.nextDouble()*(upper-lower)+lower;
    }

    public static double randGaussian(double mean, double stddev) {
        return rand.nextGaussian()*stddev+mean;
    }

    public static boolean randBool(double probability) {
        return rand.nextDouble() <= probability;
    }

    public static byte[] intToByteArray(int x) {
        return ByteBuffer.allocate(4).putInt(x).array();
    }

    public static int byteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
}
