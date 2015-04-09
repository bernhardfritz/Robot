package com.example.robot;

public class GoTo implements Command {

	double x;
	double y;
	Robot robot;

	public GoTo(Robot robot, double x, double y) {
		this.x = x;
		this.y = y;
		this.robot = robot;
	}

	@Override
	public String execute(Robot robot) throws InterruptedException {
		double ak = x - robot.getX();
		double gk = y - robot.getY();
		double angle = Math.atan(gk / ak);
		if (ak < 0)
			angle += Math.PI;
		else if (gk < 0)
			angle += 2 * Math.PI;
		double s = Math.sqrt(Math.pow(gk, 2) + Math.pow(ak, 2));
		Command cmd1 = new AbsoluteRotation(angle,robot);
		Command cmd2 = new Translation(s,robot);
		Invoker.getInstance().invoke(cmd1,robot);
		Invoker.getInstance().invoke(cmd2,robot);
		return null;
	}

}
