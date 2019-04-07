import java.lang.Math;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;


public class Simulator2 {
    public static void main(String args[]) throws IOException {
        int number_of_jobs = 1000000;

        //repeat with different parameters

        //parameters to set
        double l = 0;

        //records mean response times
        double[][] mrt = new double[4][20];

        while (l < 20) {
            double lambda = (l + 1) * 0.05;

            ExponentialDistribution interarrival_time = new ExponentialDistribution(lambda);

            int[] variance = {1, 10, 20, 50};
            for (int v = 0; v < variance.length; v++) {

                int var = variance[v];
                //calculate p, m1, m2 based on variance
                double p = (2 * var + Math.sqrt(4 * var * var - 8 * var)) / 4 / var;
                double m1 = 2 * p;
                double m2 = 2 * (1 - p);
                HyperexponentialDistribution service_time = new HyperexponentialDistribution(m1, m2, p);

                Job[] jobs = new Job[number_of_jobs];

                double arrival_time = 0;
                for (int i = 0; i < number_of_jobs; i++) {
                    jobs[i] = new Job(i, arrival_time, service_time.generate());
                    arrival_time += interarrival_time.generate();
                }

                Job server;
                double time = 0;
                double total_response_time = 0;

                for (int i = 0; i < number_of_jobs; i++) {
                    server = jobs[i];
                    if (server.getArrival() > time)
                        time = server.getArrival();
                    time += server.getSize();
                    if (i >= 10000)
                        total_response_time += time - server.getArrival();
                }

                mrt[v][(int) l] = total_response_time / (number_of_jobs - 10000);
            }
            l += 1;
        }

        write("variance_1.csv", mrt[0]);
        write("variance_10.csv", mrt[1]);
        write("variance_20.csv", mrt[2]);
        write("variance_50.csv", mrt[3]);
    }

    private static void write(String filename, double[] x) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < x.length; i++) {
            outputWriter.write(x[i] + ",");
        }
        outputWriter.flush();
        outputWriter.close();
    }
}
