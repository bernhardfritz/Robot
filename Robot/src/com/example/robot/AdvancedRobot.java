package com.example.robot;

import java.util.Observable;
import java.util.Observer;

import jp.ksksue.driver.serial.FTDriver;

public class AdvancedRobot extends Robot implements Observer{

	private MovementService mserv;
	private SensorService sserv;
	
	private float x;
	private float y;
	private float degree;
	private boolean obstacle;
	
	public AdvancedRobot(FTDriver com) {
		super(com);
		this.x=0;
		this.y=0;
		this.degree=0;
		this.obstacle=false;
		this.mserv=new MovementService();
		this.sserv=new SensorService(this);
	}
	
	public float getV() {
		//return Float.parseFloat(PropertiesManager.getInstance().getProperty("v"));
		return 28.25f;
	}
	
	public float getW() {
		//return Float.parseFloat(PropertiesManager.getInstance().getProperty("w"));
		return 87.8f;
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
		//return Long.parseLong(PropertiesManager.getInstance().getProperty("interval"));
		return 100L;
	}
	
	public MovementService getMovementService() {
		return mserv;
	}
	
	public SensorService getSensorService() {
		return null;
	}
	
	public void connect() {
		super.connect();
		Thread movementThread = new Thread(mserv);
		//Thread sensorThread = new Thread(sserv);
		movementThread.start();
		//sensorThread.start();
	}
	
	public void disconnect() {
		mserv.destroy();
		sserv.destroy();
		super.disconnect();
	}
	
	public boolean obstacleDetected() {
		if (obstacle) {
			obstacle=false;
			return true;
		} else return false; 
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
		comWrite(new byte[] { 's', '\r', '\n' });
	}

	@Override
	public void update(Observable observable, Object data) {
		obstacle=true;
	}
}
