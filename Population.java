import java.util.Arrays;

public class Population {
    private Agent[] agents;
    private int[] networkArchitecture;
    
    private static final double CROSSOVER_PROBABILITY = 0.8;
    private static final double MUTATION_PROBABILITY = 0.1;
    private static final double GENE_MUTATION_PROBABILITY = 1.0;

    public Population(int populationSize, int[] networkArchitecture) {
        if (populationSize % 2 != 0) {
            throw new IllegalArgumentException("Population must be an even number");
        }
        this.agents = new Agent[populationSize];
        this.networkArchitecture = networkArchitecture;
        for (int i = 0; i < populationSize; i++) {
            agents[i] = new Agent(networkArchitecture);
        }
    }

    public void selectParentsByRank(int numElites) {
        sortAgentsByFitness();
        int populationSize = getPopulationSize();
        if (numElites > populationSize) {
            throw new IllegalArgumentException("Number of elites greater than size of population");
        }
        double[] odds = new double[populationSize];
        double totalShares = populationSize;
        odds[populationSize-1] = populationSize;
        for (int i = populationSize-2; i >= 0; i--) {
            odds[i] = populationSize+odds[i+1];
            totalShares += odds[i];
        }
        for (int i = 0; i < populationSize; i++) {
            odds[i] = (double)odds[i]/totalShares;
        }
        RouletteSelector selector = new RouletteSelector(odds);
        Agent[] parents = new Agent[populationSize];
        for (int i = 0; i < numElites; i++) {
            parents[i] = this.agents[i];
        }
        for (int i = numElites; i < populationSize; i++) {
            int selected = selector.select();
            parents[i] = this.agents[selected].copy();
        }
        this.agents = parents;
    }

    public void sortAgentsByFitness() {
        Arrays.sort(agents);
    }
    
    public void crossoverPopulation() {
        int populationSize = getPopulationSize();
        for (int i = 0; i < populationSize; i+=2) {
            if (Utils.randBool(CROSSOVER_PROBABILITY)) {
                Chromosome c1 = this.agents[i].getChromosome();
                Chromosome c2 = this.agents[i+1].getChromosome();
                Chromosome.crossover(c1, c2);
            }
        }
    }

    public void mutatePopulation() {
        int populationSize = getPopulationSize();
        for (int i = 0; i < populationSize; i++) {
            if (Utils.randBool(MUTATION_PROBABILITY)) {
                Chromosome c = this.agents[i].getChromosome();
                Chromosome.mutate(c, GENE_MUTATION_PROBABILITY);
            }
        }
    }

    // update every agent's neural network with its chromosome
    public void updatePopulation() {
        int populationSize = getPopulationSize();
        for (Agent agent : this.agents) {
            agent.updateNetwork();
        }
    }

    public int getPopulationSize() {
        return this.agents.length;
    }

    public Agent[] getAgents() {
        return this.agents;
    }

    public void printAgents(boolean showChromosome, boolean showOutputs) {
        System.out.println("----------------------------------------");
        System.out.println("Population:");
        for (Agent agent : agents) {
            String s = "Agent " + agent.getID() + ": Fitness=" + Utils.formatDouble(agent.getFitness(), 3);
            if (showChromosome) s += " " + agent.getChromosome().toString();
            if (showOutputs) {
                double[] outputs = agent.getNetworkOutputs();
                s += " Outputs: {";
                for (int i = 0; i < outputs.length; i++) {
                    s += Utils.roundDouble(outputs[i], 2);
                    if (i < outputs.length - 1) s += "|";
                }
                s += "}";
            }
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        int[] arch = new int[]{2,2,2};
        double[] inputs = new double[]{1,1};
        Population p = new Population(10, arch);
        for (Agent a : p.getAgents()) {
            a.act(inputs);
            a.updateFitness();
        }
        p.sortAgentsByFitness();
        p.printAgents(true, false);
        p.selectParentsByRank(2);
        p.crossoverPopulation();
        p.mutatePopulation();
        for (Agent a : p.getAgents()) {
            a.act(inputs);
            a.updateFitness();
        }
        p.sortAgentsByFitness();
        p.printAgents(true, false);
    }

}
