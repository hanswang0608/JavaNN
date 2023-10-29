import java.util.ArrayList;

public class NeuralNetwork {
    private ArrayList<Layer> layers;

    public NeuralNetwork(int[] architecture) {
        this.layers = new ArrayList<Layer>(architecture.length);
        this.layers.add(0, new Layer(architecture[0], 0, ActivationFunction.FuncTypes.RELU));
        ActivationFunction.FuncTypes layerType;
        for (int i = 1; i < architecture.length; i++) {
            layerType = (i == architecture.length - 1) 
                ? ActivationFunction.FuncTypes.SIGMOID
                : ActivationFunction.FuncTypes.RELU;
            this.layers.add(i, new Layer(architecture[i], architecture[i-1], layerType));
        }
    }

    public double[] evaluate(double[] inputs) {
        if (inputs.length != layers.get(0).getNumNeurons()) {
            throw new IllegalArgumentException("Dimension of inputs does not match the input layer");
        }
        layers.get(0).setValues(inputs);
        double[] outputs = inputs;
        for (int i = 1; i < layers.size(); i++) {
            outputs = layers.get(i).evaluate(outputs);
        }
        return outputs;
    }

    public void randomizeBiases() {
        int numLayers = getNumLayers();
        for (int i = 0; i < numLayers; i++) {
            this.layers.get(i).randomizeBiases();
        }
    }

    public void randomizeWeights() {
        int numLayers = getNumLayers();
        for (int i = 0; i < numLayers; i++) {
            this.layers.get(i).randomizeWeights();
        }
    }

    public void printNetworkProperties() {
        System.out.println("----------------------------------------");
        System.out.println("Network Properties:");
        int numLayers = getNumLayers();
        System.out.println("Number of layers: " + numLayers);
        for (int i = 0; i < numLayers; i++) {
            Layer l = this.layers.get(i);
            String layerName = "Layer " + i;
            // if (i == 0) layerName += "(input)"; 
            // else if (i == numLayers-1) layerName += "(output)";
            String layerNumNeurons =  "numNeurons= " + l.getNumNeurons();
            String layerNumWeights = "numWeights= " + l.getNumWeights();
            String layerActivationType = "activation= " + l.getActivationFunction();
            System.out.println(layerName + ": " + layerNumNeurons + ", " + layerNumWeights + ", " + layerActivationType);
        }
    }

    public void printNetworkValues() {
        System.out.println("----------------------------------------");
        System.out.println("Network Values:");
        int numLayers = getNumLayers();
        for (int i = 0; i < numLayers; i++){
            this.layers.get(i).printValues();
        }
    }

    public void printNetworkBiases() {
        System.out.println("----------------------------------------");
        System.out.println("Network Biases:");
        int numLayers = getNumLayers();
        for (int i = 0; i < numLayers; i++){
            this.layers.get(i).printBiases();
        }
    }

    public void printNetworkWeights() {
        System.out.println("----------------------------------------");
        System.out.println("Network Weights:");
        int numLayers = getNumLayers();
        for (int i = 0; i < numLayers; i++){
            this.layers.get(i).printWeights();
        }
    }

    public int getNumLayers() {
        return this.layers.size();
    }

    public static void main(String[] args) {
        NeuralNetwork network = new NeuralNetwork(new int[]{2, 2, 2});
        network.randomizeBiases();
        network.randomizeWeights();
        network.evaluate(new double[]{0.5, 0.5});
        network.printNetworkProperties();
        network.printNetworkBiases();
        network.printNetworkWeights();
        network.printNetworkValues();
    }
}
