package com.example.scoutingtest4590;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView PIN_VIEW = findViewById(R.id.PIN_display);
		Button PIN_GEN = findViewById(R.id.GEN_PIN_BTN);
		PIN_GEN.setOnClickListener(v ->
		{
			StringBuilder PIN = new StringBuilder();
			for(int i = 0; i < 6; i++)
			{
				PIN.append((int)(Math.random()*10));
			}
			PIN_VIEW.setText(PIN.toString());
//		    sendToServer(PIN);
			PIN_GEN.setEnabled(false);
			new CountDownTimer(60000, 1000) {
				@SuppressLint("SetTextI18n")
				public void onTick(long millisUntilFinished) {
					PIN_GEN.setText("seconds remaining: " + millisUntilFinished / 1000);
				}
				@SuppressLint("SetTextI18n")
				public void onFinish()
				{
					PIN_GEN.setEnabled(true);
					PIN_GEN.setText("generate pin");
				}
			}.start();

		});
	}
}


/*











 */