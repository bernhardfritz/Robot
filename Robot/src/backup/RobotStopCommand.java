package backup;

import com.example.robot.Robot;

public class RobotStopCommand implements RobotCommand{
	@Override
	public String execute(Robot robot) throws InterruptedException {
		robot.comWrite(new byte[] { 's', '\r', '\n' });
		return null;
	}
}
