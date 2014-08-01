package hu.mep.communication;

import hu.mep.datamodells.Place;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2RemoteMonitorings;
import hu.mep.utils.deserializers.NotWorkingPlacesDeserializer;

import java.util.HashMap;
import java.util.Map.Entry;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetNotWorkingPlacesListAsyncTask extends AsyncTask<Void, Void, Void> {

	//private static final String TAG = "GetNotWorkingPlacesListAsyncTask";

	String hostURI;
	String resourceURI;
	String fullURI;
	
	public GetNotWorkingPlacesListAsyncTask(String hostURI) {
		this.hostURI = hostURI;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		this.resourceURI = "ios_getHibasTf.php?userId=" + 
		Session.getActualUser().getMepID();
		fullURI = hostURI + resourceURI;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		String response = "";
		response = RealCommunicator.dohttpGet(fullURI);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter( HashMap.class, new NotWorkingPlacesDeserializer());
		Gson gson = gsonBuilder.create();
		HashMap<String, String> container = gson.fromJson(response, HashMap.class);
		
		for (Place act : Session.getActualUser().getUsersPlaces().getPlaces()) {
			act.setWorkingProperly(true);
		}
		for (Entry<String, String> act : container.entrySet()) {
			Session.getActualUser().getUsersPlaces().findPlaceByID(act.getKey()).setWorkingProperly(false);
			Session.getActualUser().getUsersPlaces().findPlaceByID(act.getKey()).setLastWorkingText(act.getValue());;
		}		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		FragmentLevel2RemoteMonitorings.placeAdapter.notifyDataSetChanged();
	}
	
}
