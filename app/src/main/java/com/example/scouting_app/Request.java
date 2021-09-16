package com.example.scouting_app;

import android.util.Pair;

import com.example.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.util.*;

public class Request {
    private String url;
    private Net.Method method;
    private JSONObject data;

    public Request(String url, Net.Method method, JSONObject data) {
        this.url = url;
        this.method = method;
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Net.Method getMethod() {
        return method;
    }

    public void setMethod(Net.Method method) {
        this.method = method;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    boolean successfull, done = false;

    public boolean send() {
        Thread thread = new Thread(() -> {
            boolean done = false;
            try {
                // send registration request to server based on PIN given by system admin
                Pair<JSONObject, Boolean> response = Net.requestJSON(url, method, data);

                this.successfull = response.second;
                this.done = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        long tStartRegister = System.currentTimeMillis();
        while (!done && System.currentTimeMillis() - tStartRegister < 5000) {
            // wait for response with timeout of 5 seconds to get data.
        }

        return successfull;
    }
}
