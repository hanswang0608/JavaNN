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
}
