public class Agent {
    private Chromosome chromosome;
    private NeuralNetwork network;
    private double fitness;

    public Agent(int[] architecture) {
        this.fitness = 0;
        this.network = new NeuralNetwork(architecture);
        this.chromosome = new Chromosome(network);
        this.chromosome.randomize();
        this.network.setParameters(chromosome);
    }

    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double[] execute(double[] inputs) {
        return this.network.evaluate(inputs);
    }
}
