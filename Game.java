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
                if (i % 2 == 0) {
                    if (output[0] > 0.5) fitness++;
                    else fitness--;
                } 
                if (i % 2 == 1) {
                    if (output[0] < 0.5) fitness++;
                    else fitness--;
                } 
            }
            agent.setFitness(fitness);
        }
    }

    public void evolve() {
        population.selectParentsByRank(2);
        population.crossoverPopulation();
        population.mutatePopulation();
    }

    public void start() {
        double[] inputs = new double[100];
        for (int i = 0; i < 100; i++) {
            inputs[i] = i;
        }
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
        Game game = new Game(100000, 10, new int[]{1,4,4,1});
        game.start();
    }

}
