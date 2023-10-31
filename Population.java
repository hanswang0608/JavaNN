public class Population {
    private Chromosome[] individuals;
    private double crossoverProbability;
    private double mutationProbability;

    public Population(int numIndividuals, int numGenes) {
        this.individuals = new Chromosome[numIndividuals];
        for (int i = 0; i < individuals.length; i++) {
            this.individuals[i] = new Chromosome(numGenes);
        }
    }
}
