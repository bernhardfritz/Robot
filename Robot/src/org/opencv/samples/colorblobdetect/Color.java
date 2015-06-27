package org.opencv.samples.colorblobdetect;

import org.opencv.core.Scalar;

public enum Color {
	RED(251, 201, 149), BLUE(142, 229, 170), GREEN(89, 115, 145), MAGENTA(209, 89, 139), ORANGE(14, 168, 201);
	
	private final Scalar hsvValue;
	
	Color(double h, double s, double v) {
		this.hsvValue = new Scalar(h, s, v);
	}
	
	public Scalar hsvValue() {
		return hsvValue;
	}
}
