package hu.mep.communication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

public class GetChartAsyncTask extends AsyncTask<Void, Void, Void> {
	
	private Context context;
	private static String hostURI;
	private static String resourceURI;
	private static String fullURI;
	
	public GetChartAsyncTask(Context context, String catchedHostURI) {
		this.context = context;
		hostURI = catchedHostURI;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		resourceURI = "ios_getChart.php?id=" +
				Session.getActualChartInfoContainer().getId();
		fullURI = hostURI + resourceURI;
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
		Session.getInstance(context).setActualChart(chart);
		
		return null;
	}	
	
}
