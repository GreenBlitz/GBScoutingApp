package com.example.scouting_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.util.Constants;
import com.example.util.Net;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationPage extends AppCompatActivity {

	// outer scope variables to be accessed by separate threads
	JSONObject responseData = null;
	SharedPreferences sharedPref;
	SharedPreferences.Editor editor;
	private boolean loginSuccessful;
	private Pair<JSONObject, Boolean> loginResponse = null;
	private boolean loginThreadDone = false;
	final int MAX_WAIT_TIME = 10;
	static int uid = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) throws NumberFormatException {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_page);

		EditText PIN = findViewById(R.id.ENTER_PIN);
		Button LOGIN = findViewById(R.id.LOGIN);

		setTitle("Registration Page");

		sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE); // access phone memory
		editor = sharedPref.edit();

		JSONObject login = new JSONObject();
		try {
			//load shit into JSON
			login.put("uid", sharedPref.getInt("uid", 0));
			login.put("pass", sharedPref.getString("psw", "0"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LOGIN.setOnClickListener(v -> { // once PIN was entered and authentication has commenced
			Thread registerThread = new Thread(() -> {
				String pass = sharedPref.getString("psw", "0");
				String content = PIN.getText().toString();
				int pin = Integer.parseInt(content.equals("") ? "0" : content); // protection from parsing error for empty value in PIN
				JSONObject data = Net.createJSON(new Pair<>("pass", pass), new Pair<>("PIN", pin)); // generate JSON with password and PIN for auth
				Net.Method method = Net.Method.GET;
				String destURL = Constants.Networking.SERVER_URL.concat("auth/register?");

				boolean successful = false;
				try {
					// send registration request to server based on PIN given by system admin
					Pair<JSONObject, Boolean> response = Net.requestJSON(destURL, method, data);
					responseData = response.first;
					successful = response.second;
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					assert responseData != null; // hopefully the request actually worked so it doesn't die
					uid = responseData.getInt("uid"); // temporary handling with response before embedding the response data in system
				} catch (JSONException e) {
					e.printStackTrace();
				}
			});

			registerThread.start();
			long tStartRegister = System.currentTimeMillis();
			while (uid == -1 && System.currentTimeMillis() - tStartRegister < MAX_WAIT_TIME * 1000) {
				// wait for response with timeout of 5 seconds to get data.
			}
			try {
				// save authentication credentials in phone memory
				editor.putString("name", responseData.getString("name"));
				editor.putString("role", responseData.getString("role"));
				editor.putInt("uid", responseData.getInt("uid"));
				editor.apply();
				LoginPage.INSTANCE.login();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});
	}
}
