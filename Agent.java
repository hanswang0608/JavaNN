public class Agent {
    private Chromosome chromosome;
    private NeuralNetwork network;
    private double fitness;

    public Agent(int[] architecture) {
        this.fitness = 0;
        this.chromosome = new Chromosome(NeuralNetwork.getNumParameters(architecture));
        this.chromosome.randomize();
        this.network = new NeuralNetwork(architecture);
        updateNetwork();
    }

    public double[] execute(double[] inputs) {
        return this.network.evaluate(inputs);
    }

    public Chromosome getChromosome() {
        return this.chromosome;
    }

    public void setChromosome(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public NeuralNetwork getNetwork() {
        return this.network;
    }

    public void updateNetwork() {
        this.network.setParameters(chromosome);
    }

    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }


}
