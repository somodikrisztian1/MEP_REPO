package hu.mep.communication.charts;

import hu.mep.communication.RealCommunicator;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.AllChartNames;
import hu.mep.datamodells.charts.ChartName;
import hu.mep.utils.deserializers.ChartNamesDeserializer;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetChartNamesAsyncTask extends	AsyncTask<Void, Void, Void> {

	private Activity activity;
	private String hostURI;
	private String resourceURI;
	private String fullURI;
	private boolean forRemoteMonitoring;
	private ProgressDialog pd;

	public GetChartNamesAsyncTask(Activity activity, String hostURI, boolean forRemoteMonitoring) {
		super();
		this.activity = activity;
		this.hostURI = hostURI;
		this.forRemoteMonitoring = forRemoteMonitoring;
		
		this.pd = new ProgressDialog(this.activity);
		this.pd.setCancelable(false);
		this.pd.setMessage("Kapcsolódás a szerverhez...");
	}

	private String getSSZS() {
		String result = "";
		if (!forRemoteMonitoring) {
			Iterator<Integer> iterator = Session.getActualTopic()
					.getTabSerialNumbers().iterator();
			while (iterator.hasNext()) {
				result += String.valueOf(iterator.next());
				if (iterator.hasNext()) {
					result += ",";
				}
			}
		}
		return result;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		Session.setProgressDialog(pd);
		Session.showProgressDialog();
		
		if (forRemoteMonitoring) {
			resourceURI = "ios_getChartNames.php?tsz1_id="
					+ Session.getActualRemoteMonitoring().getID();

			fullURI = hostURI + resourceURI;
			Log.e("GetChartNames", fullURI);
		} else {
			resourceURI = "ios_getChartNames.php?tsz1_id="
					+ Session.getActualTopic().getTopicID() + "&sszs="
					+ getSSZS();

			fullURI = hostURI + resourceURI;
			Log.e("GetChartNames", fullURI);
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		String response = "";

		response = RealCommunicator.dohttpGet(fullURI);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(AllChartNames.class, new ChartNamesDeserializer());
		Gson gson = gsonBuilder.create();
		AllChartNames allChartInfo = gson.fromJson(response, AllChartNames.class);
		Session.setAllChartNames(allChartInfo.getAllChartNames());
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Session.dismissAndMakeNullProgressDialog();
		
		GetChartsAsyncTask chartGetter = new GetChartsAsyncTask(activity, hostURI, forRemoteMonitoring, null, null);
		chartGetter.execute();
	}

}
