package com.example.robot;

import java.util.LinkedList;
import java.util.Queue;

import android.util.Log;

public class MovementService implements Runnable {
	private Queue<Command> queue;
	private boolean active;
	Robot robot;

	public MovementService(Robot robot) {
		this.robot = robot;
		queue = new LinkedList<Command>();
	}

	public void addCommand(Command cmd) {
		queue.add(cmd);
	}
	
	public boolean isActive() {
		return active;
	}

	public void destroy() {
		active = false;
		Log.i("Movement","MovementService stopped");
	}

	@Override
	public void run() {
		active = true;
		Log.i("Movement","MovementService started");
		while (active) {
			if (!queue.isEmpty()) {
				Command cmd = (Command) queue.poll();
				try {
					Thread.sleep(robot.getInterval());
					Invoker.getInstance().invoke(cmd,robot);
				} catch (InterruptedException e) {
					queue.clear();
					e.printStackTrace();
				}
			}
		}
	}
}
