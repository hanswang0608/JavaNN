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
        for (int i = 0; i < numIterations; i++) {
            double[] inputs = {1,1};
            act(inputs);
            population.sortAgentsByFitness();
            population.printAgents(true);
            if (i < numIterations - 1) {
                evolve();
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game(100, 10, new int[]{2,2,2});
        game.start();
    }

}
