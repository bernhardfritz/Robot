package com.example.robot;

public class RelativeRotation implements Command {

	Robot robot;
	double angle;
	long t;

	public RelativeRotation(double angle, Robot robot) {
		this.robot = robot;
		this.angle = angle;
		//Decide to turn CW or CCW. TODO: Fix
		//if(angle>Math.PI) this.angle-=2*Math.PI;
		//else if(angle<-Math.PI) this.angle+=2*Math.PI;
		t = Math.round(Math.abs(angle) / robot.getW());
	}

	public void updateAngle(long interval) {
		double delta = Math.signum(angle) * robot.getW() * interval;
		robot.setAngle(robot.getAngle() + delta);
	}

	public void sleep() throws InterruptedException {
		if (t >= robot.getInterval()) {
			Thread.sleep(robot.getInterval());
			updateAngle(robot.getInterval());
			t -= robot.getInterval();
		} else {
			Thread.sleep(t);
			updateAngle(t);
			t = 0;
		}
	}

	@Override
	public String execute(Robot robot) throws InterruptedException {
		System.out.println("start degree: " + Math.toDegrees(robot.getAngle()));
		if (angle < 0)
			robot.robotSetVelocity((byte) (Byte.MAX_VALUE / 8),
					(byte) (Byte.MIN_VALUE / 8));
		else if (angle > 0)
			robot.robotSetVelocity((byte) (Byte.MIN_VALUE / 8),
					(byte) (Byte.MAX_VALUE / 8));
		while (t > 0) {
			sleep();
		}
		robot.comWrite(new byte[] { 's', '\r', '\n' });
		Thread.sleep(robot.getInterval());
		System.out.println("end degree: " + Math.toDegrees(robot.getAngle()));
		return null;
	}

	@Override
	public boolean isAborted() {
		return false;
	}
}
