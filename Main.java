public class Main {
    public static void main(String[] args) {
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(1, 2, 5, 1);
        System.out.println(mlp.printBiases());
        System.out.println(mlp.printWeights());

        double[] input = new double[]{1};
        double[] output = mlp.run(input);
        for (int i = 0; i < output.length; i++) {
            System.out.println(output[i]);
        }
    }
}