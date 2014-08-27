package hu.mep.communication.charts;

import hu.mep.communication.RealCommunicator;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.Chart;
import hu.mep.datamodells.charts.ChartName;
import hu.mep.mep_app.activities.ActivityLevel2NEW;
import hu.mep.mep_app.activities.ActivityLevel3ShowRemoteMonitoring;
import hu.mep.mep_app.activities.ActivityLevel3ShowTopic;
import hu.mep.utils.deserializers.ChartDeserializer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetChartsAsyncTask extends AsyncTask<Void, Void, Void> {

	//private static final String TAG = "GetChartsAsyncTask";
	
	private Activity activity;
	private String resourceURIBeginning;
	private String resourceURIDateEnding;
	private String fullResourceURI;
	private ProgressDialog pd;
	private boolean forRemoteMonitoring;
	private Calendar startDate;
	private Calendar endDate;
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
	
	
	public GetChartsAsyncTask(Activity activity, boolean forRemoteMonitoring, 
			Calendar startDate, Calendar endDate) {
		super();
		this.activity = activity;
		this.forRemoteMonitoring = forRemoteMonitoring;
		this.startDate = startDate;
		this.endDate = endDate;
		
		this.pd = new ProgressDialog(this.activity);
		this.pd.setCancelable(false);
		
		if(this.forRemoteMonitoring) {
			this.pd.setMessage("Logolási adatok letöltése...");
		} else {
			this.pd.setMessage("Témakör betöltése...");
		}
		
	}
	
	private String formatDate(Calendar date) {
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
		int i = 0;
		if(forRemoteMonitoring) {
			if(Session.getActualRemoteMonitoring().getID().equals("10.1E4C2F020800")) {
				i = 1;
			} else {
				i = 0;
			}
		}
			for(; i < Session.getAllChartNames().size(); ++i ) {
				ChartName actChartName = Session.getAllChartNames().get(i);
				if(!actChartName.getName().equals("Rendszerállapot")) {
					fullResourceURI = resourceURIBeginning + actChartName.getId() + resourceURIDateEnding;
					String response = "";
					response = RealCommunicator.dohttpGet(fullResourceURI);
					Chart chart = gson.fromJson(response, Chart.class);
					charts.add(chart);
				} else {
					//Log.e(TAG, "Nincs letöltés <-- name: " + actChartName.getName());
				}
			}
		Session.setAllCharts(charts);
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Session.dismissAndMakeNullProgressDialog();
		
		if(activity instanceof ActivityLevel2NEW) {
			if(!forRemoteMonitoring) {
				Intent intent = new Intent(activity, ActivityLevel3ShowTopic.class);
				activity.startActivity(intent);
			} else {
				Session.getActualCommunicationInterface().getActualRemoteMonitoringSettings(activity);
			}
		} else if(activity instanceof ActivityLevel3ShowRemoteMonitoring) {
			((ActivityLevel3ShowRemoteMonitoring)activity).mSectionsPagerAdapter.notifyDataSetChanged();
		} else if(activity instanceof ActivityLevel3ShowTopic) {
			((ActivityLevel3ShowTopic)activity).mSectionsPagerAdapter.notifyDataSetChanged();
		}
	}

}
