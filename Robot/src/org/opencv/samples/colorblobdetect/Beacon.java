package org.opencv.samples.colorblobdetect;

import org.opencv.core.Point;

public class Beacon {
	private ColorBlob topBlob;
	private ColorBlob bottomBlob;
	
	private Point position;
	
	public Beacon(ColorBlob topBlob, ColorBlob bottomBlob) {
		this.topBlob = topBlob;
		this.bottomBlob = bottomBlob;
	}
	
	public Beacon(ColorBlob topBlob, ColorBlob bottomBlob, Point position) {
		this.topBlob = topBlob;
		this.bottomBlob = bottomBlob;
		this.position = position;
	}

	public ColorBlob getTopBlob() {
		return topBlob;
	}

	public void setTopBlob(ColorBlob topBlob) {
		this.topBlob = topBlob;
	}

	public ColorBlob getBottomBlob() {
		return bottomBlob;
	}

	public void setBottomBlob(ColorBlob bottomBlob) {
		this.bottomBlob = bottomBlob;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	@Override
	public boolean equals(Object o) {
		Beacon that = (Beacon) o;
		return (this.topBlob.equals(that.topBlob) && this.bottomBlob.equals(that.getBottomBlob()));
	}
}