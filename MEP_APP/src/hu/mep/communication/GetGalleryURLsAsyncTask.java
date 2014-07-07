package hu.mep.communication;

import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.ImageURLList;
import hu.mep.datamodells.Session;
import hu.mep.utils.deserializers.ChatContactListDeserializer;
import hu.mep.utils.deserializers.ImageURLDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.Context;
import android.os.AsyncTask;

public class GetGalleryURLsAsyncTask extends AsyncTask<Void, Void, Void> {

	String hostURI;
	String resourceURI;
	String fullURI;
	Context context;

	public GetGalleryURLsAsyncTask(String hostURI, Context context) {
		this.hostURI = hostURI;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		resourceURI = "ios_getGalleryUrls.php";
		fullURI = hostURI + resourceURI;
	}

	@Override
	protected Void doInBackground(Void... params) {

		String response = "";
		response = RealCommunicator.dohttpGet(fullURI);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ImageURLList.class,
				new ImageURLDeserializer());
		Gson gson = gsonBuilder.create();
		ImageURLList result = gson.fromJson(response, ImageURLList.class);

		Session.setGalleryImageURLSList(result.getImageURLs());

		return null;
	}

}
