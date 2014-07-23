package hu.mep.communication.charts;

import hu.mep.communication.RealCommunicator;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.Chart;
import hu.mep.utils.deserializers.ChartDeserializer;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetActualChartAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "GetChartAsyncTask";
	private Context context;
	private String hostURI;
	private String resourceURI;
	private String fullURI;
	private Calendar startDate;
	private Calendar endDate;

	public GetActualChartAsyncTask(Context context, String catchedHostURI) {
		this.context = context;
		hostURI = catchedHostURI;
	}

	public GetActualChartAsyncTask(Context context, String catchedHostURI,
			Calendar startDate, Calendar endDate) {
		this.context = context;
		this.hostURI = catchedHostURI;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	private String formatDate(Calendar date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
		
		Log.e(TAG, "Formatted Date: " + format.format(date.getTime()));
		
		return format.format(date.getTime());
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if ((startDate != null) && (endDate != null)) {
			resourceURI = "ios_getChart.php?id="
					+ Session.getActualChartName().getId()
					+ "&fromDate=" + formatDate(startDate)
					+ "&toDate=" + formatDate(endDate);
		}
		else {
			resourceURI = "ios_getChart.php?id="
					+ Session.getActualChartName().getId();
		}
		fullURI = hostURI + resourceURI;
		Log.e("GetChart", fullURI);
	}

	@Override
	protected Void doInBackground(Void... params) {
		String response = "";
		
		response = RealCommunicator.dohttpGet(fullURI);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Chart.class, new ChartDeserializer());
		Gson gson = gsonBuilder.create();
		Chart chart = gson.fromJson(response, Chart.class);
		Session.setActualChart(chart);

		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		/*if(startDate != null && endDate != null) {
			ActivityLevel3ShowTopic.refreshFragments();
		}*/
		Log.e(TAG, "GetChartAsyncTask finished...");
		
	}

}
