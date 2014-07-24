package hu.mep.communication;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.settings.Function;
import hu.mep.datamodells.settings.Settings;
import hu.mep.datamodells.settings.Slider;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.os.AsyncTask;

public class SendSettingsAsyncTask extends AsyncTask<Void, Void, Void> {

	Activity activity;
	String resourceURI;
	
	public SendSettingsAsyncTask(Activity activity) {
		this.activity = activity;
	}
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.resourceURI = "ios_setSettings.php";
	}
	
	
	@Override
	protected Void doInBackground(Void... params) {
		
		try {
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
		
		for(Slider actSlider : Session.getTempSettings().getSliders()) {
			postDatas.put(actSlider.name, String.format("%.2f", actSlider.value));
		}
		for(Function actFunction : Session.getTempSettings().getFunctions()) {
			postDatas.put(actFunction.name, (actFunction.status ? "1" : "0"));
		}
		return postDatas;
	}

}
