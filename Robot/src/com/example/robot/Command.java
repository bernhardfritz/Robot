package com.example.robot;

public interface Command {
	String execute(Robot robot) throws InterruptedException;
	boolean isAborted();
}
