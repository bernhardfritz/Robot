package com.example.robot;

import java.util.Observable;
import java.util.Observer;

import jp.ksksue.driver.serial.FTDriver;

public class Robot implements Observer {
	private FTDriver com;

	private double x;
	private double y;
	private double angle;
	private double v;
	private double w;
	private boolean obstacle;

	private MovementService mserv;
	private SensorService sserv;

	public Robot(FTDriver com) {
		this.com = com;
		this.x = 0.0;
		this.y = 0.0;
		this.angle = 0.0;
		this.v = 0.02825; // cm/ms
		this.w = 0.001532399; // rad/ms
		this.obstacle = false;
		this.mserv = new MovementService();
		this.sserv = new SensorService(this);
	}

	public void connect() {
		if (com.begin(FTDriver.BAUD9600)) {
			MainActivity.log("connected\n");
			Thread movementThread = new Thread(mserv);
			// Thread sensorThread = new Thread(sserv);
			movementThread.start();
			// sensorThread.start();
		} else
			MainActivity.log("could not connect\n");
	}

	public void disconnect() {
		mserv.destroy();
		sserv.destroy();
		com.end();
		if (!com.isConnected()) {
			MainActivity.log("disconnected\n");
		}
	}

	public void comWrite(byte[] data) {
		if (com.isConnected()) {
			com.write(data);
		} else {
			MainActivity.log("not connected\n");
		}
	}

	public String comRead() {
		String s = "";
		int i = 0;
		int n = 0;
		while (i < 3 || n > 0) {
			byte[] buffer = new byte[256];
			n = com.read(buffer);
			s += new String(buffer, 0, n);
			i++;
		}
		return s;
	}

	public String comReadWrite(byte[] data) {
		com.write(data);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// ignore
		}
		return comRead();
	}

	public void robotSetLeds(byte red, byte blue) {
		comReadWrite(new byte[] { 'u', red, blue, '\r', '\n' });
	}

	public void robotSetVelocity(byte left, byte right) {
		comReadWrite(new byte[] { 'i', left, right, '\r', '\n' });
	}

	public void robotSetBar(byte value) {
		comReadWrite(new byte[] { 'o', value, '\r', '\n' });
	}
	
	public void robotDrive(double distance) {
		mserv.addMovement(new Translation(this, distance));
	}

	public void robotTurn(double angle) {
		mserv.addMovement(new AbsoluteRotation(this, angle));
	}

	public void robotGoTo(double x, double y) {
		mserv.addMovement(new GoTo(this, x, y));

	}

	public void robotHold() {
		comWrite(new byte[] { 's', '\r', '\n' });
	}
	
	public double getV() {
		return v;
	}

	public double getW() {
		return w;
	}

	public double getX() {
		return this.x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public double getAngle() {
		return this.angle;
	}

	public void setAngle(double angle) {
		this.angle = normalizeAngle(angle);
	}

	private double normalizeAngle(double angle) {
		while (angle < 0)
			angle += 2 * Math.PI;
		angle %= 2 * Math.PI;
		return angle;
	}

	public long getInterval() {
		// return
		// Long.parseLong(PropertiesManager.getInstance().getProperty("interval"));
		return 100;
	}

	public MovementService getMovementService() {
		return mserv;
	}

	public SensorService getSensorService() {
		return null;
	}

	public boolean obstacleDetected() {
		if (obstacle) {
			obstacle = false;
			return true;
		} else
			return false;
	}
	
	@Override
	public void update(Observable observable, Object data) {
		obstacle = true;
	}
}
