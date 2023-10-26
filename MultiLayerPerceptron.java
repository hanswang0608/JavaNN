import java.util.ArrayList;
import java.util.List;

public class MultiLayerPerceptron {
    public int numLayers;
    public List<List<double[]>> weights;
    public List<List<Double>> biases;
    
    public MultiLayerPerceptron(int inputSize, int numHiddenLayers, int hiddenLayerSize, int outputSize) {
        numLayers = numHiddenLayers + 2;
        weights = new ArrayList<List<double[]>>(numLayers-1);
        biases = new ArrayList<List<Double>>(numLayers);
        
        // init input layer
        biases.add(0, new ArrayList<Double>(inputSize));
        for (int i = 0; i < inputSize; i++) {
            biases.get(0).add(i, 0.0);
        }


        // init hidden layers
        for (int i = 1; i < numHiddenLayers+1; i++) {
            biases.add(i, new ArrayList<Double>(hiddenLayerSize));
            weights.add(i-1, new ArrayList<double[]>(hiddenLayerSize));
            for (int j = 0; j < hiddenLayerSize; j++) {
                biases.get(i).add(j, 0.0);
                weights.get(i-1).add(j, new double[biases.get(i-1).size()]);
                double[] w = weights.get(i-1).get(j);
                for (int k = 0; k < w.length; k++) {
                    w[k] = 1;
                }
            }
        }

        // init output layer
        biases.add(numLayers-1, new ArrayList<Double>(outputSize));
        weights.add(numLayers-2, new ArrayList<double[]>(outputSize));
        for (int i = 0; i < outputSize; i++) {
            biases.get(numLayers-1).add(i, 0.0);
            weights.get(numLayers-2).add(i, new double[biases.get(numLayers-2).size()]);
            double[] w = weights.get(numLayers-2).get(i);
            for (int j = 0; j < w.length; j++) {
                w[j] = 1;
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
        if (layer == 0) return inputs;

        double[] output = new double[biases.get(layer).size()];
        // calculate output for each perceptron of a layer
        for (int i = 0; i < output.length; i++) {
            float sum = 0;
            double[] w = weights.get(layer-1).get(i);
            // calculate weight sum of inputs for each perceptron
            for (int j = 0; j < w.length; j++) {
                sum += inputs[j] * w[j];
            }
            // calcuate output through activation function
            output[i] = Utils.sigmoid(biases.get(layer).get(i) + sum);
        }
        return output;
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