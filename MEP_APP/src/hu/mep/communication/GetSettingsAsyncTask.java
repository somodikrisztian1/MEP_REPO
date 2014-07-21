package hu.mep.communication;

import android.os.AsyncTask;
import android.util.Log;

public class GetSettingsAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "GetSettingsAsyncTask";
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	
		String resourseURI = "getSettings....."; // TODO!
		
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Log.e(TAG, "doInBackground running");
		
		
		return null;
	}


}
