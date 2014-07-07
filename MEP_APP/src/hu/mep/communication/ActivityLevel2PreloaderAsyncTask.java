package hu.mep.communication;

import java.util.concurrent.ExecutionException;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.os.AsyncTask;

public class ActivityLevel2PreloaderAsyncTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private String hostURI = "http://www.megujuloenergiapark.hu/";
	
	public ActivityLevel2PreloaderAsyncTask(Context context) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	
	@Override
	protected Void doInBackground(Void... params) {
		if (Session.getActualUser().isMekut()) {
			GetContactListAsyncTaskPRELOAD getContactListAsyncTask = new GetContactListAsyncTaskPRELOAD(context, hostURI);
			GetTopicListAsyncTask getTopicListAsyncTask = new GetTopicListAsyncTask(context, hostURI);
			try {
				getContactListAsyncTask.execute().get();
				getTopicListAsyncTask.execute().get();
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
		Session.dismissAndMakeNullProgressDialog();
		super.onPostExecute(result);
	}
	

}
