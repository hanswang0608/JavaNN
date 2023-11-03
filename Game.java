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

    public Agent getMostFit() {
        return this.population.getAgents()[0];
    }

    public static void main(String[] args) {
        Game game = new Game(10000, 10, new int[]{1,1,1});
        game.start();

        Agent a = game.getMostFit();
        for (int i = 1000000; i < 1000100; i++) {
            System.out.println(i + ", " + Utils.formatDouble(a.act(new double[]{i})[0]));
        }        
    }
}
