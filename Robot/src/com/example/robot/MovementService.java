package com.example.robot;

import java.util.LinkedList;
import java.util.Queue;

public class MovementService implements Runnable {
	private Queue<Movement> queue;
	private boolean active;

	public MovementService() {
		super();
		queue = new LinkedList<Movement>();
	}

	public void addMovement(Movement m) {
		queue.add(m);
	}
	
	public void destroy() {
		active=false;
	}

	@Override
	public void run() {
		active=true;
		while (active) {
			if(!queue.isEmpty()) {
				Movement m = (Movement) queue.poll();
				m.move();
			}
		}
	}
}
