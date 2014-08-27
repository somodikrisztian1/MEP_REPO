package hu.mep.communication;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.settings.Settings;
import hu.mep.mep_app.FragmentLevel3ShowSettings;
import hu.mep.mep_app.activities.ActivityLevel2NEW;
import hu.mep.mep_app.activities.ActivityLevel3ShowRemoteMonitoring;
import hu.mep.mep_app.FragmentLevel3ShowSettings;
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
	private ProgressDialog pdForRefresh;
	private ProgressDialog pdForFirstLoad;
	
	public GetSettingsAsyncTask(Activity activity, String hostURI) {
		this.activity = activity;
		
		this.pdForRefresh = new ProgressDialog(activity);
		this.pdForRefresh.setMessage("Frissítés...");
		this.pdForRefresh.setCancelable(false);
		
		this.pdForFirstLoad = new ProgressDialog(activity);
		this.pdForFirstLoad.setMessage("Rendszerállapot letöltése...");
		this.pdForFirstLoad.setCancelable(false);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		if(activity instanceof ActivityLevel3ShowRemoteMonitoring) {
			Session.setProgressDialog(pdForRefresh);
			Session.showProgressDialog();
		} else if (activity instanceof ActivityLevel2NEW) {
			Session.setProgressDialog(pdForFirstLoad);
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
			// ((ActivityLevel3ShowRemoteMonitoring)activity).mSectionsPagerAdapter.notifyDataSetChanged();
			int lastFragmentnumber = ((ActivityLevel3ShowRemoteMonitoring)activity).mSectionsPagerAdapter.getCount() -1;
			((FragmentLevel3ShowSettings)((ActivityLevel3ShowRemoteMonitoring)activity).mSectionsPagerAdapter.getItem(lastFragmentnumber)).adapter.notifyDataSetChanged();;
		} else if (activity instanceof ActivityLevel2NEW) {
			Intent intent = new Intent(activity, ActivityLevel3ShowRemoteMonitoring.class);
			activity.startActivity(intent);
		}	
	}
}
