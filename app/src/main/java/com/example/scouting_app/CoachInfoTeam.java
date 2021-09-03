package com.example.scouting_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CoachInfoTeam extends AppCompatActivity {

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coach_info_team);
		setTitle("Coach Information by Team");

		Spinner teamSelection = (Spinner) findViewById(R.id.teamHash);
		ArrayAdapter<CharSequence> matchAdapter = ArrayAdapter.createFromResource(this, R.array.features,
				android.R.layout.simple_spinner_dropdown_item);
		teamSelection.setAdapter(matchAdapter);

		ProgressBar winLoss = findViewById(R.id.WinLoseRatio);
		winLoss.setProgress(90);
		ProgressBar shoot = findViewById(R.id.ShootSuccess);
		shoot.setProgress(60);
		ProgressBar climb = findViewById(R.id.climb_success);
		climb.setProgress(30);
		TextView climbPercent = findViewById(R.id.climb_percent);
		climbPercent.setText("30" + "%");
	}
}