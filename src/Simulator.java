import java.util.LinkedList;

public class Simulator {
    public static void main(String args[]) {
        int number_of_jobs = 30;

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

        LinkedList<Job> queue = new LinkedList<>();
        Job server = jobs[0];

        double time = 0;
        double total_response_time = 0;

        double next_departure_time = server.getDeparture();
        double next_arrival_time;

        System.out.println("Jobs");
        for (Job j : jobs) {
            System.out.println("id: " + j.getId() + " arrival: " + j.getArrival() + " size: " + j.getSize());
        }
        System.out.println();


        for (int i = 0; i < number_of_jobs - 1; i++) {
            next_arrival_time = jobs[i + 1].getArrival();
            System.out.println();
            System.out.println("Job number: " + i);
            System.out.println("Server: " + "id: " + server.getId() + " arrival: " + server.getArrival() + " size: " + server.getSize());
            System.out.println("time: " + time);
            System.out.println("next dep: " + next_departure_time);
            System.out.println("next arr: " + next_arrival_time);
            printQueue(queue);
            System.out.println();

            if (next_arrival_time <= next_departure_time) {
                time = next_arrival_time;
                if (server.getId() < 0) {
                    server = jobs[i + 1];
                    server.setDeparture(time + server.getSize());
                    next_departure_time = server.getDeparture();
                } else
                    queue.add(jobs[i + 1]);
            }
            if (next_arrival_time >= next_departure_time) {
                time = server.getDeparture();
                server = new Job(-1, 0, 0);
                if (i >= 0)
                    total_response_time += server.getDeparture() - server.getArrival();
                if (!queue.isEmpty()) {
                    server = queue.remove(0);
                    server.setDeparture(time + server.getSize());
                    next_departure_time = server.getDeparture();
                }
            }
        }
        //TODO handle very last job


    }

    public static void printQueue(LinkedList<Job> jobs) {
        for (Job j : jobs) {
            System.out.print("id: " + j.getId() + " arrival: " + j.getArrival() + " size: " + j.getSize() + "\t");
        }
    }
}
