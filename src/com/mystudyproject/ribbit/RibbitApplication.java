package com.mystudyproject.ribbit;

import android.app.Application;

import com.mystudyproject.ribbit.ui.MainActivity;
import com.mystudyproject.ribbit.utils.ParseConstants;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class RibbitApplication extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "mYVMYVz3QgUqzlM4FKDQIApwo2JeRsc5hMlhdItx", 
				"ikKHmFSFoS0l4cNHvr5WsIjaiO0aTEoBE7hJ8TUX");
	
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
	
	public static void updateParseInstallation(ParseUser user) {
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
		installation.saveInBackground();
	}
}
