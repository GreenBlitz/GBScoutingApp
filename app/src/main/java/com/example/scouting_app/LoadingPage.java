package com.example.scouting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import com.example.util.Net;

public class LoadingPage extends AppCompatActivity {
	private Request r;
	private Activity nextActivity;
	private boolean done;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_games_page);

		new Thread(this::sendShit).start();
	}

	public LoadingPage(Request r, Activity nextActivity) {
		super();
		this.r = r;
		this.nextActivity = nextActivity;
	}

	public void sendShit() {
		while (!done) {
			periodic();
		}
	}

	public void periodic() {
		Pair<String, Boolean> response = Net.request(r.getUrl(), r.getMethod(), r.getData());
		if(response.second) {
			Intent transfer = new Intent(this, nextActivity.getClass());
			transfer.putExtra("data", response.first);
			startActivity(transfer);
			done = true;
		}
	}
}