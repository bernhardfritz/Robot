package com.example.robot;

import jp.ksksue.driver.serial.FTDriver;

public class AdvancedRobot extends Robot {

	private MovementService mserv;
	private SensorService sserv;
	
	private float x;
	private float y;
	private float degree;
	
	public AdvancedRobot(FTDriver com) {
		super(com);
		this.x=0;
		this.y=0;
		this.degree=0;
		this.mserv=new MovementService();
		this.sserv=new SensorService(this);
	}
	
	public float getV() {
		return Float.parseFloat(PropertiesManager.getInstance().getProperty("v"));
	}
	
	public float getW() {
		return Float.parseFloat(PropertiesManager.getInstance().getProperty("w"));
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getDegree() {
		return this.degree;
	}
	
	public void setX(float x) {
		this.x=x;
	}
	
	public void setY(float y) {
		this.y=y;
	}
	
	public void setDegree(float degree) {
		this.degree=normalizeDegree(degree);
	}
	
	public float normalizeDegree(float degree) {
		while(degree<0) degree+=360;
		degree%=360;
		return degree;
	}
	
	public long getInterval() {
		return Long.parseLong(PropertiesManager.getInstance().getProperty("interval"));
	}
	
	public MovementService getMovementService() {
		return mserv;
	}
	
	public SensorService getSensorService() {
		return null;
	}
	
	public void connect() {
		super.connect();
		mserv.start();
		sserv.start();
	}
	
	public void disconnect() {
		robotHold();
		mserv.stop();
		sserv.stop();
		super.disconnect();
	}

	public void robotDrive(float distance_cm) {
		mserv.addMovement(new Translation(this, distance_cm));
	}

	public void robotTurn(float degree) {
		mserv.addMovement(new Rotation(this,degree));
	}
	
	public void robotGoTo(float x, float y) {
		float gk=x-this.x;
		float ak=y-this.y;
		float degree=normalizeDegree((float)Math.tan(gk/ak));
		float s=(float)Math.sqrt(Math.pow(gk, 2)+Math.pow(ak, 2));
		mserv.addMovement(new Rotation(this, degree));
		mserv.addMovement(new Translation(this, s));
	}
	
	public void robotHold() {
		mserv.interrupt();
	}
}
