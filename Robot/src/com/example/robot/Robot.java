package com.example.robot;

import jp.ksksue.driver.serial.FTDriver;

public class Robot {
	private FTDriver com;

	public Robot(FTDriver com) {
		this.com = com;
	}

	public void connect() {
		if (com.begin(FTDriver.BAUD9600))
			MainActivity.log("connected\n");
		else
			MainActivity.log("could not connect\n");
	}

	public void disconnect() {
		com.end();
		if (!com.isConnected()) {
			MainActivity.log("disconnected\n");
		}
	}

	public void comWrite(byte[] data) {
		if (com.isConnected()) {
			com.write(data);
		} else {
			MainActivity.log("not connected\n");
		}
	}

	public String comRead() {
		String s = "";
		int i = 0;
		int n = 0;
		while (i < 3 || n > 0) {
			byte[] buffer = new byte[256];
			n = com.read(buffer);
			s += new String(buffer, 0, n);
			i++;
		}
		return s;
	}

	public String comReadWrite(byte[] data) {
		com.write(data);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// ignore
		}
		return comRead();
	}

	public void robotSetLeds(byte red, byte blue) {
		comReadWrite(new byte[] { 'u', red, blue, '\r', '\n' });
	}

	public void robotDrive(byte distance_cm) {
		float k = 100f / 72f;
		comReadWrite(new byte[] { 'k', (byte) (k * distance_cm), '\r', '\n' });
	}

	public void robotTurn(byte degree) {
		float k = 90f / 78f;
		comReadWrite(new byte[] { 'l', (byte) (k * degree), '\r', '\n' });
	}

	public void robotSetVelocity(byte left, byte right) {
		comReadWrite(new byte[] { 'i', left, right, '\r', '\n' });
	}

	public void robotSetBar(byte value) {
		comReadWrite(new byte[] { 'o', value, '\r', '\n' });
	}

	public void robotDriveSquare(byte distance_cm) {
		for (int i = 0; i < 4; i++) {
			robotDrive(distance_cm);
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			robotTurn((byte) 90);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
