public class Job {
    // Juhwan Jeong and Ian McClaugherty, April 2019
	/* The Job object keeps track of a job's arrival time, 
		size, and departure time. */

    private double arrival;
    private double size;

    public Job(double a, double s) {
        arrival = a;
        size = s;
    } // constructor

    public double getArrival() {
        return arrival;
    } // getArrival

    public double getSize() {
        return size;
    } // getSize


} // Job class 