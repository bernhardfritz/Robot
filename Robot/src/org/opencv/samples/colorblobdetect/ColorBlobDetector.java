package org.opencv.samples.colorblobdetect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ColorBlobDetector {
    // Lower and Upper bounds for range checking in HSV color space
    private Scalar mLowerBound = new Scalar(0);
    private Scalar mUpperBound = new Scalar(0);
    // Minimum contour area in percent for contours filtering
    private static double mMinContourArea = 0.1;
    // Color radius for range checking in HSV color space
    private Scalar mColorRadius = new Scalar(25,50,50,0);
//    private Scalar mColorRadius = new Scalar(25,25,25,0);
    private Mat mSpectrum = new Mat();
    private List<MatOfPoint> mContours = new ArrayList<MatOfPoint>();
    
    private Mat homography = null;
    
    private double destinationDistance;
    private double destinationAngle;
    
    private boolean ballDetected = false;
    
    private List<ColorBlob> colorBlobs = new ArrayList<ColorBlob>();
    
    private Scalar hsvColor = null;

    // Cache
    Mat mPyrDownMat = new Mat();
    Mat mHsvMat = new Mat();
    Mat mMask = new Mat();
    Mat mDilatedMask = new Mat();
    Mat mHierarchy = new Mat();

    public void setColorRadius(Scalar radius) {
        mColorRadius = radius;
    }

    public void setHsvColor(Scalar hsvColor) {
    	this.hsvColor = hsvColor.clone();
    	
        double minH = (hsvColor.val[0] >= mColorRadius.val[0]) ? hsvColor.val[0]-mColorRadius.val[0] : 0;
        double maxH = (hsvColor.val[0]+mColorRadius.val[0] <= 255) ? hsvColor.val[0]+mColorRadius.val[0] : 255;

        mLowerBound.val[0] = minH;
        mUpperBound.val[0] = maxH;

        mLowerBound.val[1] = hsvColor.val[1] - mColorRadius.val[1];
        mUpperBound.val[1] = hsvColor.val[1] + mColorRadius.val[1];

        mLowerBound.val[2] = hsvColor.val[2] - mColorRadius.val[2];
        mUpperBound.val[2] = hsvColor.val[2] + mColorRadius.val[2];

        mLowerBound.val[3] = 0;
        mUpperBound.val[3] = 255;

        Mat spectrumHsv = new Mat(1, (int)(maxH-minH), CvType.CV_8UC3);

        for (int j = 0; j < maxH-minH; j++) {
            byte[] tmp = {(byte)(minH+j), (byte)255, (byte)255};
            spectrumHsv.put(0, j, tmp);
        }

        Imgproc.cvtColor(spectrumHsv, mSpectrum, Imgproc.COLOR_HSV2RGB_FULL, 4);
    }

    public Mat getSpectrum() {
        return mSpectrum;
    }

    public void setMinContourArea(double area) {
        mMinContourArea = area;
    }

    public void process(Mat rgbaImage) {    	
        Imgproc.pyrDown(rgbaImage, mPyrDownMat);
        Imgproc.pyrDown(mPyrDownMat, mPyrDownMat);

        Imgproc.cvtColor(mPyrDownMat, mHsvMat, Imgproc.COLOR_RGB2HSV_FULL);

        Core.inRange(mHsvMat, mLowerBound, mUpperBound, mMask);
        Imgproc.dilate(mMask, mDilatedMask, new Mat());

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Imgproc.findContours(mDilatedMask, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Find max contour area
        double maxArea = 0;
        Iterator<MatOfPoint> each = contours.iterator();
        while (each.hasNext()) {
            MatOfPoint wrapper = each.next();
            double area = Imgproc.contourArea(wrapper);
            if (area > maxArea)
                maxArea = area;
        }

        // Filter contours by area and resize to fit the original image size
        mContours.clear();
        each = contours.iterator();
        
        destinationDistance = 0.0;
        destinationAngle = 0.0;
        
        while (each.hasNext()) {
            MatOfPoint contour = each.next();
                       
//            if (Imgproc.contourArea(contour) > mMinContourArea*maxArea) {
//                Core.multiply(contour, new Scalar(4,4), contour);
//                mContours.add(contour);
//            }
            mContours.add(contour);
            Point bottom = new Point(0, 0);
            for (Point p : contour.toList()) {
            	if (p.y > bottom.y) {
            		bottom = p;
            	}
            }
            
            destinationDistance = getObjectValue(bottom, true, false);
            destinationAngle = getObjectValue(bottom, false, true);
            break;
        }
    }
    
    public void doContourDetection(List<MatOfPoint> contours, Color color) {
    	Point topPoint = new Point(10000, 10000);
        Point rightPoint = new Point(0, 0);
        Point bottomPoint = new Point(0, 0);
        Point leftPoint = new Point(10000, 10000);
        
        for(MatOfPoint points: contours) {
        	Rect rect = Imgproc.boundingRect(points);
    	
        	if(rect.height > rect.width && rect.height*rect.width > 20*30) {
        		topPoint = new Point(rect.x + (rect.width/2.0), rect.y);
        		rightPoint = new Point(rect.x + rect.width, rect.y + (rect.height/2.0));
        		bottomPoint = new Point(rect.x + (rect.width/2.0), rect.y + rect.height);
        		leftPoint = new Point(rect.x, rect.y + (rect.height/2.0));
                
                ColorBlob blob = new ColorBlob(topPoint, rightPoint, bottomPoint, leftPoint, color);
                colorBlobs.add(blob);
//                System.out.println("Color " + color + ":");
//                System.out.println(blob);
//                System.out.println("\tPoint Count: " + points.size());
        	}
        }
        
//    	for (MatOfPoint contour : contours) {
//    		List<Point> points = contour.toList();
//    		if (points.size() < 10) {
//    			continue;
//    		}
//    		
//            for (Point p : points) {
//            	if (p.y < topPoint.y) {
//            		topPoint = p.clone();
//            	}
//            	if (p.x > rightPoint.x) {
//            		rightPoint = p.clone();
//            	}
//            	if (p.y > bottomPoint.y) {
//            		bottomPoint = p.clone();
//            	}
//            	if (p.x < leftPoint.x) {
//            		leftPoint = p.clone();
//            	}
//            }
//            
//            ColorBlob blob = new ColorBlob(topPoint, rightPoint, bottomPoint, leftPoint, color);
//            colorBlobs.add(blob);
//            System.out.println("Color " + color + ":");
//            System.out.println(blob);
//            System.out.println("\tPoint Count: " + points.size());
//    	}
    }
    
    public List<Beacon> detectBeacons() {
    	List<Beacon> beacons = new ArrayList<Beacon>();
    	List<ColorBlob> trash = new ArrayList<ColorBlob>();
    	double threshold = 120;
    	
    	for (ColorBlob blob1 : colorBlobs) {
    		for (ColorBlob blob2 : colorBlobs) {
    			if (blob1 == blob2 || trash.contains(blob1) || trash.contains(blob2)) {
    				continue;
    			}
    			
    			double middle1 = (blob1.getLeft().x + blob1.getRight().x) / 2.0;
    			double middle2 = (blob2.getLeft().x + blob2.getRight().x) / 2.0;
    			
    			// Mittelpunkte beider ColorBlobs sind übereinander
    			if (between(middle1, middle2, threshold)) {
    				// blob1 steht über blob2
    				if (between(blob1.getBottom().y, blob2.getTop().y, threshold)) {
    					beacons.add(new Beacon(blob1, blob2));
    					trash.add(blob1);
    					trash.add(blob2);
    				}
    				// blob2 steht über blob1
    				else if (between(blob2.getBottom().y, blob1.getTop().y, threshold)) {
    					beacons.add(new Beacon(blob2, blob1));
    					trash.add(blob1);
    					trash.add(blob2);
    				}
    			}
    		}
    	}
    	
    	colorBlobs.removeAll(trash);
    	
    	return beacons;
    }
    
    public double getObjectValue(Point imageBottomPoint, boolean distance, boolean angle) {
		destinationDistance = 0.0;
        destinationAngle = 0.0;
        if(homography != null) {
//        	Mat src =  new Mat(1, 1, CvType.CV_32FC2);
//	    	Mat dest = new Mat(1, 1, CvType.CV_32FC2);
//	    	src.put(0, 0, new double[] { beacon.getBottomBlob().getBottom().x, beacon.getBottomBlob().getBottom().y });
//	    	Core.perspectiveTransform(src, dest, homography);
//	    	Point dest_point = new Point(dest.get(0, 0)[0], dest.get(0, 0)[1]);
        	
        	Point dest_point = getBottomPoint(imageBottomPoint);
	    	
	    	destinationDistance = Math.sqrt(Math.pow(dest_point.x, 2.0) + Math.pow(dest_point.y, 2.0));
	    	destinationDistance = Math.round(destinationDistance * 100.0) / 1000.0;
	    	
	    	destinationAngle = Math.atan(dest_point.y / dest_point.x);
	    	destinationAngle = Math.round(destinationAngle * 100.0) / 100.0;
	    	if (dest_point.x < 0) {
				destinationAngle += Math.PI;
	    	}
	    	destinationAngle -= Math.PI / 2.0;
        }
        
        if (distance && !angle) {
        	return destinationDistance;
        }
        else if (angle && !distance) {
        	return destinationAngle;
        }
        
        return 0.0;
    }
    
    public Point getBottomPoint(Point imageBottomPoint) {
    	if(homography != null) {
        	Mat src =  new Mat(1, 1, CvType.CV_32FC2);
	    	Mat dest = new Mat(1, 1, CvType.CV_32FC2);
	    	src.put(0, 0, new double[] { imageBottomPoint.x, imageBottomPoint.y });
	    	Core.perspectiveTransform(src, dest, homography);
	    	Point dest_point = new Point(dest.get(0, 0)[0], dest.get(0, 0)[1]);
	    	
	    	return dest_point;
        }
    	
    	return null;
    }
    
    public Point getRobotPosition(Beacon beacon1, Beacon beacon2) {
//    	double distance1 = 74.49;
//    	double distance2 = 107.26;
    	
    	double distance1 = getObjectValue(beacon1.getBottomBlob().getBottom(), true, false);
    	double distance2 = getObjectValue(beacon2.getBottomBlob().getBottom(), true, false);
    	
    	System.out.println("Distance 1: " + distance1);
    	System.out.println("Distance 2: " + distance2);
    	
    	double dx = beacon2.getPosition().x - beacon1.getPosition().x;
    	double dy = beacon2.getPosition().y - beacon1.getPosition().y;
    	
    	double d = Math.sqrt(Math.pow(dy, 2) + Math.pow(dx, 2));
    	
    	double a = (Math.pow(distance1, 2) - Math.pow(distance2, 2) + Math.pow(d, 2)) / (d * 2.0);
    	
    	double x2 = beacon1.getPosition().x + (dx * a/d);
    	double y2 = beacon1.getPosition().y + (dy * a/d);
    	
    	double h = Math.sqrt(Math.pow(distance1, 2) - Math.pow(a, 2));
    	
    	double rx = (0.0 - dy) * (h / d);
    	double ry = dx * (h / d);
    	
    	Point point1 = new Point(x2 + rx, y2 + ry);
    	Point point2 = new Point(x2 - rx, y2 - ry);
    	
    	System.out.println("Position Point 1: " + point1);
    	System.out.println("Position Point 2: " + point2);
    	
    	if (isInRange(point1, -125.0, 125.0)) {
    		System.out.println("Position Point 1 selected");
    		return point1;
    	}
    	else if (isInRange(point2, -125.0, 125.0)) {
    		System.out.println("Position Point 2 selected");
    		return point2;
    	}
    	
    	System.out.println("No Position Point selected");
    	return null;
    }
    
    public double getRobotAngle(Beacon beacon1, Beacon beacon2) {
    	Point b1 = getBottomPoint(beacon1.getBottomBlob().getBottom());
    	Point b2 = getBottomPoint(beacon2.getBottomBlob().getBottom());
    	
    	double k = (b2.y - b1.y) / (b2.x - b1.x);
    	double d = b1.y - (k * b1.x);
    	
    	Point p = new Point(0.0, d);
    	double l2 = Math.sqrt(Math.pow(b2.x, 2) + Math.pow(b2.y - d, 2));
    	double l = Math.sqrt(Math.pow(b2.x, 2) + Math.pow(b2.y, 2));
    	
    	double angle = Math.acos(-(Math.pow(p.y, 2) - Math.pow(l2, 2) - Math.pow(l, 2)) / (2 * l2 * l));
    	
    	int quadrant = 0;
    	// beacon1 ist Eck-Beacon
    	if (beacon1.getPosition().x != 0 && beacon1.getPosition().y != 0) {
    		quadrant = getQuadrant(beacon1);
    	}
    	// beacon2 ist Eck-Beacon
    	else if (beacon2.getPosition().x != 0 && beacon2.getPosition().y != 0) {
    		quadrant = getQuadrant(beacon2);
    	}
    	
    	switch (quadrant) {
		case 1:
			break;
		case 2:
			angle = Math.PI - angle;
			break;
		case 3:
			angle += Math.PI;
			break;
		case 4:
			angle = 2*Math.PI - angle;
			break;
		}
    	
    	return angle;
    }
    
    private int getQuadrant(Beacon beacon) {
    	double x = beacon.getPosition().x;
    	double y = beacon.getPosition().y;
    	
    	if (x > 0 && y > 0) {
    		return 1;
    	}
    	else if (x < 0 && y > 0) {
    		return 2;
    	}
    	else if (x < 0 && y < 0) {
    		return 3;
    	}
    	else if (x > 0 && y < 0) {
    		return 4;
    	}
    	
    	return 0;
    }
    
    private boolean between(double a, double b, double threshold) {
    	return (a >= (b - threshold/2.0) && a <= (b + threshold/2.0));
    }
    
    private boolean isInRange(Point point, double minValue, double maxValue) {
    	return (point.x >= minValue && point.x <= maxValue && point.y >= minValue && point.y <= maxValue);
    }

    public List<MatOfPoint> getContours() {
        return mContours;
    }
    
    public boolean calcHomographyMatrix(Mat mRgba) {
    	System.out.println("Homography Calculation Start");
//    	  final Size mPatternSize = new Size(6, 9); // number of inner corners in the used chessboard pattern 
//    	  float x = -48.0f; // coordinates of first detected inner corner on chessboard
//    	  float y = 309.0f;
//    	  float delta = 12.0f; // size of a single square edge in chessboard
    	  final Size mPatternSize = new Size(4, 5); // number of inner corners in the used chessboard pattern 
	  	  float x = 25.0f; // coordinates of first detected inner corner on chessboard
	  	  float y = 550.0f;
	  	  float delta = 23.0f; // size of a single square edge in chessboard
    	  LinkedList<Point> PointList = new LinkedList<Point>();
    	 
    	  // Define real-world coordinates for given chessboard pattern:
    	  for (int i = 0; i < mPatternSize.height; i++) {
    	    y = 309.0f;
    	    for (int j = 0; j < mPatternSize.width; j++) {
    	      PointList.addLast(new Point(x,y));
    	      y += delta;
    	    }
    	    x += delta;
    	  }
    	  MatOfPoint2f RealWorldC = new MatOfPoint2f();
    	  RealWorldC.fromList(PointList);
    	 
    	  // Detect inner corners of chessboard pattern from image:
    	  Mat gray = new Mat();
    	  Imgproc.cvtColor(mRgba, gray, Imgproc.COLOR_RGBA2GRAY); // convert image to grayscale
    	  MatOfPoint2f mCorners = new MatOfPoint2f();
    	  boolean mPatternWasFound = Calib3d.findChessboardCorners(gray, mPatternSize, mCorners);
    	      	 
    	  // Calculate homography:
    	  if (mPatternWasFound) {
    		  // Calib3d.drawChessboardCorners(mRgba, mPatternSize, mCorners, mPatternWasFound); //for visualization 		  
    		  homography = Calib3d.findHomography(mCorners, RealWorldC);
    	      System.out.println("Homography Calculation Successful");
    		  return true;
    	  } else {
    		  System.out.println("Homography Calculation Failed");
    		  return false;
    	  }
    }
    
    public boolean isHomographyDone() {
    	return homography != null;
    }

	public boolean isBallDetected() {
		return ballDetected;
	}

	public double getDestinationDistance() {
		return destinationDistance;
	}

	public double getDestinationAngle() {
		return destinationAngle;
	}
	
	public List<ColorBlob> getColorBlobs() {
		return colorBlobs;
	}
}