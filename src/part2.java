import java.lang.Math;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;


public class part2 {
    public static void main(String args[]) throws IOException {
        int number_of_jobs = 1000000;

        //repeat with different parameters

        //parameters to set
        //l is used to iterate through loop, calculate lambda, and index into the mrt array
        double l = 0;

        //records mean response times
        double[][] mrt = new double[4][300];

        //service time drawn from exponential distribution with parameter 1
        ExponentialDistribution service_time = new ExponentialDistribution(1);

        while (l < 300) { // while loop allows us to easily compute lambda and store mean response times in mrt
            double lambda = ((l + 1) * 0.01) +1; // incrementing lambda by 0.01 each iteration


            /* The following for loop computes our parameter values based upon the current variance - which is calculated
            using the current lambda value. Using these parameters hyperexponential distribution of interarrival times
            is created. An array of jobs is created. A Job called server is created to keep track of which job
            is currently in use. The final for loop (line 59) puts a job in the server if the server is not in use. If
            the arrival time of that job is greater than the current time, we move forward in time to the job's
            arrival time. We then increase the current time by the size of the job in the server. This simulates
            the runtime of the job while on the server. To compute each job's response time, we subtract its arrival time
            from the current time, which is when the job finishes. Mean response time for each lambda and variance
            pairing is computed by taking the total response time (sum of all job response times) and dividing by
            (the number of jobs - 10000).
             */
            double[] variance = {Math.pow(lambda,2), 10*Math.pow(lambda,2), 20*Math.pow(lambda,2), 50*Math.pow(lambda,2)};
            for (int v = 0; v < variance.length; v++) {

                double var = variance[v];
                //calculate p, m1, m2 based on variance
                double p = (1 + Math.sqrt(1 - 4*Math.pow(lambda,2)/(2*var + 2*Math.pow(lambda,2)))) / 2;
                double m1 = (2 * p)/lambda;
                double m2 = (2-2*p)/lambda;
                HyperexponentialDistribution interarrival_time = new HyperexponentialDistribution(m1, m2, p);

                Job[] jobs = new Job[number_of_jobs];

                double arrival_time = 0;
                for (int i = 0; i < number_of_jobs; i++) {
                    jobs[i] = new Job(arrival_time, service_time.generate());
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

        //outputing files
        write("variance_1_lambda_squared.csv", mrt[0]);
        write("variance_10_lambda_squared.csv", mrt[1]);
        write("variance_20_lambda_squared.csv", mrt[2]);
        write("variance_50_lambda_squared.csv", mrt[3]);
    }

    //method to output mean response time arrays into files
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
