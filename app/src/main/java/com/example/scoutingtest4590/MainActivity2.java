package com.example.scoutingtest4590;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import com.example.scoutingtest4590.util.Net;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
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

    static String activity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NumberFormatException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EditText PIN = (EditText) findViewById(R.id.ENTER_PIN);
        Button LOGIN = findViewById(R.id.LOGIN);

        SharedPreferences sharedPref = MainActivity2.this.getPreferences(Context.MODE_PRIVATE); // access phone memory
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
                String destURL = "http://192.168.237.125:5000/auth/register?"; // TODO: url is currently dynamic, need to convert to some sort of DNS perhaps
                String responseData = "Request Failed";
                try {
                    responseData = Net.request(destURL, method, data); // send authentication request
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(responseData);
                activity = responseData; // temporary hadling with response before embedding the response data in system
            });

            thread.start();
            long tStart = System.currentTimeMillis();
            while (activity.equals("") && System.currentTimeMillis() - tStart < 5000) { // wait for response with timeout of 5 seconds to get data.
                System.out.println("waiting");
            }

            switchActivities(activity);
        });


    }

    public void switchActivities(String page) {
        Intent switchActivity;
        switch (page) {
            case "scouter":
                switchActivity = new Intent(this, ScouterActivity.class);
                break;
            default:
                switchActivity = new Intent(this, ErrorActivity.class);
        }

        startActivity(switchActivity);
    }

}