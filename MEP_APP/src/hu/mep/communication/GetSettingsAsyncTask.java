package hu.mep.communication;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.settings.Settings;
import hu.mep.mep_app.activities.ActivityLevel2NEW;
import hu.mep.mep_app.activities.ActivityLevel3ShowRemoteMonitoring;
import hu.mep.utils.deserializers.SettingsDeserializer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetSettingsAsyncTask extends AsyncTask<Void, Void, Void> {

	//private static final String TAG = "GetSettingsAsyncTask";

	private Activity activity;
	private String resourceURI;
	private ProgressDialog pd;
	
	public GetSettingsAsyncTask(Activity activity, String hostURI) {
		this.activity = activity;
		
		this.pd = new ProgressDialog(activity);
		this.pd.setMessage("Frissítés...");
		this.pd.setCancelable(false);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		if(activity instanceof ActivityLevel3ShowRemoteMonitoring) {
			Session.setProgressDialog(pd);
			Session.showProgressDialog();
		}
		
		resourceURI = "ios_getSettings.php?tsz1_id=" +	Session.getActualRemoteMonitoring().getID();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		String response = RealCommunicator.dohttpGet(resourceURI);
		
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Settings.class, new SettingsDeserializer());
		Gson gson = builder.create();
		Settings settings = gson.fromJson(response, Settings.class);
		
		Session.setActualSettings(settings);
		Session.setTempSettings(settings);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		Session.dismissAndMakeNullProgressDialog();
		
		if (activity instanceof ActivityLevel3ShowRemoteMonitoring) {
			((ActivityLevel3ShowRemoteMonitoring)activity).mSectionsPagerAdapter.notifyDataSetChanged();
		} else if (activity instanceof ActivityLevel2NEW) {
			Intent intent = new Intent(activity, ActivityLevel3ShowRemoteMonitoring.class);
			activity.startActivity(intent);
		}	
	}
}
