public class Chromosome {
    private double[] genes; 

    public Chromosome(NeuralNetwork network) {
        this.genes = new double[network.getNumParameters()];
        
        double[][] biases = network.getBiases();
        double[][][] weights = network.getWeights();
        int ind = 0;
        
        for (int i = 0; i < biases.length; i++) {
            for (int j = 0; j < biases[i].length; j++) {
                this.genes[ind] = biases[i][j];
                ind++;
            }
        }

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    this.genes[ind] = weights[i][j][k];
                    ind++;
                }
            }
        }
    }

    public void printChromosome() {
        String s = "[";
        for (int i = 0; i < this.genes.length; i++) {
            s += Utils.formatDouble(this.genes[i]);
            if (i < this.genes.length-1) s += "|";
        }
        s += "]";
        System.out.println("----------------------------------------");
        System.out.println("Chromosome:");
        System.out.println(s);
    }

    public static void main(String[] args) {
        NeuralNetwork network = new NeuralNetwork(new int[]{10, 6, 4, 1});
        network.randomizeBiases();
        network.randomizeWeights();
        network.printNetworkProperties();
        network.printNetworkBiases();
        network.printNetworkWeights();

        Chromosome chromosome = new Chromosome(network);
        chromosome.printChromosome();
    }

}
