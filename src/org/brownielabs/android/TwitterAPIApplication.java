package org.brownielabs.android;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class TwitterAPIApplication extends Application {
	
	private static final String TAG = TwitterAPIApplication.class.getSimpleName();
	private boolean serviceRunningFlag;
	
	public boolean isServiceRunningFlag() {
		return serviceRunningFlag;
	}

	public void setServiceRunningFlag(boolean serviceRunningFlag) {
		this.serviceRunningFlag = serviceRunningFlag;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreted");
		startService(new Intent(this, UpdaterService.class));
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		Log.d(TAG, "onTerminated");
		stopService(new Intent(this, UpdaterService.class));
	}
}
