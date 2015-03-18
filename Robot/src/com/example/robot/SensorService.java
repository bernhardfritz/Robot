package com.example.robot;

public class SensorService extends Thread {
	private AdvancedRobot robot;
	private int[] values;
	private int threshold = 12; // TODO: find appropriate threshold

	public SensorService(AdvancedRobot robot) {
		this.robot = robot;
		values = new int[5];
		for (int i = 0; i < 5; i++) {
			values[i] = 0;
		}
	}

	public int[] getValues() {
		return values;
	}

	private void update() {
		String raw = robot.comReadWrite(new byte[] { 'q', '\r', '\n' });
		String[] arr = raw.split(" ");
		for (int i = 0; i < 5; i++) {
			values[i] = (int) Long.parseLong(arr[i].replace("0x", ""), 16);
			if (values[i] < threshold)
				robot.getMovementService().interrupt();
		}
	}

	@Override
	public void run() {
		while (true) {
			update();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
