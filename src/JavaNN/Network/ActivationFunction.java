package JavaNN.Network;

public class ActivationFunction {
    public enum FuncTypes {
        SIGMOID,
        RELU,
        SIN
    }
    
    public static double activate(double x, FuncTypes func) {
        switch (func) {
            case SIGMOID: return ActivationFunction.sigmoid(x);
            case RELU: return ActivationFunction.ReLU(x);
            case SIN: return Math.sin(x);
            default: return ActivationFunction.sigmoid(x);
        }
    }

    public static double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E,-x));
    }

    public static double ReLU(double x) {
        return x > 0 ? x : 0;
    }
}