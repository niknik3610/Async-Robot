import java.util.LinkedList;
import java.util.Queue;

public class SensorBuffer{
    private Queue<Task> tasks;
    private int id;
    final int MAX_SIZE;
    int currentSize = 0;

    //arbitrary maximum size, represents maximum memory available for the buffer
    SensorBuffer(int MAX_SIZE) {
        this.MAX_SIZE = MAX_SIZE;
        tasks = new LinkedList<>();
    }

    //gets the next task for the analyzer
    public synchronized Task getTask() {
        try {
            Task toReturn = tasks.remove();
            currentSize--;
            return toReturn;
        }
        catch (Exception e) {
            return null;
        }
    }

    //adds a task to the sensor buffer
    public synchronized Boolean addTask(Double c, int sensor_id, int actuate_id) {
        if (currentSize <= MAX_SIZE) {
            tasks.add(new Task(c, id, sensor_id, actuate_id));
            id++;
            currentSize++;
            return true;
        }
        return false;

    }

}