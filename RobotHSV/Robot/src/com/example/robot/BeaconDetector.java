package com.example.robot;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import android.util.Log;

public class BeaconDetector {

	public static Mat process(Mat inputFrame) {
		
		ColorBlobDetector colorBlobDetector = new ColorBlobDetector();
		
		Scalar CONTOUR_COLOR_RED = new Scalar(0,0,0,255);
		Scalar CONTOUR_COLOR_BLUE = new Scalar(255,255,255,255);
		Scalar CONTOUR_COLOR_YELLOW = new Scalar(125,125,125,255);
		Scalar CONTOUR_COLOR_GREEN = new Scalar(255,0,255,255);
		Scalar CONTOUR_COLOR_VIOLET = new Scalar(0,255,255,255);
		
		Scalar hsvRed = new Scalar(4.59375, 235.8073, 194.8885, 0.0);
		Scalar hsvBlue = new Scalar(139.5820, 240.6171, 171.3359, 0.0);
		Scalar hsvYellow = new Scalar(40.3438, 255.0, 164.5781, 0.0);
		Scalar hsvGreen = new Scalar(97.5664, 253.8203, 156.0625, 0.0);
		Scalar hsvViolet = new Scalar(197.9896, 112.3177, 145.1875, 0.0);
		
		Mat modifiedFrame = null;
		Mat workingFrame = null;
		
		if(inputFrame != null)
			workingFrame = inputFrame.clone();
		
		/* RED */
		if(workingFrame != null) {
			colorBlobDetector.setHsvColor(hsvRed);
			colorBlobDetector.process(workingFrame);
	        List<MatOfPoint> contoursRed = colorBlobDetector.getContours();
            Imgproc.drawContours(workingFrame, contoursRed, -1, CONTOUR_COLOR_RED);
		}

		/* BLUE */
		if(workingFrame != null) {
			colorBlobDetector.setHsvColor(hsvBlue);
			colorBlobDetector.process(workingFrame);
	        List<MatOfPoint> contoursBlue = colorBlobDetector.getContours();
            Imgproc.drawContours(workingFrame, contoursBlue, -1, CONTOUR_COLOR_BLUE);
		}
        
        /* YELLOW */
		if(workingFrame != null) {
			colorBlobDetector.setHsvColor(hsvYellow);
			colorBlobDetector.process(workingFrame);
	        List<MatOfPoint> contoursYellow = colorBlobDetector.getContours();
            Imgproc.drawContours(workingFrame, contoursYellow, -1, CONTOUR_COLOR_YELLOW);
		}
		
        /* GREEN */
		if(workingFrame != null) {
			colorBlobDetector.setHsvColor(hsvGreen);
			colorBlobDetector.process(workingFrame);
	        List<MatOfPoint> contoursGreen = colorBlobDetector.getContours();
            Imgproc.drawContours(workingFrame, contoursGreen, -1, CONTOUR_COLOR_GREEN);
		}
		
        /* VIOLET */
		if(workingFrame != null) {
			colorBlobDetector.setHsvColor(hsvViolet);
			colorBlobDetector.process(workingFrame);
	        List<MatOfPoint> contoursViolet = colorBlobDetector.getContours();
            Imgproc.drawContours(workingFrame, contoursViolet, -1, CONTOUR_COLOR_VIOLET);
		}
		
		if(workingFrame != null) {
			modifiedFrame = workingFrame.clone();
			return modifiedFrame;
		} else {
	        return inputFrame;
		} 
		
	}
}
