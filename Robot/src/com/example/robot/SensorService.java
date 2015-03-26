package com.example.robot;

import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensorService extends Observable implements Runnable {
	private Robot robot;
	private boolean active;
	private double[][] values;
	private int cycle = 0;
	private int threshold = 30; // TODO: find appropriate threshold

	public SensorService(Robot robot) {
		this.robot = robot;
		addObserver(robot);
		active = true;
		values = new double[8][4];
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

	private void update() {
		String raw = robot.comReadWrite(new byte[] { 'q', '\r', '\n' });
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
			setChanged();
			notifyObservers(true);
		} else {
			setChanged();
			notifyObservers(false);
		}
		cycle = (cycle + 1) % values[0].length;
		// MainActivity.sensorLog();
	}

	public void destroy() {
		active = false;
	}

	@Override
	public void run() {
		System.out.println("sensorservice started");
		while (active) {
			update();
			try {
				Thread.sleep(robot.getInterval());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("sensorservice stopped");
	}
}
