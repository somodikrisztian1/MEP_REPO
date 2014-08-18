package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.ChatContactListDeserializer;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetContactListAsyncTaskPRELOAD extends AsyncTask<Void, Void, Void> {

	//private static final String TAG = "GetContactListAsyncTaskPRELOAD";

	String resourceURI;
	Context context;
	ChatContactList before = new ChatContactList(new ArrayList<ChatContact>());

	public GetContactListAsyncTaskPRELOAD(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		resourceURI = "ios_getContactList.php?userId="
				+ Session.getActualUser().getMepID();
	}

	@Override
	protected Void doInBackground(Void... nothing) {
		String response = "";

		response = RealCommunicator.dohttpGet(resourceURI);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ChatContactList.class,
				new ChatContactListDeserializer());
		Gson gson = gsonBuilder.create();
		ChatContactList after = gson.fromJson(response, ChatContactList.class);

		Session.setActualChatContactList(after);

		return null;
	}

}