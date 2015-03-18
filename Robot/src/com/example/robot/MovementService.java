package com.example.robot;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MovementService extends Thread {
	private BlockingQueue<Thread> queue;

	public MovementService() {
		super();
		queue = new LinkedBlockingQueue<Thread>();
	}

	public void addMovement(Runnable r) {
		try {
			queue.put(new Thread(r));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread t = queue.take();
				t.start();
				t.wait();
			} catch (InterruptedException e) {
				queue.clear();
			}
		}
	}
}
