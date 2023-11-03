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
                if (inputs[i][0] == Math.PI) {
                    fitness += output;
                } else {
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
        double[][] inputs = new double[100][];
        for (int i = 0; i < 100; i++) {
            inputs[i] = new double[]{i/10};
        }
        inputs[0][0] = Math.PI;
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
        Game game = new Game(10000, 10, new int[]{1,4,3,1});
        game.start();

        Agent a = game.getMostFit();
        System.out.println(a.act(new double[]{Math.PI})[0]);
        System.out.println(a.act(new double[]{3.2})[0]);
        System.out.println(a.act(new double[]{3.5})[0]);
        System.out.println(a.act(new double[]{1234})[0]);
    }
}
