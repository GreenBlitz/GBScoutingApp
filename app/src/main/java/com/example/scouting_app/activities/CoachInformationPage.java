package com.example.scouting_app.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scouting_app.R;

import java.util.ArrayList;

public class CoachInformationPage extends AppCompatActivity {

	private int[] teamHashes;
	private Spinner featureSelection;

	public CoachInformationPage() {
		super();
		teamHashes = new int[]{4590, 1574, 1619, 1687, 1690, 254};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coach_information);

		setTitle("Coach Information");

		featureSelection = findViewById(R.id.feature_selection);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.features,
				android.R.layout.simple_spinner_dropdown_item);
		featureSelection.setAdapter(adapter);
//		featureSelection.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

		@SuppressLint("CutPasteId") Spinner matchSelection = findViewById(R.id.feature_selection);
		ArrayAdapter<CharSequence> matchAdapter = ArrayAdapter.createFromResource(this, R.array.features,
				android.R.layout.simple_spinner_dropdown_item);
		matchSelection.setAdapter(matchAdapter);
	}

	public static int getStats(int teamHash) {
		// TODO: make actual function to create stats report

		switch (teamHash) {
			case 4590:
				return R.drawable.new_logo4590;
			case 1574:
				return R.drawable.miscar_logo;
			case 1619:
				return R.drawable.up_a_creek_logo;
			case 1687:
				return R.drawable.citrus_circuits_logo;
			case 1690:
				return R.drawable.orbit_logo;
			case 254:
				return R.drawable.cheesy_puffs_logo;
			case 0:
				return R.drawable.red_alliance_logo;
			case -1:
				return R.drawable.blue_alliance_logo;
			case -2:
				return R.drawable.qual_logo;
			case -3:
				return R.drawable.feature_info;
			default:
				return -1;
		}
	}

	public ArrayList<View> filterType(ArrayList<View> lst) {
		ArrayList<View> ret = new ArrayList<>();

		for (View v : lst) {
			if (v instanceof ToggleButton) {
				ret.add(v);
			}
		}
		return ret;
	}

	public ArrayList<View> getChildren(ViewGroup root, ArrayList<View> lst) {
		for (int i = 0; i < root.getChildCount(); i++) {
			View child = root.getChildAt(i);
			if (child instanceof ViewGroup) {
				getChildren((ViewGroup) child, lst);
			} else {
				lst.add(child);
			}
		}
		return lst;
	}

	public ArrayList<View> getChildren(ViewGroup root) {
		return getChildren(root, new ArrayList<>());
	}

	@SuppressLint({"NewApi", "UseCompatLoadingForDrawables", "NonConstantResourceId"})
	public void changeInfo(View v) {
		ViewGroup coachInfo = findViewById(R.id.coachInfo);
		ArrayList<View> children = filterType(getChildren(coachInfo));

		ImageView stats = findViewById(R.id.image1);
		stats.setVisibility(((ToggleButton) v).isChecked() ? View.VISIBLE : View.INVISIBLE);

		int res;
		int id = v.getId();
		switch (id) {
			case R.id.redAllianceButton:
				res = 0;
				break;
			case R.id.blueAllianceButton:
				res = -1;
				break;
			case R.id.qualInfo:
				res = -2;
				break;
			case R.id.feature_info:
				res = -3;
				break;
			default:
				res = Integer.parseInt((String) ((ToggleButton) v).getText());
				break;
		}
		stats.setImageResource(getStats(res));

		for (View child : children) {
			if (child.getId() != id) {
				((ToggleButton) child).setChecked(false);
			}

			boolean red = getChildren(findViewById(R.id.redAlliance)).contains(child);
			boolean blue = getChildren(findViewById(R.id.blueAlliance)).contains(child);
			ToggleButton button = (ToggleButton) child;
			if (!button.isChecked()) {
				button.setBackground(getDrawable(red ? R.drawable.button_border_red_unclicked
						: blue ? R.drawable.button_border_blue_unclicked
						: R.drawable.button_border_qual_unclicked));

				int textColor = getColor(red || blue ? R.color.white : R.color.white);
				button.setTextColor(textColor);
			} else {
				button.setBackground(getDrawable(red ? R.drawable.button_border_red_clicked
						: blue ? R.drawable.button_border_blue_clicked
						: R.drawable.button_border_qual_clicked));

				int textColor = getColor(red ? R.color.redAllianceColor :
						blue ? R.color.blueAllianceColor :
								R.color.black);
				button.setTextColor(textColor);
				button.setOutlineAmbientShadowColor(textColor);
			}
		}
	}

}