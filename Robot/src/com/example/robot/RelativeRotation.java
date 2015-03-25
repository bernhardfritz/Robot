package com.example.robot;

public class RelativeRotation implements Movement{

	Robot robot;
	double angle;
	long t;
	
	public RelativeRotation(Robot robot, double angle) {
		this.robot=robot;
		this.angle=angle;
		t = Math.round(angle/robot.getW());
	}
	
	public void updateAngle(long interval) {
		double delta = Math.signum(angle)*robot.getW()*interval;
		robot.setAngle(robot.getAngle()+delta);
	}
	
	public void sleep() throws InterruptedException {
		if(t>=robot.getInterval()) {
			Thread.sleep(robot.getInterval());
			updateAngle(robot.getInterval());
			t-=robot.getInterval();
		} else {
			Thread.sleep(t);
			robot.setAngle(robot.getAngle()+angle);
			t=0;
		}
	}
	
	@Override
	public void move() throws InterruptedException {
		System.out.println("start degree: " + Math.toDegrees(robot.getAngle()));
		if (angle < 0)
			robot.robotSetVelocity((byte) (Byte.MAX_VALUE / 8),
					(byte) (Byte.MIN_VALUE / 8));
		else if (angle > 0)
			robot.robotSetVelocity((byte) (Byte.MIN_VALUE / 8),
					(byte) (Byte.MAX_VALUE / 8));
		while(t>0) {
			sleep();
		}
		robot.comWrite(new byte[] { 's', '\r', '\n' });
		Thread.sleep(robot.getInterval());
		System.out.println("end degree: " + Math.toDegrees(robot.getAngle()));
	}
}
