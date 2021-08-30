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

        TextView avgAuto = (TextView) findViewById(R.id.avgAuto);
        avgAuto.setText("9000");

        TextView maxAuto = (TextView) findViewById(R.id.maxAuto);
        maxAuto.setText("9000");

        TextView avgTele = (TextView) findViewById(R.id.avgTele);
        avgTele.setText("9000");

        TextView maxTele = (TextView) findViewById(R.id.maxTele);
        maxTele.setText("9000");

        TextView avgCycles = (TextView) findViewById(R.id.avgCycle);
        avgCycles.setText("9000");

        TextView maxCycles = (TextView) findViewById(R.id.maxCycle);
        maxCycles.setText("9000");

        TextView percentClimb = (TextView) findViewById(R.id.percentClimbing);
        percentClimb.setText("9000");

        TextView lastClimb = (TextView) findViewById(R.id.lastClimbing);
        lastClimb.setText("9000");

        TextView percentWheelCount = (TextView) findViewById(R.id.percentWheelCount);
        percentWheelCount.setText("9000");

        TextView lastWheelCount = (TextView) findViewById(R.id.lastWheelCount);
        lastWheelCount.setText("9000");

        TextView percentWheelColor = (TextView) findViewById(R.id.percentWheelColor);
        percentWheelColor.setText("9000");

        TextView lastWheelColor = (TextView) findViewById(R.id.lastWheelColor);
        lastWheelColor.setText("9000");

        TextView comments = (TextView) findViewById(R.id.comments);
        comments.setText("over 9 thousand");

    }
}
