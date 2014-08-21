package hu.mep.communication;

import hu.mep.datamodells.AllTopicsList;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.TopicListDeserializer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
//import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetTopicListAsyncTask extends AsyncTask<Void, Void, Void> {

	//private static final String TAG = "GetTopicListAsyncTask";
	private static String resourceURI;
	private Activity activity;
	private ProgressDialog pd;
	
	public GetTopicListAsyncTask(Activity activity) {
		this.activity = activity;
		this.pd = new ProgressDialog(this.activity);
		this.pd.setCancelable(false);
		this.pd.setMessage("Felhasználói adatok betöltése folyamatban...");
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Session.setProgressDialog(pd);
		Session.showProgressDialog();
		if(!Session.getActualUser().isMekut() && Session.getActualUser().getUsersPlaces() == null) {
			resourceURI = "ios_getTemakorok.php?userId=" + Session.getActualUser().getMepID() +	"&demo=1";
		} else {
			resourceURI = "ios_getTemakorok.php?userId=" + Session.getActualUser().getMepID();
		}
		//Log.e("GETTOPICLISTASYNCTASK", resourceURI);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		String response = "";
		if (Session.getActualUser().isMekut() ||
				(!Session.getActualUser().isMekut() && Session.getActualUser().getUsersPlaces() == null )) {
			response = RealCommunicator.dohttpGet(resourceURI);
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(AllTopicsList.class, new TopicListDeserializer());
			Gson gson = gsonBuilder.create();
			AllTopicsList topics = gson.fromJson(response, AllTopicsList.class);
			if(topics.getAllTopics() != null) {
				Session.setAllTopicsList(topics.getAllTopics());
			}
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	
		Session.dismissAndMakeNullProgressDialog();
		Session.setAnyUserLoggedIn(true);
		
		Session.getActualCommunicationInterface().getChatPartners(activity);	
		
	}
	
}
