package com.example.scouting_app.networking;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;
import java.util.Queue;

public class NetworkIssuesHandling {
	private static Queue<Request> entries;
	private static boolean initialized = false;
	private static SharedPreferences prefs;
	private static final String SERIALIZE_KEY = "serialized";

	private static void serialize() {
		JSONArray jsonArray = new JSONArray();

		for (Request req : entries) {
			jsonArray.put(req.toJSON());
		}
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(SERIALIZE_KEY, jsonArray.toString());
		editor.apply();
	}

	private static boolean loadFromSerialized() {
		try {
			String asStr = prefs.getString(SERIALIZE_KEY, null);
			if (asStr == null) {
				return false;
			}
			JSONArray deserialized = new JSONArray(asStr);
			entries = new LinkedList<>();
			for (int i = 0; i < deserialized.length(); i++) {
				entries.add(Request.fromJSON(deserialized.getJSONObject(i)));
			}
			return true;
		} catch (ClassCastException | JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void addToQueue(Request request) {
		entries.add(request);
		serialize();
	}

	private static void main() {
		while (true) {
			if (entries.size() > 0) {
				Request temp = entries.remove();
				if (!temp.send()) {
					entries.add(temp);
				} else {
					serialize();
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void init(SharedPreferences p) {
		if (!initialized) {
			prefs = p;
			if (!loadFromSerialized()) {
				entries = new LinkedList<>();
			}
			new Thread(NetworkIssuesHandling::main).start();
			initialized = true;
		}
	}

}
