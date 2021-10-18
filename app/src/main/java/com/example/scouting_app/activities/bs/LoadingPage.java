package com.example.scouting_app.activities.bs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.scouting_app.R;
import com.example.scouting_app.networking.Request;

public class LoadingPage extends AppCompatActivity {
	private Request request;
	private Activity nextActivity;
	private boolean done;

	public static int WAIT_TIME = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading_page);

		new Thread(this::sendShit).start();
	}

	public LoadingPage(Request request, Activity nextActivity) {
		super();
		this.request = request;
		this.nextActivity = nextActivity;
	}

	public void sendShit() {
		while (!done) {
			periodic();
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void periodic() {
		if (request.send()) {
			Intent transfer = new Intent(this, nextActivity.getClass());
			transfer.putExtra("data", request.getResponse());
			startActivity(transfer);
			done = true;
		}
	}
}