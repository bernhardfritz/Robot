package com.example.robot;

import jp.ksksue.driver.serial.FTDriver;

public class AdvancedRobot extends Robot {

	private MovementService mserv;
	private SensorService sserv;
	public AdvancedRobot(FTDriver com) {
		super(com);
		this.mserv=new MovementService();
		this.sserv=new SensorService(this);
	}
	
	public float getV() {
		return Float.parseFloat(PropertiesManager.getInstance().getProperty("v"));
	}
	
	public float getW() {
		return Float.parseFloat(PropertiesManager.getInstance().getProperty("w"));
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
		mserv.stop();
		sserv.stop();
		super.disconnect();
	}

	public void robotDrive(float distance_cm) {
		
	}

	public void robotTurn(float degree) {
		
	}
}
