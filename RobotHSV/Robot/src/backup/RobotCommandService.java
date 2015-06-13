package backup;

import java.util.LinkedList;
import java.util.Queue;

import com.example.robot.Robot;

public class RobotCommandService implements Runnable{

	private Queue<RobotCommand> queue;
	private boolean active;
	Robot robot;
	private String measurement;

	public RobotCommandService(Robot robot) {
		this.robot = robot;
		queue = new LinkedList<RobotCommand>();
	}

	public void addCommand(RobotCommand cmd) {
		queue.add(cmd);
	}

	public void destroy() {
		active = false;
	}
	
	public String getLatestMeasurement() {
		return measurement;
	}

	@Override
	public void run() {
		active = true;
		while (active) {
			if (!queue.isEmpty()) {
				RobotCommand cmd = (RobotCommand) queue.poll();
				try {
					String tmp = RobotInvoker.getInstance().invoke(cmd,robot);
					if(tmp != null) measurement = tmp; 
				} catch (InterruptedException e) {
					queue.clear();
					e.printStackTrace();
				}
			}
		}
	}
}
