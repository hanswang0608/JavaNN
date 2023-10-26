public class Utils {
    public enum ActivationFunction {
        SIGMOID, RELU
    }

    public static double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E,-x));
    }

    public static double ReLU(double x) {
        return x > 0 ? x : 0;
    }
}