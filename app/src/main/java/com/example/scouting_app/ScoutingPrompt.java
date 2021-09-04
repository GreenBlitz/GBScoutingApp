package com.example.scouting_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator; //haha
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.util.Constants;
import com.example.util.scouter.ScoutingData;
import com.example.util.scouter.ScoutingEntry;

import java.util.Objects;

public class ScoutingPrompt extends AppCompatActivity {
    //Scouting info
    int cycles = 0;
    int autoBalls = 0;
    int teleopBalls = 0;

    boolean GreenBlitzColorsEnabled = false;
    @SuppressLint("WrongViewCast")

    private CheckBox RouletteWheelByRotations;
    private CheckBox RouletteWheelByColor;
    private EditText commentsView;
    private Vibrator vibrator;
    private TextView autoBallsView;
    private TextView cyclesView;
    private TextView teleopBallsView;
    private TextView teamHash, gameID;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch climbed;

    SharedPreferences sharedPref;
    Intent intent;

    @SuppressLint({"WrongViewCast", "CutPasteId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_prompt);
        setTitle("Scouting Prompt");

        intent = getIntent();

        sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        cyclesView = findViewById(R.id.cycles);
        autoBallsView = findViewById(R.id.autoBalls);
        teleopBallsView = findViewById(R.id.teleopBalls);
        commentsView = findViewById(R.id.commentsTextBox);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        RouletteWheelByRotations = findViewById(R.id.rotationCheckBox);
        RouletteWheelByColor = findViewById(R.id.colorCheckBox);
        climbed = findViewById(R.id.climbedSwitch);
        teamHash = findViewById(R.id.team);
        teamHash.setText(intent.getStringExtra("team"));
        gameID = findViewById(R.id.game);
        gameID.setText(intent.getStringExtra("gameID"));

    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void addPoints(View v) {
        vibrator.vibrate(Constants.ScoutingPrompt.vibrationTime); //haptic feedback for buttons

        switch (v.getId()) {
            case R.id.moreAutoBalls:
                autoBalls++;
                break;
            case R.id.lessAutoBalls:
                if (autoBalls > 0) autoBalls--;
                break;
            case R.id.moreCycles:
                cycles++;
                break;
            case R.id.lessCycles:
                if (cycles > 0) cycles--;
                break;
            case R.id.moreTeleBalls:
                teleopBalls++;
                break;
            case R.id.lessTeleBalls:
                if (teleopBalls > 0) teleopBalls--;
                break;
        }

        cyclesView.setText(String.valueOf(cycles));
        autoBallsView.setText(String.valueOf(autoBalls));
        teleopBallsView.setText(String.valueOf(teleopBalls));
        //This is a weird way to do it but it's the only one that works ¯\_(ツ)_/¯
    }

    @SuppressLint("WrongViewCast")
    public void submit(View v) {

        Thread thread = new Thread(() -> {
            ScoutingData<Object> teamNumInfo = new ScoutingData<>("team", intent.getStringExtra("team"));
            ScoutingData<Object> gameNumInfo = new ScoutingData<>("game", intent.getStringExtra("gameID"));

            ScoutingData<Object> uid = new ScoutingData<>("uid", sharedPref.getInt("uid", 0));
            ScoutingData<Object> psw = new ScoutingData<>("psw", sharedPref.getString("psw", ""));
            ScoutingData<Object> cyclesInfo = new ScoutingData<>("cycles", cycles);
            ScoutingData<Object> teleOpBallsInfo = new ScoutingData<>("tele_balls", teleopBalls);
            ScoutingData<Object> autoBallsInfo = new ScoutingData<>("auto_balls", autoBalls);
            ScoutingData<Object> didClimbInfo = new ScoutingData<>("climb", climbed.isChecked());
            ScoutingData<Object> RouletteWheelByRotationsInfo = new ScoutingData<>("color_wheel_1", RouletteWheelByRotations.isChecked() ? 1 : 0);
            ScoutingData<Object> RouletteWheelByColorInfo = new ScoutingData<>("color_wheel_2", RouletteWheelByColor.isChecked() ? 1 : 0);
            ScoutingData<Object> commentsInfo = new ScoutingData<>("comments", Objects.requireNonNull(commentsView.getText()).toString());

            ScoutingEntry scoutingEntry = new ScoutingEntry(uid, psw, cyclesInfo, teleOpBallsInfo, autoBallsInfo,
                    didClimbInfo, RouletteWheelByRotationsInfo, RouletteWheelByColorInfo, commentsInfo, teamNumInfo, gameNumInfo);
            try {
                scoutingEntry.sendData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();

        Toast.makeText(getApplicationContext(), "Scouting submitted!", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void toggleTheme() {
        //not yet implemented
        if (GreenBlitzColorsEnabled) getTheme().applyStyle(R.style.Theme_ScoutingApp, true);
        else getTheme().applyStyle(R.style.Theme_GreenBlitzColorsGood, true);
        GreenBlitzColorsEnabled = !GreenBlitzColorsEnabled;
        setContentView(R.layout.activity_scouting_prompt);
    }
}