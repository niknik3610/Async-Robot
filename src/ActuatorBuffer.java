import java.util.LinkedList;
import java.util.Queue;

public class ActuatorBuffer{
    private Queue<Task> tasks = new LinkedList<>();

    public synchronized Task getTask() {
        try {
            Task toReturn = tasks.remove();
            return toReturn;
        }
        catch (Exception e) {
            return null;
        }
    }

    public synchronized void addTask(Task task) {
        tasks.add(task);
    }
}