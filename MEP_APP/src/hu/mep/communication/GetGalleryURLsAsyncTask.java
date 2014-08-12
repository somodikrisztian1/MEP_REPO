package hu.mep.communication;

import hu.mep.datamodells.ImageURLList;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel1Gallery;
import hu.mep.utils.deserializers.ImageURLDeserializer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetGalleryURLsAsyncTask extends AsyncTask<Void, Void, Boolean> {

	String hostURI;
	String resourceURI;
	Activity activity;
	ProgressDialog pd;

	public GetGalleryURLsAsyncTask(Activity activity, String hostURI) {
		this.activity = activity;
		this.hostURI = hostURI;
		
		this.pd = new ProgressDialog(this.activity);
		this.pd.setCancelable(false);
		this.pd.setMessage("Képek betöltése...");
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		Session.setProgressDialog(pd);
		Session.showProgressDialog();
		
		resourceURI = "ios_getGalleryUrls.php";
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		String response = "";
		response = RealCommunicator.dohttpGet(resourceURI);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ImageURLList.class,
				new ImageURLDeserializer());
		Gson gson = gsonBuilder.create();
		ImageURLList result = gson.fromJson(response, ImageURLList.class);

		List<String> before = new ArrayList<String>();
		if(Session.getGalleryImageURLSList() != null) {
			before.addAll(Session.getGalleryImageURLSList());
		}
		
		Session.setGalleryImageURLSList(result.getImageURLs());
		
		List<String> after = Session.getGalleryImageURLSList();
		if(before.containsAll(after)) {
			return false;
		}
		return true;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if(result) {
			GetGalleryImagesAsyncTask imageGetter = new GetGalleryImagesAsyncTask(activity, hostURI);
			imageGetter.execute();
		}
		else {
			Session.dismissAndMakeNullProgressDialog();
			
			Intent i = new Intent(activity, ActivityLevel1Gallery.class);
			activity.startActivity(i);
		}
	}

}
