package com.facebook.android.foxeswithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.parse.ParseAnalytics;

public class MainActivity extends FragmentActivity {
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_main);
		
		// Disable the Home button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		ParseAnalytics.trackAppOpened(getIntent());

	}

	
	/** Called when the user clicks the Send button */
	public void addFox(View view) {
		Intent intent = new Intent(this, AddFoxActivity.class);
		startActivity(intent);
	}

}
