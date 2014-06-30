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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetChatMessagesListAsyncTask extends
		AsyncTask<Void, Void, Void> {

	String hostURI;
	String resourceURI;
	Context context;
//	ProgressDialog pd;

	public GetChatMessagesListAsyncTask(Context context, String hostURI) {
		this.context = context;
		this.hostURI = hostURI;
		/*pd = new ProgressDialog(context.getApplicationContext());
		pd.setTitle("Üzenetek letöltése");
		pd.setCancelable(false);
		pd.setMessage("Kérem várjon!\nLetöltés folyamatban...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);*/
	}

	@Override
	protected void onPreExecute() {
		//Log.e("ASYNCTASK", "onPreExecute() running");
	//	pd.show();
		/*Session.getInstance(context).setProgressDialog(pd);
		Session.getInstance(context).showProgressDialog();
		*/
		resourceURI = "ios_getLastMessages.php?userId="
				+ Session.getActualUser().getMepID()
				+ "&contactId="
				+ Session.getActualChatPartner()
						.getUserID() + "&lastDate="
				+ Session.getLastChatMessageOrder();
	}

	@Override
	protected Void doInBackground(Void... nothing) {
		//Log.e("ASYNCTASK", "doInBackground() running");
		String response = "";
		String fullURI = hostURI + resourceURI;
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
		//Log.e("ASYNCTASK", "response= " + response);
		if (!response.equals("[]")) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(ChatMessagesList.class,
					new ChatMessagesListDeserializer());
			Gson gson = gsonBuilder.create();
			ChatMessagesList messages = gson.fromJson(response,
					ChatMessagesList.class);
			/*Log.e("GSON...", "beérkezett üzenetek száma: "
					+ messages.getChatMessagesList().size());*/
			Session.getInstance(context).setChatMessagesList(messages);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		//pd.dismiss();
		ActivityLevel3Chat.adapter.notifyDataSetChanged();
		super.onPostExecute(result);
	}
}
