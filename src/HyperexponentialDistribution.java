import java.util.Random;

public class HyperexponentialDistribution {
    private double m1, m2, p;
    private ExponentialDistribution exp1, exp2;
    private Random r;

    public HyperexponentialDistribution(double m1, double m2, double p) {
        this.m1 = m1;
        this.m2 = m2;
        this.p = p;

        exp1 = new ExponentialDistribution(m1);
        exp2 = new ExponentialDistribution(m2);

        r = new Random();
    }

    public double generate() {
        if (r.nextDouble() <= p)
            return exp1.generate();
        else
            return exp2.generate();
    }

    public double getMean() {
        return p / m1 + (1 - p) / m2;
    }

    private double getXSquared() {
        return 2 * p / (m1 * m1) + 2 * (1 - p) / (m2 * m2);
    }

    public double getVariance() {
        return getXSquared() - Math.pow(getMean(), 2);
    }

    public double fakeGenerate(){
        return r.nextInt(20);
    }
}
