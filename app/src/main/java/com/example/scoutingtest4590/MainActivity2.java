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
    String POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int SIZE = 30;
    Random r = new Random();

    public String genPass() {
        String ret = "";
        for (int i = 0; i < SIZE; i++) {
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

        SharedPreferences sharedPref = MainActivity2.this.getPreferences(Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();
        if (sharedPref.getString("password", "0").equals("0")) {
            editor.putString("password", genPass());
            editor.apply();
        }


        LOGIN.setOnClickListener(v -> {
            Thread thread = new Thread(() -> {
                String pass = sharedPref.getString("password", "0");
                String content = PIN.getText().toString();
                int pin = Integer.parseInt(content.equals("") ? "0" : content);
                JSONObject data = Net.createJSON(new Pair<>("pass", pass), new Pair<>("PIN", pin));
                Net.Method method = Net.Method.GET;
                String destURL = "http://192.168.111.125:5000/?"; // TODO: url is currently dynamic, need to convert to some sort of DNS perhaps
                String responseData = "Request Failed";
                try {
                    responseData = Net.request(destURL, method, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(responseData);
                activity = responseData;
            });

            thread.start();
            long tStart = System.currentTimeMillis();
            while (activity.equals("") && System.currentTimeMillis() - tStart < 5000) {
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