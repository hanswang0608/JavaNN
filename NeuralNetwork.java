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

    public int getNumLayers() {
        return this.architecture.length;
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

    public void printNetworkSize() {
        int numLayers = getNumLayers();
        System.out.println("Network Attributes:");
        System.out.println("Number of layers: " + numLayers);
        for (int i = 0; i < numLayers; i++) {
            String layerName = "Layer " + i;
            // if (i == 0) layerName += "(input)"; 
            // else if (i == numLayers-1) layerName += "(output)";
            System.out.println(layerName + ": Size=" + this.architecture[i]);
        }
    }

    public void printNetworkValues() {
        int numLayers = getNumLayers();
        for (int i = 0; i < numLayers; i++){
            
        }
    }

    public static void main(String[] args) {
        NeuralNetwork network = new NeuralNetwork(new int[]{1, 1});
        double[] outputs = network.evaluate(new double[]{0});
        for (int i = 0; i < outputs.length; i++) {
            System.out.println(outputs[i]);
        }
        network.printNetworkSize();
    }
}
 