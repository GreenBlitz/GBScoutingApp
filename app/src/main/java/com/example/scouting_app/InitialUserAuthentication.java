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
    final String POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // all possible letters for pass
    final int SIZE = 30; // pass size
    JSONObject responseData = null;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Random r = new Random();
    private boolean loginSuccessfull;
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

    static int uid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NumberFormatException {
        EditText PIN = findViewById(R.id.ENTER_PIN);
        Button LOGIN = findViewById(R.id.LOGIN);

        sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE); // access phone memory
        System.out.println("RAZ RAZ RAZ 1: " + sharedPref.getAll().toString());
        editor = sharedPref.edit();

        if (sharedPref.getString("psw", "0").equals("0")) { // if didn't save anything for password must generate one and save it
            editor.putString("psw", genPass()); // save generated password
            editor.apply();
        } else {
            JSONObject login = new JSONObject();
            try {
                login.put("uid", sharedPref.getInt("uid", 0));
                login.put("pass", sharedPref.getString("psw", "0"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Thread thread = new Thread(() -> {
                try {
                    loginResponse = Net.requestJSON(Constants.Networking.serverURL.concat("auth/login?"), Net.Method.GET, login);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("loginResponse.second: " + loginResponse.second);
                    System.out.println("loginResponse.first.success: " + loginResponse.first.getBoolean("success"));
                    if (loginResponse.second && loginResponse.first.getBoolean("success")) {
                        editor.putString("name", loginResponse.first.getString("name"));
                        editor.putString("role", loginResponse.first.getString("role"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    loginSuccessfull = loginResponse.second && loginResponse.first.getBoolean("success");
                    System.out.println("loginSuccessful: " + loginSuccessfull);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loginThreadDone = true;
            });
            thread.start();

            long tStart = System.currentTimeMillis();
            while (!loginThreadDone && System.currentTimeMillis() - tStart < 5000) {
            }
            if (loginResponse == null) {
                finish();
            } else {
                System.out.println("OREL: " + loginSuccessfull);
                if (loginSuccessfull) {
                    System.out.println("How did we get here?");
                    Intent i = new Intent(this, GamesPage.class);
                    startActivity(i);

                }
            }

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_init_user_auth);
            setTitle("Initial User Authentication");


            LOGIN.setOnClickListener(v -> { // once PIN was entered and authentication has commenced
                Thread registerThread = new Thread(() -> {
                    String pass = sharedPref.getString("psw", "0");
                    String content = PIN.getText().toString();
                    int pin = Integer.parseInt(content.equals("") ? "0" : content); // protection from parsing error for empty value in PIN
                    JSONObject data = Net.createJSON(new Pair<>("pass", pass), new Pair<>("PIN", pin)); // generate JSON with password and PIN for auth
                    Net.Method method = Net.Method.GET;
                    String destURL = Constants.Networking.serverURL.concat("auth/register?"); // TODO: url is currently dynamic, need to convert to some sort of DNS perhaps


                    boolean successful = false;
                    try {
                        Pair<JSONObject, Boolean> response = Net.requestJSON(destURL, method, data); // send authentication request

                        responseData = response.first;
                        successful = response.second;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("keyword" + responseData);
                    try {
                        assert responseData != null;
                        uid = responseData.getInt("uid"); // temporary handling with response before embedding the response data in system
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });

                registerThread.start();
                long tStartRegister = System.currentTimeMillis();
                while (uid == -1 && System.currentTimeMillis() - tStartRegister < 5000) { // wait for response with timeout of 5 seconds to get data.
//				System.out.println("waiting");
                }

                try {
                    System.out.println("RAZ RAZ RAZ 2: " + editor);
                    editor.putString("name", responseData.getString("name"));
                    editor.putString("role", responseData.getString("role"));
                    editor.putInt("uid", responseData.getInt("uid"));
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent switchActivity;
                switchActivity = new Intent(this, GamesPage.class);
                startActivity(switchActivity);

            });
        }


    }
}
