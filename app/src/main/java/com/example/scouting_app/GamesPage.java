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
import androidx.core.content.res.ResourcesCompat;

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

	public void addGameJSON(JSONObject jsonObject) throws JSONException {
		//temporary placeholder strings from the json
		String redTeam1 = jsonObject.getString("redTeam1");
		String redTeam2 = jsonObject.getString("redTeam2");
		String redTeam3 = jsonObject.getString("redTeam3");
		String blueTeam1 = jsonObject.getString("blueTeam1");
		String blueTeam2 = jsonObject.getString("blueTeam2");
		String blueTeam3 = jsonObject.getString("blueTeam3");
		String matchTime = jsonObject.getString("matchTime");
		String matchTag = jsonObject.getString("matchTag");

	}

	@SuppressLint("ResourceAsColor")
	public void addGame(View v) {
		Drawable redBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.red_background, null);
		Drawable blueBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.blue_background, null);
		int textColor = Color.parseColor("#FFFFFF");

		TableRow newRow = new TableRow(this);
		TableRow redAlliance = new TableRow(this);
		TableRow blueAlliance = new TableRow(this);

		TextView[] redAllies = new TextView[4];
		for (int i = 0; i < 4; i++) {
			redAllies[i] = new TextView(this);
			redAllies[i].setBackground(redBackground);
			redAllies[i].setText("4590");
			redAllies[i].setTextColor(textColor);
			redAllies[i].setTextSize(24);
			redAllies[i].setGravity(0x11);
			redAlliance.addView(redAllies[i]);
		}

		TextView[] blueAllies = new TextView[4];
		for (int i = 0; i < 4; i++) {
			blueAllies[i] = new TextView(this);
			blueAllies[i].setBackground(blueBackground);
			blueAllies[i].setText("4590");
			blueAllies[i].setTextColor(textColor);
			blueAllies[i].setTextSize(24);
			blueAllies[i].setGravity(0x11);
			blueAlliance.addView(blueAllies[i]);
		}

		blueAllies[0].setTextSize(20);
		redAllies[0].setTextSize(20);
		redAllies[0].setBackground(null);
		blueAllies[0].setBackground(null);

		TableLayout minorTable = new TableLayout(this);
		minorTable.addView(redAlliance);
		minorTable.addView(blueAlliance);
		newRow.addView(minorTable);
		mainTable.addView(newRow);
	}
}