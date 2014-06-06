package hu.mep.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.Session;
import hu.mep.utils.ChatContactListDeserializer;
import hu.mep.utils.MD5Encoder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class GetContactListAsyncTask extends AsyncTask<Void, Void, String> {

	String hostURI;
	String resourceURI;
	Context context;

	public GetContactListAsyncTask(Context context, String hostURI) {
		this.context = context;
		this.hostURI = hostURI;
	}

	@Override
	protected void onPreExecute() {
		// Log.e("ASYNCTASK", "onPreExecute() running");
		resourceURI = "ios_getContactList.php?userId="
				+ Session.getInstance(context).getActualUser().getMepID();
	}

	@Override
	protected String doInBackground(Void... nothing) {
		// Log.e("ASYNCTASK", "doInBackground() running");
		String response = "";
		String fullURI = hostURI + resourceURI;
		 Log.e("ASYNCTASK", "fullURI: " + fullURI);
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

		Log.e("GetContactListAsyncTask.doInBackground()", response);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ChatContactList.class,
				new ChatContactListDeserializer());
		Gson gson = gsonBuilder.create();
		ChatContactList contacts = gson.fromJson(response,
				ChatContactList.class);
		Session.getInstance(context).setActualChatContactList(contacts);

		for (ChatContact actContact : Session.getInstance(context)
				.getActualChatContactList().getContacts()) {
			Log.e("GetContactListAsyncTask", actContact.getName());
			Log.e("GetContactListAsyncTask", "Kép letöltése...");
			downloadProfilePictureForChatContact(actContact);
		}

		return response;
	}

	private void downloadProfilePictureForChatContact(ChatContact contact) {
		try {
			Bitmap bmp;
			URL imgURL = new URL(contact.getImageURL());
			HttpURLConnection connection = (HttpURLConnection) imgURL
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			bmp = BitmapFactory.decodeStream(input);
			// Megnézzük, álló vagy fekvő tájolású-e.
			int fixSize = (bmp.getWidth() < bmp.getHeight() ? bmp.getWidth()
					: bmp.getHeight());
			// A rövidebb oldal szerint vágunk egy nagy négyzetre.
			bmp = Bitmap.createBitmap(bmp, 0, 0, fixSize, fixSize);
			// Skálázás 200×200-as négyzetre.
			bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);
			contact.setProfilePicture(bmp);

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return;
		}
		return;
	}

}
