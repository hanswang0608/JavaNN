public class Game {
    private int numIterations;
    private Population population;

    public Game(int numIterations, int populationSize, int[] networkArchitecture) {
        this.numIterations = numIterations;
        this.population = new Population(populationSize, networkArchitecture);
    }

    public void act(double[] inputs) {
        for (Agent agent : population.getAgents()) {
            agent.act(inputs);
            agent.updateFitness();
        }
    }

    public void evolve() {
        population.selectParentsByRank();
        population.crossoverPopulation();
        population.mutatePopulation();
    }

    public void start() {
        double[] inputs = {1,1};
        for (int i = 0; i < numIterations; i++) {
            act(inputs);
            population.sortAgentsByFitness();
            // population.printAgents(true, true);
            if (i < numIterations - 1) {
                evolve();
            }
        }
        population.printAgents(true, true);
    }

    public static void main(String[] args) {
        Game game = new Game(1000, 10, new int[]{2,3,4,1});
        game.start();
    }

}
