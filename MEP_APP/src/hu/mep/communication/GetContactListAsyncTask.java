package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2Chat;
import hu.mep.mep_app.activities.ActivityLevel2NEW;
import hu.mep.utils.deserializers.ChatContactListDeserializer;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetContactListAsyncTask extends AsyncTask<Void, Void, Void> {

	String hostURI;
	String resourceURI;
	ChatContactList before;
	private Activity activity = null;

	public GetContactListAsyncTask() {
	}
	
	public GetContactListAsyncTask(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Log.e("ASYNCTASK", "onPreExecute() running");
		resourceURI = "ios_getContactList.php?userId=" + Session.getActualUser().getMepID();
	}

	@Override
	protected Void doInBackground(Void... nothing) {

		String response = "";
		response = RealCommunicator.dohttpGet(resourceURI);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ChatContactList.class, new ChatContactListDeserializer());
		Gson gson = gsonBuilder.create();
		
		ChatContactList result = gson.fromJson(response, ChatContactList.class);
		ChatContactList temp = Session.getActualChatContactList();
		
		for (ChatContact act : result.getContacts()) {
			act.setProfilePicture(temp.getImageFromContactID(act.getUserID()));
		}
		
		Session.setActualChatContactList(result);

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		if(activity != null) {
			Intent intent = new Intent(activity, ActivityLevel2NEW.class);
			activity.startActivity(intent);
		} else {
			FragmentLevel2Chat.contactAdapter.notifyDataSetChanged();
			Session.getActualCommunicationInterface().getCharPartnersImages();
		}
	}

}