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


}
