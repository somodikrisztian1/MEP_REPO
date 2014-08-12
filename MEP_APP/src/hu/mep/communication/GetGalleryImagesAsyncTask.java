 package hu.mep.communication;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel1Gallery;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class GetGalleryImagesAsyncTask extends AsyncTask<Void, Void, Void> {

	//private static final String TAG = "GetGalleryImagesAsyncTask";
	String hostURI;
	String resourceURI;
	Activity activity;
	ProgressDialog pd;

	public GetGalleryImagesAsyncTask(Activity activity, String hostURI) {
		this.activity = activity;
		this.hostURI = hostURI;
		/*
		pd = new ProgressDialog(this.activity);
		pd.setCancelable(false);
		pd.setMessage("Képek letöltése folyamatban...");
		*/
	}

	@Override
	protected void onPreExecute() {
		/*Session.setProgressDialog(pd);
		Session.showProgressDialog();*/
		resourceURI = "images/iphone/";
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			for (String actImageURL : Session.getGalleryImageURLSList()) {
				URL fullURI = new URL(hostURI + resourceURI + actImageURL);
				HttpURLConnection connection = (HttpURLConnection) fullURI.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap bm = BitmapFactory.decodeStream(input);

				WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
				DisplayMetrics displayMetrics = new DisplayMetrics();
				wm.getDefaultDisplay().getMetrics(displayMetrics);

				int width;
				int height;

				int pictureWidth;
				int pictureHeight;

				double ratio = 0.0;
				
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
							
				if (width > bm.getWidth()) {
					pictureWidth = width;
					ratio = ((double) pictureWidth) / bm.getWidth();
				} else {
					pictureWidth = bm.getWidth();
					ratio = ((double) pictureWidth) / width;
				}
				
				pictureHeight = (int) Math.ceil( bm.getHeight() * ratio );
				
				bm = Bitmap.createScaledBitmap(bm, pictureWidth, pictureHeight, true);
				
				if( height - this.getStatusBarHeight() < pictureHeight) {
					bm = Bitmap.createBitmap(bm, 0, 0, pictureWidth, height-this.getStatusBarHeight());
				} else {
					bm = Bitmap.createBitmap(bm, 0, 0, pictureWidth, pictureHeight);
				}
				Session.addPictureToGallery(bm);
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
		Session.dismissAndMakeNullProgressDialog();

		Intent i = new Intent(activity, ActivityLevel1Gallery.class);
		activity.startActivity(i);
	}
	
	public int getStatusBarHeight() {
	      int result = 0;
	      int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
	      if (resourceId > 0) {
	          result = activity.getResources().getDimensionPixelSize(resourceId);
	      } 
	      
	      return result;
	}

}
