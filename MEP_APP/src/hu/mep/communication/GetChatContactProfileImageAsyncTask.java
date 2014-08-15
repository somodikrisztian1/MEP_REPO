package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2Chat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class GetChatContactProfileImageAsyncTask extends AsyncTask<ChatContact, Void, Void> {
	
	public GetChatContactProfileImageAsyncTask() {
	}
	
	@Override
	protected Void doInBackground(ChatContact... params) {
	
		ChatContact contact = params[0];
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
			return null;
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		FragmentLevel2Chat.contactAdapter.notifyDataSetChanged();
	}

}
