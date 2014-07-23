package hu.mep.communication.charts;

import hu.mep.communication.RealCommunicator;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.AllChartNames;
import hu.mep.utils.deserializers.ChartNamesDeserializer;

import java.util.Iterator;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetChartNamesAsyncTask extends
		AsyncTask<Void, Void, Void> {

	private String hostURI;
	private String resourceURI;
	private String fullURI;
	private boolean isRemoteMonitoring;

	public GetChartNamesAsyncTask(String hostURI,
			boolean isRemoteMonitoring) {
		super();
		this.hostURI = hostURI;
		this.isRemoteMonitoring = isRemoteMonitoring;
	}

	private String getSSZS() {
		String result = "";
		if (!isRemoteMonitoring) {
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
		if (isRemoteMonitoring) {
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

}
