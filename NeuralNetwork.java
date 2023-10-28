import java.util.ArrayList;

public class NeuralNetwork {
    private ArrayList<Layer> layers;
    private int[] architecture;

    public NeuralNetwork(int[] architecture) {
        this.architecture = architecture;
        this.layers = new ArrayList<Layer>(architecture.length-1);
        Layer.ActivationFunction layerType;
        for (int i = 1; i < architecture.length; i++) {
            layerType = (i == architecture.length - 1) 
                ? Layer.ActivationFunction.SIGMOID
                : Layer.ActivationFunction.RELU;
            this.layers.add(i-1, new Layer(architecture[i], architecture[i-1], layerType));
        }
    }

    public double[] evaluate(double[] inputs) {
        if (inputs.length != architecture[0]) {
            throw new IllegalArgumentException("Dimension of inputs does not match the input layer");
        }
        double[] outputs = inputs;
        for (int i = 0; i < layers.size(); i++) {
            outputs = layers.get(i).evaluate(outputs);
        }
        return outputs;
    }

    public static void main(String[] args) {
        NeuralNetwork network = new NeuralNetwork(new int[]{2, 2, 2});
        network.evaluate(new double[]{1.0, 2.0});
    }
}
 