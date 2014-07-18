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

//		String response = "";

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

				int width;
				int height;
				
				if ( !Session.isTablet() )
				{
					width = displayMetrics.widthPixels;
					height = displayMetrics.heightPixels;
				}
				else
				{
					width = displayMetrics.heightPixels;
					height = displayMetrics.widthPixels;
				}

				int pictureWidth = 0;
				int pictureHeight = 0;

				double ratio = 0.0;
				
				if (width > bm.getWidth()) {
					pictureWidth = width;
					ratio = ((double) pictureWidth) / bm.getWidth();
				} else {
					pictureWidth = bm.getWidth();
					ratio = ((double) pictureWidth) / width;
				}
				
				if (height > bm.getHeight()) {
					pictureHeight = height;
				}
				else {
					pictureHeight = bm.getHeight();
				}
				
				pictureHeight = (int) Math.ceil( bm.getHeight() * ratio );

//				Log.e("galéria", "pictureHeight: " + pictureHeight + " számolás: bm.get: " + bm.getHeight() + " ratio: " + ratio );
				
				bm = Bitmap.createScaledBitmap(bm, pictureWidth, pictureHeight, true);
				
				bm = Bitmap.createBitmap(bm, 0, 0, pictureWidth, height-this.getStatusBarHeight());

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
	
	public int getStatusBarHeight() { 
	      int result = 0;
	      int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
	      if (resourceId > 0) {
	          result = context.getResources().getDimensionPixelSize(resourceId);
	      } 
	      
	      Log.e("getStatusBarHeight", "result: " + result);
	      return result;
	}

}
