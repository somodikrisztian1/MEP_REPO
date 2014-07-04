package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.os.AsyncTask;

public class ActivityLevel2PreloaderAsyncTask extends AsyncTask<Void, Void, Void> {

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	
	@Override
	protected Void doInBackground(Void... params) {
		if (Session.getActualUser().isMekut()) {
			Session.getActualCommunicationInterface().getChatPartners();
			Session.getActualCommunicationInterface().getTopicList();
		}
		return null;
	}
	

	@Override
	protected void onPostExecute(Void result) {
		Session.dismissAndMakeNullProgressDialog();
		super.onPostExecute(result);
	}
	

}
