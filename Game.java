public class Game {
    private int numIterations;
    private Population population;

    public Game(int numIterations, int populationSize, int[] networkArchitecture) {
        this.numIterations = numIterations;
        this.population = new Population(populationSize, networkArchitecture);
    }

    public void act(double[] inputs) {
        for (Agent agent : population.getAgents()) {
            double fitness = 0;
            for (int i = 0; i < inputs.length; i++) {
                double[] input = new double[]{inputs[i]};
                double[] output = agent.act(input);
                if (i == 0) {
                    if (output[0] > 0.5) fitness++;
                    else fitness--;
                } 
                if (i == 1) {
                    if (output[0] < 0.5) fitness++;
                    else fitness--;
                } 
            }
            agent.setFitness(fitness);
        }
    }

    public void evolve() {
        population.selectParentsByRank();
        population.crossoverPopulation();
        population.mutatePopulation();
    }

    public void start() {
        double[] inputs = new double[]{0,1};
        for (int i = 0; i < numIterations; i++) {
            act(inputs);
            population.sortAgentsByFitness();
            // population.printAgents(true, false);
            if (i < numIterations - 1) {
                evolve();
            }
        }
        population.printAgents(true, true);
    }

    public static void main(String[] args) {
        Game game = new Game(100000, 10, new int[]{1,1,1});
        game.start();
    }

}
