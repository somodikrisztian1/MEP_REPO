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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

public class GetContactListAsyncTaskNEW extends AsyncTask<Void, Void, String> {

	String hostURI;
	String resourceURI;
	Context context;
	ChatContactList before = new ChatContactList(new ArrayList<ChatContact>());

	public GetContactListAsyncTaskNEW(Context context, String hostURI) {
		this.context = context;
		this.hostURI = hostURI;
	}

	@Override
	protected void onPreExecute() {
		// Log.e("ASYNCTASK", "onPreExecute() running");
		resourceURI = "ios_getContactList.php?userId=" + Session.getActualUser().getMepID();
	}

	@Override
	protected String doInBackground(Void... nothing) {
		// Log.e("ASYNCTASK", "doInBackground() running");
		String response = "";
		String fullURI = hostURI + resourceURI;
		// Log.e("ASYNCTASK", "fullURI: " + fullURI);

		response = RealCommunicator.dohttpGet(fullURI);

		// Log.e("GetContactListAsyncTask.doInBackground()", response);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ChatContactList.class, new ChatContactListDeserializer());
		Gson gson = gsonBuilder.create();
		ChatContactList after = gson.fromJson(response, ChatContactList.class);
		if (Session.getActualChatContactList() != null) {
			before = new ChatContactList(Session.getActualChatContactList().getContacts());
		}
		

		for (ChatContact actContact : after.getContacts()) {
			if (!isCancelled()) {
				boolean on = (actContact.isOnline() == 1 ? true : false);
				/*Log.i("getContactsAT",
						actContact.getName() + (on ? "[on]" : "[off]") + "("
								+ actContact.getUnreadedMessageNumber() + ")");
				 */
				actContact.setProfilePicture(before
						.getImageFromContactID(actContact.getUserID()));
				if (actContact.getProfilePicture() == null) {
					downloadProfilePictureForChatContact(actContact);
				}
			}
		}
		
		Session.setActualChatContactList(after);

		return response;
	}

	private void downloadProfilePictureForChatContact(ChatContact contact) {
		try {
			Bitmap bmp;

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
			int fixSize = (bmp.getWidth() < bmp.getHeight() ? bmp.getWidth()
					: bmp.getHeight());
			// A rövidebb oldal szerint vágunk egy nagy négyzetre.
			bmp = Bitmap.createBitmap(bmp, 0, 0, fixSize, fixSize);
			// Skálázás 200×200-as négyzetre.
			bmp = Bitmap.createScaledBitmap(bmp, 250, 250, true);
			contact.setProfilePicture(bmp);
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