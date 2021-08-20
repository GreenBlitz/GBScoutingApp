package com.example.scouting_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Vibrator; //haha
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
	int cycles = 0;
	int autoBalls = 0;
	int teleopBalls = 0;
	boolean didClimb = false;
	boolean GreenBlitzColors = false;
	@SuppressLint("WrongViewCast")
//	TextInputEditText commentsView = findViewById(R.id.commentsTextBox);

	private Vibrator myVib;
	private TextView autoBallsView;
	private TextView cyclesView;
	private TextView teleopBallsView;
	@SuppressLint({"WrongViewCast", "CutPasteId", "WrongViewCast"})

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cyclesView = findViewById(R.id.cycles);
		autoBallsView = findViewById(R.id.autoBalls);
		teleopBallsView = findViewById(R.id.teleopBalls);
		myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
	}

	@SuppressLint({"NonConstantResourceId", "SetTextI18n"})
	public void addPoints(View v)
	{
		myVib.vibrate(35); //haptic feedback for buttons
		switch (v.getId())
		{
			case R.id.moreAutoBalls:
				autoBalls++;
				break;
			case R.id.lessAutoBalls:
				if(autoBalls > 0) autoBalls--;
				break;
			case R.id.moreCycles:
				cycles++;
				break;
			case R.id.lessCycles:
				if(cycles > 0) cycles--;
				break;
			case R.id.moreTeleBalls:
				teleopBalls++;
				break;
			case R.id.lessTeleBalls:
				if(teleopBalls > 0) teleopBalls--;
				break;
			case R.id.imageView:
//				toggleTheme();
				break;
		}

		cyclesView.setText(String.valueOf(cycles));
		autoBallsView.setText(String.valueOf(autoBalls));
		teleopBallsView.setText(String.valueOf(teleopBalls));
		//This is a weird way to do it but it's the only one that works ¯\_(ツ)_/¯
	}

	public void submit(View v)
	{
		//TODO: fix commentsView issue & redo color wheel
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch climbed = findViewById(R.id.climbedSwitch);
		didClimb = climbed.isChecked();
//		String commentsText = Objects.requireNonNull(commentsView.getText()).toString();
		Toast.makeText(getApplicationContext(), "Scouting submitted!", Toast.LENGTH_SHORT).show();
		recreate();
	}

	public void toggleTheme()
	{
		if (GreenBlitzColors) getTheme().applyStyle(R.style.Theme_ScoutingApp, true);
		else getTheme().applyStyle(R.style.Theme_GreenBlitzColors, true);
		GreenBlitzColors = !GreenBlitzColors;
		setContentView(R.layout.activity_main);
	}

}