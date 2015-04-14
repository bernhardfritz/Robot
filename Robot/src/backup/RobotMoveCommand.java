package backup;

import com.example.robot.Robot;

public class RobotMoveCommand implements RobotCommand{
	@Override
	public String execute(Robot robot) throws InterruptedException {
		robot.comWrite(new byte[] { 'w', '\r', '\n' });
		return null;
	}
}
