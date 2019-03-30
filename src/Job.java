public class Job {
    // Juhwan Jeong and Ian McClaugherty, April 2019
	/* The Job object keeps track of a job's arrival time, 
		size, and departure time. */

    private int id;
    private double arrival;
    private double size;
    private double departure;

    public Job(int i, double a, double s) {
        id = i;
        arrival = a;
        size = s;
        departure = a + s;
    } // constructor

    public double getArrival() {
        return arrival;
    } // getArrival

    public double getSize() {
        return size;
    } // getSize

    public void setDeparture(double time) {
        departure = time;
    } // setDeparture

    public double getDeparture() {
        return departure;
    }

    public int getId() {
        return id;
    }

} // Job class 