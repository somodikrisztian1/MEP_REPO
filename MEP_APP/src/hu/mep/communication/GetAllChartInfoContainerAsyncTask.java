package hu.mep.communication;

import hu.mep.datamodells.AllChartInfoContainer;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.ChartInfoContainerDeserializer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetAllChartInfoContainerAsyncTask extends
AsyncTask<Void, Void, Void> {
	
	private static String hostURI;
	private static String resourceURI;
	private static String fullURI;
	private static boolean isRemoteMonitoring;	

	public GetAllChartInfoContainerAsyncTask(String catchedHostURI, boolean isRemoteMonitoring) {
		super();
		hostURI = catchedHostURI;
		GetAllChartInfoContainerAsyncTask.isRemoteMonitoring = isRemoteMonitoring;
	}
	
	private String getSSZS() {
		String result = "";
		if(isRemoteMonitoring) {
			/* NINCS MIBŐL AZ SSZ-EKET KINYERNI!!! */
			/*Iterator<Integer> iterator = Session.getActualRemoteMonitoring().ge
			while(iterator.hasNext()) {
				result += String.valueOf(iterator.next());
				if(iterator.hasNext()) {
					result += ",";
				}
			}	*/	
		} else {
			Iterator<Integer> iterator = Session.getActualTopic().getTabSerialNumbers().iterator();
			while(iterator.hasNext()) {
				result += String.valueOf(iterator.next());
				if(iterator.hasNext()) {
					result += ",";
				}
			}			
		}
		return result;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(isRemoteMonitoring) {
			resourceURI = "ios_getChartNames.php?tsz1_id=" +
					Session.getActualRemoteMonitoring().getID();
					
					fullURI = hostURI + resourceURI;
					Log.e("GetChartNames", fullURI);
		}
		else {
			resourceURI = "ios_getChartNames.php?tsz1_id=" +
					Session.getActualTopic().getTopicID()
					+ "&sszs=" + getSSZS();
					
					fullURI = hostURI + resourceURI;
					Log.e("GetChartNames", fullURI);
		}
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
			Session.setAllChartInfoContainer(allChartInfo.getAllChartInfoContainer());
		}
		
		return null;
	}

}
