package hu.mep.communication;

import hu.mep.datamodells.ChatMessagesList;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel3Chat;
import hu.mep.utils.deserializers.ChatMessagesListDeserializer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetChatMessagesListAsyncTask extends AsyncTask<Void, Void, Void> {

	String hostURI;
	String resourceURI;
	Context context;

	public GetChatMessagesListAsyncTask(Context context, String hostURI) {
		this.context = context;
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
		// Log.e("ASYNCTASK", "doInBackground() running");
		String response = "";
		String fullURI = hostURI + resourceURI;
		
		response = RealCommunicator.dohttpGet(fullURI);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ChatMessagesList.class,
				new ChatMessagesListDeserializer());
		Gson gson = gsonBuilder.create();
		ChatMessagesList messages = gson.fromJson(response, ChatMessagesList.class);
		Log.e("GSON", "Beérkezett üzenetek száma: " + messages.getChatMessagesList().size());
		
		Session.setChatMessagesList(messages);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// pd.dismiss();
		ActivityLevel3Chat.adapter.notifyDataSetChanged();
		super.onPostExecute(result);
	}
}
