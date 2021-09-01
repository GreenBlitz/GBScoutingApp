package com.example.util;

import android.util.Pair;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class Net {

    public static JSONObject createJSON(Pair<Object, Object>... indices) {
        /**
         * @param indices: Array of pairs commentating key to value parity.
         * @returns: JSON object containing all items in indices.
         *
         * Runs a for loop through the array for each pair
         * getting first as the key and second as the value in the JSON.
         * exporting each object with their toString() function
         *
         * requirement: can't have to Pairs with the same key.
         * requirement: all objects given must have a toString() function so it doesn't simply put the pointer.
         */
        JSONObject ret = new JSONObject();

        try {
            for (Pair<Object, Object> index : indices) {
                ret.put(index.first.toString(), index.second.toString()); // Add entry to JSON with key and value.
            }
        } catch (JSONException e) {
            e.printStackTrace(); // throws JSONException in case entry is unsuccessful
        }

        return ret;
    }

    public static JSONObject createJSON(ArrayList<Object> keys, ArrayList<Object> items) throws AssertionError {
        /**
         * @param keys: ArrayList of key objects.
         * @param items: ArrayList of value objects.
         * @returns: JSON object containing all items with the key as keys values and value as items values.
         *
         * requirement: can't have to Pairs with the same key.
         * requirement: all objects given must have a toString() function so it doesn't simply put the pointer.
         */

        JSONObject ret = new JSONObject();
        assert keys.size() == items.size();

        try {
            for (int i = 0; i < keys.size(); i++) {
                ret.put(keys.get(i).toString(), items.get(i).toString()); // Add entry to JSON with key and value.
            }
        } catch (JSONException e) {
            e.printStackTrace(); // throws JSONException in case entry is unsuccessful
        }

        return ret;
    }

    public static String formatURL(String destURL, JSONObject data) {
        /**
         * @param data: JSON object to format for http augmentation.
         * @param destURL: IP and port for server destination as request url.
         *
         * @returns: formatted string for URL object to send request.
         *
         * Loops over keys iterator from data until empty, formats and adds upon for each item.
         */
        if (data == null) return destURL;

        String url = destURL;
        if (!url.endsWith("?")) {
            url = url.concat("?");
        }
        url = url.concat("json=" + data.toString());

        return url;
    }

    public static Pair<String, Boolean> request(String dest, Method method, JSONObject data) {
        URL url = null;
        try {
            System.out.println(formatURL(dest, data));
            url = new URL(formatURL(dest, data));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            assert url != null;
            conn = (HttpURLConnection) url.openConnection(); // open request connection with server
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert conn != null;
            conn.setRequestMethod(method.toString()); // set request method
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream())); // create input buffer which chunks response data
        } catch (IOException e) {
            e.printStackTrace();
        }
        String strCurrentLine;
        String allText = "";
        try {
            if (br == null) {
                return new Pair<>("", false);
            }
            strCurrentLine = br.readLine();
            while (strCurrentLine != null) { // adds each chunk until runs out
                allText = allText.concat(strCurrentLine); // concat data
                strCurrentLine = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Pair<>(allText, true);
    }

    public static Pair<String, Boolean> request(String dest, Method method) {
        return request(dest, method, null);
    }

    public static Pair<JSONObject, Boolean> requestJSON(String dest, Method method, JSONObject data) throws JSONException {
        Pair<String, Boolean> response = request(dest, method, data);
        if (!response.second) {
            return new Pair<>(null, false);
        }

        JSONObject json;
        try {
            json = new JSONObject(response.first);
        } catch (JSONException e) {
            return new Pair<>(null, false);
        }

        return new Pair<>(json, true);
    }

    public static Pair<JSONObject, Boolean> requestJSON(String dest, Method method) throws JSONException {
        return requestJSON(dest, method, null);
    }

    public enum Method {
        GET("GET"),
        POST("POST"),
        HEAD("HEAD"),
        OPTIONS("OPTIONS"),
        PUT("PUT"),
        DELETE("DELETE"),
        TRACE("TRACE");

        private final String display;

        Method(String display) {
            this.display = display;
        }

        @NonNull
        @Override
        public String toString() {
            return this.display;
        }

    }

}