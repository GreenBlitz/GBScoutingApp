package com.example.scouting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
	int autoBalls = 0;
	int cycles = 0;
	int ballsPerCycle = 0;
	boolean didClimb = false;

	private TextView autoBallsView;
	private TextView cyclesView;
	private TextView ballsPerCycleView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cyclesView = findViewById(R.id.cycles);
		autoBallsView = findViewById(R.id.autoBalls);
		ballsPerCycleView = findViewById(R.id.ballsPerCycle);
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
				autoBalls--;
				break;
			case R.id.moreCycles:
				cycles++;
				break;
			case R.id.lessCycles:
				cycles--;
				break;
			case R.id.moreBalls:
				ballsPerCycle++;
				break;
			case R.id.lessBalls:
				ballsPerCycle--;
				break;
		}

		cyclesView.setText(String.valueOf(cycles));
		autoBallsView.setText(String.valueOf(autoBalls));
		ballsPerCycleView.setText(String.valueOf(ballsPerCycle));
		//This is a weird way to do it but it's the only one that works ¯\_(ツ)_/¯
	}

	public void submit(View view)
	{
		@SuppressLint("UseSwitchCompatOrMaterialCode") Switch climbed = findViewById(R.id.climbedSwitch);
		didClimb = climbed.isChecked();
	}
}