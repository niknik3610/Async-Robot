//used for tracking robot position across actuators
public class Robot{
    private double robotPos = 0;
    //left = false, right = true
    private boolean robotDirection;

    public Robot(double initPos, boolean initDir) {
        robotPos = initPos;
        robotDirection = initDir;
    }

    public double getRobotPos() {
        return robotPos;
    }

    public void setRobotPos(double x) {
        robotPos = x;   
    }

    public boolean getRobotDir() {
        return robotDirection;
    }

    public void setRobotDir(boolean x) {
        robotDirection = x;
    }
}