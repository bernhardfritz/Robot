package com.example.robot;

public class AbsoluteRotation implements Command {
	private Robot robot;
	private double absoluteAngle;

	public AbsoluteRotation(double absoluteAngle, Robot robot) {
		this.robot = robot;
		this.absoluteAngle = absoluteAngle;
	}

	@Override
	public String execute(Robot robot) throws InterruptedException {
		double relativeAngle = absoluteAngle - robot.getAngle();
		Invoker.getInstance().invoke(new RelativeRotation(relativeAngle,robot),robot);
		return null;
	}
}
