package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class CoachInfoTeam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info_team);

        Spinner teamSelection = (Spinner) findViewById(R.id.teamHash);
        ArrayAdapter<CharSequence> matchAdapter = ArrayAdapter.createFromResource(this, R.array.features,
                android.R.layout.simple_spinner_dropdown_item);

        teamSelection.setAdapter(matchAdapter);

        ProgressBar winLoss = (ProgressBar) findViewById(R.id.WinLoseRatio);
        winLoss.setProgress(90);

        ProgressBar shoot = (ProgressBar) findViewById(R.id.ShootSuccess);
        shoot.setProgress(60);

        ProgressBar climb = (ProgressBar) findViewById(R.id.climb_success);
        climb.setProgress(30);

        TextView climbPercent = (TextView) findViewById(R.id.climb_percent);
        climbPercent.setText("30" + "%");
    }
}