import java.text.DecimalFormat;

public class Utils {
    private static DecimalFormat df = new DecimalFormat("0.00");
    
    public static String formatDouble(double x) {
        return df.format(x);
    }
}
