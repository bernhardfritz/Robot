package com.example.robot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MyOnClickListener implements OnClickListener {

	private Robot robot;
	private String xString;
	private String yString;
	private HsvSelector hsvSelector;

	public MyOnClickListener(Robot robot) {
		this.robot = robot;
		hsvSelector = HsvSelector.getInstance();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.toggleBlob: // toggleButtonConnect
				if (((ToggleButton) v.findViewById(R.id.toggleBlob)).isChecked())
					hsvSelector.start();
				else
					hsvSelector.stop();
				break;
							
//			case R.id.buttonMinus: // buttonMinus
//				robot.comWrite(new byte[] { '-', '\r', '\n' });
//				break;
//			case R.id.buttonPlus: // buttonPlus
//				robot.comWrite(new byte[] { '+', '\r', '\n' });
//				break;
//			case R.id.buttonW: // buttonW
//				robot.comWrite(new byte[] { 'w', '\r', '\n' });	
//				//robot.robotDrive(200.0);
//				break;
//			case R.id.buttonA: // buttonA
//				robot.comWrite(new byte[] { 'a', '\r', '\n' });
//				break;
//			case R.id.buttonS: // buttonS
//				robot.comWrite(new byte[] { 's', '\r', '\n' });
//				break;
//			case R.id.buttonD: // buttonD
//				robot.comWrite(new byte[] { 'd', '\r', '\n' });
//				break;
//			case R.id.buttonLedSwitch: // buttonLedOn
//				robot.getBallPosition();
//				break;
//			case R.id.buttonGo:
//				xString = MainActivity.xValue.getText().toString();
//				yString = MainActivity.yValue.getText().toString();
//				if(xString.compareTo("") != 0 && yString.compareTo("") != 0) {
//					double xValue = new Double(xString);
//					double yValue = new Double(yString);
//					robot.robotGoTo(xValue, yValue);
//				}
//				break;
//	//			case R.id.buttonReadSensor: // buttonReadSensor
//	//			// robot.comReadWrite(data);
//	//			// robot.robotDrive((byte)50);
//	//			String raw = robot.comReadWrite(new byte[] { 'q', '\r','\n' });
//	//			System.out.println(raw);
//	//			Pattern p = Pattern.compile("0x\\w\\w");
//	//			Matcher m = p.matcher(raw);
//	//			StringBuilder sb = new StringBuilder();
//	//			for(int i=0; i<8; i++) {
//	//				m.find();
//	//				sb.append(Long.parseLong(m.group().replace("0x",""),16)+" ");
//	//			}
//	//			MainActivity.sensorLog(sb.toString());
//	//			break;
//			case R.id.buttonCatchBall:
//				xString = MainActivity.xValue.getText().toString();
//				yString = MainActivity.yValue.getText().toString();
//				if(xString.compareTo("") != 0 && yString.compareTo("") != 0) {
//					double xValue = new Double(xString);
//					double yValue = new Double(yString);
//					robot.robotCatchBall(xValue, yValue);
//					Log.i("ClickListener", "Catch Ball at (" + xValue + ", " + yValue + ")");
//				}
//				break;
		}
	}
}
