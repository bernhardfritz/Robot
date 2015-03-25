package com.example.robot;

public class AbsoluteRotation implements Movement {
	private Robot robot;
	private double absoluteAngle;

	public AbsoluteRotation(Robot robot, double absoluteAngle) {
		this.robot = robot;
		this.absoluteAngle = absoluteAngle;
	}

	@Override
	public void move() throws InterruptedException {
		double relativeAngle = absoluteAngle - robot.getAngle();
		new RelativeRotation(robot, relativeAngle).move();
	}
}
