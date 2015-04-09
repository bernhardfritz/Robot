package com.example.robot;

import java.util.LinkedList;
import java.util.Queue;

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

	public void destroy() {
		active = false;
	}

	@Override
	public void run() {
		active = true;
		while (active) {
			if (!queue.isEmpty()) {
				Command cmd = (Command) queue.poll();
				try {
					Invoker.getInstance().invoke(cmd,robot);
				} catch (InterruptedException e) {
					queue.clear();
					e.printStackTrace();
				}
			}
		}
	}
}
