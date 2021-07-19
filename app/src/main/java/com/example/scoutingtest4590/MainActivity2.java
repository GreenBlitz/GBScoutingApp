package com.example.scoutingtest4590;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {
	String PIN = "";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		TextView ENTER_PIN = findViewById(R.id.ENTER_PIN);
		Button LOGIN = findViewById(R.id.LOGIN);
		
		LOGIN.setOnClickListener(v ->
		{
			PIN = ENTER_PIN.getText().toString();
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("PIN", PIN);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				File newfile = new File("output.json");
				newfile.createNewFile();
				FileWriter file = new FileWriter("output.json");
				file.write(jsonObject.toString());
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("JSON file created: " + jsonObject);
		});
	}
}