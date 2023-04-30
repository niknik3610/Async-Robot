class Analyze implements Runnable {
    //cross is weird x, didn't know what it was called
    private float cross;
    private SensorBuffer S_BUFFER;
    private Actuate[] actuators;
    
    public Analyze(float cr, SensorBuffer S_BUFFER, Actuate[] actuators) {
        cross = cr;
        this.S_BUFFER = S_BUFFER;
        this.actuators = actuators;
    } 
    
    public void run() {
        long cTime;
        double y;
        while (true) {
            try {
                Task task = S_BUFFER.getTask();
                if (task != null) { 
                    //performs movement calculation
                    y = Math.pow(1-task.getCValue(), cross);
                    task.setMove(y);
                    actuators[task.getActID()].addTask(task);
                    cTime = (long) (task.getCValue()*1000);
                    Thread.sleep(cTime);
                }
                else {
                    System.out.println("Analyzer Error: No result to process");
                    Thread.sleep(100);
                }
                Thread.sleep(5);
            } 
            catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Analyzer Quitting");
    }
}