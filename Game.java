public class Game {
    private int numIterations;
    private Population population;

    public Game(int numIterations, int populationSize, int[] networkArchitecture) {
        this.numIterations = numIterations;
        this.population = new Population(populationSize, networkArchitecture);
    }

    public void act(double[][] inputs) {
        for (Agent agent : population.getAgents()) {
            double fitness = 0;
            for (int i = 0; i < inputs.length; i++) {
                double output = agent.act(inputs[i])[0];
                // if (output > 0.5) {
                //     if (i == 3) fitness++;
                //     else fitness--;
                // }
                // if (output < 0.5) {
                //     if (i < 3) fitness++;
                //     else fitness--;
                // }
                if (i == 3) {
                    fitness += output;
                }
                if (i < 3) {
                    fitness -= output;
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
        double[][] inputs = new double[4][];
        inputs[0] = new double[]{0,0};
        inputs[1] = new double[]{0,1};
        inputs[2] = new double[]{1,0};
        inputs[3] = new double[]{1,1};
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

    public Agent getMostFit() {
        return this.population.getAgents()[0];
    }

    public static void main(String[] args) {
        Game game = new Game(1000, 10, new int[]{2,2,2,1});
        game.start();

        Agent a = game.getMostFit();
        System.out.println(a.act(new double[]{1,1})[0]);
    }
}
