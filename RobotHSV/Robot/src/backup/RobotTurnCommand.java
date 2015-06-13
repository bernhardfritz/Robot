package backup;

import com.example.robot.Robot;

public class RobotTurnCommand implements RobotCommand {
	@Override
	public String execute(Robot robot) throws InterruptedException {
		robot.robotSetVelocity((byte) (Byte.MIN_VALUE / 8),
				(byte) (Byte.MAX_VALUE / 8));
		return null;
	}
}
