package com.example.robot;

public class Rotation implements Runnable {
	private AdvancedRobot robot;
	private float degree;

	public Rotation(AdvancedRobot robot, float degree) {
		this.robot = robot;
		this.degree = degree;
	}

	@Override
	public void run() {
		if (degree < 0)
			robot.robotSetVelocity((byte) 128, (byte) 0);
		else
			robot.robotSetVelocity((byte) 0, (byte) 128);
		try {
			Thread.sleep((long) (degree / robot.getW()));
		} catch (InterruptedException e) {
			robot.comWrite(new byte[] { 'x', '\r', '\n' });
		}
		robot.comWrite(new byte[] { 'x', '\r', '\n' });
	}
}
