package hu.mep.communication;

import java.util.concurrent.ExecutionException;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public class ActivityLevel2PreloaderAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "ActivityLevel2PreloaderAsyncTask";
	private Context context;
	private String hostURI = "http://www.megujuloenergiapark.hu/";
	
	public ActivityLevel2PreloaderAsyncTask(Context context) {
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.i(TAG, "onPreExecute running");
	}
	
	
	@Override
	protected Void doInBackground(Void... params) {
		Log.i(TAG, "doInBackground running");
		GetContactListAsyncTaskPRELOAD getContactListAsyncTask = new GetContactListAsyncTaskPRELOAD(context, hostURI);
		
		
		try {
			/** Ez ezért kell, mert az AsyncTask által indított AsyncTask e verzió fölött nem jól működik!!!!! */
			if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {
				getContactListAsyncTask.execute().get();
			} else {
				getContactListAsyncTask.executeOnExecutor(THREAD_POOL_EXECUTOR).get();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		if (Session.getActualUser().isMekut()) {
			GetTopicListAsyncTask getTopicListAsyncTask = new GetTopicListAsyncTask(context, hostURI);			
			try {
				/** Ez ezért kell, mert az AsyncTask által indított AsyncTask e verzió fölött nem jól működik!!!!! */
				if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {
					getTopicListAsyncTask.execute().get();
				} else {
					getTopicListAsyncTask.executeOnExecutor(THREAD_POOL_EXECUTOR).get();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		Log.i(TAG, "doInBackground finished");
		return null;
	}

}
