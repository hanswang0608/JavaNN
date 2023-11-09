package JavaNN.Training;
import JavaNN.Network.NeuralNetwork;
import JavaNN.Util.Config;
import JavaNN.Util.Utils;

public class Chromosome {
    private double[] genes;

    private static final double GENE_LOWER_LIMIT = Config.GENE_LOWER_LIMIT;
    private static final double GENE_UPPER_LIMIT = Config.GENE_UPPER_LIMIT;
    private static final double WHOLE_ARITHMETIC_CROSSOVER_COEFFICIENT = Config.WHOLE_ARITHMETIC_CROSSOVER_COEFFICIENT;
    
    public Chromosome(int numGenes) {
        this.genes = new double[numGenes];
    }

    public Chromosome(double[] genes) {
        this.genes = genes;
    }

    public Chromosome(NeuralNetwork network) {
        this.genes = network.getParameters();
    }

    // return two children by crossover of two parents
    public static void crossover(Chromosome a, Chromosome b) {
        uniformCrossover(a, b);
    }

    public static void mutate(Chromosome c, double probability) {
        c.uniformMutation(probability);
    }
    
    // randomize entire chromosome within range
    public void randomize() {
        for (int i = 0; i < this.genes.length; i++) {
            this.genes[i] = Utils.randDouble(GENE_LOWER_LIMIT, GENE_UPPER_LIMIT);
        }
    }

    // produce two children through uniform crossover
    public static void uniformCrossover(Chromosome p1, Chromosome p2) {
        double[] g1 = p1.getGenes();
        double[] g2 = p2.getGenes();

        if (g1.length != g2.length) {
            throw new IllegalArgumentException("Cannot perform crossover on chromosomes of different length");
        }

        for (int i = 0; i < g1.length; i++) {
            if (Utils.randBool(0.5)) {
                double temp = g1[i];
                g1[i] = g2[i];
                g2[i] = temp;
            }
        }

        p1.setGenes(g1);
        p2.setGenes(g2);
    }

    public static void wholeArithmeticCrossover(Chromosome p1, Chromosome p2) {
        double[] g1 = p1.getGenes();
        double[] g2 = p2.getGenes();
        
        if (g1.length != g2.length) {
            throw new IllegalArgumentException("Cannot perform crossover on chromosomes of different length");
        }

        for (int i = 0; i < g1.length; i++) {
            g1[i] = WHOLE_ARITHMETIC_CROSSOVER_COEFFICIENT*g1[i]+(1-WHOLE_ARITHMETIC_CROSSOVER_COEFFICIENT)*g2[i];
            g2[i] = WHOLE_ARITHMETIC_CROSSOVER_COEFFICIENT*g2[i]+(1-WHOLE_ARITHMETIC_CROSSOVER_COEFFICIENT)*g1[i];
        }
        
        p1.setGenes(g1);
        p2.setGenes(g2);
    }

    // mutate each gene independently by probability, sampled from uniform distribution within param limits
    public void uniformMutation(double probability) {
        for (int i = 0; i < this.genes.length; i++) {
            if (Utils.randBool(probability)) {
                this.genes[i] = Utils.randDouble(GENE_LOWER_LIMIT, GENE_UPPER_LIMIT);
            }
        }
    }

    // add random noise to every gene
    public void gaussianMutation(double probability) {
        double range = (GENE_UPPER_LIMIT-GENE_LOWER_LIMIT)/4;
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

    public String toString() {
        String s = "[";
        for (int i = 0; i < this.genes.length; i++) {
            s += Utils.prettifyDouble(this.genes[i]);
            if (i < this.genes.length-1) s += "|";
        }
        s += "]";
        return s;
    }

    public static double clampToLimits(double x) {
        if (x > GENE_UPPER_LIMIT) x = GENE_UPPER_LIMIT;
        if (x < GENE_LOWER_LIMIT) x = GENE_LOWER_LIMIT;
        return x;
    }

    public static double getDistanceToLimit(double x) {
        return Math.min(Math.abs(GENE_UPPER_LIMIT-x), Math.abs(GENE_LOWER_LIMIT-x));
    }

    public static void main(String[] args) {
        Chromosome p1 = new Chromosome(12);
        Chromosome p2 = new Chromosome(12);
        p1.randomize();
        p2.randomize();
        System.out.println(p1);
        System.out.println(p2);
        Chromosome.uniformCrossover(p1, p2);
        p1.gaussianMutation(1);
        p2.gaussianMutation(1);
        System.out.println(p1);
        System.out.println(p2);
    }
}
