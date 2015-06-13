package com.example.robot;

import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensorManager extends Observable {
	private Robot robot;
	private boolean active;
	private double[][] values;
	private int cycle = 0;
	private int threshold = 30; // TODO: find appropriate threshold

	public SensorManager(Robot robot) {
		this.robot = robot;
		addObserver(robot);
		enable();
		values = new double[8][3];
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				values[i][j] = 255;
			}
		}
	}

	private double getAverage(double array[]) {
		double average = 0.0;
		for (int i = 0; i < array.length; i++) {
			average += array[i];
		}
		average /= array.length;
		return average;
	}

	public void update() throws InterruptedException {
		new Thread() {
			public void run() {
				if(!active) return;
				String raw = null;
				try {
					raw = Invoker.getInstance().invoke(new Measurement(), robot);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Pattern p = Pattern.compile("0x\\w\\w");
				Matcher m = p.matcher(raw);
				for (int i = 0; i < 8; i++) {
					if (m.find()) {
						values[i][cycle] = 0.0 + Long.parseLong(
								m.group().replace("0x", ""), 16);
					}
				}
				if (getAverage(values[2]) < threshold // front left
						|| getAverage(values[3]) < threshold // front right
						|| getAverage(values[6]) < threshold) { // front middle
					for (int i = 0; i < values.length; i++) {
						for (int j = 0; j < values[i].length; j++) {
							values[i][j] = 255;
						}
					}
					setChanged();
					notifyObservers(true);
				} else {
					setChanged();
					notifyObservers(false);
				}
				cycle = (cycle + 1) % values[0].length;
				// MainActivity.sensorLog();
			}
		}.start();
	}

	public void enable() {
		active = true;
	}
	
	public void disable() {
		active = false;
	}
}
