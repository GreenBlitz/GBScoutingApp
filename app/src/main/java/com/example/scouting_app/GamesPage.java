package com.example.scouting_app;

import static com.example.util.Constants.GamesPage.TEAMS_PER_ALLIANCE;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.example.util.Constants;

public class GamesPage extends AppCompatActivity {

    LinearLayout mainTable;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_page);

        mainTable = findViewById(R.id.firstRow);
    }

    public JSONObject parseJSON(String JSONString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSONString);
        } catch (JSONException err) {
            Log.d("Error", err.toString());
        }
        return jsonObject;
    }

    public void addGameJSON(JSONObject jsonObject) throws JSONException {
        //temporary placeholder strings from the json
        String redTeam1 = jsonObject.getString("redTeam1");
        String redTeam2 = jsonObject.getString("redTeam2");
        String redTeam3 = jsonObject.getString("redTeam3");
        String blueTeam1 = jsonObject.getString("blueTeam1");
        String blueTeam2 = jsonObject.getString("blueTeam2");
        String blueTeam3 = jsonObject.getString("blueTeam3");
        String matchTime = jsonObject.getString("matchTime");
        String matchTag = jsonObject.getString("matchTag");

    }

    @SuppressLint("ResourceAsColor")
    public void addGame(View v) {
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

            TextView timeQual = new TextView(this);
            timeQual.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3));
            timeQual.setTextColor(textColor);
            timeQual.setGravity(Gravity.CENTER);
            timeQual.setTextSize(20);

            String text;

            switch (i) {
                case 0:
                    text = "16:20";
                    break;
                case 1:
                    text = "Qual 17";
                    break;
                default:
                    text = "broken";
            }

            timeQual.setText(text);
            alliance.addView(timeQual);

            for (int j = 0; j < TEAMS_PER_ALLIANCE; j++) {
                TextView team = new TextView(this);
                team.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2));
                team.setText(String.format("\t%d, %d\t", i, j));
                team.setGravity(Gravity.CENTER);
                team.setTextColor(textColor);
                team.setBackground(bgs[i]);
                team.setTextSize(24);

                alliance.addView(team);
            }

            game.addView(alliance);
        }

        mainTable.addView(game);
    }
}