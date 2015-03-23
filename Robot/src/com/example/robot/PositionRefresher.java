package com.example.robot;

public class PositionRefresher {

	private double DIAMETER = 0.1;
	private int TICKSPERREVOLUTION = 360;
	private double B = 0.1;
	
	public double calculateDelta(int tick) {
		
		double delta = 0;
		double circumference = Math.PI * DIAMETER;
		
		delta = (circumference / TICKSPERREVOLUTION) * tick;
		
		return delta;
		
	}
	
	public void move(PositionVector vector, int ticksL, int ticksR) {
		
		double x = vector.getX();
		double y = vector.getY();
		double theta = vector.getTheta();
		
		double deltaL = calculateDelta(ticksL);
		double deltaR = calculateDelta(ticksR);
		
		double deltaS = (deltaL + deltaR) / 2;
		double deltaTheta = (deltaL - deltaR) / B;

		x += deltaS * Math.cos((theta + (deltaTheta / 2)) * (Math.PI/180));
		y += deltaS * Math.sin((theta + (deltaTheta / 2)) * (Math.PI/180));
		theta += deltaTheta;
		theta %= 360;
		
		vector.setPosition(x, y, theta);
		
	}
}
