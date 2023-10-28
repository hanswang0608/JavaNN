import java.util.ArrayList;

public class Layer {
    private ArrayList<Double> biases;
    private ArrayList<ArrayList<Double>> weights;
    private ArrayList<Double> values;
    private ActivationFunction func;

    public Layer(int size, int numWeights, ActivationFunction func) {
        this.biases = new ArrayList<Double>(size);
        this.values= new ArrayList<Double>(size);
        this.weights = new ArrayList<ArrayList<Double>>(size);
        this.func = func;

        for (int i = 0; i < size; i++) {
            this.biases.add(i, 0.0);
        }
        for (int i = 0; i < size; i++) {
            this.values.add(i, 0.0);
        }
        for (int i = 0; i < size; i++) {
            this.weights.add(i, new ArrayList<Double>(numWeights));
            for (int j = 0; j < numWeights; j++) {
                this.weights.get(i).add(j, 0.0);
            }
        }
    }

    public Layer(int size, int numWeights, double[] biases, double[][] weights, ActivationFunction func) {
        this.biases = new ArrayList<Double>(size);
        this.values= new ArrayList<Double>(size);
        this.weights = new ArrayList<ArrayList<Double>>(size);
        this.func = func;

        for (int i = 0; i < size; i++) {
            this.biases.add(i, biases[i]);
        }
        for (int i = 0; i < size; i++) {
            this.values.add(i, 0.0);
        }
        for (int i = 0; i < size; i++) {
            this.weights.add(i, new ArrayList<Double>(numWeights));
            for (int j = 0; j < numWeights; j++) {
                this.weights.get(i).add(j, weights[i][j]);
            }
        }
    }

    public int getSize() {
        return this.biases.size();
    }

    public int getNumWeights() {
        return this.weights.get(0).size();
    }

    public double[] getBiases() {
        int numNeurons = getSize();
        double[] ret = new double[numNeurons];
        for (int i = 0; i < numNeurons; i++) {
            ret[i] = this.biases.get(i).doubleValue();
        }
        return ret;
    }

    public void setBiases(double[] biases) {
        int numNeurons = getSize();
        if (biases.length != numNeurons) {
            throw new IllegalArgumentException("Dimension of arg is different than biases");
        }
        for (int i = 0; i < numNeurons; i++) {
            this.biases.set(i, biases[i]);
        }
    }

    public double[] getValues() {
        int numNeurons = getSize();
        double[] ret = new double[numNeurons];
        for (int i = 0; i < numNeurons; i++) {
            ret[i] = this.values.get(i).doubleValue();
        }
        return ret;
    }

    public double[][] getWeights() {
        int numNeurons = getSize();
        int numWeights = getNumWeights();
        double[][] ret = new double[numNeurons][numWeights];
        for (int i = 0; i < numNeurons; i++) {
            for (int j = 0; j < numWeights; j++) {
                ret[i][j] = this.weights.get(i).get(j).doubleValue();
            }
        }
        return ret;
    }

    public void setWeights(double[][] weights) {
        int numNeurons = getSize();
        int numWeights = getNumWeights();
        if (weights.length != numNeurons || weights[0].length != numWeights) {
            throw new IllegalArgumentException("Dimension of arg is different than weights");
        }
        for (int i = 0; i < numNeurons; i++) {
            for (int j = 0; j < numWeights; j++) {
                this.weights.get(i).set(j, weights[i][j]);
            }
        }
    }

    private double activate(double x) {
        switch (this.func) {
            case SIGMOID: return Utils.sigmoid(x);
            case RELU: return Utils.ReLU(x);
            default: return Utils.sigmoid(x);
        }
    }

    public double[] evaluate(double[] inputs) {
        double[] output = new double[getSize()];
        int numWeights = getNumWeights();
        for (int i = 0; i < output.length; i++) {
            double sum = 0;
            for (int j = 0; j < numWeights; j++) {
                sum += inputs[j] * this.weights.get(i).get(j);
            }
            output[i] = activate(sum + this.biases.get(i));
            this.values.set(i, output[i]);
        }
        return output;
    }

    public void printValues() {
        int numNeurons = getSize();
        String s = "";
        for (int i = 0; i < numNeurons; i++) {
            s += this.values.get(i).toString() + ' ';
        }
        System.out.println(s);
    }

    public enum ActivationFunction {
        SIGMOID,
        RELU
    }
}