class Sense implements Runnable {
    private final int SENSOR_ID;
    private SensorBuffer S_BUFFER;
    private final int lambda;
    private final int actuatorCount;

    public Sense(int id, int lambda, SensorBuffer S_BUFFER, int actuatorCount) {
        SENSOR_ID = id;
        this.S_BUFFER = S_BUFFER;
        this.lambda = lambda;
        this.actuatorCount = actuatorCount;
    }
    
    //finding a random int using poisson distribution, source: https://en.wikipedia.org/wiki/Poisson_distribution
    private int generateRand(int lamda) {
        double L = Math.exp(-1*lamda);
        int K = 0;
        double P = 1;
        double U;
        while (P > L) {
            K = K + 1;
            U = Math.random();
            P = P * U;
        }
        return K - 1;
    }
    
    public void run() {
        while (true) {
            try{
                int timer = (generateRand(lambda));
                //c = complexity
                double c = Math.random()*0.25;
                Boolean errorPrint = true;
                //error can only print once per task, actuator chosen randomly
                while (!S_BUFFER.addTask(c, SENSOR_ID, (int)(Math.random()*actuatorCount))){
                    if (errorPrint) {
                        System.out.println("Sensor " + SENSOR_ID + " Waiting");
                        errorPrint = false;
                    }
                }
                if (timer > 0) Thread.sleep(Math.round(1000/timer));
                Thread.sleep(5);
            }
            catch (InterruptedException e) {
                break;
            }
        }
    }
}