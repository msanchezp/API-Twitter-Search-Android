package org.brownielabs.android;

import android.app.Application;
import android.util.Log;

public class TwitterAPIApplication extends Application {
	
	private static final String TAG = TwitterAPIApplication.class.getSimpleName();
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreted");
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.d(TAG, "onTerminated");
	}
}
