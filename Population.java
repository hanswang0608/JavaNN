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

    public void selectByRank() {
        sortAgentsByFitness();
        int populationSize = getPopulationSize();
        double[] shares = new double[populationSize];
        double totalShares = 1;
        shares[populationSize-1] = 1;
        for (int i = populationSize-2; i >= 0; i--) {
            shares[i] = 2*shares[i+1];
            totalShares += shares[i];
        }
        

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

    public int getPopulationSize() {
        return this.agents.length;
    }

    public Agent[] getAgents() {
        return this.agents;
    }

    public void printAgents() {
        System.out.println("----------------------------------------");
        System.out.println("Population:");
        for (Agent agent : agents) {
            System.out.println(agent);
        }
    }

    public static void main(String[] args) {
        int[] arch = new int[]{2,2,2};
        double[] inputs = new double[]{1,1};
        Population p = new Population(4, arch);
        for (Agent a : p.getAgents()) {
            a.act(inputs);
            a.updateFitness();
        }
        p.selectByRank();
        
    }

}
