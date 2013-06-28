package com.facebook.android.foxeswithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends Activity {
	
	private Session session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("activity", "login");
		setContentView(R.layout.activity_login);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}
	
	public void logInWithParse(View view) {
		EditText userEditText = (EditText) findViewById(R.id.user_name);
		String username = userEditText.getText().toString();
		
		EditText passEditText = (EditText) findViewById(R.id.password);
		String password = passEditText.getText().toString();
	
		ParseUser.logInInBackground(username, password, new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      // Hooray! The user is logged in.
			    	user.put( "name", user.getString("username"));
			    	goToMain();
			    } else {
			      // Login failed. Look at the ParseException to see what happened.
			    	Log.d("photoPicker", "Error: " + e.getMessage());
			    }
			  }
		});
	}
	
	public void logInWithFacebook(View view){
		ParseFacebookUtils.logIn(this, new LogInCallback() {
			  @Override
			  public void done(ParseUser user, ParseException err) {
			    if (user == null) {
			      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
			    } else if (user.isNew()) {
			      Log.d("MyApp", "User signed up and logged in through Facebook!");
			      session = Session.getActiveSession();
			      makeMeRequest(session, user);
			    } else {
			      Log.d("MyApp", "User logged in through Facebook!");
			      session = Session.getActiveSession();
			      makeMeRequest(session, user);
			    }
			  }
			});
		
	}
	
	private void makeMeRequest(final Session session, ParseUser user) {
	    // Make an API call to get user data and define a 
	    // new callback to handle the response.
		final ParseUser parseUser = user;
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {
	        @Override
	        public void onCompleted(GraphUser fbUser, Response response) {
	            // If the response is successful
	            if (session == Session.getActiveSession()) {
	                if (fbUser != null) {
	                    // Set the Parse name to the user's Facebook name.
	                    parseUser.put( "name", fbUser.getName());
	                    goToMain();
	                }
	            }
	            if (response.getError() != null) {
	                // Handle errors.
	            	Log.d("FB request", "could not be completed");
	            }
	        }
	    });
	    request.executeAsync();
	} 
	
	public void goToMain(){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void signUpWithParse(View view) {
		ParseUser user = new ParseUser();
		EditText userEditText = (EditText) findViewById(R.id.user_name);
		String username = userEditText.getText().toString();
		user.setUsername(username);
		
		EditText passEditText = (EditText) findViewById(R.id.password);
		String password = passEditText.getText().toString();
		user.setPassword(password);
		 
		// other fields can be set just like with ParseObject
		user.put("name", username);
		 
		user.signUpInBackground(new SignUpCallback() {
		  public void done(ParseException e) {
		    if (e == null) {
		      // Hooray! Let them use the app now.
		    	goToMain();
		    } else {
		      // Sign up didn't succeed. Look at the ParseException
		      // to figure out what went wrong
		    	Log.d("sign up", "Error: " + e.getMessage());
		    }
		  }
		});
	}

}
