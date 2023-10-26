import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiLayerPerceptron {
    public int numLayers;
    public List<List<double[]>> weights;
    public List<List<Double>> biases;
    public Utils.ActivationFunction af;
    
    public MultiLayerPerceptron(int inputSize, int numHiddenLayers, int hiddenLayerSize, int outputSize, boolean randomizeWeights, boolean randomizeBiases, Utils.ActivationFunction af) {
        this.af = af;
        numLayers = numHiddenLayers + 1;
        weights = new ArrayList<List<double[]>>(numLayers);
        biases = new ArrayList<List<Double>>(numLayers);
        
        Random rand = new Random();

        // init hidden layers
        for (int i = 0; i < numHiddenLayers; i++) {
            biases.add(i, new ArrayList<Double>(hiddenLayerSize));
            weights.add(i, new ArrayList<double[]>(hiddenLayerSize));
            for (int j = 0; j < hiddenLayerSize; j++) {
                double b = 0;
                if (randomizeBiases) {
                    b = (rand.nextDouble()-0.5)*10;
                }
                biases.get(i).add(j, b);
                int numWeights = i == 0 ? inputSize : biases.get(i-1).size();
                weights.get(i).add(j, new double[numWeights]);
                double[] w = weights.get(i).get(j);
                for (int k = 0; k < w.length; k++) {
                    double r = 1;
                    if (randomizeWeights) {
                        r = (rand.nextDouble()-0.5) * 2 * 10;
                    }
                    w[k] = r;
                }
            }
        }

        // init output layer
        biases.add(numLayers-1, new ArrayList<Double>(outputSize));
        weights.add(numLayers-1, new ArrayList<double[]>(outputSize));
        for (int i = 0; i < outputSize; i++) {
            double b = 0;
            if (randomizeBiases) {
                b = (rand.nextDouble()-0.5)*10;
            }
            biases.get(numLayers-1).add(i, b);
            int numWeights = numLayers == 1 ? inputSize : biases.get(numLayers-2).size();
            weights.get(numLayers-1).add(i, new double[numWeights]);
            double[] w = weights.get(numLayers-1).get(i);
            for (int j = 0; j < w.length; j++) {
                double r = 1;
                if (randomizeWeights) {
                    r = (rand.nextDouble()-0.5) * 2 * 10;
                }
                w[j] = r;
            }
        }
    }

    public double[] run(double[] inputs) {
        double[] nextInputs = inputs.clone();
        for (int i = 0; i < biases.size(); i++) {
            nextInputs = runLayer(nextInputs, i);
        }
        return nextInputs;
    }

    public double[] runLayer(double[] inputs, int layer) {

        double[] output = new double[biases.get(layer).size()];
        // calculate output for each perceptron of a layer
        for (int i = 0; i < output.length; i++) {
            float sum = 0;
            double[] w = weights.get(layer).get(i);
            // calculate weight sum of inputs for each perceptron
            for (int j = 0; j < w.length; j++) {
                sum += inputs[j] * w[j];
            }
            // calcuate output through activation function
            output[i] = evaluate(biases.get(layer).get(i) + sum);
        }
        return output;
    }

    public double evaluate(double x) {
        switch (af) {
            case SIGMOID: return Utils.sigmoid(x);
            case RELU: return Utils.ReLU(x);
            default: return Utils.sigmoid(x);
        }
    }

    public String printBiases() {
        String output = "";
        for (int i = 0; i < biases.size(); i++) {
            List<Double> layer = biases.get(i);
            for (int j = 0; j < layer.size(); j++) {
                output += layer.get(j) + " ";
            }
            output += "\n";
        }
        return output;
    }

    public String printWeights() {
        String output = "";
        for (int i = 0; i < weights.size(); i++) {
            List<double[]> layer = weights.get(i);
            for (int j = 0; j < layer.size(); j++) {
                for (int k = 0; k < layer.get(j).length; k++) {
                    output += layer.get(j)[k] + " ";
                }
                output += " | ";
            }
            output += "\n";
        }
        return output;
    }
}