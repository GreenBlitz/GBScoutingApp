package com.example.scouting_app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class GamesPage extends AppCompatActivity {

	TableLayout mainTable;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games_page);

		mainTable = findViewById(R.id.mainTable);
	}

	public JSONObject parseJSON(String JSONString) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(JSONString);
		} catch (JSONException err) {
			Log.d("Error", err.toString());
		}
		return jsonObject;
	}

	public void addGameJSON(JSONObject jsonObject) {

	}

	@SuppressLint("ResourceAsColor")
	public void addGame(View v) {
		Drawable redBackground = findViewById(R.id.redTeam11).getBackground();
		Drawable blueBackground = findViewById(R.id.blueTeam11).getBackground();

		TableRow newRow = new TableRow(this);

		TextView redTeam1 = new TextView(this);
		TextView redTeam2 = new TextView(this);
		TextView redTeam3 = new TextView(this);
		redTeam1.setBackground(redBackground);
		redTeam2.setBackground(redBackground);
		redTeam3.setBackground(redBackground);
		redTeam1.setText("4590");
		redTeam2.setText("4590");
		redTeam3.setText("4590");
		redTeam1.setTextColor(Color.parseColor("#FFFFFF"));
		redTeam2.setTextColor(Color.parseColor("#FFFFFF"));
		redTeam3.setTextColor(Color.parseColor("#FFFFFF"));
		redTeam1.setTextSize(24);
		redTeam2.setTextSize(24);
		redTeam3.setTextSize(24);

		TextView blueTeam1 = new TextView(this);
		TextView blueTeam2 = new TextView(this);
		TextView blueTeam3 = new TextView(this);
		blueTeam1.setBackground(blueBackground);
		blueTeam2.setBackground(blueBackground);
		blueTeam3.setBackground(blueBackground);
		blueTeam1.setText("4590");
		blueTeam2.setText("4590");
		blueTeam3.setText("4590");
		blueTeam1.setTextColor(Color.parseColor("#FFFFFF"));
		blueTeam2.setTextColor(Color.parseColor("#FFFFFF"));
		blueTeam3.setTextColor(Color.parseColor("#FFFFFF"));
		blueTeam1.setTextSize(24);
		blueTeam2.setTextSize(24);
		blueTeam3.setTextSize(24);

		TextView matchTime = new TextView(this);
		matchTime.setTextColor(Color.parseColor("#FFFFFF"));
		matchTime.setText("16:20"); //temp line
		matchTime.setTextSize(20);

		TextView matchTag = new TextView(this);
		matchTag.setTextColor(Color.parseColor("#FFFFFF"));
		matchTag.setText("Qual 17 "); //temp line
		matchTag.setTextSize(20);

		TableRow redAlliance = new TableRow(this);
		redAlliance.addView(matchTime);
		redAlliance.addView(redTeam1);
		redAlliance.addView(redTeam2);
		redAlliance.addView(redTeam3);

		TableRow blueAlliance = new TableRow(this);
		blueAlliance.addView(matchTag);
		blueAlliance.addView(blueTeam1);
		blueAlliance.addView(blueTeam2);
		blueAlliance.addView(blueTeam3);

		TableLayout minorTable = new TableLayout(this);
		minorTable.addView(redAlliance);
		minorTable.addView(blueAlliance);

		newRow.addView(minorTable);

		mainTable.addView(newRow);
	}
}