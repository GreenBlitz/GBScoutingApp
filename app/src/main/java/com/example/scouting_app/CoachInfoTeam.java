package com.example.scouting_app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CoachInfoTeam extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info_team);

        setTitle("Coach Information by Team");

        TextView teamHash = (TextView) findViewById(R.id.teamHash);
        teamHash.setText("4590");

        TextView winLoss = (TextView) findViewById(R.id.winRate);
        winLoss.setText("90%");

        TextView ranking = (TextView) findViewById(R.id.ranking);
        ranking.setText("2");
    }
}
