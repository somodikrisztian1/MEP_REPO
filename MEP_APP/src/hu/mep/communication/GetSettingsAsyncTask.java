package hu.mep.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.settings.Settings;
import hu.mep.utils.deserializers.SettingsDeserializer;
import android.os.AsyncTask;
import android.util.Log;

public class GetSettingsAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "GetSettingsAsyncTask";
	private String hostURI;
	private String fullURI;
	private String resourceURI;
	
	public GetSettingsAsyncTask(String hostURI) {
		this.hostURI = hostURI;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		resourceURI = "ios_getSettings.php?tsz1_id=" +
				Session.getActualRemoteMonitoring().getID();
		fullURI = hostURI + resourceURI;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Log.e(TAG, "doInBackground running");
		
		String response = RealCommunicator.dohttpGet(fullURI);
		
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Settings.class, new SettingsDeserializer());
		Gson gson = builder.create();
		Settings settings = gson.fromJson(response, Settings.class);
		
		Session.setActualSettings(settings);
		
		return null;
	}


}
