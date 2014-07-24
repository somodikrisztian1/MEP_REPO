package hu.mep.communication.charts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.mep.communication.GetSettingsAsyncTask;
import hu.mep.communication.RealCommunicator;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.Chart;
import hu.mep.datamodells.charts.ChartName;
import hu.mep.mep_app.activities.ActivityLevel2NEW;
import hu.mep.mep_app.activities.ActivityLevel3ShowRemoteMonitoring;
import hu.mep.mep_app.activities.ActivityLevel3ShowTopic;
import hu.mep.utils.deserializers.ChartDeserializer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class GetChartsAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "GetChartsAsyncTask";
	
	private Activity activity;
	private String hostURI;
	private String resourceURIBeginning;
	private String resourceURIDateEnding;
	private String fullURI;
	private ProgressDialog pd;
	private boolean forRemoteMonitoring;
	private Calendar startDate;
	private Calendar endDate;
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
	
	
	public GetChartsAsyncTask(Activity activity, String hostURI, boolean forRemoteMonitoring, 
			Calendar startDate, Calendar endDate) {
		super();
		this.activity = activity;
		this.hostURI = hostURI;
		this.forRemoteMonitoring = forRemoteMonitoring;
		this.startDate = startDate;
		this.endDate = endDate;
		
		this.pd = new ProgressDialog(this.activity);
		this.pd.setCancelable(false);
		
		if(this.forRemoteMonitoring) {
			this.pd.setMessage("Távfelügyelet betöltése...");
		} else {
			this.pd.setMessage("Témakör betöltése...");
		}
		
	}
	
	private String formatDate(Calendar date) {
		//Log.e(TAG, "Formatted Date: " + format.format(date.getTime()));
		return format.format(date.getTime());
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Session.setProgressDialog(pd);
		Session.showProgressDialog();

		resourceURIBeginning = "ios_getChart.php?id=";

		if ((startDate != null) && (endDate != null)) {
			resourceURIDateEnding = "&fromDate=" + formatDate(startDate)
					+ "&toDate=" + formatDate(endDate);
		} else {
			resourceURIDateEnding = "";
		}
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		List<Chart> charts = new ArrayList<Chart>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Chart.class, new ChartDeserializer());
		Gson gson = gsonBuilder.create();
		for (ChartName actChartName : Session.getAllChartNames()) {
			if(!actChartName.getName().equals("Rendszerállapot")) {
				Log.e(TAG, "letöltés name: " + actChartName.getName());
				fullURI = hostURI + resourceURIBeginning + actChartName.getId() + resourceURIDateEnding;
				String response = "";
				response = RealCommunicator.dohttpGet(fullURI);
				Chart chart = gson.fromJson(response, Chart.class);
				charts.add(chart);
			} else {
				Log.e(TAG, "Nincs letöltés <-- name: " + actChartName.getName());
			}
		}
		Session.setAllCharts(charts);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		if(forRemoteMonitoring) {
			Session.getActualCommunicationInterface().getActualRemoteMonitoringSettings(activity);
		} else {
			Session.dismissAndMakeNullProgressDialog();
			
			if (activity instanceof ActivityLevel3ShowTopic) {
				((ActivityLevel3ShowTopic)activity).mSectionsPagerAdapter.notifyDataSetChanged();
			} else if (activity instanceof ActivityLevel2NEW) {
				Intent intent = new Intent(activity, ActivityLevel3ShowTopic.class);
				activity.startActivity(intent);
			}
		}
	}

}
