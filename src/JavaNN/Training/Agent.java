package JavaNN.Training;
import JavaNN.Network.NeuralNetwork;
import JavaNN.Util.Utils;

public class Agent implements Comparable<Agent>{
    private Chromosome chromosome;
    private NeuralNetwork network;
    private double fitness;
    private int id;

    private static int agentsCount = 0;

    public Agent(int[] architecture) {
        this.fitness = 0;
        this.chromosome = new Chromosome(NeuralNetwork.getNumParameters(architecture));
        this.chromosome.randomize();
        this.network = new NeuralNetwork(architecture);
        updateNetwork();

        this.id = agentsCount;
        agentsCount++;
    }

    public Agent(NeuralNetwork network) {
        this.fitness = 0;
        this.network = network;
        this.chromosome = new Chromosome(network);

        this.id = agentsCount;
        agentsCount++;
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
        this.network.setParameters(chromosome.getGenes());
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
        return dummyFitnessFunction(results);
    }

    private double dummyFitnessFunction(double[] input) {
        int sum = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] > 0.99) {
                sum += 10;
            }
        }
        return sum;
    }

    // dummy fitness function that returns fitness as RMS error of PI
    private double piRMS(double[] x) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += Math.pow(x[i]-Math.PI/10, 2);
        }
        return 1-sum/x.length;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public int compareTo(Agent other) {
        return Double.compare(other.getFitness(), this.fitness);
    }

    public Agent copy() {
        Agent clone = new Agent(this.network.getArchitecture());
        clone.chromosome.setGenes(this.chromosome.getGenes().clone());
        return clone;
    }

    public String toString() {
        return "Agent " + this.id + ": Fitness=" + Utils.formatDouble(this.fitness, 3); 
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
