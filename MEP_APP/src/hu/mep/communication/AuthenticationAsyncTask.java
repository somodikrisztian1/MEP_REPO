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
import hu.mep.utils.others.MD5Encoder;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class AuthenticationAsyncTask extends AsyncTask<Void, Void, String>{

	private String hostURI;
	private String resourceURI;
	private Context context;
	private String username;
	private String password;
	
	public AuthenticationAsyncTask(Context context, String username, String password, String hostURI) {
		this.context = context;
		this.username = username;
		this.password = password;
		this.hostURI = hostURI;
	}
	
	
	@Override
	protected void onPreExecute() {
		resourceURI = "iphonelogin_do.php?username=" + username
				+ "&password=" + MD5Encoder.encodePasswordWithMD5(password);
	}
	
	@Override
	protected String doInBackground(Void... nothing) {
		String response = "";
		String fullURI = hostURI + resourceURI;
		//Log.e("fullURI is: ", fullURI);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(fullURI);
		try {
			HttpResponse execute = client.execute(httpGet);
			InputStream content = execute.getEntity().getContent();

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					content));
			String s = "";
			while ((s = buffer.readLine()) != null) {
				response += s;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// Log.e("response:", response);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PlaceList.class,
				new PlaceListDeserializer());
		Gson gson = gsonBuilder.create();
		User newUser = gson.fromJson(response, User.class);
		Session.getInstance(context).setActualUser(newUser);
		downloadProfilePictureForActualUser();
		
		return "";
	}

	private void downloadProfilePictureForActualUser() {
		try {

			HttpURLConnection connection = (HttpURLConnection) Session
					.getInstance(context).getActualUser().getImageURL()
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Session.getInstance(context).getActualUser()
					.setProfilePicture(BitmapFactory.decodeStream(input));

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return;
		}
		return;
	}
	
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Itt kell majd elindítani az ActivityLevel2-t.
		super.onPostExecute(result);
	}
	
}
