package hu.mep.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import hu.mep.datamodells.PlaceList;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.User;
import hu.mep.utils.deserializers.PlaceListDeserializer;
import hu.mep.utils.deserializers.UserDeserializer;
import hu.mep.utils.others.MD5Encoder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class AuthenticationAsyncTask extends AsyncTask<Void, Void, Void> {

	private static final String TAG = "AuthenticationAsyncTask";

	private String hostURI;
	private String resourceURI;
	private String username;
	private String password;
	private static String fullURI;

	public AuthenticationAsyncTask(String username, String password,
			String hostURI) {
		this.username = username;
		this.password = password;
		this.hostURI = hostURI;
	}

	@Override
	protected void onPreExecute() {
		Log.e(TAG, "onPreExecute");
		resourceURI = "iphonelogin_do.php?username=" + username + "&password="
				+ MD5Encoder.encodePasswordWithMD5(password);
		fullURI = hostURI + resourceURI;
	}

	@Override
	protected Void doInBackground(Void... nothing) {
		Log.e(TAG, "doInBackground begin...");
		String response = "";

		// Log.e("fullURI is: ", fullURI);
		response = RealCommunicator.dohttpGet(fullURI);
		
		Log.e("response:", response);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
		Gson gson = gsonBuilder.create();
		User newUser = gson.fromJson(response, User.class);

		Session.setActualUser(newUser);
		if (newUser != null) {
			downloadProfilePictureForActualUser();
		}
		Log.e(TAG, "doInBackground finished...");
		return null;
	}

	private void downloadProfilePictureForActualUser() {
		try {

			HttpURLConnection connection = (HttpURLConnection) Session
					.getActualUser().getImageURL().openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Session.getActualUser().setProfilePicture(
					BitmapFactory.decodeStream(input));

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return;
		}
		return;
	}

	/*@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Log.e(TAG, "onPostExecute");
		//Session.dismissAndMakeNullProgressDialog();
		return;
	}
*/
}
