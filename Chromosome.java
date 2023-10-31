import java.util.Arrays;
import java.util.Random;

public class Chromosome {
    private double[] genes;
    private double fitness;
    private int id;

    private static int chromosomeCount;
    
    private static final double PARAM_LOWER_LIMIT = -10;
    private static final double PARAM_UPPER_LIMIT = 10;
    private static final double GENE_MUTATION_PROBABILITY = 0.1;
    
    public Chromosome(int numGenes) {
        this.genes = new double[numGenes];
        this.id = chromosomeCount;
        chromosomeCount++;
    }

    public Chromosome(double[] genes) {
        this.genes = genes;
        this.id = chromosomeCount;
        chromosomeCount++;
    }

    public Chromosome(NeuralNetwork network) {
        this.genes = new double[network.getNumParameters()];
        setGenesFromNetwork(network);
        this.id = chromosomeCount;
        chromosomeCount++;
    }

    // return two children by crossover of two parents
    public static Chromosome[] crossover(Chromosome a, Chromosome b) {
        return uniformCrossover(a, b);
    }

    public void mutate() {
        uniformMutation(GENE_MUTATION_PROBABILITY);
    }
    
    // randomize entire chromosome within range
    public void randomize() {
        for (int i = 0; i < this.genes.length; i++) {
            this.genes[i] = Utils.randDouble(PARAM_LOWER_LIMIT, PARAM_UPPER_LIMIT);
        }
    }

    // produce two children through uniform crossover
    public static Chromosome[] uniformCrossover(Chromosome p1, Chromosome p2) {
        Chromosome[] children = new Chromosome[2];
        double[] g1 = p1.getGenes();
        double[] g2 = p2.getGenes();
        double[] c1 = new double[g1.length];
        double[] c2 = new double[g2.length];

        for (int i = 0; i < g1.length; i++) {
            if (Utils.randBool(0.5)) {
                c1[i] = g1[i];
                c2[i] = g2[i];
            } else {
                c1[i] = g2[i];
                c2[i] = g1[i];
            }
        }

        children[0] = new Chromosome(c1);
        children[1] = new Chromosome(c2);
        return children;
    }

    // mutate each gene independently by probability, sampled from uniform distribution within param limits
    public void uniformMutation(double probability) {
        for (int i = 0; i < this.genes.length; i++) {
            if (Utils.randBool(probability)) {
                this.genes[i] = Utils.randDouble(PARAM_LOWER_LIMIT, PARAM_UPPER_LIMIT);
            }
        }
    }

    // add random noise to every gene
    public void gaussianMutation(double probability) {
        double range = (PARAM_UPPER_LIMIT-PARAM_LOWER_LIMIT)/4;
        for (int i = 0; i < this.genes.length; i++) {
            if (Utils.randBool(probability)) {
                this.genes[i] = clampToLimits(Utils.randGaussian(this.genes[i], range));
            }
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
        System.out.print("Chromosome " + this.id + ": ");
        System.out.println(s);
    }

    public static double clampToLimits(double x) {
        if (x > PARAM_UPPER_LIMIT) x = PARAM_UPPER_LIMIT;
        if (x < PARAM_LOWER_LIMIT) x = PARAM_LOWER_LIMIT;
        return x;
    }

    public static double getDistanceToLimit(double x) {
        return Math.min(Math.abs(PARAM_UPPER_LIMIT-x), Math.abs(PARAM_LOWER_LIMIT-x));
    }

    public static void main(String[] args) {
        Chromosome p1 = new Chromosome(12);
        Chromosome p2 = new Chromosome(12);
        p1.randomize();
        p2.randomize();
        p1.printChromosome();
        p2.printChromosome();
        Chromosome[] children = Chromosome.crossover(p1, p2);
        for (Chromosome child: children) {
            child.printChromosome();
        }
        
    }
}
