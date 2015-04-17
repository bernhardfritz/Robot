package com.example.robot;

public class Measurement implements Command {
	@Override
	public String execute(Robot robot) throws InterruptedException {
		return robot.comReadWrite(new byte[] { 'q', '\r', '\n' });
	}

	@Override
	public boolean isAborted() {
		return false;
	}

}
