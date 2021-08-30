package com.example.scouting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
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

    @SuppressLint("SetTextI18n")
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
        authentication.put("teamHash", "4590"); // TODO: convert definite team string to given team from Intent

        Pair<JSONObject, Boolean> response = Net.requestJSON(Constants.Networking.serverURL + requestSubdomain, Net.Method.GET, authentication);
        JSONObject teamInfo = response.first;

        TextView teamHash = (TextView) findViewById(R.id.teamHash);
        teamHash.setText("4590");

        TextView winLoss = (TextView) findViewById(R.id.winRate);
        winLoss.setText(teamInfo.getDouble("winRate") + "%");

        TextView ranking = (TextView) findViewById(R.id.ranking);
        ranking.setText(teamInfo.getInt("ranking"));

        TextView avgAuto = (TextView) findViewById(R.id.avgAuto);
        avgAuto.setText("" + teamInfo.getDouble("avgAuto"));

        TextView maxAuto = (TextView) findViewById(R.id.maxAuto);
        maxAuto.setText(teamInfo.getInt("maxAuto"));

        TextView avgTele = (TextView) findViewById(R.id.avgTele);
        avgTele.setText("" + teamInfo.getDouble("avgTele"));

        TextView maxTele = (TextView) findViewById(R.id.maxTele);
        maxTele.setText(teamInfo.getInt("maxTele"));

        TextView avgCycles = (TextView) findViewById(R.id.avgCycle);
        avgCycles.setText("" + teamInfo.getDouble("avgCycles"));

        TextView maxCycles = (TextView) findViewById(R.id.maxCycle);
        maxCycles.setText(teamInfo.getInt("maxCycles"));

        double percentClimbValue = teamInfo.getDouble("percentClimb");
        TextView percentClimb = (TextView) findViewById(R.id.percentClimbingText);
        percentClimb.setText(percentClimbValue + "%");
        ProgressBar percentClimbBar = (ProgressBar) findViewById(R.id.percentClimbingBar);
        percentClimbBar.setProgress((int) percentClimbValue);

        TextView lastClimb = (TextView) findViewById(R.id.lastClimbing);
        lastClimb.setText(teamInfo.getString("lastClimb"));

        double percentWheelCountValue = teamInfo.getDouble("percentWheelCount");
        TextView percentWheelCount = (TextView) findViewById(R.id.percentWheelCountText);
        percentWheelCount.setText(percentWheelCountValue + "%");
        ProgressBar percentWheelCountBar = (ProgressBar) findViewById(R.id.percentWheelCountBar);
        percentWheelCountBar.setProgress((int) percentWheelCountValue);

        TextView lastWheelCount = (TextView) findViewById(R.id.lastWheelCount);
        lastWheelCount.setText(teamInfo.getString("lastWheelCount"));

        double percentWheelColorValue = teamInfo.getDouble("percentWheelColor");
        TextView percentWheelColor = (TextView) findViewById(R.id.percentWheelColorText);
        percentWheelColor.setText(percentWheelColorValue + "%");
        ProgressBar percentWheelColorBar = (ProgressBar) findViewById(R.id.percentWheelColorBar);
        percentWheelColorBar.setProgress((int) percentWheelColorValue);

        TextView lastWheelColor = (TextView) findViewById(R.id.lastWheelColor);
        lastWheelColor.setText(teamInfo.getString("lastWheelColor"));

        TextView comments = (TextView) findViewById(R.id.comments);
        comments.setText(teamInfo.getString("comments"));

    }
}
