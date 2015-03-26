package com.example.robot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		case R.id.buttonMinus: // buttonMinus
			robot.comWrite(new byte[] { '-', '\r', '\n' });
			break;
		case R.id.buttonW: // buttonW
			//robot.comWrite(new byte[] { 'w', '\r', '\n' });
			robot.robotDrive(500.0);
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
			robot.robotTurn(Math.PI / 2);
			robot.robotTurn(Math.PI);
			robot.robotTurn(3 * Math.PI / 2);
			robot.robotTurn(2 * Math.PI);
			break;
		case R.id.buttonX: // buttonX
			robot.comWrite(new byte[] { 'x', '\r', '\n' });
			break;
		case R.id.buttonUp: // buttonUp
			// robot.robotSetBar(value);
			for (int i = 0; i < 4; i++) {
				robot.robotTurn(Math.PI / 2);
			}
			break;
		case R.id.buttonLedOn: // buttonLedOn
			for (int i = 0; i < 4; i++) {
				robot.robotDrive(30.0);
				robot.robotTurn(Math.PI / 2);
			}
			break;
		case R.id.buttonReadSensor: // buttonReadSensor
			// robot.comReadWrite(data);
			// robot.robotDrive((byte)50);
			String raw = robot.comReadWrite(new byte[] { 'q', '\r','\n' });
			System.out.println(raw);
			Pattern p = Pattern.compile("0x\\w\\w");
			Matcher m = p.matcher(raw);
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<8; i++) {
				m.find();
				sb.append(Long.parseLong(m.group().replace("0x",""),16)+" ");
			}
			MainActivity.sensorLog(sb.toString());
			break;
		case R.id.buttonLedOff: // buttonLedOff
			robot.robotGoTo(50, 0);
			robot.robotGoTo(50, 50);
			robot.robotGoTo(0, 50);
			robot.robotGoTo(0, 0);
			break;
		case R.id.buttonRotate:
			double angle = Math.random() * 2 * Math.PI;
			System.out.println(Math.toDegrees(angle));
			robot.robotTurn(angle);
			break;
		}
	}
}
