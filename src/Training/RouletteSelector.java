package src.Training;
import src.Util.Utils;

public class RouletteSelector {
    private double[] roulette;

    public RouletteSelector(double[] odds) {
        this.roulette = new double[odds.length];
        roulette[0] = odds[0];
        for (int i = 1; i < odds.length; i++) {
            roulette[i] = roulette[i-1] + odds[i];
        } 
        if (Double.compare(Utils.roundDouble(roulette[roulette.length-1], 5), 1.0) != 0) {
            throw new IllegalArgumentException("Sum of odds does not equal 1");
        }
    }

    public int select() {
        double rng = Utils.randDouble(0, 1);
        // linear search instead of binary because array should be small
        for (int i = 0; i < this.roulette.length; i++) {
            if (rng < this.roulette[i]) {
                return i;
            }
        }

        return -1;
    }

    public String toString() {
        String s = "(";
        for (int i = 0; i < this.roulette.length; i++) {
            s += Utils.formatDouble(roulette[i], 3);
            if (i != this.roulette.length-1) s += "|";
        }
        return s + ")";
    }

}
