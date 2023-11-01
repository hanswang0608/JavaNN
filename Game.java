public class Game {
    private int numIterations;
    private Population population;

    public Game(int numIterations, int populationSize, int[] networkArchitecture) {
        this.numIterations = numIterations;
        this.population = new Population(populationSize, networkArchitecture);
    }

    public static void main(String[] args) {
        Game game = new Game(2, 2, new int[]{2,2,2});
    }

}
