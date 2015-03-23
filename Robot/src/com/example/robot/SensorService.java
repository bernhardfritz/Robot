package com.example.robot;

import java.util.Observable;

public class SensorService extends Observable implements Runnable {
	private AdvancedRobot robot;
	private boolean active;
	private int[] values;
	private int threshold = 12; // TODO: find appropriate threshold

	public SensorService(AdvancedRobot robot) {
		this.robot = robot;
		active = true;
		values = new int[8];
		for (int i = 0; i < 8; i++) {
			values[i] = 0;
		}
	}

	public int[] getValues() {
		return values;
	}

	private void update() {
		String raw = robot.comReadWrite(new byte[] { 'q', '\r', '\n' });
		raw = raw.replace("command execution marked", "");
		raw = raw.replace("sensor: ", "");
		raw = raw.replace("\n", "");
		raw = raw.replace("0x", "");
		String[] arr = raw.split(" ");
		for (int i = 0; i < 8; i++) {
			values[i] = (int) Long.parseLong(arr[i], 16);
			if (values[i] < threshold)
				notifyObservers();
		}
	}

	public void destroy() {
		active = false;
	}

	@Override
	public void run() {
		while (active) {
			update();
			try {
				Thread.sleep(robot.getInterval());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
