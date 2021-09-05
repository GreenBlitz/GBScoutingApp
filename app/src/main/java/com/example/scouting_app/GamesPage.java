package com.example.scouting_app;

import static com.example.util.Constants.GamesPage.TEAMS_PER_ALLIANCE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.util.Constants;
import com.example.util.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GamesPage extends AppCompatActivity {

	LinearLayout mainTable;
	JSONObject responseData = null;
	private static int ids;
	SharedPreferences sharedPref;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games_page);
		setTitle("Games Page");

		mainTable = findViewById(R.id.mainTable);
		sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE); // access phone memory
		ids = 0;

		Thread thread = new Thread(() -> {
			//get password and id
			String pass = sharedPref.getString("psw", "0");
			int uid = sharedPref.getInt("uid", 0);
			JSONObject data = Net.createJSON(new Pair<>("pass", pass), new Pair<>("uid", uid)); //generate JSON with password and PIN for auth
			Net.Method method = Net.Method.GET; //create the JSON and get the GET method
			String destURL = Constants.Networking.serverURL.concat("general/games?");
			boolean successful = false;
			try {
				// send authentication requesting games from server
				Pair<JSONObject, Boolean> response = Net.requestJSON(destURL, method, data); // send auth request
				responseData = response.first;
				successful = response.second;
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread.start();
		long tStart = System.currentTimeMillis();
		while (responseData == null && System.currentTimeMillis() - tStart < 5000) {
			//wait for response with timeout of 5 seconds to get data since the speed of cyber isn't ∞
		}

		JSONArray arr = null;
		try {
			arr = (JSONArray) responseData.get("games");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < arr.length(); i++) { //triple-nested loop
			try {  //defining shit for each team in each alliance for each game
				JSONObject game = arr.getJSONObject(i);
				JSONArray teams = game.getJSONArray("alliances");
				String[][] teamHashes = new String[2][3]; //2-D array for teams in alliances
				for (int j = 0; j < teams.length(); j++) {
					JSONArray alliance = teams.getJSONArray(j); //get the alliance
					for (int k = 0; k < alliance.length(); k++) {
						teamHashes[j][k] = alliance.getString(k); //fill the array
					}
				}
				addGame(game.getString("time"), game.getString("gameID"), teamHashes);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressLint({"ResourceAsColor", "DefaultLocale", "SetTextI18n"})
	public void addGame(String time, String gameID, String[][] alliances) throws JSONException {
		//background variables
		Drawable redBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.red_background, null);
		Drawable blueBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.blue_background, null);
		Drawable[] bgs = {redBackground, blueBackground};
		int textColor = Color.parseColor("#FFFFFF");

		LinearLayout game = new LinearLayout(this);
		game.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
		game.setOrientation(LinearLayout.VERTICAL);

		//set the linear layout for the alliances
		for (int i = 0; i < Constants.GamesPage.ALLIANCES; i++) {
			//define the BS LayoutParams
			LinearLayout alliance = new LinearLayout(this);
			alliance.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
			alliance.setOrientation(LinearLayout.HORIZONTAL);
			//stuff
			TextView timeOrGame = new TextView(this);
			timeOrGame.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3));
			timeOrGame.setTextColor(textColor);
			timeOrGame.setGravity(Gravity.CENTER);
			timeOrGame.setTextSize(20);
			timeOrGame.setId(ids);
			ids++;
			String text;

			switch (i) { //is this time or qual
				case 0:
					text = time.substring(10, 16); //remove the seconds
					break;
				case 1:
					text = gameID;
					break;
				default:
					text = "broken :(";
			}

			timeOrGame.setText(text);
			alliance.addView(timeOrGame);

			for (int j = 0; j < TEAMS_PER_ALLIANCE; j++) {
				//set all the bullshit for each time
				TextView team = new TextView(this);
				team.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2));
				team.setText(String.format("\t%d, %d\t", i, j));
				team.setGravity(Gravity.CENTER);
				team.setTextColor(textColor);
				team.setBackground(bgs[i]);
				team.setTextSize(24);
				team.setText(alliances[i][j]);
				team.setId(ids);
				team.setOnClickListener(v -> {
					assert v instanceof TextView;
					String role = sharedPref.getString("role", "nigga");
					System.out.println("WIFI: " + role);

					Intent newIntent;
					// create dynamic transfer from games activity to either scouting prompt or coach team info
					switch (role) {
						case "scouter":
							newIntent = new Intent(GamesPage.this, ScoutingPrompt.class);
							@SuppressLint("ResourceType") TextView gameID1 = findViewById(v.getId() - (v.getId() % 8) + 4);
							newIntent.putExtra("gameID", (String) gameID1.getText()); // give scouting prompt information for game which is being scouted
							break;
						case "coach":
							newIntent = new Intent(GamesPage.this, CoachInfoTeam.class);
							break;
						default:
							newIntent = new Intent(GamesPage.this, ErrorActivity.class);
							break;
					}
					newIntent.putExtra("team", (String) ((TextView) v).getText()); // transfer allocated team in intent
					startActivity(newIntent);
				});
				ids++;
				alliance.addView(team);
			}
			game.addView(alliance);
		}
		mainTable.addView(game);
	}
}
