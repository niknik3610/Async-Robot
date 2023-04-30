class Actuate implements Runnable{
    //every actuator has it's own buffer, due to having Actuator id's determine Actuator doing work
    private ActuatorBuffer A_BUFFER = new ActuatorBuffer();
    private final int ACTUATE_ID;
    private Boolean robotDirection;
    private Object actuatorLock;
    private Robot robot;
    
    public Actuate(int id, Robot robot, Object lock) {
        ACTUATE_ID = id;
        actuatorLock = lock;
        this.robot = robot;
    }
    
    public void addTask(Task task) {
        A_BUFFER.addTask(task);
    }

    private Task getTask() {
        return A_BUFFER.getTask();
    }

    public void run() {
        double oldPos;
        double newPos;
        Boolean error = true;
        int lastID = 0;
        
        while (true) {
            try {
                Task task = getTask();
                if (task != null) {
                    error = false;
                    //synchronized to make sure multiple actuators don't try to move robot at once, also makes sure
                    //direction and position is only accessed once at a time
                    synchronized(actuatorLock) {
                        lastID = task.getID();
                        //algorithm to allow for robot to move other direction, once hitting wall
                        if (task.getMove() != -1) {
                            double movement = task.getMove();
                            oldPos = robot.getRobotPos();
                            newPos = oldPos;
                            robotDirection = robot.getRobotDir();
                            
                            if (robotDirection) {
                                movement = movement*-1;
                            }
                            newPos = newPos + movement;
                            if (newPos > 1) {
                                double negative = newPos- 1;
                                newPos = 1 - negative;
                                robotDirection = true;
                            }
                            else if (newPos < 0) {
                                newPos = newPos * -1;
                                robotDirection = false;
                            }
                            robot.setRobotDir(robotDirection);
                            robot.setRobotPos(newPos);
                            System.out.println("Robot moving. Task id {" + task.getID() + "}, task complexity { + "+ task.getCValue() + "}, result { " + task.getMove()+ "}, old position: {" + oldPos + "}, new position: {"+ newPos +"}, Sensor: " + task.getSensID() + ", Actuator: " + ACTUATE_ID); 

                        }
                    }
                }
                else {
                    //prevents the error from being spammed
                    if (!error) {
                        error = true;
                        System.out.println("Actuator Error: No result to process. Last task processed {" + lastID + "}. Actuator_ID: " + ACTUATE_ID);
                    }
                }
                Thread.sleep(10);

            }
            catch (InterruptedException e) {
                break;
            }
        }
    }
}