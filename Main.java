public class Main {
    public static void main(String[] args) {
        double[] b = new double[]{1.0, 2.0};
        double[][] w = new double[2][];
        w[0] = new double[]{3.0, 4.0}; 
        w[1] = new double[]{5.0, 6.0}; 
        Layer l = new Layer(2, 2, b, w);
        b = l.getBiases();
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        w = l.getWeights();
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                System.out.println(w[i][j]);
            }
        }
        b = new double[]{1.1, 2.2};
        l.setBiases(b);
        b = l.getBiases();
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        w[0] = new double[]{3.3, 4.4}; 
        w[1] = new double[]{5.5, 6.6}; 
        l.setWeights(w);
        w = l.getWeights();
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                System.out.println(w[i][j]);
            }
        }
        l.printValues();
    }
}