import java.lang.Thread;
/** Main Controller Class*/
public class Driver {
    private Actuate[] actuators;
    private Thread[] threads;
    private Analyze analyzer;
    private Thread A_THREAD;

    //Actuator Lock used to synchronize moving the robot, only one actuator can move the robot at a time
    private Object actuatorLock;
    
    private float cross;
    private int lamda;
    
    //Buffer for sensor to put tasks on, accessed by Analyzer
    SensorBuffer S_BUFFER;
    Robot ROBOT;

    /**
     * 
     * @param cr is Strange X used for calculating movement
     * @param lamda is used for poisson random function, changes the amount of tasks performed by sensor every second 
     * @param zeroPos the starting position of the robot
     */
    public Driver(float cr, int lamda, double zeroPos) {
        if (zeroPos <= 1 && zeroPos >= 0) {
            ROBOT = new Robot(zeroPos, false);
        }
        else {
            System.out.println("Initial Position out of Bounds, setting to 0");
            ROBOT = new Robot(0, false);
        }
        cross = cr;
        this.lamda = lamda;
        S_BUFFER = new SensorBuffer(10);
    }

    /**
     * Initilizes the robot controller
     * @param sensors the amount of sensors required
     * @param actuators the amount of actuators required
     */
    public void init(int sensors, int actuators) {
        threads = new Thread[sensors + actuators]; 
        this.actuators = new Actuate[actuators];
        actuatorLock = new Object();
        
        for (int i = 0; i < sensors + actuators; i++) {
            if (i < sensors) {
                threads[i] = new Thread(new Sense(i, lamda, S_BUFFER, actuators));        
            }
            else {
                this.actuators[i-sensors] = new Actuate(i-sensors, ROBOT, actuatorLock);
                threads[i] = new Thread(this.actuators[i-sensors]);
            }
        }    
        
        analyzer = new Analyze(cross, S_BUFFER, this.actuators);
        A_THREAD = new Thread(analyzer);
    }

    public void run() {
        System.out.println("Starting");
        for (Thread threads : threads) {
            threads.start();
        }
        A_THREAD.start();

        try {Thread.sleep(10000);} catch (InterruptedException e) {}
        
        for (Thread threads : threads) {
            threads.interrupt();
        }
        A_THREAD.interrupt();
        System.exit(0);
    }

    public static void main(String[] args) {
        Driver driver = new Driver(8,2,0);
        //checks for command line parameters 
        if (args.length == 3)
            driver = new Driver(Float.parseFloat(args[0]), Integer.parseInt(args[1]), Double.parseDouble(args[2]));
           
        
        driver.init(3,3);
        driver.run();
    }

}