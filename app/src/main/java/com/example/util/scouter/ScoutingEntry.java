package com.example.util.scouter;

import static com.example.util.Net.createJSON;

import com.example.util.Constants;
import com.example.util.Net;

import java.util.ArrayList;
import java.util.Collections;

public class ScoutingEntry {
	private ArrayList<ScoutingData<Object>> gameEntry;
	public static final String dataSubdomain = "scouting/?";

	public ScoutingEntry(ArrayList<ScoutingData<Object>> gameEntry) {
		this.gameEntry = gameEntry;
	}

	public ScoutingEntry(ScoutingData<Object>... gameEntry) {
		ArrayList<ScoutingData<Object>> a = new ArrayList<>();
		Collections.addAll(a, gameEntry);
		this.gameEntry = a;
	}

	public ArrayList<ScoutingData<Object>> getGameEntry() {
		return gameEntry;
	}

	public void resetEntry() {
		this.gameEntry = new ArrayList<>();
	}

	public void addData(ScoutingData<Object> data) {
		gameEntry.add(data);
	}

	public void sendData() throws Exception {
		ArrayList<Object> keys = new ArrayList<>(), values = new ArrayList<>();
		for (ScoutingData<Object> data : gameEntry) {
			keys.add(data.getKey());
			values.add(data.getValue());
		}

		System.out.println("jayson" + createJSON(keys, values).toString());
		Net.request(Constants.Networking.serverURL.concat(dataSubdomain), Net.Method.POST, createJSON(keys, values));
	}
}
