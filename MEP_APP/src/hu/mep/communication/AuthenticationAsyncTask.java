package hu.mep.communication;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.User;
import hu.mep.utils.deserializers.UserDeserializer;
import hu.mep.utils.others.AlertDialogFactory;
import hu.mep.utils.others.MD5Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuthenticationAsyncTask extends AsyncTask<Void, Void, Boolean> {

	private static final String TAG = "AuthenticationAsyncTask";

	private Activity activity;
	private String hostURI;
	private String resourceURI;
	private String username;
	private String password;
	private String fullURI;
	private ProgressDialog pd;

	public AuthenticationAsyncTask(Activity activity, String username,
			String password, String hostURI) {
		this.activity = activity;
		this.username = username;
		this.password = password;
		this.hostURI = hostURI;
		
		this.pd = new ProgressDialog(this.activity);
		this.pd.setCancelable(false);
		this.pd.setTitle("Kérem várjon!");
		this.pd.setMessage("Felhasználói adatok ellenőrzése folyamatban...");
		
	}

	@Override
	protected void onPreExecute() {

		Session.setProgressDialog(pd);
		Session.showProgressDialog();

		Log.e(TAG, "onPreExecute");
		resourceURI = "iphonelogin_do.php?username=" + username + "&password="
				+ MD5Encoder.encodePasswordWithMD5(password);
		fullURI = hostURI + resourceURI;
	}
	
	@Override
	protected Boolean doInBackground(Void... nothing) {
		Log.e(TAG, "doInBackground begin...");
		String response = "";
		
		response = RealCommunicator.dohttpGet(fullURI);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
		Gson gson = gsonBuilder.create();
		User newUser = gson.fromJson(response, User.class);

		Session.setActualUser(newUser);
		if (newUser != null) {
			downloadProfilePictureForActualUser();
			return true;
		}
		Log.e(TAG, "doInBackground finished...");
		return false;
	}

	private void downloadProfilePictureForActualUser() {
		try {

			HttpURLConnection connection = (HttpURLConnection) Session
					.getActualUser().getImageURL().openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Session.getActualUser().setProfilePicture(BitmapFactory.decodeStream(input));

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return;
		}
		return;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
		if(result) {
			Log.e(TAG, "onPostExecute -> successful login --> dismissDialog");
			Session.dismissAndMakeNullProgressDialog();
			
			ActivityLevel2PreloaderAsyncTask at = new ActivityLevel2PreloaderAsyncTask(activity);
			at.execute();
		} else {
			Log.e(TAG, "onPostExecute -> unsuccessful login --> alertDialog");
			Session.dismissAndMakeNullProgressDialog();
			Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForBadCredentials(activity));
			Session.showAlertDialog();
		}
		return;
	}

}
