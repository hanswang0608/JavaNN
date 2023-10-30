import java.util.ArrayList;

public class NeuralNetwork {
    private ArrayList<Layer> layers;

    public NeuralNetwork(int[] architecture) {
        this.layers = new ArrayList<Layer>(architecture.length);
        this.layers.add(0, new Layer(architecture[0], 0, null));
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
        for (int i = 1; i < numLayers; i++) {
            this.layers.get(i).randomizeBiases();
        }
    }

    public void randomizeWeights() {
        int numLayers = getNumLayers();
        for (int i = 1; i < numLayers; i++) {
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
            String layerNumNeurons =  "numNeurons=" + l.getNumNeurons();
            String layerNumWeights = "numWeights=" + l.getNumWeights();
            String layerActivationType = "activation=" + l.getActivationFunction();
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
        for (int i = 1; i < numLayers; i++){
            this.layers.get(i).printBiases();
        }
    }

    public void printNetworkWeights() {
        System.out.println("----------------------------------------");
        System.out.println("Network Weights:");
        int numLayers = getNumLayers();
        for (int i = 1; i < numLayers; i++){
            this.layers.get(i).printWeights();
        }
    }

    public int getNumLayers() {
        return this.layers.size();
    }

    // returns biases excluding input layer
    public double[][] getBiases() {
        int numLayers = getNumLayers();
        double[][] biases = new double[numLayers-1][];
        for (int i = 1; i < numLayers; i++) {
            biases[i-1] = this.layers.get(i).getBiases();
        }
        return biases;
    }

    // returns weights excluding input layer
    public double[][][] getWeights() {
        int numLayers = getNumLayers();
        double[][][] weights = new double[numLayers-1][][];
        for (int i = 1; i < numLayers; i++) {
            weights[i-1] = this.layers.get(i).getWeights();
        }
        return weights;
    }

    public int[] getArchitecture() {
        int numLayers = getNumLayers();
        int[] architecture = new int[numLayers];
        for (int i = 0; i < numLayers; i++) {
            architecture[i] = this.layers.get(i).getNumNeurons();
        }
        return architecture;
    }

    // returns the total number of biases and weights in this network
    public int getNumParameters() {
        int numLayers = getNumLayers();
        int numParams = 0;
        for (int i = 1; i < numLayers; i++) {
            int numNeurons = this.layers.get(i).getNumNeurons();
            int numWeights = this.layers.get(i).getNumWeights();
            numParams += numNeurons + numNeurons*numWeights;
        }
        return numParams;
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

        double[][][] w = network.getWeights();
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                for (int k = 0; k < w[i][j].length; k++) {
                    System.out.println(w[i][j][k]);
                }
            }
        }
    }
}
