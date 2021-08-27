package com.example.scouting_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.util.Constants;
import com.example.util.scouter.ScoutingData;
import com.example.util.scouter.ScoutingEntry;

import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
	//Scouting info
	int cycles = 0;
	int autoBalls = 0;
	int teleopBalls = 0;

	boolean GreenBlitzColorsEnabled = false;
	@SuppressLint("WrongViewCast")

	private CheckBox[] checkBoxes;
	private EditText commentsView;
	private Vibrator myVib;
	private TextView autoBallsView;
	private TextView cyclesView;
	private TextView teleopBallsView;
	@SuppressLint("UseSwitchCompatOrMaterialCode")
	private Switch climbed;

	@SuppressLint({"WrongViewCast", "CutPasteId", "WrongViewCast"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cyclesView = findViewById(R.id.cycles);
		autoBallsView = findViewById(R.id.autoBalls);
		teleopBallsView = findViewById(R.id.teleopBalls);
		commentsView = findViewById(R.id.commentsTextBox);
		myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		checkBoxes = new CheckBox[2];
		checkBoxes[0] = findViewById(R.id.rotationCheckBox);
		checkBoxes[1] = findViewById(R.id.colorCheckBox);
		climbed = findViewById(R.id.climbedSwitch);
	}

	@SuppressLint({"NonConstantResourceId", "SetTextI18n"})
	public void addPoints(View v) {
		myVib.vibrate(Constants.ScoutingPrompt.vibratorOpTime); //haptic feedback for buttons

		switch (v.getId()) {
			case R.id.moreAutoBalls:
				autoBalls++;
				break;
			case R.id.lessAutoBalls:
				if (autoBalls > 0) autoBalls--;
				break;
			case R.id.moreCycles:
				cycles++;
				break;
			case R.id.lessCycles:
				if (cycles > 0) cycles--;
				break;
			case R.id.moreTeleBalls:
				teleopBalls++;
				break;
			case R.id.lessTeleBalls:
				if (teleopBalls > 0) teleopBalls--;
				break;
		}

		cyclesView.setText(String.valueOf(cycles));
		autoBallsView.setText(String.valueOf(autoBalls));
		teleopBallsView.setText(String.valueOf(teleopBalls));
		//This is a weird way to do it but it's the only one that works ¯\_(ツ)_/¯
	}

	@SuppressLint("WrongViewCast")
	public void submit(View v) throws Exception {
		boolean[] colorWheel = new boolean[checkBoxes.length];
		for (int i = 0; i < colorWheel.length; i++) {
			colorWheel[i] = checkBoxes[i].isChecked();
		}

		Thread thread = new Thread(() -> {
//			ScoutingData<Object> teamNumInfo = new ScoutingData<>("teamHash", teamHash);
//			ScoutingData<Object> gameNumInfo = new ScoutingData<>("gameNum", gameID);
			ScoutingData<Object> cyclesInfo = new ScoutingData<>("cycles", cycles);
			ScoutingData<Object> teleOpBallsInfo = new ScoutingData<>("teleOpBalls", teleopBalls);
			ScoutingData<Object> autoBallsInfo = new ScoutingData<>("autonomousBalls", autoBalls);
			ScoutingData<Object> didClimbInfo = new ScoutingData<>("climbed", climbed.isChecked());
			ScoutingData<Object> colorWheelInfoInfo = new ScoutingData<>("colorWheel", Arrays.toString(colorWheel).replace(" ", ""));
			ScoutingData<Object> commentsInfo = new ScoutingData<>("comments", Objects.requireNonNull(commentsView.getText()).toString());
			ScoutingData<Object> uid = new ScoutingData<>("id", "");
			ScoutingData<Object> psw = new ScoutingData<>("psw", "");

			ScoutingEntry scoutingEntry = new ScoutingEntry(cyclesInfo, teleOpBallsInfo, autoBallsInfo, didClimbInfo, colorWheelInfoInfo, commentsInfo);
			try {
				scoutingEntry.sendData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread.start();


		Toast.makeText(getApplicationContext(), "Scouting submitted!", Toast.LENGTH_SHORT).show();
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	public void toggleTheme() {
		//not yet implemented
		if (GreenBlitzColorsEnabled) getTheme().applyStyle(R.style.Theme_ScoutingApp, true);
		else getTheme().applyStyle(R.style.Theme_GreenBlitzColorsGood, true);
		GreenBlitzColorsEnabled = !GreenBlitzColorsEnabled;
		setContentView(R.layout.activity_main);
	}
}