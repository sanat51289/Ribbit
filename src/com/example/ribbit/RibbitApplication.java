package com.example.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class RibbitApplication extends Application{
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "mYVMYVz3QgUqzlM4FKDQIApwo2JeRsc5hMlhdItx", "ikKHmFSFoS0l4cNHvr5WsIjaiO0aTEoBE7hJ8TUX");
		
	}
}
