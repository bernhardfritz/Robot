package com.example.robot;

import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class HsvSelector implements OnSeekBarChangeListener {
	
	/* instance for singleton */
	private static HsvSelector instance = null;
	
	/* variables for color blob */
	ColorBlobDetector colorBlobDetector;
	Scalar color;
	
	/* variable to start/end the hsv selector */
	private boolean running = false;
	
	/* private constructor for singleton */
	private HsvSelector() {}
	
	/* get instance for singleton */
	public static HsvSelector getInstance() {
		if(instance == null) {
			instance = new HsvSelector();
		}
		return instance;
	}
	
	/* start the selector */
	public void start() {
		running = true;
		colorBlobDetector = new ColorBlobDetector();
		color = new Scalar(0,0,0,0);
		Log.i("HsvSelector", "HsvSelector started");
	}
	
	/* stop the selector */
	public void stop() {
		running = false;
		Log.i("HsvSelector", "HsvSelector stopped");
	}
	
	/* show if it is started */
	public boolean isRunning() {
		if(running) {
			return true;
		} else {
			return false;
		}
	}
	
	/* set a new color */
	public void setColor(Scalar hsvColor) {
		Log.i("HsVSelector", "Detector set to: (" + color.val[0] + ", " + color.val[1] + ", " + color.val[2] + ", " + color.val[3] + ")");
		color = hsvColor;
		colorBlobDetector.setHsvColor(color);
	}

	/* process the new frame */
	public Mat process(Mat inputFrame) {
		
		Scalar CONTOUR_COLOR = new Scalar(255,255,0,255);
				
		colorBlobDetector.process(inputFrame);
        List<MatOfPoint> contours = colorBlobDetector.getContours();
        Imgproc.drawContours(inputFrame, contours, -1, CONTOUR_COLOR);
        
        String hsvString = "HSV: (" + color.val[0] + ", " + color.val[1] + ", " + color.val[2] + ")";
        Core.putText(inputFrame, hsvString, new Point(20, 40), 2, 1, new Scalar(0, 0, 0, 255), 2);
		
	    return inputFrame;
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		
		if(running) {
			
			if(seekBar.getTag().equals("BarH")) {
				color.val[0] = (double)seekBar.getProgress() / 1000.0;
			} else if (seekBar.getTag().equals("BarS")) {
				color.val[1] = (double)seekBar.getProgress() / 1000.0;
			} else if (seekBar.getTag().equals("BarV")) {
				color.val[2] = (double)seekBar.getProgress() / 1000.0;
			}

			setColor(color);	
			
		}
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	
}
