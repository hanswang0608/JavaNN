import java.util.Arrays;

public class Population {
    private Agent[] agents;
    private int generation;
    private double averageFitness;
    

    public Population(int populationSize, int[] networkArchitecture) {
        if (populationSize % 2 != 0) {
            throw new IllegalArgumentException("Population must be an even number");
        }
        this.agents = new Agent[populationSize];
        this.generation = 0;
        this.averageFitness = 0;
        for (int i = 0; i < populationSize; i++) {
            agents[i] = new Agent(networkArchitecture);
        }
    }
    
    // execute one iteration of Genetic Algorithm
    // perform crossover and mutation on current population to produce a new generation
    public void evolve() {
        
    }

    public void sortAgentsByFitness() {
        Arrays.sort(agents);
    }
    
    public static void crossover(Agent a1, Agent a2) {
        Chromosome c1 = a1.getChromosome();
        Chromosome c2 = a2.getChromosome();
        Chromosome.crossover(c1, c2);
    }

    public static void mutate(Agent agent) {
        Chromosome chromosome = agent.getChromosome();
        chromosome.mutate();
    }

    public void printAgents() {
        for (Agent agent : agents) {
            System.out.println(agent.getFitness());
        }
    }

    public static void main(String[] args) {
        int[] arch = new int[]{2,2,2};
        double[] inputs = new double[]{1,1};
        Population p = new Population(2, arch);
        for (Agent a : p.agents) {
            a.act(inputs);
            a.updateFitness();
        }
        p.printAgents();
        p.sortAgentsByFitness();
        p.printAgents();
    }

}
