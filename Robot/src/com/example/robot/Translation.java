package com.example.robot;

public class Translation implements Command {
	private Robot robot;
	private double distance;
	private long t;
	private boolean aborted;

	public Translation(double distance, Robot robot) {
		this.robot = robot;
		this.distance = distance;
		t = Math.round(distance / robot.getV());
		aborted = false;
	}

	private void updateXY(long interval) {
		robot.setX(robot.getX() + robot.getV() * interval
				* Math.cos(robot.getAngle()));
		robot.setY(robot.getY() + robot.getV() * interval
				* Math.sin(robot.getAngle()));
	}

	private void sleep() throws InterruptedException {
		if (t >= robot.getInterval()) {
			Thread.sleep(robot.getInterval());
			updateXY(robot.getInterval());
			t -= robot.getInterval();
			if(t>=robot.getInterval()) robot.getSensorManager().update();
		} else {
			Thread.sleep(t);
			updateXY(t);
			t = 0;
		}
	}

	@Override
	public String execute(Robot robot) throws InterruptedException {
		System.out.println("start x: " + robot.getX() + " y: " + robot.getY());
		if (distance < 0)
			robot.comWrite(new byte[] { 'x', '\r', '\n' });
		else if (distance > 0)
			robot.comWrite(new byte[] { 'w', '\r', '\n' });
		try {
			while (t > 0) {
				if (robot.obstacleDetected()) {
					aborted = true;
					break;
				}
				sleep();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		robot.comWrite(new byte[] { 's', '\r', '\n' });
		Thread.sleep(robot.getInterval());
		System.out.println("end x: " + robot.getX() + " y: " + robot.getY());
		return null;
	}

	@Override
	public boolean isAborted() {
		return aborted;
	}
}
