package com.example.scouting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.util.Constants;
import com.example.util.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoachInfoTeam extends AppCompatActivity {
    public static final String requestSubdomain = "coach/team";
    private Intent intent;
    private String teamHash;
    static JSONObject teamInfo = null;
    static JSONObject authentication;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info_team);

        setTitle("Coach Information by Team");

        intent = getIntent();
        teamHash = intent.getStringExtra("team");


        SharedPreferences sharedPref = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE); // access phone memory
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();

        authentication = new JSONObject();
        try {
            authentication.put("id", sharedPref.getString("uid", "none"));
            authentication.put("psw", sharedPref.getString("password", "none"));
            authentication.put("team", teamHash);
            System.out.println("ORI: " + authentication.toString());
            System.out.println(teamHash);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread net = new Thread(() -> {
            Pair<JSONObject, Boolean> response = new Pair<>(null, false);
            try {
                response = Net.requestJSON(Constants.Networking.serverURL + requestSubdomain, Net.Method.GET, authentication);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            teamInfo = response.first;
        });
        net.start();

        long tStart = System.currentTimeMillis();
        while (teamInfo == null && System.currentTimeMillis() - tStart < 5000) { // wait for response with timeout of 5 seconds to get data.
        }

        System.out.println(teamInfo.toString());

        try {
            TextView teamHash = (TextView) findViewById(R.id.teamHash);
            teamHash.setText(this.teamHash);

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


            LinearLayout gamesPlayed = (LinearLayout) findViewById(R.id.gamesPlayed);
            JSONArray games = teamInfo.getJSONArray("games");
            for (int i = 0; i < games.length(); i++) {
                TextView game = new TextView(this);
                game.setText(games.getString(i));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 5, 5, 5);
                game.setLayoutParams(params);
                game.setMaxLines(1);
                game.setTextColor(Color.WHITE);
                game.setTextSize(10);

                gamesPlayed.addView(game);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

