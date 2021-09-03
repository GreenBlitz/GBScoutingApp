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
import android.view.View;
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
import org.w3c.dom.Text;

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
        mainTable = findViewById(R.id.mainTable);
        sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE); // access phone memory
        setTitle("Games Page");

        ids = 0;

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE); // access phone memory

        Thread thread = new Thread(() -> {
            String pass = sharedPref.getString("password", "0");
            int uid = sharedPref.getInt("uid", 0);
            JSONObject data = Net.createJSON(new Pair<>("pass", pass), new Pair<>("uid", uid)); // generate JSON with password and PIN for auth
            Net.Method method = Net.Method.GET;
            String destURL = Constants.Networking.serverURL.concat("general/games?"); // TODO: url is currently dynamic, need to convert to some sort of DNS perhaps
            boolean successful = false;
            try {
                Pair<JSONObject, Boolean> response = Net.requestJSON(destURL, method, data); // send authentication request
                System.out.println("NITZ: " + response);
                responseData = response.first;
                successful = response.second;
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        thread.start();
        long tStart = System.currentTimeMillis();
        while (responseData == null && System.currentTimeMillis() - tStart < 5000) { // wait for response with timeout of 5 seconds to get data.
        }
        JSONArray arr = null;
        try {
            System.out.println("ROY ROY ROY" + responseData);
            arr = (JSONArray) responseData.get("games");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < arr.length(); i++) {
            try {
                System.out.println(arr.get(i));
                JSONObject game = arr.getJSONObject(i);
                JSONArray teams = game.getJSONArray("alliances");
                System.out.println(teams.length());
                String[][] teamHashes = new String[2][3];
                for (int j = 0; j < teams.length(); j++) {
                    JSONArray alliance = teams.getJSONArray(j);
                    for (int k = 0; k < alliance.length(); k++) {
                        teamHashes[j][k] = alliance.getString(k);
                    }
                }
                addGame(game.getString("time"), game.getString("gameID"), teamHashes);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int id = 0; id < ids; id++) {
            System.out.println(id + ": " + ((TextView) findViewById(id)).getText());
        }
    }

    @SuppressLint({"ResourceAsColor", "DefaultLocale", "SetTextI18n"})
    public void addGame(String time, String gameID, String[][] alliances) throws JSONException {
        Drawable redBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.red_background, null);
        Drawable blueBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.blue_background, null);
        Drawable[] bgs = {redBackground, blueBackground};
        int textColor = Color.parseColor("#FFFFFF");

        LinearLayout game = new LinearLayout(this);
        game.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
        game.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < Constants.GamesPage.ALLIANCES; i++) {
            LinearLayout alliance = new LinearLayout(this);
            alliance.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
            alliance.setOrientation(LinearLayout.HORIZONTAL);

            TextView timeOrGame = new TextView(this);
            timeOrGame.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3));
            timeOrGame.setTextColor(textColor);
            timeOrGame.setGravity(Gravity.CENTER);
            timeOrGame.setTextSize(20);
            timeOrGame.setId(ids);
            ids++;

            String text;

            switch (i) {
                case 0:
                    text = (String) time.substring(10, 16); //remove the seconds
                    break;
                case 1:
                    text = gameID;
                    break;
                default:
                    text = "broken";
            }

            timeOrGame.setText(text);
            alliance.addView(timeOrGame);

            for (int j = 0; j < TEAMS_PER_ALLIANCE; j++) {
                TextView team = new TextView(this);
                team.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2));
                team.setText(String.format("\t%d, %d\t", i, j));
                team.setGravity(Gravity.CENTER);
                team.setTextColor(textColor);
                team.setBackground(bgs[i]);
                team.setTextSize(24);
                team.setText(alliances[i][j]);
                team.setId(ids);
                team.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        assert v instanceof TextView;

                        String role = "coach";

                        Intent i;
                        switch (role) {
                            case "scouter":
                                i = new Intent(GamesPage.this, ScoutingPrompt.class);
                                @SuppressLint("ResourceType") TextView gameID = findViewById(v.getId() - (v.getId() % 8) + 4);
                                i.putExtra("gameID", (String) gameID.getText());
                                break;
                            case "coach":
                                i = new Intent(GamesPage.this, CoachInfoTeam.class);
                                break;
                            default:
                                return;
                        }

                        i.putExtra("team", (String) ((TextView) v).getText());

                        startActivity(i);

                    }
                });
                ids++;

                alliance.addView(team);
            }
            game.addView(alliance);
        }
        mainTable.addView(game);
    }
}