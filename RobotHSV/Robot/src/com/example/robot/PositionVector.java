package com.example.robot;

public class PositionVector {

	private double x;
	private double y;
	private double theta;

	public PositionVector() {
		this.x = 0.0;
		this.y = 0.0;
		this.theta = 0.0;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getTheta() {
		return theta;
	}

	public void setPosition(double x, double y, double theta) {
		this.x = x;
		this.y = y;
		this.theta = theta;
	}

}
