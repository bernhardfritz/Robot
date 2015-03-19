package com.example.robot;

public class Rotation implements Runnable {
	private AdvancedRobot robot;
	private float absoluteDegree;
	private float relativeDegree;
	private long t;

	public Rotation(AdvancedRobot robot, float absoluteDegree) {
		this.robot = robot;
		this.absoluteDegree = absoluteDegree;
		this.relativeDegree = robot.normalizeDegree(absoluteDegree)
				- robot.getDegree();
		this.t = Math.round(Math.floor(relativeDegree) / (robot.getW() / 1000));
	}

	public float getRemainingDegree() {
		return relativeDegree;
	}

	private void sleep() throws InterruptedException {
		if (t < robot.getInterval()) {
			Thread.sleep(t);
			robot.setDegree(absoluteDegree);
			relativeDegree = 0;
			t = 0;
		} else {
			Thread.sleep(robot.getInterval());
			float deltaDegree = Math.signum(relativeDegree) * robot.getW()
					* (robot.getInterval() / 1000);
			robot.setDegree(robot.getDegree() + deltaDegree);
			relativeDegree -= deltaDegree;
			t -= robot.getInterval();
		}
	}

	@Override
	public void run() {
		if (relativeDegree < 0)
			robot.robotSetVelocity(Byte.MAX_VALUE, Byte.MIN_VALUE);
		else
			robot.robotSetVelocity(Byte.MIN_VALUE, Byte.MAX_VALUE);
		try {
			while (t > 0) {
				sleep();
			}
		} catch (InterruptedException e) {
			robot.comWrite(new byte[] { 'x', '\r', '\n' });
		}
		robot.comWrite(new byte[] { 'x', '\r', '\n' });
	}
}
