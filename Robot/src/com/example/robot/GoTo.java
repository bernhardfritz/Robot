package com.example.robot;

public class GoTo implements Command {

	double x;
	double y;
	Robot robot;
	double sign;

	public GoTo(Robot robot, double x, double y) {
		this.x = x;
		this.y = y;
		this.robot = robot;
		sign = 1.0;
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
		System.out.println("distance: "+s);
		Command cmd1 = new AbsoluteRotation(angle,robot);
		Command cmd2 = new Translation(s,robot);
		Invoker.getInstance().invoke(cmd1,robot);
		Invoker.getInstance().invoke(cmd2,robot);
		// Collision avoidance start
		if(cmd2.isAborted()) {
			Command ca1 = new RelativeRotation(sign*(Math.PI/2.0),robot);
			Command ca2 = new Translation(30.0,robot);
			Invoker.getInstance().invoke(ca1, robot);
			Invoker.getInstance().invoke(ca2, robot);
			if(ca2.isAborted()) sign *= -1.0;
			execute(robot);
		}
		// Collision avoidance end
		return null;
	}

	@Override
	public boolean isAborted() {
		return false;
	}

}
