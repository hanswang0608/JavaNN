public class Main {
    public static void main(String[] args) {
        Layer l = new Layer(2, 2);
        Double[] b = l.getBiases();
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        Double[][] w = l.getWeights();
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                System.out.println(w[i][j]);
            }
        }
    }
}