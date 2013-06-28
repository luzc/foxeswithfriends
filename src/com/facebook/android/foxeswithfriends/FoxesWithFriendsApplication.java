package com.facebook.android.foxeswithfriends;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;

public class FoxesWithFriendsApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, this.getString(R.string.parse_app_id), this.getString(R.string.parse_client_key));
		
		ParseFacebookUtils.initialize(this.getString(R.string.app_id));
		Log.d("app id", this.getString(R.string.app_id));

		//ParseUser.enableAutomaticUser();
		//ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		//defaultACL.setPublicReadAccess(true);
		
		//ParseACL.setDefaultACL(defaultACL, true);
		

	}

}
