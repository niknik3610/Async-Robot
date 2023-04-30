public class Task {
	private double c;
	private final int TASK_ID;
	private double move = -1;
	private final int SENSOR_ID;
	private int ACTUATE_ID;

	Task(double c, int task_id, int sensor_id, int actuate_id) {
		this.c = c;
		this.TASK_ID = task_id;
		this.SENSOR_ID = sensor_id;
		this.ACTUATE_ID = actuate_id;
	}

	
	Task(double c, int id, double move, int sensor_id) {
		this.c = c;
		this.TASK_ID = id;
		this.move = move;
		this.SENSOR_ID = sensor_id;
	}


	public void setMove(double move) {
		this.move = move;
	}


	public void printTask() {
		System.out.println("Task ID: " + TASK_ID);
	}

	public double getCValue() {
		return c;
	}

	public int getID() {
		return TASK_ID;
	}

	public double getMove() {
		return move;
	}

	public int getSensID() {
		return SENSOR_ID;
	}

	public int getActID() {
		return ACTUATE_ID;
	}
}
