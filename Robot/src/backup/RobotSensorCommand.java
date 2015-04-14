package backup;

import com.example.robot.Robot;

public class RobotSensorCommand implements RobotCommand{
	@Override
	public String execute(Robot robot) throws InterruptedException {
		return robot.comReadWrite(new byte[] { 'q', '\r', '\n' });
	}
}
