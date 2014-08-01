package hu.mep.communication;

import hu.mep.datamodells.ChatMessagesList;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel3Chat;
import hu.mep.utils.deserializers.ChatMessagesListDeserializer;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetChatMessagesListAsyncTask extends AsyncTask<Void, Void, Void> {

	//private static final String TAG = "GetChatMessagesListAsyncTask";
	
	String hostURI;
	String resourceURI;

	public GetChatMessagesListAsyncTask(String hostURI) {
		this.hostURI = hostURI;
	}

	@Override
	protected void onPreExecute() {
		resourceURI = "ios_getLastMessages.php?userId="
				+ Session.getActualUser().getMepID() + "&contactId="
				+ Session.getActualChatPartner().getUserID() + "&lastDate="
				+ Session.getLastChatMessageOrder();
	}

	@Override
	protected Void doInBackground(Void... nothing) {
		String response = "";
		String fullURI = hostURI + resourceURI;
		
		response = RealCommunicator.dohttpGet(fullURI);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ChatMessagesList.class, new ChatMessagesListDeserializer());
		Gson gson = gsonBuilder.create();
		ChatMessagesList messages = gson.fromJson(response, ChatMessagesList.class);
		
		Session.setChatMessagesList(messages);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		
		if(ActivityLevel3Chat.adapter != null) {
			ActivityLevel3Chat.adapter.notifyDataSetChanged();
		}
	}
}
