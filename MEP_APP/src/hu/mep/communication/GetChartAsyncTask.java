package hu.mep.communication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.mep.datamodells.AllTopicsList;
import hu.mep.datamodells.Chart;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.ChartDeserializer;
import hu.mep.utils.deserializers.TopicListDeserializer;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetChartAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "GetChartAsyncTask";
	private Context context;
	private String hostURI;
	private String resourceURI;
	private String fullURI;
	private Calendar startDate;
	private Calendar endDate;

	public GetChartAsyncTask(Context context, String catchedHostURI) {
		this.context = context;
		hostURI = catchedHostURI;
	}

	public GetChartAsyncTask(Context context, String catchedHostURI,
			Calendar startDate, Calendar endDate) {
		this.context = context;
		this.hostURI = catchedHostURI;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	private String formatDate(Calendar date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		Log.e(TAG, "Formatted Date: " + format.format(date));
		return format.format(date);
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if ((startDate != null) && (endDate != null)) {
			resourceURI = "ios_getChart.php?id="
					+ Session.getActualChartInfoContainer().getId()
					+ "&fromDate=" + formatDate(startDate)
					+ "&toDate=" + formatDate(endDate) ;
		}
		else {
			resourceURI = "ios_getChart.php?id="
					+ Session.getActualChartInfoContainer().getId();
		}
		fullURI = hostURI + resourceURI;
		Log.e("GetChart", fullURI);
	}

	@Override
	protected Void doInBackground(Void... params) {
		String response = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(fullURI);
		try {
			HttpResponse execute = client.execute(httpGet);
			InputStream content = execute.getEntity().getContent();

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					content));
			String s = "";
			while ((s = buffer.readLine()) != null) {
				response += s;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Chart.class, new ChartDeserializer());
		Gson gson = gsonBuilder.create();
		Chart chart = gson.fromJson(response, Chart.class);
		Session.setActualChart(chart);

		return null;
	}

}
