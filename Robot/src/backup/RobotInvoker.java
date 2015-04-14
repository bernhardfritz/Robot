package backup;

import com.example.robot.Robot;

public class RobotInvoker {
	private static class SingletonHelper {
		private static final RobotInvoker INSTANCE = new RobotInvoker();
	}
	
	public static RobotInvoker getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	public String invoke(RobotCommand cmd,Robot robot) throws InterruptedException {
		return cmd.execute(robot);
	}
}
