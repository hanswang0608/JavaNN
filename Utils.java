import java.text.DecimalFormat;
import java.util.Random;

public class Utils {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final Random rand = new Random();
    
    public static String formatDouble(double x) {
        return df.format(x);
    }

    public static double randDouble(double lower, double upper) {
        return rand.nextDouble()*(upper-lower)+lower;
    }

    public static double randGaussian(double mean, double stddev) {
        return rand.nextGaussian()*stddev+mean;
    }
}
