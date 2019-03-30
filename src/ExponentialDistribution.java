import java.util.Random;

public class ExponentialDistribution {

    private double lambda;
    private Random r;

    public ExponentialDistribution(double lambda) {
        this.lambda = lambda;
        r = new Random();
    }

    public double generate() {
        double area = r.nextDouble();
        return -(Math.log(1 - area)) / lambda;
    }

    public double getMean() {
        return 1 / lambda;
    }

    public double getVariance() {
        return 1 / lambda / lambda;
    }

    public double fakeGenerate(){
        return r.nextInt(10);
    }
}
