package com.example.robot;

public class Translation implements Runnable {
	private AdvancedRobot robot;
	private float distance_cm;
	
	public Translation(AdvancedRobot robot, float distance_cm) {
		this.robot=robot;
		this.distance_cm=distance_cm;
	}
	
	@Override
	public void run() {
		robot.comWrite(new byte[] { 'w', '\r', '\n' });
		try {
			Thread.sleep((long) (distance_cm/robot.getV()));
		} catch (InterruptedException e) {
			robot.comWrite(new byte[] { 'x', '\r', '\n' });
		}
		robot.comWrite(new byte[] { 'x', '\r', '\n' });
	}
}
