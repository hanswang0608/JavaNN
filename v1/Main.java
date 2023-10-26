public class Main {
    public static void main(String[] args) {
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(1, 1, 2, 1, true, true, Utils.ActivationFunction.RELU);
        System.out.println(mlp.printBiases());
        System.out.println(mlp.printWeights());

        double[] input = new double[]{1};
        double[] output = mlp.run(input);
        for (int i = 0; i < output.length; i++) {
            System.out.println(output[i]);
        }

    }
}