package org.brownielabs.android;

import java.util.ArrayList;

import org.brownielabs.android.list.TweetAdapter;
import org.brownielabs.android.models.Tweet;
import org.brownielabs.android.utils.ConstantsUtils;
import org.brownielabs.android.utils.TwitterUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.clientebuscadortwitter.R;

public class TimelineActivity extends Activity {

	private ListView lvTimeline;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		lvTimeline = (ListView)findViewById(R.id.lv_timeline);
		
		new GetTimelineTask().execute();
	}
	
	private void updateListView(ArrayList<Tweet> tweets){
		lvTimeline.setAdapter(new TweetAdapter(this, R.layout.row_tweet, tweets));
	}
	
	class GetTimelineTask extends AsyncTask<Object, Void, ArrayList<Tweet>>{

		private ProgressDialog progressDialog;
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(TimelineActivity.this);
			progressDialog.setTitle(getResources().getString(R.string.label_tweet_search_loader));
			progressDialog.show();
		}

		@Override
		protected ArrayList<Tweet> doInBackground(Object... params) {
			return TwitterUtils.getTimelineForSearchTerm(ConstantsUtils.SEARCH_TERM);
		}
		
		@Override
		protected void onPostExecute(ArrayList<Tweet> timeline) {
			// TODO Auto-generated method stub
			super.onPostExecute(timeline);
			progressDialog.dismiss();
			
			if(timeline.isEmpty()){
				Toast.makeText(TimelineActivity.this,
						getResources().getString(R.string.label_tweets_not_found), 
						Toast.LENGTH_SHORT).show();
			}else{
				updateListView(timeline);
			}
		}
		
	}
}
