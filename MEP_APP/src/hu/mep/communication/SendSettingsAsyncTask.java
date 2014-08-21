package hu.mep.communication;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.settings.Function;
import hu.mep.datamodells.settings.Slider;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.os.AsyncTask;


public class SendSettingsAsyncTask extends AsyncTask<Void, Void, Void> {

	// private static final String TAG = "SendSettingsAsyncTask";
	
	public static final int OPTION_ONLY_THERMOSTATS = 0;
	public static final int OPTION_ONLY_TANKS = 1;
	public static final int OPTION_ALL_FUNCTION = 2;	
	
	Activity activity;
	String resourceURI;
	int option;
	
	public SendSettingsAsyncTask(Activity activity, int option) {
		this.activity = activity;
		this.option = option;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.resourceURI = "ios_setSettings.php";
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		
		try {
		//logPostDatas(preparePostDatas());
		RealCommunicator.httpPost(resourceURI, preparePostDatas());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Session.getActualCommunicationInterface().getActualRemoteMonitoringSettings(activity);
	}
	
	private HashMap<String, String> preparePostDatas() {
		HashMap<String, String> postDatas = new HashMap<String, String>();
		postDatas.put("tsz1_id", Session.getActualRemoteMonitoring().getID());
		switch (option) {
		case OPTION_ONLY_THERMOSTATS:
			for(Slider actSlider : Session.getTempSettings().getSliders()) {
				if(!(actSlider.name.equals("th1_temp")) && !(actSlider.name.equals("tp1_temp"))) {
					postDatas.put(actSlider.name, String.format("%.2f", actSlider.value));
				}
			}
			break;
		case OPTION_ONLY_TANKS:
			for(Slider actSlider : Session.getTempSettings().getSliders()) {
				if(actSlider.name.equals("th1_temp") || actSlider.name.equals("tp1_temp")) {
					postDatas.put(actSlider.name, String.format("%.2f", actSlider.value));
				}
			}
			break;
		case OPTION_ALL_FUNCTION:
			for(Function actFunction : Session.getTempSettings().getFunctions()) {
				postDatas.put(actFunction.name, (actFunction.status ? "1" : "0"));
			}
			break;
		default:
			break;
		}
		return postDatas;
	}
	
	/*
	private void logPostDatas(HashMap<String, String> postDatas) {
		Log.e(TAG, "Logging post datas!!!");
		for (Entry<String, String> act : postDatas.entrySet()) {
			Log.e(TAG, act.getKey() + ":" + act.getValue());
		}
	}
	 */
}
