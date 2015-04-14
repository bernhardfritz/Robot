package backup;

import com.example.robot.Robot;

public interface RobotCommand {
	String execute(Robot robot) throws InterruptedException;
}
