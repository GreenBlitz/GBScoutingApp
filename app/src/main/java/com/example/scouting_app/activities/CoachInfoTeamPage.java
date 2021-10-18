package com.example.scouting_app.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scouting_app.R;
import com.example.util.Constants;
import com.example.util.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoachInfoTeamPage extends AppCompatActivity {
	public static final String requestSubdomain = "coach/team";
	private Intent intent;
	private String teamHash;
	static JSONObject teamInfo = null;
	static JSONObject authentication;
	SharedPreferences sharedPref;

	@SuppressLint("SetTextI18n")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coach_info_team);
		setTitle("Coach Information by Team");

		intent = getIntent();
		teamHash = intent.getStringExtra("team");
		sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE); // access phone memory
		@SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();

		authentication = new JSONObject();
		try {
			//create json object for coach identity authentication
			authentication.put("id", String.valueOf(sharedPref.getInt("uid", 0)));
			authentication.put("psw", sharedPref.getString("psw", "none"));
			authentication.put("team", String.valueOf(teamHash));
			System.out.println("Mission failed successfully");
		} catch (Exception e) {
			System.out.println("auth failed");
			e.printStackTrace();
		}

		Thread net = new Thread(() -> {
			Pair<JSONObject, Boolean> response = new Pair<>(null, false);
			try {
				// send request to server requesting the team info to present
				response = Net.requestJSON(Constants.Networking.SERVER_URL + requestSubdomain, Net.Method.GET, authentication);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			teamInfo = response.first;
		});
		//start thread
		net.start();

		long tStart = System.currentTimeMillis();
		while (teamInfo == null && System.currentTimeMillis() - tStart < 5000) { // wait for response with timeout of 5 seconds to get data.
		}

		try {
			//define all the text views
			TextView teamHash = findViewById(R.id.teamHash);
			teamHash.setText(this.teamHash);

			TextView winLoss = findViewById(R.id.winRate);
			winLoss.setText(("" + teamInfo.getDouble("win_rate")).substring(0, 4) + "%");

			TextView ranking = findViewById(R.id.ranking);
			ranking.setText(teamInfo.getString("ranking_or_alliance"));

			TextView avgAuto = findViewById(R.id.avgAuto);
			avgAuto.setText("" + teamInfo.getDouble("auto_balls_avg"));
			TextView maxAuto = findViewById(R.id.maxAuto);
			maxAuto.setText("" + teamInfo.getInt("auto_balls_max"));

			TextView avgTele = findViewById(R.id.avgTele);
			avgTele.setText("" + teamInfo.getDouble("tele_balls_avg"));
			TextView maxTele = findViewById(R.id.maxTele);
			maxTele.setText("" + teamInfo.getInt("tele_balls_max"));

			TextView avgCycles = findViewById(R.id.avgCycle);
			avgCycles.setText("" + teamInfo.getDouble("cycles_avg"));
			TextView maxCycles = findViewById(R.id.maxCycle);
			maxCycles.setText("" + teamInfo.getInt("cycles_max"));

			double percentClimbValue = teamInfo.getDouble("climb_avg");
			TextView percentClimb = findViewById(R.id.percentClimbingText);
			percentClimb.setText(percentClimbValue + "%");
			ProgressBar percentClimbBar = findViewById(R.id.percentClimbingBar);
			percentClimbBar.setProgress((int) percentClimbValue);
			TextView lastClimb = findViewById(R.id.lastClimbing);
			lastClimb.setText(teamInfo.getString("climb_last"));

			double percentWheelCountValue = teamInfo.getDouble("color_wheel_1_avg");
			TextView percentWheelCount = findViewById(R.id.percentWheelCountText);
			percentWheelCount.setText(percentWheelCountValue + "%");
			ProgressBar percentWheelCountBar = findViewById(R.id.percentWheelCountBar);
			percentWheelCountBar.setProgress((int) percentWheelCountValue);
			TextView lastWheelCount = findViewById(R.id.lastWheelCount);
			lastWheelCount.setText(teamInfo.getString("color_wheel_1_last"));

			double percentWheelColorValue = teamInfo.getDouble("color_wheel_2_avg");
			TextView percentWheelColor = findViewById(R.id.percentWheelColorText);
			percentWheelColor.setText(percentWheelColorValue + "%");
			ProgressBar percentWheelColorBar = findViewById(R.id.percentWheelColorBar);
			percentWheelColorBar.setProgress((int) percentWheelColorValue);
			TextView lastWheelColor = findViewById(R.id.lastWheelColor);
			lastWheelColor.setText(teamInfo.getString("color_wheel_2_last"));

			TextView comments = findViewById(R.id.comments);
			comments.setText(teamInfo.getString("comments"));

			LinearLayout gamesPlayed = findViewById(R.id.gamesPlayed);
			JSONArray games = teamInfo.getJSONArray("games");
			for (int i = 0; i < games.length(); i++) {
				//set the games
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
