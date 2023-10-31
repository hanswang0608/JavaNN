import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        System.out.println(rand.nextGaussian()*10);
    }
}