package com.example.robot;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class MyOnClickListener implements OnClickListener {

	private AdvancedRobot robot;

	public MyOnClickListener(AdvancedRobot robot) {
		this.robot = robot;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.toggleButton1: // toggleButtonConnect
			if (((ToggleButton) v.findViewById(R.id.toggleButton1)).isChecked())
				robot.connect();
			else
				robot.disconnect();
			break;
		case R.id.buttonMinus: // buttonMinus
			robot.comWrite(new byte[] { '-', '\r', '\n' });
			break;
		case R.id.buttonW: // buttonW
			// robot.comWrite(new byte[] { 'w', '\r', '\n' });
			robot.robotDrive(100f);
			break;
		case R.id.buttonPlus: // buttonPlus
			robot.comWrite(new byte[] { '+', '\r', '\n' });
			break;
		case R.id.buttonA: // buttonA
			robot.comWrite(new byte[] { 'a', '\r', '\n' });
			break;
		case R.id.buttonS: // buttonS
			robot.comWrite(new byte[] { 's', '\r', '\n' });
			break;
		case R.id.buttonD: // buttonD
			robot.comWrite(new byte[] { 'd', '\r', '\n' });
			break;
		case R.id.buttonDown: // buttonDown
			// robot.robotSetBar(value);
			robot.robotTurn(180f);
			break;
		case R.id.buttonX: // buttonX
			robot.comWrite(new byte[] { 'x', '\r', '\n' });
			break;
		case R.id.buttonUp: // buttonUp
			// robot.robotSetBar(value);

			for (int i = 0; i < 4; i++) {
				robot.robotDrive(100f);
				robot.robotTurn(90f);
			}

			break;
		case R.id.buttonLedOn: // buttonLedOn
			// robot.robotDriveSquare(50);
			robot.robotTurn(90f);
			robot.robotTurn(90f);
			break;
		case R.id.buttonReadSensor: // buttonReadSensor
			// robot.comReadWrite(data);
			// robot.robotDrive((byte)50);
			MainActivity.sensorLog(robot.comReadWrite(new byte[] { 'q', '\r',
					'\n' }));
			break;
		case R.id.buttonLedOff: // buttonLedOff
			robot.robotGoTo(100, 0);
			robot.robotGoTo(100, 100);
			robot.robotGoTo(0, 100);
			robot.robotGoTo(0, 0);
			break;
		case R.id.button1:
			/*
			 * for(int i:robot.getSensorService().getValues()) {
			 * MainActivity.sensorLog(""+i+" "); }
			 */

		}
	}
}
