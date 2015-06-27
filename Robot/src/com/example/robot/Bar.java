package com.example.robot;

public class Bar implements Command {
	private byte value;
	
	public Bar(byte value) {
		this.value = value;
	}

	@Override
	public String execute(Robot robot) throws InterruptedException {
		if (value == (byte) 255) {
			for (int i = 0; i < 20; i++) {
				robot.comWrite(new byte[] { '+', '\r', '\n' });
				Thread.sleep(200);
			}
		}
		else {
			for (int i = 0; i < 20; i++) {
				robot.comWrite(new byte[] { '-', '\r', '\n' });
				Thread.sleep(200);
			}
		}
		return null;
	}

	@Override
	public boolean isAborted() {
		return false;
	}
}
