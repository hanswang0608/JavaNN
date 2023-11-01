import java.util.Arrays;

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

    public double[] act(double[] inputs) {
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

    public double[] getNetworkOutputs() {
        return this.network.getOutputs();
    }

    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    
    // update this agent's fitness based on its neural network outputs
    public void updateFitness() {
        setFitness(calculateFitness(this.network.getOutputs()));
    }

    // explicitly provide it some results to evaluate as fitness
    public void updateFitness(double[] results) {
        setFitness(calculateFitness(results));
    }

    // return a fitness of the agent based on results
    public double calculateFitness(double[] results) {
        double sum = 0;
        for (int i = 0; i < results.length; i++) {
            sum += Math.pow(results[i]-Math.PI/10, 2);
        }
        return 1-sum/results.length;
    }

    public static void main(String[] args) {
        Agent agent = new Agent(new int[]{2,2,2});
        agent.act(new double[]{0,0});
        agent.updateFitness();
        double[] a = agent.getNetworkOutputs();
        for (int i = 0; i < 2; i++) {
            System.out.println(a[i]);
        }
        System.out.println(agent.getFitness());
    }
}
