package com.example.robot;

import java.util.Observable;

import org.opencv.core.*;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class CameraService extends Observable implements Runnable {
	private Robot robot;
	private boolean active;
	private int numberOfCircles;
	private VideoCapture camera;

	public CameraService(Robot robot) {
		this.robot = robot;
		addObserver(robot);
		active = true;		
		numberOfCircles = 0;
		setupCamera();
	}
	
	private void setupCamera() {
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
		camera = new VideoCapture();
		/* try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    if(!camera.isOpened()){
	        System.out.println("Camera Error");
	    }
	    else{
	        System.out.println("Camera OK?");
	    } */
	}
	
	private void getFrame() {
		Mat cameraFrame = new Mat();
		Mat circles = new Mat();
		
		camera.read(cameraFrame);
		// cameraFrame = cameraFrame.gray(); <- convert to gray
    	
    	Imgproc.GaussianBlur(cameraFrame, cameraFrame, new Size(9, 9), 2, 2);
    	Imgproc.HoughCircles(cameraFrame, circles, Imgproc.CV_HOUGH_GRADIENT, 2, cameraFrame.rows()/8, 200, 100, 0, 0 );
    	
    	if (circles.cols() != numberOfCircles)
    		numberOfCircles = circles.cols();
	}

	private void update() throws InterruptedException {
		
		//???
		
		if (true) {
			setChanged();
			notifyObservers(true);
		} else {
			setChanged();
			notifyObservers(false);
		}
	}

	public void destroy() {
		active = false;
	}

	@Override
	public void run() {
		System.out.println("cameraservice started");
		while (active) {
			try {
				//update();
				getFrame();
				Thread.sleep(robot.getInterval());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("cameraservice stopped");
	}
}
