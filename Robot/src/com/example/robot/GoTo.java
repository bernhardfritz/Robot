package com.example.robot;

public class GoTo implements Movement {

	double x;
	double y;
	Robot robot;

	public GoTo(Robot robot, double x, double y) {
		this.x = x;
		this.y = y;
		this.robot = robot;
	}

	@Override
	public void move() throws InterruptedException {
		double ak = x - robot.getX();
		double gk = y - robot.getY();
		double angle = Math.atan(gk / ak);
		if (ak < 0)
			angle += Math.PI;
		else if (gk < 0)
			angle += 2 * Math.PI;
		double s = Math.sqrt(Math.pow(gk, 2) + Math.pow(ak, 2));
		Movement m1 = new AbsoluteRotation(robot, angle);
		Movement m2 = new Translation(robot, s);
		m1.move();
		m2.move();
	}

}
