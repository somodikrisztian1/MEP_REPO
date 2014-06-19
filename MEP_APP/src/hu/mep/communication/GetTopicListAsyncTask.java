package hu.mep.communication;

import hu.mep.datamodells.AllTopicsList;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.TopicCategory;
import hu.mep.utils.deserializers.TopicListDeserializer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;

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

		//Log.e("GetContactListAsyncTask.doInBackground()", response);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(AllTopicsList.class, new TopicListDeserializer());
		Gson gson = gsonBuilder.create();
		AllTopicsList topics = gson.fromJson(response, AllTopicsList.class);
		Session.getInstance(context).setAllTopicsList(topics.getAllTopics());
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	
}
