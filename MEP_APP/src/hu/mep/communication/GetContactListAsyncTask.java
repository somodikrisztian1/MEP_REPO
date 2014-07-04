package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2Chat;
import hu.mep.utils.deserializers.ChatContactListDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetContactListAsyncTask extends AsyncTask<Void, Void, String> {

	String hostURI;
	String resourceURI;
	Context context;
	ChatContactList before = new ChatContactList(new ArrayList<ChatContact>());

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

		//Log.e("GetContactListAsyncTask.doInBackground()", response);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ChatContactList.class,
				new ChatContactListDeserializer());
		Gson gson = gsonBuilder.create();
		ChatContactList contacts = gson.fromJson(response,
				ChatContactList.class);
		Session.getInstance(context).setActualChatContactList(contacts);

		for (ChatContact actContact : Session.getInstance(context)
				.getActualChatContactList().getContacts()) {
			if(isCancelled()) {
				break;
			}
			if (actContact.getProfilePicture() == null) {
				//Log.e("GetContactListAsyncTask", actContact.getName());
				//Log.e("GetContactListAsyncTask", "Kép letöltése...");
				downloadProfilePictureForChatContact(actContact);
			}
		}

		return response;
	}

	private void downloadProfilePictureForChatContact(ChatContact contact) {
		try {
			Bitmap bmp;
			if (contact.getProfilePicture() == null) {
				URL imgURL = null;
				if (contact.getImageURL().toUpperCase().endsWith(".JPG")
						|| contact.getImageURL().toUpperCase().endsWith(".JPEG")
						|| contact.getImageURL().toUpperCase().endsWith(".PNG")
						|| contact.getImageURL().toUpperCase().endsWith(".GIF")
						|| contact.getImageURL().toUpperCase().endsWith(".BMP")) {
					imgURL = new URL(contact.getImageURL());
				} else {
					imgURL = new URL(
							"http://megujuloenergiapark.hu/images/avatar/empty.jpg");
				}
				Log.e("GetContactListAsyncTask", contact.getName());
				Log.e("GetContactListAsyncTask", "Kép letöltése...");

				HttpURLConnection connection = (HttpURLConnection) imgURL
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				bmp = BitmapFactory.decodeStream(input);
				// Megnézzük, álló vagy fekvő tájolású-e.
				int fixSize = (bmp.getWidth() < bmp.getHeight() ? bmp
						.getWidth() : bmp.getHeight());
				// A rövidebb oldal szerint vágunk egy nagy négyzetre.
				bmp = Bitmap.createBitmap(bmp, 0, 0, fixSize, fixSize);
				// Skálázás 200×200-as négyzetre.
				bmp = Bitmap.createScaledBitmap(bmp, 250, 250, true);
				contact.setProfilePicture(bmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return;
		}
		return;
	}

	@Override
	protected void onPostExecute(String result) {
		FragmentLevel2Chat.contactAdapter.notifyDataSetChanged();
		super.onPostExecute(result);
	}

}
