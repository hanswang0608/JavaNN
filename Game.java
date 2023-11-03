import java.io.IOException;

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
                if (i == 1 || i == 2) {
                    fitness += output;
                }
                else {
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
        population.updatePopulation();
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
        // Game game = new Game(1000, 10, new int[]{2,2,2,1,});
        // game.start();

        // Agent a = game.getMostFit();
        // System.out.println(a.getID() + ", " + a.act(new double[]{1,1})[0]);
        // a.getNetwork().saveToFile("models/test.model", true);

        try {
            Agent b = new Agent(NeuralNetwork.loadFromFile("models/test.model"));
            System.out.println(b.getID() + ", " + b.act(new double[]{1,0})[0]);
            System.out.println(b.getChromosome());
        } catch (IOException e) {
            System.out.println("failed");
        }
    }
}
