package com.example.robot;

import jp.ksksue.driver.serial.FTDriver;
import android.app.Activity;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private Robot robot;
	private MyOnClickListener myOnClickListener;

	private ToggleButton toggleButtonConnect;
	private Button buttonMinus;
	private Button buttonW;
	private Button buttonPlus;
	private Button buttonA;
	private Button buttonS;
	private Button buttonD;
	private Button buttonDown;
	private Button buttonX;
	private Button buttonUp;
	private Button buttonLedOn;
	private Button buttonReadSensor;
	private Button buttonLedOff;
	private static TextView textLog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		robot = new Robot(new FTDriver(
				(UsbManager) getSystemService(USB_SERVICE)));
		myOnClickListener = new MyOnClickListener(robot);

		toggleButtonConnect = (ToggleButton) findViewById(R.id.toggleButton1);
		buttonMinus = (Button) findViewById(R.id.buttonMinus);
		buttonW = (Button) findViewById(R.id.buttonW);
		buttonPlus = (Button) findViewById(R.id.buttonPlus);
		buttonA = (Button) findViewById(R.id.buttonA);
		buttonS = (Button) findViewById(R.id.buttonS);
		buttonD = (Button) findViewById(R.id.buttonD);
		buttonDown = (Button) findViewById(R.id.buttonDown);
		buttonX = (Button) findViewById(R.id.buttonX);
		buttonUp = (Button) findViewById(R.id.buttonUp);
		buttonLedOn = (Button) findViewById(R.id.buttonLedOn);
		buttonReadSensor = (Button) findViewById(R.id.buttonReadSensor);
		buttonLedOff = (Button) findViewById(R.id.buttonLedOff);

		toggleButtonConnect.setOnClickListener(myOnClickListener);
		buttonMinus.setOnClickListener(myOnClickListener);
		buttonW.setOnClickListener(myOnClickListener);
		buttonPlus.setOnClickListener(myOnClickListener);
		buttonA.setOnClickListener(myOnClickListener);
		buttonS.setOnClickListener(myOnClickListener);
		buttonD.setOnClickListener(myOnClickListener);
		buttonDown.setOnClickListener(myOnClickListener);
		buttonX.setOnClickListener(myOnClickListener);
		buttonUp.setOnClickListener(myOnClickListener);
		buttonLedOn.setOnClickListener(myOnClickListener);
		buttonReadSensor.setOnClickListener(myOnClickListener);
		buttonLedOff.setOnClickListener(myOnClickListener);

		textLog = (TextView) findViewById(R.id.textView1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static void log(String s) {
		textLog.append(s);
	}
}
