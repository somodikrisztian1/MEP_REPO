package hu.mep.communication;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class GetGalleryImagesAsyncTask extends AsyncTask<Void, Void, Void> {
	
	String hostURI;
	String resourceURI;
	Context context;
	
	public GetGalleryImagesAsyncTask(String hostURI, Context context) {
		this.hostURI = hostURI;
		this.context = context;
	}
	

	@Override
	protected void onPreExecute() {
		// Log.e("ASYNCTASK", "onPreExecute() running");
		resourceURI = "images/iphone/";
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		String response = "";
		
		// Log.e("ASYNCTASK", "fullURI: " + fullURI);
				
		try {
			for (String actImageURL : Session.getGalleryImageURLSList()) {
				URL fullURI = new URL(hostURI + resourceURI + actImageURL);
			HttpURLConnection connection = (HttpURLConnection) fullURI.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Session.addPictureToGallery(BitmapFactory.decodeStream(input));
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return null;
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Session.dismissAndMakeNullProgressDialog();
	}
	
}
