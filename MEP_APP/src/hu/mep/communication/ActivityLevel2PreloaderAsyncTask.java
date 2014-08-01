package hu.mep.communication;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel2NEW;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

public class ActivityLevel2PreloaderAsyncTask extends AsyncTask<Void, Void, Void> {

	//private static final String TAG = "ActivityLevel2PreloaderAsyncTask";
	private Activity activity;
	private String hostURI = "http://www.megujuloenergiapark.hu/";
	private ProgressDialog pd;
	
	public ActivityLevel2PreloaderAsyncTask(Activity activity) {
		this.activity = activity;
		this.pd = new ProgressDialog(this.activity);
		this.pd.setCancelable(false);
		this.pd.setMessage("Felhasználói adatok betöltése folyamatban...");
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Session.setProgressDialog(pd);
		Session.showProgressDialog();
	}
	
	
	@Override
	protected Void doInBackground(Void... params) {
		GetContactListAsyncTaskPRELOAD getContactListAsyncTask = new GetContactListAsyncTaskPRELOAD(activity, hostURI);
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
			GetTopicListAsyncTask getTopicListAsyncTask = new GetTopicListAsyncTask(hostURI);		
			
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
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	
		Session.dismissAndMakeNullProgressDialog();
		Session.setAnyUserLoggedIn(true);
		
		Intent i = new Intent(activity, ActivityLevel2NEW.class);
		activity.startActivity(i);
	}

}
