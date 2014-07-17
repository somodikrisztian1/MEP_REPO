package hu.mep.communication;

import hu.mep.datamodells.Session;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

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
				HttpURLConnection connection = (HttpURLConnection) fullURI
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();

				Bitmap bm = BitmapFactory.decodeStream(input);

				WindowManager wm = (WindowManager) context
						.getSystemService(Context.WINDOW_SERVICE);

				DisplayMetrics displayMetrics = new DisplayMetrics();
				wm.getDefaultDisplay().getMetrics(displayMetrics);

				int width = displayMetrics.widthPixels;
				int height = displayMetrics.heightPixels;

				int pictureWidth = 0;
				int pictureHeight = 0;

				if (width > bm.getWidth()) {
					pictureWidth = width;
				} else {
					pictureWidth = bm.getWidth();
				}

				if (height > bm.getHeight()) {
					pictureHeight = height;
				} else {
					pictureHeight = bm.getHeight();
				}

				bm = Bitmap.createScaledBitmap(bm, pictureWidth, pictureHeight,
						true);

				Session.addPictureToGallery(bm);

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
