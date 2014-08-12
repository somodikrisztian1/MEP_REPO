package hu.mep.communication.charts;

import hu.mep.communication.RealCommunicator;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.OneLineAndTwoBarChartContainer;
import hu.mep.mep_app.activities.ActivityLevel2NEW;
import hu.mep.mep_app.activities.ActivityLevel3ShowRemoteMonitoring;
import hu.mep.utils.deserializers.BarChartDeserializer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetSolarPanelJsonAsyncTask extends AsyncTask<Void, Void, Void> {
	
	private Activity activity;
	private ProgressDialog pd;
	private String resourceURI;
	private Calendar beginDate;
	private Calendar endDate;
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm");

	public GetSolarPanelJsonAsyncTask(Activity activity, 
			Calendar beginDate, Calendar endDate) {
	
		this.activity = activity;
		
		this.beginDate = beginDate;
		this.endDate = endDate;
		
		this.pd = new ProgressDialog(activity);
		this.pd.setCancelable(false);
		this.pd.setMessage("Távfelügyelet betöltése...");
		
	}
	
	private String formatDate(Calendar date) {
		return dateFormatter.format(date.getTime());
	}
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Session.setProgressDialog(pd);
		Session.showProgressDialog();
		if(beginDate != null && endDate != null) {
			resourceURI =  "ios_getNapelemJson.php?id=" + Session.getActualRemoteMonitoring().getID()
					+ "&fromDate=" + formatDate(beginDate)
					+ "&toDate=" + formatDate(endDate);
		} else {
			resourceURI = "ios_getNapelemJson.php?id=" + Session.getActualRemoteMonitoring().getID();
		}
	}
	
	@Override
	protected Void doInBackground(Void... params) {

		String response = "";
		
		response =RealCommunicator.dohttpGet(resourceURI);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(OneLineAndTwoBarChartContainer.class, new BarChartDeserializer());
		Gson gson = gsonBuilder.create();
		OneLineAndTwoBarChartContainer result = gson.fromJson(response, OneLineAndTwoBarChartContainer.class);
		Session.setActualLineAndBarChartContainer(result);
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Session.dismissAndMakeNullProgressDialog();
		if(activity instanceof ActivityLevel2NEW) {
			Intent i = new Intent(activity, ActivityLevel3ShowRemoteMonitoring.class);
			activity.startActivity(i);
		} else {
			((ActivityLevel3ShowRemoteMonitoring)activity).mSectionsPagerAdapter.notifyDataSetChanged();
		}
	}
	
}
