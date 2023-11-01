public class Population {
    private Agent[] agents;

    public Population(int populationSize, int[] networkArchitecture) {
        this.agents = new Agent[populationSize];
        for (int i = 0; i < populationSize; i++) {
            agents[i] = new Agent(networkArchitecture);
        }
    }
    
    // execute one iteration of Genetic Algorithm
    // perform crossover and mutation on current population to produce a new generation
    public void evolve() {

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

    public static void main(String[] args) {
        int[] arch = new int[]{2,2,2};
        Agent a1 = new Agent(arch);
        Agent a2 = new Agent(arch);
        a1.getChromosome().printChromosome();
        a2.getChromosome().printChromosome();

        Population.mutate(a1);
        Population.mutate(a2);
        
        a1.getChromosome().printChromosome();
        a2.getChromosome().printChromosome();
    }

}
