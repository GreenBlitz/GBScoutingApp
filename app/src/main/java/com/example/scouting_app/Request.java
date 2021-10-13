package com.example.scouting_app;

import android.util.Pair;

import com.example.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Request {
	private String url;
	private Net.Method method;
	private JSONObject data;
	private int waitTime;
	private String response;

	public Request(String url, Net.Method method, JSONObject data, int waitSeconds) {
		this.url = url;
		this.method = method;
		this.data = data;
		this.waitTime = waitSeconds * 1000;
	}

	public Request(String url, Net.Method method, JSONObject data) {
		this(url, method, data, 5);
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

	boolean successful, done = false;

	public boolean send() {
		this.done = false;
		Thread thread = new Thread(() -> {
			try {
				// send registration request to server based on PIN given by system admin

				Pair<String, Boolean> response = Net.request(url, method, data);

				this.successful = response.second;
				this.response = response.first;
				this.done = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		thread.start();
		long tStartRegister = System.currentTimeMillis();
		while (!done && System.currentTimeMillis() - tStartRegister < waitTime) {
			// wait for response with timeout of 5 seconds to get data.
		}

		return successful;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public JSONObject toJSON() {
		return Net.createJSON(new Pair<>("url", this.url), new Pair<>("method", this.method.toString()), new Pair<>("data", data), new Pair<>("waitTime", this.waitTime));
	}

	public static Request fromJSON(JSONObject json) throws JSONException {
		return new Request(json.getString("url"), Net.Method.valueOf(json.getString("method")), new JSONObject(json.getString("data")), json.getInt("waitTime"));
	}

	public String getResponse() {
		return response;
	}

	public JSONObject getResponseJSON() throws JSONException {
		return new JSONObject(response);
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
