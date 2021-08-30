package com.example.scouting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.util.Constants;
import com.example.util.Net;

import org.json.JSONException;
import org.json.JSONObject;

public class CoachInfoTeam extends AppCompatActivity {
    public static final String requestSubdomain = "coach/team";

    @Override
    protected void onCreate(Bundle savedInstanceState) throws JSONException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info_team);

        setTitle("Coach Information by Team");

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE); // access phone memory
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();

        JSONObject authentication = new JSONObject();
        authentication.put("uid", sharedPref.getString("uid", "none"));
        authentication.put("psw", sharedPref.getString("password", "none"));
        authentication.put("team", "4590"); // TODO: convert definite team string to given team from Intent

        String teamInfo = Net.request(Constants.Networking.serverURL + requestSubdomain, Net.Method.GET, authentication);

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

        TextView percentClimb = (TextView) findViewById(R.id.percentClimbingText);
        percentClimb.setText("75%");
        ProgressBar percentClimbBar = (ProgressBar) findViewById(R.id.percentClimbingBar);
        percentClimbBar.setProgress(75);

        TextView lastClimb = (TextView) findViewById(R.id.lastClimbing);
        lastClimb.setText("9000");

        TextView percentWheelCount = (TextView) findViewById(R.id.percentWheelCountText);
        percentWheelCount.setText("20%");
        ProgressBar percentWheelCountBar = (ProgressBar) findViewById(R.id.percentWheelCountBar);
        percentWheelCountBar.setProgress(20);

        TextView lastWheelCount = (TextView) findViewById(R.id.lastWheelCount);
        lastWheelCount.setText("9000");

        TextView percentWheelColor = (TextView) findViewById(R.id.percentWheelColorText);
        percentWheelColor.setText("59%");
        ProgressBar percentWheelColorBar = (ProgressBar) findViewById(R.id.percentWheelColorBar);
        percentWheelColorBar.setProgress(59);

        TextView lastWheelColor = (TextView) findViewById(R.id.lastWheelColor);
        lastWheelColor.setText("9000");

        TextView comments = (TextView) findViewById(R.id.comments);
        comments.setText("over 9 thousand");

    }
}
