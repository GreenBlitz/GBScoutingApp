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

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
	//Scouting info
	int cycles = 0;
	int autoBalls = 0;
	int teleopBalls = 0;
	boolean isClimb = false;
	String commentsText = "";

	boolean GreenBlitzColorsEnabled = false;
	@SuppressLint("WrongViewCast")

	private CheckBox[] checkBoxes;
	private EditText commentsView;
	private Vibrator myVib;
	private TextView autoBallsView;
	private TextView cyclesView;
	private TextView teleopBallsView;

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
		checkBoxes[0] = findViewById(R.id.checkBox2);
		checkBoxes[1] = findViewById(R.id.checkBox3);
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
	public void submit(View v) {
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch climbed = findViewById(R.id.climbedSwitch);
		isClimb = climbed.isChecked();
		commentsText = Objects.requireNonNull(commentsView.getText()).toString();

		boolean[] colorWheel = new boolean[checkBoxes.length];
		for (int i = 0; i < colorWheel.length; i++) {
			colorWheel[i] = checkBoxes[i].isChecked();
		}

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