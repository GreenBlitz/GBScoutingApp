package com.example.scouting_app;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorActivity extends AppCompatActivity {
	private SoundPool soundPool;
	private AudioManager audio;
	private int richardRotation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_error);
		setTitle("Error");
		Toast.makeText(getApplicationContext(), "ERROR ENCOUNTERED", Toast.LENGTH_LONG).show();
		//oh shit

		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		AudioAttributes audioAttributes = new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
				.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
				.build();
		soundPool = new SoundPool.Builder().setMaxStreams(4).setAudioAttributes(audioAttributes).build();
		richardRotation = soundPool.load(this, R.raw.richardrotation, 1);
	}

	public void playMusic(View v) {
		for (int i = 0; i < 100; i++)
			audio.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
		soundPool.play(richardRotation, 1, 1, 0, 0, 1);
	}
}