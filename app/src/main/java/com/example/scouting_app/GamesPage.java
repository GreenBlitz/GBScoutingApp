package com.example.scouting_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class GamesPage extends AppCompatActivity {

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games_page);
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

	public void updateGame(JSONObject gameData) {
		
	}
}