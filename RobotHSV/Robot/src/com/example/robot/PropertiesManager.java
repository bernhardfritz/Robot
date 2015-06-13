package com.example.robot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

	private static final String filename = "config.properties";
	private Properties prop;

	private PropertiesManager() {
		init();
	}

	private static class SingletonHelper {
		private static final PropertiesManager INSTANCE = new PropertiesManager();
	}

	public static PropertiesManager getInstance() {
		return SingletonHelper.INSTANCE;
	}

	private void init() {
		InputStream input = null;
		try {
			input = new FileInputStream(filename);
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getProperty(String name) {
		return prop.getProperty(name);
	}
}
