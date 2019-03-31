public class Simulator2 {
    public static void main(String args[]) {
        int number_of_jobs = 15;

        //repeat with different parameters
        double lambda = 0.5;
        double m1 = 0.5;
        double m2 = 0.5;
        double p = m1 / (m1 + m2);

        ExponentialDistribution interarrival_time = new ExponentialDistribution(lambda);
        HyperexponentialDistribution service_time = new HyperexponentialDistribution(m1, m2, p);

        Job[] jobs = new Job[number_of_jobs];

        double arrival_time = 0;
        for (int i = 0; i < number_of_jobs; i++) {
            jobs[i] = new Job(i, arrival_time, service_time.fakeGenerate());
            arrival_time += interarrival_time.fakeGenerate();
        }

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

        Job server;
        double time = 0;
        double total_response_time = 0;

        for (int i = 0; i < number_of_jobs; i++) {
            server = jobs[i];
            if (server.getArrival() > time)
                time = server.getArrival();
            System.out.print("Job " + i + " started at time =  " + time);
            time += server.getSize();
            total_response_time += time - server.getArrival();
            System.out.println(" and ended at time = " + time);
        }
        System.out.println(time);
    }
}
