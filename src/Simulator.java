import java.util.LinkedList;

public class Simulator {
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

        LinkedList<Job> queue = new LinkedList<>();
        Job server = jobs[0];

        double time = 0;
        double total_response_time = 0;

        double next_departure_time = server.getDeparture();
        double next_arrival_time;

        Job nullJob = new Job(-1, 0, 0);

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



        int skip_counter = 0;

        boolean processed = false;


        for (int i = 0; i < number_of_jobs - 1; i++) {
            //TODO handle last job
            next_arrival_time = jobs[i + 1].getArrival();
            /*
            System.out.println("----------------------");
            System.out.println("Current Job number: " + i);
            System.out.println("Job just arrived: " + Integer.toString(i + 1));
            System.out.println("Server before arrival: " + "id: " + server.getId() + " arrival: " + server.getArrival()
                    + " size: " + server.getSize() + "departure: " + server.getDeparture());
            System.out.println("time b4 arrival: " + time);
            System.out.println("next dep b4 arrival: " + next_departure_time);
            System.out.println("next arr b4 arrival: " + next_arrival_time);
            System.out.print("queue b4 arrival: ");
            printQueue(queue);
            System.out.println();
            */
            if (next_arrival_time < next_departure_time) {
                time = next_arrival_time;
                if (server.getId() < 0) {
                    server = jobs[i + 1];
                    server.setDeparture(time + server.getSize());
                } else
                    queue.add(jobs[i + 1]);
                processed = false;
            } else {
                time = next_departure_time;
                queue.add(jobs[i + 1]);

                if (skip_counter > 10000)
                    total_response_time += time - server.getArrival();
                else
                    skip_counter++;

                if (!queue.isEmpty()) {
                    server = queue.remove(0);
                    server.setDeparture(time + server.getSize());
                    next_departure_time = server.getDeparture();
                } else
                    server = nullJob;
                processed = true;
            }
        }
        System.out.println(server.getId());
        System.out.println(time);
        System.out.println(processed);


        while (!queue.isEmpty()) {
            next_arrival_time = queue.get(0).getArrival();

            if (next_arrival_time <= next_departure_time)
                time = next_departure_time;
            else
                time = next_arrival_time;

            if (skip_counter > 10000)
                total_response_time += server.getDeparture() - server.getArrival();
            else
                skip_counter++;
            server = queue.remove(0);
            server.setDeparture(time + server.getSize());
            next_departure_time = server.getDeparture();
        }

        System.out.println(time);
            /*
            System.out.println("Server after arrival: " + "id: " + server.getId() + " arrival: " + server.getArrival()
                    + " size: " + server.getSize() + "departure: " + server.getDeparture());
            System.out.println("time after arrival: " + time);
            System.out.println("next dep after arrival: " + next_departure_time);
            System.out.println("next arr after arrival: " + next_arrival_time);
            System.out.print("queue after arrival: ");
            printQueue(queue);
            System.out.println();
            */

    }

    public static void printQueue(LinkedList<Job> jobs) {
        for (Job j : jobs) {
            System.out.print("id: " + j.getId() + " arrival: " + j.getArrival() + " size: " + j.getSize() + "\t");
        }
    }
}
