package hu.mep.communication;

import java.util.concurrent.ExecutionException;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.os.AsyncTask;

public class ActivityLevel2PreloaderAsyncTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private String hostURI = "http://www.megujuloenergiapark.hu/";
	
	public ActivityLevel2PreloaderAsyncTask(Context context) {
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	
	@Override
	protected Void doInBackground(Void... params) {
		GetContactListAsyncTaskPRELOAD getContactListAsyncTask = new GetContactListAsyncTaskPRELOAD(context, hostURI);
		try {
			getContactListAsyncTask.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		if (Session.getActualUser().isMekut()) {
				GetTopicListAsyncTask getTopicListAsyncTask = new GetTopicListAsyncTask(context, hostURI);
			try {
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
