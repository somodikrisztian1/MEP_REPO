package hu.mep.communication;

import hu.mep.datamodells.AllTopicsList;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.TopicListDeserializer;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetTopicListAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "GetTopicListAsyncTask";
	private static String resourceURI;
	
	
	public GetTopicListAsyncTask() {
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(!Session.getActualUser().isMekut() && Session.getActualUser().getUsersPlaces() == null) {
			resourceURI = "ios_getTemakorok.php?userId=" + Session.getActualUser().getMepID() +
					"&demo=1";
		} else {
			resourceURI = "ios_getTemakorok.php?userId=" + Session.getActualUser().getMepID();
		}
		Log.e("GETTOPICLISTASYNCTASK", resourceURI);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		String response = "";

		response = RealCommunicator.dohttpGet(resourceURI);
		Log.e(TAG, "response=" + response );
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(AllTopicsList.class, new TopicListDeserializer());
		Gson gson = gsonBuilder.create();
		AllTopicsList topics = gson.fromJson(response, AllTopicsList.class);
		if(topics.getAllTopics() != null) {
			Session.setAllTopicsList(topics.getAllTopics());
		}
		return null;
	}
	
}
