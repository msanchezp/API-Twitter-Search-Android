package org.brownielabs.android;

import org.brownielabs.android.utils.ConstantsUtils;
import org.brownielabs.android.utils.TwitterUtils;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	
	private static final String TAG = UpdaterService.class.getSimpleName();

	static final int DELAY = 60000;
	private boolean runFlag = false;
	
	private Updater updater;
	private TwitterAPIApplication application;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreated");
		updater = new Updater();
		application = (TwitterAPIApplication) getApplication();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
		runFlag = false;
		application.setServiceRunningFlag(false);
		updater.interrupt();
		updater = null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d(TAG, "onStarted");
		runFlag = true;
		application.setServiceRunningFlag(true);
		updater.start();
		return START_STICKY;
	}
	
	private class Updater extends Thread{
		
		public Updater(){
			super("UpdaterService-UpdaterThread");
		}
		
		@Override
		public void run() {
			UpdaterService updaterService = UpdaterService.this;
			while(updaterService.runFlag){
				Log.d(TAG, "UpdaterThread running");
				try{
					TwitterUtils.getTimelineForSearchTerm(ConstantsUtils.SEARCH_TERM);
					Thread.sleep(DELAY);
				}catch(InterruptedException e){
					updaterService.runFlag = false;
					application.setServiceRunningFlag(false);
				}
			}
		}
	}
	
}
