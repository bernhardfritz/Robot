package com.example.robot;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import jp.ksksue.driver.serial.FTDriver;
import android.app.Activity;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements CvCameraViewListener2, OnTouchListener {

	private static final String TAG = "Robot";
	
	private Robot robot;
	private MyOnClickListener myOnClickListener;

	private ToggleButton toggleBlob;
	
	/* for camera support */
	private CameraBridgeViewBase mOpenCvCameraView;
	
	/* for color blob */
	private Mat mRgba;
	private Scalar blobColorHsv;
	private Scalar blobColorRgba;
	
	/* Hsv Selector */
	HsvSelector hsvSelector;
	private SeekBar seekBarH;
	private SeekBar seekBarS;
	private SeekBar seekBarV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_main);

		robot = new Robot(new FTDriver(
				(UsbManager) getSystemService(USB_SERVICE)));
		myOnClickListener = new MyOnClickListener(robot);

		toggleBlob = (ToggleButton) findViewById(R.id.toggleBlob);
		toggleBlob.setOnClickListener(myOnClickListener);

		/* for camera support */
		mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.camera_surface_view);
		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		mOpenCvCameraView.setCvCameraViewListener(this);
		
		/* for hsv selector */
		hsvSelector = HsvSelector.getInstance();
		
		seekBarH = (SeekBar) findViewById(R.id.seekBar1);
		seekBarS = (SeekBar) findViewById(R.id.seekBar2);
		seekBarV = (SeekBar) findViewById(R.id.seekBar3);
		
		seekBarH.setOnSeekBarChangeListener(hsvSelector);
		seekBarS.setOnSeekBarChangeListener(hsvSelector);
		seekBarV.setOnSeekBarChangeListener(hsvSelector);
		
		//buttonCatchBall.setOnClickListener(myOnClickListener);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static void log(String s) {
		//textLog.append(s);
	}

	public static void sensorLog(String s) {
		//sensorLog.setText("");
		//sensorLog.append(s);
	}

	/* Camera function */
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG,"OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.setOnTouchListener(MainActivity.this);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
	
    /* Camera function */
    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }
    
    /* Camera function */
	@Override
	public void onCameraViewStarted(int width, int height) { 
		mRgba = new Mat(height, width, CvType.CV_8UC4);
	}

	/* Camera function */
	@Override
	public void onCameraViewStopped() { 
		mRgba.release();
	}

	/* Camera function */
	@Override
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		
		if(hsvSelector.isRunning()) {
			mRgba = inputFrame.rgba();
			return hsvSelector.process(inputFrame.rgba());
		} else {
			return inputFrame.rgba();
		}
		
	}

	/* onTouch listener:
	 * before the robot is connected and the camera service is running
	 * a new color can be defined with a click event.
	 * code mainly similar to the example
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
	    
		if(hsvSelector.isRunning()) {
			
			/* calculate ratio between image-size and view-size */
			double ratioX = mRgba.cols() / (double)mOpenCvCameraView.getWidth();
			double ratioY = mRgba.rows() / (double)mOpenCvCameraView.getHeight();
			
			/* get location of touch */
			int x = (int)(event.getX() * ratioX);
	        int y = (int)(event.getY() * ratioY);
	
	        /* log touched coordinates */
	        Log.i(TAG, "Touched on coordinates: (" + x + ", " + y + ")");
	        
	        /* check if the touch was outside the picture */
	        if ((x < 0) || (y < 0) || (x > mRgba.cols()) || (y > mRgba.rows())) return false;
	        
	        /* create a rectangle */
	        Rect touchedRect = new Rect();
	
	        /* set location of top left corner */
	        touchedRect.x = (x>4) ? x-4 : 0;
	        touchedRect.y = (y>4) ? y-4 : 0;
	
	        /* set width of rectangle */
	        touchedRect.width = (x+4 < mRgba.cols()) ? x + 4 - touchedRect.x : mRgba.cols() - touchedRect.x;
	        touchedRect.height = (y+4 < mRgba.rows()) ? y + 4 - touchedRect.y : mRgba.rows() - touchedRect.y;
	        
	        /* extract a rectangular submatrix out of the picture */
	        Mat touchedRegionRgba = mRgba.submat(touchedRect);
	        
	        /* convert the submatrix to HSV-Color-Space */
	        Mat touchedRegionHsv = new Mat();
	        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);
	        
	        /* calculate average color of touched region */
	        /* sum up values for h, s and v */
	        blobColorHsv = Core.sumElems(touchedRegionHsv);
	        int pointCount = touchedRect.width*touchedRect.height;
	        /* calculate average for h, s and v */
	        for (int i = 0; i < blobColorHsv.val.length; i++)
	        	blobColorHsv.val[i] /= pointCount;
	        
	        /* log touched color */
	        Log.i(TAG, "Touched HSV-Color: (" + blobColorHsv.val[0] + ", " + blobColorHsv.val[1] +
	                ", " + blobColorHsv.val[2] + ", " + blobColorHsv.val[3] + ")");
	        
	        /* set the new color for hsv selector */
	        hsvSelector.setColor(blobColorHsv);
	        seekBarH.setProgress((int)blobColorHsv.val[0] * 1000);
	        seekBarS.setProgress((int)blobColorHsv.val[1] * 1000);
	        seekBarV.setProgress((int)blobColorHsv.val[2] * 1000);
	        
		}
		
		return false;
		
	}

	
}
