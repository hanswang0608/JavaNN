package src.Util;
import src.Network.ActivationFunction;

public class Config {
    /* GENETIC ALGORITHM PARAMETERS */

    /* Probability of crossover between two chromosomes */
    public static final double CROSSOVER_PROBABILITY = 0.8;

    /* Probability of chromosome mutation */
    public static final double MUTATION_PROBABILITY = 0.1;

    /* Probability of individual genes mutating when chromosome mutation is triggered */
    public static final double GENE_MUTATION_PROBABILITY = 1.0;

    /* Lower bound for the value of a gene */
    public static final double GENE_LOWER_LIMIT = -10.0;

    /* Upper bound for the value of a gene */
    public static final double GENE_UPPER_LIMIT = 10.0;

    /* Coefficient used in the whole arithmetic crossover operation */
    public static final double WHOLE_ARITHMETIC_CROSSOVER_COEFFICIENT = 0.6;

    /* END OF GENETIC ALGORITHM PARAMETERS */



    /* NEURAL NETWORK PARAMETERS */

    /* Activation function for hidden layers */
    public static final ActivationFunction.FuncTypes HIDDEN_LAYER_TYPE = 
        ActivationFunction.FuncTypes.RELU;

    /* Activation function for output layer */
    public static final ActivationFunction.FuncTypes OUTPUT_LAYER_TYPE = 
        ActivationFunction.FuncTypes.SIGMOID;

    /* END OF NEURAL NETWORK PARAMETERS */
}
