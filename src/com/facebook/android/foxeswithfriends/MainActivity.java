package com.facebook.android.foxeswithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;

public class MainActivity extends FragmentActivity {
	
	//public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Disable the Home button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(false);
		
		ParseAnalytics.trackAppOpened(getIntent());
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
		  // do stuff with the user
			setContentView(R.layout.activity_main);
			
			String name = currentUser.getString("name");
			TextView msg = (TextView) findViewById(R.id.personal_msg);
			msg.setText("Hi, "+name+"!");

		} else {
		  // show the signup or login screen
			//Log.d("no user", "this is good");
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}

	}

	
	/** Called when the user clicks the New button */
	public void addFox(View view) {
		Intent intent = new Intent(this, AddFoxActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the log out button */
	public void logOut(View view) {
		ParseUser.logOut();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

}
