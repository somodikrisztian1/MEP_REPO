package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2Chat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
//import android.util.Log;

public class GetChatContactProfileImageAsyncTask extends AsyncTask<Void, Void, Void> {
	
	//private static final String TAG = "GetChatContactProfileImageAsyncTask";
	private ChatContactList temp;
	
	public GetChatContactProfileImageAsyncTask() {
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		temp = Session.getActualChatContactList();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
	
		for (ChatContact contact : temp.getContacts()) {
			
			if(!Session.isAnyUserLoggedIn()) {
				return null;
			}
			
			Bitmap bmp;
			URL imgURL = null;
			if (contact.getImageURL().toUpperCase().endsWith(".JPG")
					|| contact.getImageURL().toUpperCase().endsWith(".JPEG")
					|| contact.getImageURL().toUpperCase().endsWith(".PNG")
					|| contact.getImageURL().toUpperCase().endsWith(".GIF")
					|| contact.getImageURL().toUpperCase().endsWith(".BMP")) {
				try {
					imgURL = new URL(contact.getImageURL());
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				if(contact.getImageURL().equals("http://megujuloenergiapark.hu/images/avatar/empty.jpg")) {
					//Log.e(TAG, "skip downloading empty image...");
					continue;
				}
				if(contact.getProfilePicture().sameAs(Session.getEmptyProfilePicture()) && 
						!contact.getImageURL().equals("http://megujuloenergiapark.hu/images/avatar/empty.jpg")) {
					//Log.e(TAG, "downloading from " + contact.getImageURL());
					HttpURLConnection connection = null; 
					try {
						connection = (HttpURLConnection) imgURL.openConnection();
						connection.setDoInput(true);
						connection.connect();
						InputStream input = connection.getInputStream();
						bmp = BitmapFactory.decodeStream(input);
						// Megnézzük, álló vagy fekvő tájolású-e.
						int fixSize = (bmp.getWidth() < bmp.getHeight() ? bmp.getWidth() : bmp.getHeight());
						// A rövidebb oldal szerint vágunk egy nagy négyzetre.
						bmp = Bitmap.createBitmap(bmp, 0, 0, fixSize, fixSize);
						// Skálázás 200×200-as négyzetre.
						bmp = Bitmap.createScaledBitmap(bmp, 250, 250, true);
						contact.setProfilePicture(bmp);
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		}

		return null;
	}

	
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		//Log.e(TAG, "onpostexecute....contactAdapter.notifyDataSetChanged()");
		FragmentLevel2Chat.contactAdapter.notifyDataSetChanged();
	}
	
}
