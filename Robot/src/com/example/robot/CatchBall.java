package com.example.robot;

import org.opencv.core.Point;

import android.util.Log;
import android.widget.TextView;

public class CatchBall implements Command {

	double goalX;
	double goalY;
	Robot robot;
	CameraService cserv;
	Point ballPosition;
	boolean caught;
	
	private TextView textLeft;
	private TextView textUpDown;
	private TextView textRight;
	
	private double left;
	private double right;
	private double up;
	private double down;
	
	public CatchBall(Robot robot, double goalX, double goalY) {
		this.goalX = goalX;
		this.goalX = goalX;
		this.robot = robot;
		this.caught = false;		
	}
	
	private void findBall(Robot robot) throws InterruptedException {
		
		int totalRotation = 0;
		
		while(ballPosition.x == 0.00) {
			
			/* get ball position from camera */
			cserv.findBall();
			Thread.sleep(1000);
			ballPosition.x = cserv.ballPosition.x;
			ballPosition.y = cserv.ballPosition.y;
			Log.i("CatchBall", "Ball Position: (" + ballPosition.x + ", " + ballPosition.y + ")");
			
			if(ballPosition.x == 0) {
				/* rotate 1/8 complete rotation if ball is still not in image */
				Command cmdRotate = new AbsoluteRotation((2.0*Math.PI)/8.0, robot);
				Invoker.getInstance().invoke(cmdRotate, robot);
				Log.i("CatchBall", "rotate to find ball");
				totalRotation++;
			}
			
			/* check if robot turned more than 360 degrees */
			if(totalRotation > 8) break;
			
		}
	}
	
	@Override
	public String execute(Robot robot) throws InterruptedException {
		
		Log.i("CatchBall", "Started to catch");
		cserv = cserv.getInstance();
		ballPosition = new Point(0.0, 0.0);
		
		left = robot.getLeft();
		right = robot.getRight();
		up = robot.getUp();
		down = robot.getDown();

		boolean centered = false;
		boolean aligned = false;
		
		/* find ball */
		findBall(robot);
			
		/* center ball */
		while(!centered) {
			
			/* get ball position from camera */
			cserv.findBall();
			Thread.sleep(1000);
			ballPosition.x = cserv.ballPosition.x;
			Log.i("findBall", "Ball Position: (" + ballPosition.x + ", " + ballPosition.y);
			
			if(ballPosition.x < left) {
				
				//MainActivity.setTextVisibility("left");
				Log.i("CatchBall", "left");
			
				/* rotate left if ball is left of border */
				Command cmdRotateLeft = new AbsoluteRotation((2.0*Math.PI)/16.0, robot);
				Invoker.getInstance().invoke(cmdRotateLeft, robot);
				
			} else if(ballPosition.x > right) {
				
				//MainActivity.setTextVisibility("right");
				Log.i("CatchBall", "right");
				
				Command cmdRotateRight = new AbsoluteRotation((-1.0)*(2.0*Math.PI)/16.0, robot);
				Invoker.getInstance().invoke(cmdRotateRight, robot);
				
			} else {
				
				//MainActivity.setTextVisibility("noLeftRight");
				centered = true;
				Log.i("CatchBall", "centered");
				
			}
		}
		
		/* align ball */
		while(!aligned) {
			
			/* get ball position from camera */
			cserv.findBall();
			Thread.sleep(1000);
			ballPosition.y = cserv.ballPosition.y;
			Log.i("findBall", "Ball Position: (" + ballPosition.x + ", " + ballPosition.y);
			
			if(ballPosition.y < up) {
				
				//MainActivity.setTextVisibility("upDown");
				Log.i("CatchBall", "up");
			
				/* go forward 5cm if ball is over border */
				Command cmdForward = new Translation(8.0, robot);
				Invoker.getInstance().invoke(cmdForward, robot);
				
			} else if(ballPosition.y > down) {
				
				//MainActivity.setTextVisibility("upDown");
				Log.i("CatchBall", "down");
			
				/* go back 5cm if ball is over border */
				Command cmdBack = new Translation(-5.0, robot);
				Invoker.getInstance().invoke(cmdBack, robot);
				
			} else {
				
				//MainActivity.setTextVisibility("noUpDown");
				aligned = true;
				Log.i("CatchBall", "aligned");
				
			}		
		}
		
		/* lower latch */
		Log.i("CatchBall", "lowering the latch");
		robot.robotSetBar((byte)0);
		Thread.sleep(1000);
		
		/* go to goal */
		Log.i("CatchBall", "going to goal");
		Command goToGoal = new GoTo(robot, goalX, goalY);
		Invoker.getInstance().invoke(goToGoal, robot);
		
		/* rise latch */
		Log.i("CatchBall", "rising the latch");
		robot.robotSetBar((byte)255);
		Thread.sleep(1000);
		
		/* go home */
		Log.i("CatchBall", "going home");
		Command goToHome = new GoTo(robot, 0.0, 0.0);
		Invoker.getInstance().invoke(goToHome, robot);
		
		/* rotate to zero */
		Log.i("CatchBall", "Rotate to zero");
		Command turnToZero = new AbsoluteRotation(0, robot);
		Invoker.getInstance().invoke(turnToZero, robot);
		
		return null;
		
	}

	@Override
	public boolean isAborted() {
		return false;
	}

}
