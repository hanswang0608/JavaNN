public class Perceptron {
    public double bias;

    public Perceptron() {
        this.bias = 0;
    }

    public Perceptron(double bias) {
        this.bias = bias;
    }

    public double activate(double input) {
        return Utils.sigmoid(input+bias);
    }
}
