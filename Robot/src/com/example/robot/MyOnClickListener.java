package com.example.robot;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class MyOnClickListener implements OnClickListener {

	private Robot robot;

	public MyOnClickListener(Robot robot) {
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
		case R.id.button1: // buttonMinus
			robot.comWrite(new byte[] { '-', '\r', '\n' });
			break;
		case R.id.button2: // buttonW
			robot.comWrite(new byte[] { 'w', '\r', '\n' });
			break;
		case R.id.button3: // buttonPlus
			robot.comWrite(new byte[] { '+', '\r', '\n' });
			break;
		case R.id.button4: // buttonA
			robot.comWrite(new byte[] { 'a', '\r', '\n' });
			break;
		case R.id.button5: // buttonS
			robot.comWrite(new byte[] { 's', '\r', '\n' });
			break;
		case R.id.button6: // buttonD
			robot.comWrite(new byte[] { 'd', '\r', '\n' });
			break;
		case R.id.button7: // buttonDown
			// robot.robotSetBar(value);
			break;
		case R.id.button8: // buttonX
			robot.comWrite(new byte[] { 'x', '\r', '\n' });
			break;
		case R.id.button9: // buttonUp
			// robot.robotSetBar(value);
			break;
		case R.id.button10: // buttonLedOn
			// robot.robotSetLeds(red, blue);
			break;
		case R.id.button11: // buttonReadSensor
			// robot.comReadWrite(data);
			break;
		case R.id.button12: // buttonLedOff
			// robot.robotSetLeds(red, blue);
			break;
		}
	}
}
