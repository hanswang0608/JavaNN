import java.util.ArrayList;

public class Layer {
    private ArrayList<Double> biases;
    private ArrayList<ArrayList<Double>> weights;

    public Layer(int numNeurons, int numWeights) {
        this.biases = new ArrayList<Double>(numNeurons);
        this.weights = new ArrayList<ArrayList<Double>>(numNeurons);

        for (int i = 0; i < numNeurons; i++) {
            this.biases.add(i, 0.0);
        }
        for (int i = 0; i < numNeurons; i++) {
            this.weights.add(i, new ArrayList<Double>(numWeights));
            for (int j = 0; j < numWeights; j++) {
                this.weights.get(i).add(j, 0.0);
            }
        }
    }

    public Layer(int numNeurons, int numWeights, double[] biases, double[][] weights) {
        this.biases = new ArrayList<Double>(numNeurons);
        this.weights = new ArrayList<ArrayList<Double>>(numNeurons);

        for (int i = 0; i < numNeurons; i++) {
            this.biases.add(i, biases[i]);
        }
        for (int i = 0; i < numNeurons; i++) {
            this.weights.add(i, new ArrayList<Double>(numWeights));
            for (int j = 0; j < numWeights; j++) {
                this.weights.get(i).add(j, weights[i][j]);
            }
        }
    }

    public int getSize() {
        return this.biases.size();
    }

    public Double[] getBiases() {
        return this.biases.toArray(Double[]::new);
    }

    public Double[][] getWeights() {
        Double[][] ret = new Double[this.biases.size()][this.weights.get(0).size()];
        for (int i = 0; i < this.biases.size(); i++) {
            ret[i] = this.weights.get(i).toArray(Double[]::new);
        }
        return ret;
    }
}