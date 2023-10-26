public class Main {
    public static void main(String[] args) {
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(5, 2, 5, 5, true, true);
        System.out.println(mlp.printBiases());
        System.out.println(mlp.printWeights());

        double[] input = new double[]{0,0,0,0,0};
        double[] output = mlp.run(input);
        for (int i = 0; i < output.length; i++) {
            System.out.println(output[i]);
        }

    }
}