package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.ChatContactListDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetContactListAsyncTaskPRELOAD extends AsyncTask<Void, Void, Void> {

	//private static final String TAG = "GetContactListAsyncTaskPRELOAD";
	String hostURI;
	String resourceURI;
	Context context;
	ChatContactList before = new ChatContactList(new ArrayList<ChatContact>());

	public GetContactListAsyncTaskPRELOAD(Context context, String hostURI) {
		this.context = context;
		this.hostURI = hostURI;
	}

	@Override
	protected void onPreExecute() {
		resourceURI = "ios_getContactList.php?userId="
				+ Session.getActualUser().getMepID();
	}

	@Override
	protected Void doInBackground(Void... nothing) {
		String response = "";
		String fullURI = hostURI + resourceURI;
		
		response = RealCommunicator.dohttpGet(fullURI);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ChatContactList.class,
				new ChatContactListDeserializer());
		Gson gson = gsonBuilder.create();
		ChatContactList after = gson.fromJson(response, ChatContactList.class);
		
		for (ChatContact actContact : after.getContacts()) {
			downloadProfilePictureForChatContact(actContact);
		}

		Session.setActualChatContactList(after);

		return null;
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
				HttpURLConnection connection = (HttpURLConnection) imgURL.openConnection();
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
				
			} else {
				contact.setProfilePicture(Session.getEmptyProfilePicture());
			
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		return;
	}

}