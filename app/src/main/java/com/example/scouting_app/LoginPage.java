package com.example.scouting_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;

import com.example.util.Constants;
import com.example.util.Net;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class LoginPage extends AppCompatActivity {
	final String POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // all possible letters for pass
	final int SIZE = 30; // pass size
	SharedPreferences sharedPref;
	SharedPreferences.Editor editor;
	Random r = new Random();
	private boolean loginSuccessful;
	private Pair<JSONObject, Boolean> loginResponse = null;
	private boolean loginThreadDone = false;

	public String genPass() {
		String ret = "";
		for (int i = 0; i < SIZE; i++) { // picks random char from POOL, SIZE times and appends to string.
			int idx = r.nextInt(POOL.length());
			ret = ret.concat(POOL.substring(idx, idx + 1));
		}
		return ret;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);
		setTitle("Login Page");
		Thread thread = new Thread(this::login);
		thread.start();
	}

	public void login() {
		sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE); // access phone memory
		editor = sharedPref.edit();
		if (sharedPref.getString("psw", "0").equals("0")) {
			editor.putString("psw", genPass()); // save generated password
			editor.apply();
			Intent switchActivity = new Intent(this, RegistrationPage.class);
			startActivity(switchActivity);
		}
		JSONObject login = new JSONObject();
		try {
			//load shit into JSON
			login.put("uid", sharedPref.getInt("uid", 0));
			login.put("pass", sharedPref.getString("psw", "0"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(() -> {
			try {
				// try to login first in case user has already been registered.
				loginResponse = Net.requestJSON(Constants.Networking.serverURL.concat("auth/login?"), Net.Method.GET, login);
				System.out.println(loginResponse.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				// if response succeeded and the login was successful update name and role
				if (loginResponse.second && loginResponse.first.getBoolean("success")) {
					// update name and role in case one of the scouters didn't come or they have a different role throughout the competition
					editor.putString("name", loginResponse.first.getString("name"));
					editor.putString("role", loginResponse.first.getString("role"));
					editor.commit();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				loginSuccessful = loginResponse.second && loginResponse.first.getBoolean("success");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			loginThreadDone = true;
			// thread is done, so main thread can get viable information
		});
		thread.start();
		long tStart = System.currentTimeMillis();
		while (!loginThreadDone && System.currentTimeMillis() - tStart < 5000) {
			// wait 5s for networking thread to finish
		}
		loginThreadDone = false;
		if (loginResponse == null) { // if login request fell through be done since we aren't handling networking issues as of now
			Intent switchActivity = new Intent(this, ErrorActivity.class);
			startActivity(switchActivity);
		} else {
			if (loginSuccessful) {
				// if login was successful there is no need to register new user, go to games page
				Intent switchActivity = new Intent(this, GamesPage.class);
				startActivity(switchActivity);
			}
		}
	}
}