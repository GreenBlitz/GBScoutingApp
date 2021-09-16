package com.example.scouting_app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_error);
		setTitle("Error");
		Toast.makeText(getApplicationContext(), "ERROR ENCOUNTERED", Toast.LENGTH_LONG).show();
		//oh shit
	}
}