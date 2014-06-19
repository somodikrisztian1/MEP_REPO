package hu.mep.communication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.mep.datamodells.AllChartInfoContainer;
import hu.mep.datamodells.ChatMessagesList;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.ChartInfoContainerDeserializer;
import android.content.Context;
import android.os.AsyncTask;

public class GetAllChartInfoContainerAsyncTask extends
AsyncTask<Void, Void, Void> {
	
	private Context context;
	private static String hostURI;
	private static String resourceURI;
	private static String fullURI;
	
	
	

	public GetAllChartInfoContainerAsyncTask(Context context, String catchedHostURI) {
		super();
		this.context = context;
		hostURI = catchedHostURI;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		resourceURI = "http://www.megujuloenergiapark.hu/ios_getChartNames.php?tsz1_id=" +
		Session.getInstance(context).getActualTopic().getTopicID();
		fullURI = hostURI + resourceURI;
	}



	@Override
	protected Void doInBackground(Void... params) {
		String response = "";
		//Log.e("ASYNCTASK", "fullURI: " + fullURI);
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
		
		if (!response.equals("[]")) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(AllChartInfoContainer.class, new ChartInfoContainerDeserializer());
			Gson gson = gsonBuilder.create();
			AllChartInfoContainer allChartInfo = gson.fromJson(response,
					AllChartInfoContainer.class);
			Session.getInstance(context).setAllChartInfoContainer(allChartInfo.getAllChartInfoContainer());
		}
		
		return null;
	}

}
