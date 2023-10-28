import java.util.ArrayList;

public class NeuralNetwork {
    private ArrayList<Layer> layers;
    private int[] architecture;

    public NeuralNetwork(int[] architecture) {
        this.architecture = architecture;
        this.layers = new ArrayList<Layer>(architecture.length-1);
        for (int i = 1; i < architecture.length; i++) {
            this.layers.add(i-1, new Layer(architecture[i], architecture[i-1]));
        }
    }
}
