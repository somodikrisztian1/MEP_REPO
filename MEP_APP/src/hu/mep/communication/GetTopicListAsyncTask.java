package hu.mep.communication;

import hu.mep.datamodells.AllTopicsList;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.TopicListDeserializer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetTopicListAsyncTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private static String hostURI;
	private static String resourceURI;
	private static String fullURI;
	
	
	public GetTopicListAsyncTask(Context context, String catchedHostURI) {
		this.context = context;
		hostURI = catchedHostURI;
	}
	
	@Override
	protected void onPreExecute() {
		resourceURI = "ios_getTemakorok.php?userId=" + Session.getActualUser().getMepID();
		fullURI = hostURI + resourceURI;
		super.onPreExecute();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		String response = "";

		response = RealCommunicator.dohttpGet(fullURI);

		/*Log.e("GetTopicListAsyncTask.doInBackground()", "response:" + response);*/

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(AllTopicsList.class, new TopicListDeserializer());
		Gson gson = gsonBuilder.create();
		AllTopicsList topics = gson.fromJson(response, AllTopicsList.class);
		Session.setAllTopicsList(topics.getAllTopics());
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		//Session.dismissAndMakeNullProgressDialog();
	}
	
}
