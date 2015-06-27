package org.opencv.samples.colorblobdetect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.ksksue.driver.serial.FTDriver;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;

import com.example.robot.AbsoluteRotation;
import com.example.robot.Bar;
import com.example.robot.Command;
import com.example.robot.GoTo;
import com.example.robot.Invoker;
import com.example.robot.RelativeRotation;
import com.example.robot.Robot;
import com.example.robot.Translation;

import android.app.Activity;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

public class ColorBlobDetectionActivity extends Activity implements OnTouchListener, CvCameraViewListener2 {
    private static final String  TAG              = "OCVSample::Activity";

    private boolean              mIsColorSelected = false;
    private Mat                  mRgba;
    private Scalar               mBlobColorRgba;
    private Scalar               mBlobColorHsv;
    private ColorBlobDetector    mDetector;
    private Mat                  mSpectrum;
    private Size                 SPECTRUM_SIZE;
    private Scalar               CONTOUR_COLOR;

    private CameraBridgeViewBase mOpenCvCameraView;
    
	private Robot robot;
	
	private List<Beacon> beaconList;
	private List<ColorBlob> ballList;
	
	private Point target = null;
	
	private boolean selfLocalisationDone = false;
	private boolean ballFound = false;
	
	private final int MAX_COUNT = 10;
	private int count = MAX_COUNT + 1;
	private List<List<Beacon>> beaconListList;
	private boolean doBeaconDetection = false;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.setOnTouchListener(ColorBlobDetectionActivity.this);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public ColorBlobDetectionActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.color_blob_detection_surface_view);
        
        robot = new Robot(new FTDriver((UsbManager) getSystemService(USB_SERVICE)));
        
//        beaconList = new ArrayList<Beacon>();
//        initBeaconList();
//        
//        ballList = new ArrayList<ColorBlob>();
        initBallList();
        
        target = new Point(100.0, 100.0);
        
//        beaconListList = new ArrayList<List<Beacon>>();

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.color_blob_detection_activity_surface_view);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
    private void initBeaconList() {
//    	ColorBlob topBlob = new ColorBlob(Color.RED);
//    	ColorBlob bottomBlob = new ColorBlob(Color.YELLOW);
//    	Point position = new Point(-125.0, 125.0);
//    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
//    	
//    	topBlob = new ColorBlob(Color.BLUE);
//    	bottomBlob = new ColorBlob(Color.GREEN);
//    	position = new Point(0.0, 125.0);
//    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
//    	
//    	topBlob = new ColorBlob(Color.RED);
//    	bottomBlob = new ColorBlob(Color.BLUE);
//    	position = new Point(125.0, 125.0);
//    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
//    	
//    	topBlob = new ColorBlob(Color.BLUE);
//    	bottomBlob = new ColorBlob(Color.YELLOW);
//    	position = new Point(125.0, 0.0);
//    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
//    	
//    	topBlob = new ColorBlob(Color.YELLOW);
//    	bottomBlob = new ColorBlob(Color.RED);
//    	position = new Point(125.0, -125.0);
//    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
//    	
//    	topBlob = new ColorBlob(Color.GREEN);
//    	bottomBlob = new ColorBlob(Color.BLUE);
//    	position = new Point(0.0, -125.0);
//    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
//    	
//    	topBlob = new ColorBlob(Color.BLUE);
//    	bottomBlob = new ColorBlob(Color.RED);
//    	position = new Point(-125.0, -125.0);
//    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
//    	
//    	topBlob = new ColorBlob(Color.YELLOW);
//    	bottomBlob = new ColorBlob(Color.GREEN);
//    	position = new Point(-125.0, 0.0);
//    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
    	
    	ColorBlob topBlob = new ColorBlob(Color.BLUE);
    	ColorBlob bottomBlob = new ColorBlob(Color.RED);
    	Point position = new Point(-125.0, 125.0);
    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
    	
    	topBlob = new ColorBlob(Color.BLUE);
    	bottomBlob = new ColorBlob(Color.GREEN);
    	position = new Point(0.0, 125.0);
    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
    	
//    	topBlob = new ColorBlob(Color.MAGENTA);
//    	bottomBlob = new ColorBlob(Color.ORANGE);
//    	position = new Point(125.0, 125.0);
//    	beaconList.add(new Beacon(topBlob, bottomBlob, position));
    }
    
    private void initBallList() {
//    	ballList.add(new ColorBlob(Color.ORANGE));
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.connect:
			robot.connect();
			return true;
		case R.id.disconnect:
			robot.disconnect();
			return true;
		case R.id.calcHomography:
			mDetector.calcHomographyMatrix(mRgba);
			return true;
		case R.id.taskAction:
//			if (!mDetector.isHomographyDone()) {
//				System.out.println("Homography matrix not calculated");
//			}
//			else {
				doBeaconAction();
//			}
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void test() {
		Beacon beacon1 = new Beacon(new ColorBlob(Color.GREEN), new ColorBlob(Color.RED), new Point(0.0, 100.0));
		Beacon beacon2 = new Beacon(new ColorBlob(Color.RED), new ColorBlob(Color.GREEN), new Point(100.0, 100.0));
		System.out.println("Robot Position: "  + mDetector.getRobotPosition(beacon1, beacon2));
//		System.out.println("Robot Angle: " + mDetector.getRobotAngle(beacon1, beacon2));
	}
	
	private void taskAction() {
		while (!selfLocalisationDone) {
			searchForBeacons();
			try {
				Invoker.getInstance().invoke(new Translation(50.0, robot), robot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		while (!ballFound) {
			searchForBalls();
			try {
				Invoker.getInstance().invoke(new Translation(50.0, robot), robot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void searchForBeacons() {
		System.out.println("Beacon Search Start");
		
		for (int i = 0; i < 8; i++) {
			doBeaconAction();
			
			if (selfLocalisationDone) {
				break;
			}
			
			try {
				Invoker.getInstance().invoke(new RelativeRotation(Math.PI/4.0, robot), robot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Beacon Search End");
	}
	
	private void doBeaconAction() {
//		beaconListList.clear();
//		count = 0;
		List<Point> destPoints = new ArrayList<Point>();
		destPoints.add(new Point(62.5, 62.5));
		destPoints.add(new Point(62.5, 187.5));
		destPoints.add(new Point(187.5, 187.5));
		destPoints.add(new Point(187.5, 62.5));
		
//		Scalar ballColor = new Scalar(15, 223, 255);
		Scalar ballColor = new Scalar(247, 136, 245);
		
		for (int i = 0;; i++) {
			
			catchBall(ballColor);
			
			try {
				Invoker.getInstance().invoke(new GoTo(robot, destPoints.get(i).x, destPoints.get(i).y), robot);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (i == 3) {
				i = 0;
			}
		}
	}

	private void catchBall(Scalar ballColor) {
		mDetector.setHsvColor(ballColor);
    	Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);
        mDetector.process(mRgba);
        
        double distance = mDetector.getDestinationDistance();
		double angle = mDetector.getDestinationAngle();
		
		if (distance > 0 && angle > 0) {
			Command cmd0 = new Bar((byte) 255);
			Command cmd1 = new RelativeRotation(angle, robot);
			Command cmd2 = new Translation(distance - 5.0, robot);
			Command cmd3 = new Bar((byte) 0);
			Command cmd4 = new GoTo(robot, target.x, target.y);
			Command cmd5 = new Bar((byte) 255);
			Command cmd6 = new RelativeRotation(Math.PI, robot);
			
			try {
				Invoker.getInstance().invoke(cmd0, robot);
				Invoker.getInstance().invoke(cmd1, robot);
				Invoker.getInstance().invoke(cmd2, robot);
				Invoker.getInstance().invoke(cmd3, robot);
				Invoker.getInstance().invoke(cmd4, robot);
				Invoker.getInstance().invoke(cmd5, robot);
				Invoker.getInstance().invoke(cmd6, robot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<Beacon> beaconActionImplementation() {
		for (Color color : Color.values()) {
			mDetector.setHsvColor(color.hsvValue());
	    	Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);
	        mDetector.process(mRgba);
	        mDetector.doContourDetection(mDetector.getContours(), color);
		}
		
		List<Beacon> beacons = mDetector.detectBeacons();
		
        // nur Beacons behandeln, die auch in beaconList vorhanden sind
        beacons.retainAll(beaconList);
        
        return beacons;
	}
	
	private void searchForBalls() {
		System.out.println("Ball Search Start");
		
		for (int i = 0; i < 8; i++) {
			doBallAction();
			
			if (ballFound) {
				break;
			}
			
			try {
				Invoker.getInstance().invoke(new RelativeRotation(Math.PI/4.0, robot), robot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Ball Search End");
	}
	
	private void doBallAction() {
		for (Color color : Color.values()) {
			System.out.println("Color " + color + ":");
			mDetector.setHsvColor(color.hsvValue());
	    	Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);
	        mDetector.process(mRgba);
	        mDetector.doContourDetection(mDetector.getContours(), color);
		}
		// Beacons aus mDetector.colorBlobs entfernen
		mDetector.detectBeacons();
		List<ColorBlob> balls = mDetector.getColorBlobs();
		
		// TODO: Workspace Exploration
//		if (balls.isEmpty()) {
//			exploreWorkspace();
//		}
		
		System.out.println("Ball count: " + balls.size());
		for (ColorBlob ball : balls) {
			System.out.println("Ball Detection Point: Distance = " + mDetector.getObjectValue(ball.getBottom(), true, false) + 
					"; Angle = " + mDetector.getObjectValue(ball.getBottom(), false, true));
			System.out.println("Ball color: " + ball.getColor());
		}
		// nur Balls behandeln, die auch in ballList vorhanden sind
		balls.retainAll(ballList);
		
		if (!balls.isEmpty()) {
			ColorBlob ball = balls.get(0);
			double distance = mDetector.getObjectValue(ball.getBottom(), true, false);
			double angle = mDetector.getObjectValue(ball.getBottom(), false, true);
			
			Command cmd0 = new Bar((byte) 255);
			Command cmd1 = new RelativeRotation(angle, robot);
			Command cmd2 = new Translation(distance - 5.0, robot);
			Command cmd3 = new Bar((byte) 0);
			Command cmd4 = new GoTo(robot, target.x, target.y);
			Command cmd5 = new Bar((byte) 255);
			Command cmd6 = new RelativeRotation(Math.PI, robot);
			
			try {
				Invoker.getInstance().invoke(cmd0, robot);
				Invoker.getInstance().invoke(cmd1, robot);
				Invoker.getInstance().invoke(cmd2, robot);
				Invoker.getInstance().invoke(cmd3, robot);
				Invoker.getInstance().invoke(cmd4, robot);
				Invoker.getInstance().invoke(cmd5, robot);
				Invoker.getInstance().invoke(cmd6, robot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ballFound = true;
		}
	}

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mDetector = new ColorBlobDetector();
        mSpectrum = new Mat();
        mBlobColorRgba = new Scalar(255);
        mBlobColorHsv = new Scalar(255);
        SPECTRUM_SIZE = new Size(200, 64);
        CONTOUR_COLOR = new Scalar(255,0,0,255);
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public boolean onTouch(View v, MotionEvent event) {
//        int cols = mRgba.cols();
//        int rows = mRgba.rows();
//
//        int xOffset = (mOpenCvCameraView.getWidth() - cols) / 2;
//        int yOffset = (mOpenCvCameraView.getHeight() - rows) / 2;
//
//        int x = (int)event.getX() - xOffset;
//        int y = (int)event.getY() - yOffset;
//
//        Log.i(TAG, "Touch image coordinates: (" + x + ", " + y + ")");
//
//        if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) return false;
//
//        Rect touchedRect = new Rect();
//
//        touchedRect.x = (x>4) ? x-4 : 0;
//        touchedRect.y = (y>4) ? y-4 : 0;
//
//        touchedRect.width = (x+4 < cols) ? x + 4 - touchedRect.x : cols - touchedRect.x;
//        touchedRect.height = (y+4 < rows) ? y + 4 - touchedRect.y : rows - touchedRect.y;
//
//        Mat touchedRegionRgba = mRgba.submat(touchedRect);
//
//        Mat touchedRegionHsv = new Mat();
//        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);
//
//        // Calculate average color of touched region
//        mBlobColorHsv = Core.sumElems(touchedRegionHsv);
//        int pointCount = touchedRect.width*touchedRect.height;
//        for (int i = 0; i < mBlobColorHsv.val.length; i++)
//            mBlobColorHsv.val[i] /= pointCount;
//
//        mBlobColorRgba = converScalarHsv2Rgba(mBlobColorHsv);
//
//        Log.i(TAG, "Touched rgba color: (" + mBlobColorRgba.val[0] + ", " + mBlobColorRgba.val[1] +
//                ", " + mBlobColorRgba.val[2] + ", " + mBlobColorRgba.val[3] + ")");

//        mDetector.setHsvColor(mBlobColorHsv);
//        Scalar green = new Scalar(107.75, 255.0, 106.33);
//        Scalar red = new Scalar(255.0, 216.47, 177.97);
//        mDetector.setHsvColor(green);

//        mIsColorSelected = true;
    	
//    	mDetector.setHsvColor(ballColor);
//    	Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);
//        mDetector.process(mRgba);
//        System.out.println("Ball Detection Point: Distance = " + mDetector.getDestinationDistance() + "; Angle = " + mDetector.getDestinationAngle());

//        touchedRegionRgba.release();
//        touchedRegionHsv.release();

        return false; // don't need subsequent touch events
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();

//        for (Color col : Color.values()) {
//        	mDetector.setHsvColor(col.hsvValue());
//            mDetector.process(mRgba);
//            List<MatOfPoint> contours = mDetector.getContours();
//            //Imgproc.drawContours(mRgba, contours, -1, converScalarHsv2Rgba(col.hsvValue()));
//            
//            for(MatOfPoint points: contours) {
//            	Rect rect = Imgproc.boundingRect(points);
//        	
//            	if(rect.height > rect.width && rect.height*rect.width > 20*30) {	
//            		Core.rectangle(mRgba, new Point(rect.x, rect.y), new Point(rect.x+rect.width, rect.y+rect.height), new Scalar(255, 255, 0, 0), 1);
//            	}
//            }
//        }
//        
//        if (count < MAX_COUNT) {
//        	List<Beacon> beaconsTemp = beaconActionImplementation();
//        	beaconListList.add(beaconsTemp);
//        	count++;
//        }
//        else if (count == MAX_COUNT) {
//        	List<Beacon> beacons = beaconListList.get(0);
//        	for (List<Beacon> bl : beaconListList) {
////        		beacons.retainAll(bl);
//        		for (Beacon b : bl) {
//        			if (!beacons.contains(b)) {
//        				beacons.add(b);
//        			}
//        		}
//        	}
//        	
//        	System.out.println("Beacon count: " + beacons.size());
//            for (Beacon b : beacons) {
//            	System.out.println("Beacon top color: " + b.getTopBlob().getColor());
//            	System.out.println("Beacon bottom color: " + b.getBottomBlob().getColor());
//            	System.out.println("Beacon bottomm point: " + b.getBottomBlob().getBottom());
//            }
//        	
//        	if (beacons.size() >= 2) {
//            	Beacon beacon1 = beacons.get(0);
//            	Beacon beacon2 = beacons.get(1);
//            	
//            	// beacon1 muss immer das linke Beacon, beacon2 immer das rechte Beacon sein
//            	if (beacon2.getBottomBlob().getBottom().x < beacon1.getBottomBlob().getBottom().x) {
//            		beacon1 = beacons.get(1);
//            		beacon2 = beacons.get(0);
//            	}
//            	
//            	beacon1.setPosition(beaconList.get(beaconList.indexOf(beacon1)).getPosition());
//            	beacon2.setPosition(beaconList.get(beaconList.indexOf(beacon2)).getPosition());
//            	
//    	        Point robotPosition = mDetector.getRobotPosition(beacon1, beacon2);
//    	        System.out.println("Robot Position: " + robotPosition);
//    	        
//    	        double robotAngle = mDetector.getRobotAngle(beacon1, beacon2);
//    	        System.out.println("Robot Angle: " + Math.toDegrees(robotAngle) + "°");
//    	        
//    	        selfLocalisationDone = true;
//            }
//        	
//        	count++;
//        }

        return mRgba;
    }

    private Scalar converScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }
}