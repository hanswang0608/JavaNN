import java.util.Random;

public class Chromosome {
    private double[] genes;

    public Chromosome(int numGenes) {
        this.genes = new double[numGenes];
    }

    public Chromosome(double[] genes) {
        this.genes = genes;
    }

    public Chromosome(NeuralNetwork network) {
        this.genes = new double[network.getNumParameters()];
        
        setGenesFromNetwork(network);
    }

    // add random noise to every gene
    public void mutate(double mutationProbability) {
        Random rand = new Random();
        for (int i = 0; i < this.genes.length; i++) {
            this.genes[i] += rand.nextDouble();
        }
    }

    public double[] getGenes() {
        return this.genes;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
    }

    public void setGenesFromNetwork(NeuralNetwork network) {
        if (this.genes.length != network.getNumParameters()) {
            throw new IllegalArgumentException("Dimension of network does not match length of chromosome");
        }
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
        NeuralNetwork network = new NeuralNetwork(new int[]{1, 5, 3});
        network.randomizeBiases();
        network.randomizeWeights();
        network.printNetworkBiases();
        network.printNetworkWeights();

        Chromosome chromosome = new Chromosome(network);
        chromosome.genes[0] = 1000.0;
        chromosome.printChromosome();

        network.setParameters(chromosome);
        network.printNetworkProperties();
        network.printNetworkBiases();
        network.printNetworkWeights();
        network.printNetworkValues();
    }

}
