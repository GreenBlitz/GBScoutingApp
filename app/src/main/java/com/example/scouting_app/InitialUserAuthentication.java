package com.example.scouting_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import java.util.Random;

public class InitialUserAuthentication extends AppCompatActivity {
    String POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // all possible letters for pass
    int SIZE = 30; // pass size
    Random r = new Random();

    public String genPass() {
        String ret = "";
        for (int i = 0; i < SIZE; i++) { // picks random char from POOL, SIZE times and appends to string.
            int idx = r.nextInt(POOL.length());
            ret = ret.concat(POOL.substring(idx, idx + 1));
        }
        return ret;
    }

    static int uid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NumberFormatException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_user_auth);

        setTitle("Initial User Authentication");

        EditText PIN = (EditText) findViewById(R.id.ENTER_PIN);
        Button LOGIN = findViewById(R.id.LOGIN);

        SharedPreferences sharedPref = InitialUserAuthentication.this.getPreferences(Context.MODE_PRIVATE); // access phone memory
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();
        if (sharedPref.getString("password", "0").equals("0")) { // if didn't save anything for password must generate one and save it
            editor.putString("password", genPass()); // save generated password
            editor.apply();
        }

        LOGIN.setOnClickListener(v -> { // once PIN was entered and authentication has commenced
            Thread thread = new Thread(() -> {
                String pass = sharedPref.getString("password", "0");
                String content = PIN.getText().toString();
                int pin = Integer.parseInt(content.equals("") ? "0" : content); // protection from parsing error for empty value in PIN
                JSONObject data = Net.createJSON(new Pair<>("pass", pass), new Pair<>("PIN", pin)); // generate JSON with password and PIN for auth
                Net.Method method = Net.Method.GET;
                String destURL = Constants.Networking.serverURL.concat("auth/register?"); // TODO: url is currently dynamic, need to convert to some sort of DNS perhaps
                JSONObject responseData = null;
                boolean successfull = false;
                try {
                    Pair<JSONObject, Boolean> response = Net.requestJSON(destURL, method, data); // send authentication request
                    responseData = response.first;
                    successfull = response.second;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(responseData);
                try {
                    uid = responseData.getInt("uid"); // temporary handling with response before embedding the response data in system
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

            thread.start();
            long tStart = System.currentTimeMillis();
            while (uid == -1 && System.currentTimeMillis() - tStart < 5000) { // wait for response with timeout of 5 seconds to get data.
                System.out.println("waiting");
            }

            editor.putInt("uid", uid);
        });
    }

    public void switchActivities(String page) {
        Intent switchActivity;
        switch (page) {
            case "scouter": //TODO: shitty one-case switch statement, pls fix
                switchActivity = new Intent(this, ScoutingPrompt.class);
                break;
            default:
                switchActivity = new Intent(this, ErrorActivity.class);
        }

        startActivity(switchActivity);
    }

}