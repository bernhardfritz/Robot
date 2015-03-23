package com.example.robot;

public class Translation implements Runnable {
	private AdvancedRobot robot;
	private float relative_distance_cm;
	private long t;

	public Translation(AdvancedRobot robot, float relative_distance_cm) {
		this.robot = robot;
		this.relative_distance_cm = relative_distance_cm;
		t = Math.round(relative_distance_cm / robot.getV())*1000;
		System.out.println("fahre "+relative_distance_cm+" cm");
	}

	public float getRemainingDistance() {
		return relative_distance_cm;
	}

	private void sleep() throws InterruptedException {
		if (t < robot.getInterval()) {
			Thread.sleep(t);
			robot.setX(robot.getX() + robot.getV() * (t / 1000)
					* (float) Math.sin(robot.getDegree()));
			robot.setY(robot.getY() + robot.getV() * (t / 1000)
					* (float) Math.cos(robot.getDegree()));
			relative_distance_cm = 0;
			t = 0;
		} else {
			Thread.sleep(robot.getInterval());
			robot.setX(robot.getX() + robot.getV()
					* (robot.getInterval() / 1000)
					* (float) Math.sin(robot.getDegree()));
			robot.setY(robot.getY() + robot.getV()
					* (robot.getInterval() / 1000)
					* (float) Math.cos(robot.getDegree()));
			float delta_distance_cm = Math.signum(relative_distance_cm)
					* robot.getV() * (robot.getInterval() / 1000);
			relative_distance_cm -= delta_distance_cm;
			t -= robot.getInterval();
		}
	}

	@Override
	public void run() {
		System.out.println("translation gestartet" + this);
		if (relative_distance_cm < 0)
			robot.comWrite(new byte[] { 'x', '\r', '\n' });
		else
			robot.comWrite(new byte[] { 'w', '\r', '\n' });
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
