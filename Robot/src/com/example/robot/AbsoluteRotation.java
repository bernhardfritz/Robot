package com.example.robot;

public class AbsoluteRotation implements Movement {
	private Robot robot;
	private double relativeAngle;

	public AbsoluteRotation(Robot robot, double absoluteAngle) {
		this.robot = robot;
		this.relativeAngle = absoluteAngle-robot.getAngle();
	}
	
	@Override
	public void move() throws InterruptedException {
		new RelativeRotation(robot, relativeAngle).move();
	}
}
