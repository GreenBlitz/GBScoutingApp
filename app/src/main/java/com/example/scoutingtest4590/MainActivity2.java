package com.example.scoutingtest4590;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class MainActivity2 extends AppCompatActivity {
    String PIN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView ENTER_PIN = findViewById(R.id.ENTER_PIN);
        Button LOGIN = findViewById(R.id.LOGIN);

        LOGIN.setOnClickListener(v ->
        {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        //Your code goes here
                        PIN = ENTER_PIN.getText().toString();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("PIN", PIN);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
//				File newFile = new File("output.json");
                            FileWriter file = new FileWriter("output.json");
                            file.write(jsonObject.toString());
                            file.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("JSON file created: " + jsonObject);


                        URL url = null;
                        try {
                            url = new URL("http://192.168.43.143:5000/?a=kk");
                            System.out.println("successfully created URL");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        HttpURLConnection conn = null;
                        try {
                            conn = (HttpURLConnection) url.openConnection();
                            System.out.println("Successfully initiated connection");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        conn.setDoOutput(false);
                        try {
                            conn.setRequestMethod("GET");
                            System.out.println(conn.getRequestMethod());
                            System.out.println("Successfully set request method");
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }
                        
                        BufferedReader br = null;
                        try {
                            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            System.out.println("Successfully created Input Buffer");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String strCurrentLine = "";
                        String allText = "";
                        while (true) {
                            try {
                                if ((strCurrentLine = br.readLine()) == null) break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            allText = allText.concat(strCurrentLine);
                        }
                        System.out.println(allText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        });
    }

	/*

	@SuppressLint("SetTextI18n")
	public JSONObject foo(String url, JSONObject json)
	{
		//This method was copied from the internet
		JSONObject jsonObjectResp = null;

		try
		{
			MediaType JSON = MediaType.parse("application/json; charset=utf-8");
			OkHttpClient client = new OkHttpClient();

			okhttp3.RequestBody body = RequestBody.create(JSON, json.toString());
			okhttp3.Request request = new okhttp3.Request.Builder()
					.url(url)
					.post(body)
					.build();

			okhttp3.Response response = client.newCall(request).execute();

			//This was added manually
			Button LOGIN = findViewById(R.id.LOGIN);
			LOGIN.setText("so tired");

			assert response.body() != null;
			String networkResp = Objects.requireNonNull(response.body()).string();
			if (!networkResp.isEmpty()) jsonObjectResp = parseJSONStringToJSONObject(networkResp);
		}
		catch (Exception ex)
		{
			String err = String.format("{\"result\":\"false\",\"error\":\"%s\"}", ex.getMessage());
			jsonObjectResp = parseJSONStringToJSONObject(err);
		}

		return jsonObjectResp;
	}

	private static JSONObject parseJSONStringToJSONObject(final String strr)
	{
		//This method was copied from the internet
		JSONObject response = null;
		try {
			response = new JSONObject(strr);
		} catch (Exception ex) {
			//  Log.e("Could not parse malformed JSON: \"" + json + "\"");
			try {
				response = new JSONObject();
				response.put("result", "failed");
				response.put("data", strr);
				response.put("error", ex.getMessage());
			} catch (Exception ignored) {
			}
		}
		return response;
	}

	 */
}