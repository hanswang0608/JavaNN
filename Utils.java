public class Utils {
    public static double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E,-x));
    }
}
