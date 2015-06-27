package org.opencv.samples.colorblobdetect;

import org.opencv.core.Point;

public class ColorBlob {
	private Point top;
	private Point right;
	private Point bottom;
	private Point left;
	
	private Color color;
	
	public ColorBlob(Point top, Point right, Point bottom, Point left, Color color) {
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.left = left;
		this.color = color;
	}
	
	public ColorBlob(Color color) {
		this.top = null;
		this.right = null;
		this.bottom = null;
		this.left = null;
		this.color = color;
	}

	public Point getTop() {
		return top;
	}

	public void setTop(Point top) {
		this.top = top;
	}

	public Point getRight() {
		return right;
	}

	public void setRight(Point right) {
		this.right = right;
	}

	public Point getBottom() {
		return bottom;
	}

	public void setBottom(Point bottom) {
		this.bottom = bottom;
	}

	public Point getLeft() {
		return left;
	}

	public void setLeft(Point left) {
		this.left = left;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public boolean equals(Object o) {
		ColorBlob that = (ColorBlob) o;
		return this.color.equals(that.color);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ColorBlob:");
		sb.append("\n\tTop: ");
		sb.append(top);
		sb.append("\n\tRight: ");
		sb.append(right);
		sb.append("\n\tBottom: ");
		sb.append(bottom);
		sb.append("\n\tLeft: ");
		sb.append(left);
		
		return sb.toString();
	}
}
