package hu.mep.communication;

import hu.mep.datamodells.AllTopicsList;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.TopicListDeserializer;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetTopicListAsyncTask extends AsyncTask<Void, Void, Void> {

	private static String hostURI;
	private static String resourceURI;
	private static String fullURI;
	
	
	public GetTopicListAsyncTask(String catchedHostURI) {
		hostURI = catchedHostURI;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		resourceURI = "ios_getTemakorok.php?userId=" + Session.getActualUser().getMepID();
		fullURI = hostURI + resourceURI;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		String response = "";

		response = RealCommunicator.dohttpGet(fullURI);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(AllTopicsList.class, new TopicListDeserializer());
		Gson gson = gsonBuilder.create();
		AllTopicsList topics = gson.fromJson(response, AllTopicsList.class);
		Session.setAllTopicsList(topics.getAllTopics());
		return null;
	}
	
}
