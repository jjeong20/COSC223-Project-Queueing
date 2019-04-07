public class Simulator2 {
    public static void main(String args[]) {
        int number_of_jobs = 1000000;

        //repeat with different parameters

        //0.1, 0.2, ...
        double lambda = 0.5;


        double m1 = 0.5;
        double m2 = 0.5;
        double p = m1 / (m1 + m2);

        ExponentialDistribution interarrival_time = new ExponentialDistribution(lambda);
        //TODO: check equality
        HyperexponentialDistribution service_time = new HyperexponentialDistribution(m1, m2, p);

        Job[] jobs = new Job[number_of_jobs];

        double arrival_time = 0;
        for (int i = 0; i < number_of_jobs; i++) {
            jobs[i] = new Job(i, arrival_time, service_time.generate());
            arrival_time += interarrival_time.generate();
        }

        /*
        System.out.println("Jobs");
        for (Job j : jobs) {
            System.out.println("id: " + j.getId() + " arrival: " + j.getArrival() + " size: " + j.getSize());
        }
        System.out.println();
        double cur = 0;
        double wait = 0;
        double actualsum = 0;
        for (Job j : jobs) {
            if (cur < j.getArrival()) {
                wait += j.getArrival() - cur;
                System.out.println("Wait til job " + j.getId());
                cur = j.getDeparture();
            }
            actualsum += j.getSize();
            cur = cur + j.getSize();
        }
        System.out.println(wait);
        System.out.println(actualsum);
        System.out.println();
        */

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
        double mean_response_time = total_response_time / (number_of_jobs - 10000);
    }
}
