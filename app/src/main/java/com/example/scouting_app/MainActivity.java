package com.example.scouting_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
	int cycles = 0;
	int autoBalls = 0;
	int teleopBalls = 0;
	boolean didClimb = false;
	@SuppressLint("WrongViewCast")
//	TextInputEditText commentsView = findViewById(R.id.commentsTextBox);

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
		autoBallsView = findViewById(R.id.cycles);
		teleopBallsView = findViewById(R.id.teleopBalls);
	}

	@SuppressLint({"NonConstantResourceId", "SetTextI18n"})
	public void addPoints(View v)
	{
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
				if(teleopBalls > 0) teleopBalls++;
				break;
		}

		cyclesView.setText(String.valueOf(cycles));
		autoBallsView.setText(String.valueOf(autoBalls));
		teleopBallsView.setText(String.valueOf(teleopBalls));
		//This is a weird way to do it but it's the only one that works ¯\_(ツ)_/¯
	}

	public void submit(View view)
	{
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch climbed = findViewById(R.id.climbedSwitch);
		didClimb = climbed.isChecked();
//		String commentsText = Objects.requireNonNull(commentsView.getText()).toString();
	}
}