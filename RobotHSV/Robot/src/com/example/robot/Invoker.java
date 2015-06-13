package com.example.robot;

public class Invoker {
	
	private static class SingletonHelper {
		private static final Invoker INSTANCE = new Invoker();
	}
	
	public static Invoker getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	public String invoke(Command cmd,Robot robot) throws InterruptedException {
		return cmd.execute(robot);
	}
}
