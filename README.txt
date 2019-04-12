Main Files

    Experiment1.java and Experiment2.java are the only files with the main method.

    Experiment1.java does not require any command line parameters so it can simply be run.
    It does the following things:
        1. Set lambda = 0.01. Create an ExponentialDistribution object called interarrival_time with parameter lambda.
        2. For each variance value in [1, 10, 20, 50], calculate the parameters p, m1, m2 and create an
        HyperexponentialDistribution object called service_time.
        3. Create a Job array of size 1,000,000 and using the 2 distributions to generate interarrival times and service
        times, fill the array with Jobs.
        4. Using a loop to simulate each job's entry and exit out of the system, compute the mean response time and save
        the result.
        5. Repeat steps 1-4 after incrementing lambda by 0.01 until lambda reaches 1.
        6. Output the arrays with mean response times as a csv file - these were then used in an excel file to graph
        mean response times versus lambda.

    Experiment2.java does not require any command line parameters so it can simply be run.
    It does the following things:
        1. Create an ExponentialDistribution object called service_time with parameter 1.
        2. Set lambda = 1.01; this value is to be the expected value of the interarrival time distribution.
        3. For each variance value in [1*lambda^2, 10*lambda^2, 20*lamb da^2, 50*lambda^2], calculate the parameters p,
        m1, m2 and create an HyperexponentialDistribution object called interarrival_times.
        4. Create a Job array of size 1,000,000 and using the 2 distributions to generate interarrival times and service
        times, fill the array with Jobs.
        5. Using a loop to simulate each job's entry and exit out of the system, compute the mean response time and save
        the result.
        6. Repeat steps 2-5 after incrementing lambda by 0.01 until lambda reaches 4.
        7. Output the arrays with mean response times as a csv file - these are then used to graph the results in
        Analysis.py.

    The output files are then fed into a Python file called Analysis.py.
    Analysis.py does not require any command line parameters either.
    It does the following things:
        1. Create a list called means and feed it with numbers from 1.01 to 4 with 0.01 increments. These values
        represent the expected values that will be graphed on the x-axis.
        2. Reads in the csv files and saves them into dictionary of lists called mrt, where each list holds the mean
        response times for one variance value.
        3. Then, create a plot where each list in mrt (4 in total) is plotted against means.
        4. Save the figure as a png file.

Other Object Files

    Distributions
        ExponentialDistribution.java
            Creates an ExponentialDistribution object where constructor takes in parameter lambda. The generate method
            draws a value from the created distribution.
        HyperexponentialDistribution.java
            Creates a HyperexponentialDistribution object where constructor takes in parameters m1, m2, p. Then, the
            constructor creates two private ExponentialDistribution objects - one with parameter m1, the other with m2.
            When the generate method is called, with probability p, the object will call the generate from the
            ExponentialDistribution object with parameter m1; and with probability 1-p, it will call generate from the
            other ExponentialDistribution object.

    Job.java
        Creates a Job object where constructor takes in arrival time and size. It has accessor methods getArrival and
        getSize.