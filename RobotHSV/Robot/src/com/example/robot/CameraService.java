package com.example.robot;

import java.util.ArrayList;
import java.util.List;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import android.util.Log;

public class CameraService implements Runnable {
	
	private Robot robot;
	private String TAG = "CameraService";
	
	/* instance variable for singleton */
	private static CameraService instance = null;
	
	/* boolean run variable */
	public static boolean active = false;
	
	/* frames to work with */
	public static Mat originalFrame;
	public static Mat modifiedFrame;
	private Mat workingFrame;
	
	/* for color blob detection */
	public static Scalar blobColorHsv;
	public static boolean blobColorChanged = false;
	private boolean blobColorSet;
	private ColorBlobDetector colorBlobDetector;
	private Scalar CONTOUR_COLOR = new Scalar(255,0,0,255); // red
	private Scalar LOWEST_POINT_COLOR = new Scalar(0,255,0,255); // green
	private Scalar GRID_COLOR = new Scalar(0,0,0,255); // black
	private int GRID_WIDTH = 2;
	
	/* for ball detection */
	public Point ballPosition;
	private double left;
	private double right;
	private double up;
	private double down;
	
	/* private constructor for singleton */
	private CameraService() {}
	
	/* public constructor as singleton */
	public static CameraService getInstance() {
		if(instance == null) {
			instance = new CameraService();
		}
		return instance;
	}
	
	/* function to stop the service */
	public void destroy() {
		Log.i("Camera","CameraService stopped");
		active = false;
	}
	
	public void findBall() {

		/* log message */
		Log.i("CameraService","find function called");
		
		/* get borders */
		//this.left = robot.getLeft();
		//this.right = robot.getRight();
		//this.up = robot.getUp();
		//this.down = robot.getDown();

		/* clone input into frame to work with */
		if(originalFrame != null)
			workingFrame = originalFrame.clone();
		
		/* color blob detection */
		if (blobColorSet && workingFrame != null) {
            
			/* log message */
			Log.i("CameraService","finding  Ball");
			
			/* find contour and draw it */
			colorBlobDetector.process(workingFrame);
            List<MatOfPoint> contours = colorBlobDetector.getContours();
            Imgproc.drawContours(workingFrame, contours, -1, CONTOUR_COLOR);
            
            /* find lowest point of contour and draw it */
            Point lowestPoint = new Point(0.0, 0.0);
            for(MatOfPoint mat : contours) {
            	for(Point pt : mat.toList()) {
            		if(pt.y > lowestPoint.y) {
            			lowestPoint.x = pt.x;
            			lowestPoint.y = pt.y;
            		}
            	}
            }
            Core.circle(workingFrame, lowestPoint, 2, LOWEST_POINT_COLOR, 2);

            /* draw grid */
            //Core.line(workingFrame, new Point(left, 0), new Point(left, workingFrame.height()), GRID_COLOR, GRID_WIDTH); // vertical left
            //Core.line(workingFrame, new Point(right, 0), new Point(right, workingFrame.height()), GRID_COLOR, GRID_WIDTH); // vertical right
            //Core.line(workingFrame, new Point(0, up), new Point(workingFrame.width(), up), GRID_COLOR, GRID_WIDTH); // horizontal top
            //Core.line(workingFrame, new Point(0, down), new Point(workingFrame.width(), down), GRID_COLOR, GRID_WIDTH); // horizontal up
            
			/* set postition of ball */
			ballPosition.x = lowestPoint.x;
			ballPosition.y = lowestPoint.y;
			
        }
		
		/* clone working frame into output frame */
		if(workingFrame != null)
			modifiedFrame = workingFrame.clone();
			
	}
	
	public void run() {
		
		Log.i("Camera","CameraService started");
		active = true;

		/* start a new colorBlobDetector */
		colorBlobDetector = new ColorBlobDetector();
		blobColorSet = false;
		
		/* reset on start */
		ballPosition = new Point(0.0, 0.0);
		workingFrame = null;
		modifiedFrame = null;
		
		/* set a new color if it was changed */
		if(blobColorChanged) {
			blobColorSet = colorBlobDetector.setHsvColor(blobColorHsv);
			blobColorChanged = false;
			Log.i(TAG, "set a new color for blob-detector: " + blobColorHsv);
		}
		
		while(active) {
			
		}
		
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}
	
}
