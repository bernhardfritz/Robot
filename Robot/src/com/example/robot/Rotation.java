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
		this.t = Math.round(Math.floor(relativeDegree) / robot.getW())*1000;
		System.out.println("drehe "+absoluteDegree+" grad");
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
		System.out.println("drehung gestartet" + this);
		if (relativeDegree < 0)
			robot.robotSetVelocity((byte) (Byte.MAX_VALUE/8), (byte) (Byte.MIN_VALUE/8));
		else
			robot.robotSetVelocity((byte) (Byte.MIN_VALUE/8), (byte) (Byte.MAX_VALUE/8));
		try {
			while (t > 0) {
				sleep();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		robot.comWrite(new byte[] { 's', '\r', '\n' });
	}
}
