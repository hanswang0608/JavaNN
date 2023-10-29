import java.util.ArrayList;

public class Layer {
    private ArrayList<Double> biases;
    private ArrayList<ArrayList<Double>> weights;
    private ArrayList<Double> values;
    private ActivationFunction.FuncTypes func;

    public Layer(int numNeurons, int numWeights, ActivationFunction.FuncTypes func) {
        this.biases = new ArrayList<Double>(numNeurons);
        this.values= new ArrayList<Double>(numNeurons);
        this.weights = new ArrayList<ArrayList<Double>>(numNeurons);
        this.func = func;

        for (int i = 0; i < numNeurons; i++) {
            this.biases.add(i, 0.0);
        }
        for (int i = 0; i < numNeurons; i++) {
            this.values.add(i, 0.0);
        }
        for (int i = 0; i < numNeurons; i++) {
            this.weights.add(i, new ArrayList<Double>(numWeights));
            for (int j = 0; j < numWeights; j++) {
                this.weights.get(i).add(j, 0.0);
            }
        }
    }

    public Layer(int numNeurons, int numWeights, double[] biases, double[][] weights, ActivationFunction.FuncTypes func) {
        this.biases = new ArrayList<Double>(numNeurons);
        this.values= new ArrayList<Double>(numNeurons);
        this.weights = new ArrayList<ArrayList<Double>>(numNeurons);
        this.func = func;

        for (int i = 0; i < numNeurons; i++) {
            this.biases.add(i, biases[i]);
        }
        for (int i = 0; i < numNeurons; i++) {
            this.values.add(i, 0.0);
        }
        for (int i = 0; i < numNeurons; i++) {
            this.weights.add(i, new ArrayList<Double>(numWeights));
            for (int j = 0; j < numWeights; j++) {
                this.weights.get(i).add(j, weights[i][j]);
            }
        }
    }

    public double[] evaluate(double[] inputs) {
        double[] output = new double[getNumNeurons()];
        int numWeights = getNumWeights();
        for (int i = 0; i < output.length; i++) {
            double sum = 0;
            for (int j = 0; j < numWeights; j++) {
                sum += inputs[j] * this.weights.get(i).get(j);
            }
            output[i] = ActivationFunction.activate(sum + this.biases.get(i), this.func);
            this.values.set(i, output[i]);
        }
        return output;
    }

    public int getNumNeurons() {
        return this.biases.size();
    }

    public int getNumWeights() {
        return this.weights.get(0).size();
    }

    public double[] getBiases() {
        int numNeurons = getNumNeurons();
        double[] ret = new double[numNeurons];
        for (int i = 0; i < numNeurons; i++) {
            ret[i] = this.biases.get(i).doubleValue();
        }
        return ret;
    }

    public void setBiases(double[] biases) {
        int numNeurons = getNumNeurons();
        if (biases.length != numNeurons) {
            throw new IllegalArgumentException("Dimension of arg is different than biases");
        }
        for (int i = 0; i < numNeurons; i++) {
            this.biases.set(i, biases[i]);
        }
    }

    public double[] getValues() {
        int numNeurons = getNumNeurons();
        double[] ret = new double[numNeurons];
        for (int i = 0; i < numNeurons; i++) {
            ret[i] = this.values.get(i).doubleValue();
        }
        return ret;
    }

    public void setValues(double[] values) {
        int numNeurons = getNumNeurons();
        if (values.length != numNeurons) {
            throw new IllegalArgumentException("Dimension of arg is different than values");
        }
        for (int i = 0; i < numNeurons; i++) {
            this.values.set(i, values[i]);
        }
    }

    public double[][] getWeights() {
        int numNeurons = getNumNeurons();
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
        int numNeurons = getNumNeurons();
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

    public ActivationFunction.FuncTypes getActivationFunction() {
        return this.func;
    }

    public void setActivationFunction(ActivationFunction.FuncTypes func) {
        this.func = func;
    }

    public void printValues() {
        int numNeurons = getNumNeurons();
        String s = "";
        for (int i = 0; i < numNeurons; i++) {
            s += this.values.get(i).toString() + ' ';
        }
        System.out.println(s);
    }

    public static void main(String[] args) {
        double[] b = new double[]{1.0, 2.0};
        double[][] w = new double[2][];
        double[] inputs = new double[]{5, 10};
        w[0] = new double[]{3.0, 4.0}; 
        w[1] = new double[]{5.0, 6.0}; 
        Layer l = new Layer(2, 2, b, w, ActivationFunction.FuncTypes.RELU);
        b = l.getBiases();
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        w = l.getWeights();
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                System.out.println(w[i][j]);
            }
        }
        b = new double[]{1.1, 2.2};
        l.setBiases(b);
        b = l.getBiases();
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        w[0] = new double[]{3.3, 4.4}; 
        w[1] = new double[]{5.5, 6.6}; 
        l.setWeights(w);
        w = l.getWeights();
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                System.out.println(w[i][j]);
            }
        }
        l.evaluate(inputs);
        l.printValues();
    }
}