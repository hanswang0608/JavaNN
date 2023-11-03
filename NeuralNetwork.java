import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class NeuralNetwork {
    private ArrayList<Layer> layers;

    private static final ActivationFunction.FuncTypes HIDDEN_LAYER_TYPE = ActivationFunction.FuncTypes.RELU;
    private static final ActivationFunction.FuncTypes OUTPUT_LAYER_TYPE = ActivationFunction.FuncTypes.SIGMOID;

    public NeuralNetwork(int[] architecture) {
        this.layers = new ArrayList<Layer>(architecture.length);
        this.layers.add(0, new Layer(architecture[0], 0, null));
        ActivationFunction.FuncTypes layerType;
        for (int i = 1; i < architecture.length; i++) {
            layerType = (i == architecture.length - 1) 
                ? OUTPUT_LAYER_TYPE
                : HIDDEN_LAYER_TYPE;
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

    public int getNumLayers() {
        return this.layers.size();
    }

    public int getNumBiases() {
        int numBiases = 0;
        int numLayers = getNumLayers();
        for (int i = 1; i < numLayers; i++) {
            numBiases += this.layers.get(i).getNumNeurons();
        }
        return numBiases;
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

    
    public double[][] getBiasesFromParameters(double[] parameters) {
        int numLayers = getNumLayers();
        double[][] biases = new double[numLayers-1][];
        int ind = 0;
        for (int i = 0; i < numLayers-1; i++) {
            int numNeurons = this.layers.get(i+1).getNumNeurons();
            biases[i] = new double[numNeurons];
            for (int j = 0; j < numNeurons; j++) {
                biases[i][j] = parameters[ind];
                ind++;
            }
        }
        return biases;
    }

    public int getNumWeights() {
        int numWeights = 0;
        int numLayers = getNumLayers();
        for (int i = 1; i < numLayers; i++) {
            numWeights += this.layers.get(i).getNumNeurons() * this.layers.get(i).getNumWeights();
        }
        return numWeights;
    }

    public double[][][] getWeightsFromParameters(double[] parameters) {
        int numLayers = getNumLayers();
        double[][][] weights = new double[numLayers-1][][];
        int ind = getNumBiases();
        for (int i = 0; i < numLayers-1; i++) {
            int numNeurons = this.layers.get(i+1).getNumNeurons();
            int numWeights = this.layers.get(i+1).getNumWeights();
            weights[i] = new double[numNeurons][numWeights];
            for (int j = 0; j < numNeurons; j++) {
                for (int k = 0; k < numWeights; k++) {
                    weights[i][j][k] = parameters[ind];
                    ind++;
                }
            }
        }
        return weights;
    }

    // sets biases excluding input layer
    public void setBiases(double[][] biases) {
        if (biases.length != getNumLayers()-1) {
            throw new IllegalArgumentException("Dimension of arg is different than num of layers");
        }
        for (int i = 0; i < biases.length; i++) {
            this.layers.get(i+1).setBiases(biases[i]);
        }
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

    // sets weights excluding input layer
    public void setWeights(double[][][] weights) {
        if (weights.length != getNumLayers()-1) {
            throw new IllegalArgumentException("Dimension of arg is different than num of layers");
        }
        for (int i = 0; i < weights.length; i++) {
            this.layers.get(i+1).setWeights(weights[i]);
        }
    }

    public double[][] getValues() {
        int numLayers = getNumLayers();
        double[][] values = new double[numLayers][];
        for (int i = 0; i < numLayers; i++) {
            values[i] = this.layers.get(i).getValues();
        }
        return values;
    }

    public double[] getOutputs() {
        return this.layers.get(getNumLayers()-1).getValues();
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

    public static int getNumParameters(int[] architecture) {
        int numParams = 0;
        for (int i = 1; i < architecture.length; i++) {
            numParams += architecture[i] + architecture[i]*architecture[i-1];
        }
        return numParams;
    }

    // get parameters of the network flattened into an array.
    // Ordering: Biases -> Weights, input to output, top to bottom.
    // Input layer is ignored.
    public double[] getParameters() {
        int numParams = this.getNumParameters();
        double[] params = new double[numParams];
        double[][] biases = this.getBiases();
        double[][][] weights = this.getWeights();
        int ind = 0;
        
        for (int i = 0; i < biases.length; i++) {
            for (int j = 0; j < biases[i].length; j++) {
                params[ind] = biases[i][j];
                ind++;
            }
        }

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    params[ind] = weights[i][j][k];
                    ind++;
                }
            }
        }
        
        return params;
    }

    public void setParameters(double[] parameters) {
        setBiases(getBiasesFromParameters(parameters));
        setWeights(getWeightsFromParameters(parameters));
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

    public void saveToFile(String filename, boolean overwrite) {
        
        try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("Model saved: " + file.getName());
            } else {
                System.out.print("File " + "\"" + filename + "\"" + " already exists, ");
                if (overwrite) {
                    System.out.println("overwriting..");
                } else {
                    System.out.println("aborting.");
                    return;
                }
            }

            /*
             * Structure of save file:
             * NUM_LAYERS - int (4 bytes)
             * ARCHITECTURE - [int] (NUM_LAYERS*4 bytes)
             * BIASES/WEIGHTS - [double] (numParams*8 bytes)
             */

            FileOutputStream fos = new FileOutputStream(filename);
            
            int[] architecture = this.getArchitecture();
            double[] parameters = this.getParameters();

            int bufSize = 4 + architecture.length * 4 + parameters.length * 8;
            byte[] buf = new byte[bufSize];
            int bufInd = 0;

            ByteBuffer.wrap(buf).putInt(architecture.length);
            bufInd += 4;

            for (int i = 0; i < architecture.length; i++) {
                ByteBuffer.wrap(buf, bufInd, 4).putInt(architecture[i]);
                bufInd += 4;
            }

            for (int i = 0; i < parameters.length; i++) {
                ByteBuffer.wrap(buf, bufInd, 8).putDouble(parameters[i]);
                bufInd += 8;
            }
            
            fos.write(buf);
            fos.close();
        } catch (IOException e) {
            System.out.println("Error occured while creating model save file.");
            e.printStackTrace();
        }
    }

    public static NeuralNetwork loadFromFile(String filename) throws IOException{
        try {
            FileInputStream fis = new FileInputStream(filename);
            byte[] buf = fis.readAllBytes();
            int bufInd = 0;
            
            NeuralNetwork network;
            int numLayers;
            int[] architecture;
            int numParams;
            double[] parameters;
            
            numLayers = ByteBuffer.wrap(buf, bufInd, 4).getInt();
            bufInd += 4;
            
            architecture = new int[numLayers];
            for (int i = 0; i < architecture.length; i++) {
                architecture[i] = ByteBuffer.wrap(buf, bufInd, 4).getInt();
                bufInd += 4;
            }

            numParams = getNumParameters(architecture);
            parameters = new double[numParams];
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = ByteBuffer.wrap(buf, bufInd, 8).getDouble();
                bufInd += 8;
            }

            network = new NeuralNetwork(architecture);
            network.setParameters(parameters);

            fis.close();
            
            return network;
        } catch (IOException e) {
            System.out.println("Error occured while loading model save file.");
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        NeuralNetwork network = new NeuralNetwork(new int[]{1, 5, 3});
        network.randomizeBiases();
        network.randomizeWeights();
        network.evaluate(new double[]{0.5});
        network.printNetworkProperties();
        network.printNetworkBiases();
        network.printNetworkWeights();
        network.printNetworkValues();;

    }
}
