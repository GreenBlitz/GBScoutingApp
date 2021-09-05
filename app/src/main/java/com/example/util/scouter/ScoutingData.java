package com.example.util.scouter;

public class ScoutingData<T> {
	private String key; // basically a json entry
	private T value;

	public ScoutingData(String key, T value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public String toString() {
		return String.format(this.value instanceof String ? "\"%s\": \"%s\"" : "\"%s\": %s", this.key, this.value.toString()); // string representation for json, honestly JSONObject is much preferable
	}
}
