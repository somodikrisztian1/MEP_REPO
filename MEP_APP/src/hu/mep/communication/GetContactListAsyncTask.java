package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2Chat;
import hu.mep.utils.deserializers.ChatContactListDeserializer;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetContactListAsyncTask extends AsyncTask<Void, Void, Void> {

	String hostURI;
	String resourceURI;
	Context context;
	ChatContactList before = new ChatContactList(new ArrayList<ChatContact>());

	public GetContactListAsyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Log.e("ASYNCTASK", "onPreExecute() running");
		resourceURI = "ios_getContactList.php?userId=" + Session.getActualUser().getMepID();
	}

	@Override
	protected Void doInBackground(Void... nothing) {
		// Log.e("ASYNCTASK", "doInBackground() running");
		String response = "";

		response = RealCommunicator.dohttpGet(resourceURI);

		// Log.e("GetContactListAsyncTask.doInBackground()", response);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ChatContactList.class, new ChatContactListDeserializer());
		Gson gson = gsonBuilder.create();
		
		ChatContactList after = gson.fromJson(response, ChatContactList.class);
		
		Session.setActualChatContactList(after);

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		FragmentLevel2Chat.contactAdapter.notifyDataSetChanged();
		
	}

}