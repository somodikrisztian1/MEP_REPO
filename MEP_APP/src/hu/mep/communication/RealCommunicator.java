package hu.mep.communication;

import hu.mep.datamodells.Place;
import hu.mep.datamodells.User;
import hu.mep.datamodells.Session;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RealCommunicator implements ICommunicator {

	private static final String TAG = "RealCommunicator.java";
	HttpClient httpclient;
	final String MainURL = "http://www.megujuloenergiapark.hu/";

	private static RealCommunicator instance = null;

	private RealCommunicator() {
		httpclient = new DefaultHttpClient();
	}

	public static synchronized RealCommunicator getInstance() {
		if (instance == null) {
			instance = new RealCommunicator();
		}
		return instance;
	}

	public String httpPost(String file, HashMap<String, String> post)
			throws ClientProtocolException, IOException {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		Iterator it = post.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			nameValuePairs.add(new BasicNameValuePair((String) pairs.getKey(),
					(String) pairs.getValue()));
		}

		HttpPost httppost = new HttpPost(MainURL + file);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		HttpResponse response = httpclient.execute(httppost);
		String data = new BasicResponseHandler().handleResponse(response);
		return data;
	}

	@Override
	public void authenticateUser(String username, String password) {
		Log.d("AUTHENTICATE - USERNAME", username);
		Log.d("AUTHENTICATE - PASSWORD", password);
		HashMap<String, String> post = new HashMap<String, String>();
		//post.put("action", "GET");
		post.put("username", username);
		post.put("password", password);
		
		String data = null;
		
		
		try {
				data = httpPost("iphonelogin_do.php", post);
				} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//JSONArray jsonArray = null;
			User newUser = null;
			try {
				//jsonArray = new JSONArray(data);
				JSONObject jsonObject = new JSONObject(data);
				
				int newMepID = Integer.parseInt(jsonObject.getString(User.mepIDTag));
				
				String newName = jsonObject.getString(User.nameTag);
				
				URL imageURL = new URL(jsonObject.getString(User.imageURLTag));
				
				int mekutINT = Integer.parseInt(jsonObject.getString(User.mekutTag));
				boolean mekut = (mekutINT != 0);
				
				int teacherINT = Integer.parseInt(jsonObject.getString(User.teacherTag));
				boolean teacher = (teacherINT != 0);
				
				int moderatorINT = Integer.parseInt(jsonObject.getString(User.moderatorTag));
				boolean moderator = (moderatorINT != 0);
				
				String placesString = jsonObject.getString(User.placesTag);

				int placesCount = jsonObject.getInt(User.placesCountTag);
				
				newUser = new User(newMepID, newName, imageURL, mekut, teacher, moderator, processPlacesFromJSON(placesCount, placesString));
				
				
			} catch (MalformedURLException e) {
					Log.e(TAG, "Malformed image URL in the JSON catched during authentication...");
					e.printStackTrace();
			} catch (JSONException e1) {
					Log.e(TAG, "Something happened while processing the JSON catched during authentication...");
				e1.printStackTrace();
			}

			Session.getInstance().setActualUser(newUser);
			return;
	}
	
	private ArrayList<Place> processPlacesFromJSON(int count,String data) {
		ArrayList<Place> result = new ArrayList<Place>();
		
		try {
			JSONObject allPlaces = new JSONObject(data);
			JSONObject actPlace = null;
			String newPlaceName;
			String newPlaceID;
			String newPlaceDescription;
			String newPlaceLocation;
			
			for(Integer i = 1; i <= count; ++i) {
				actPlace = new JSONObject(allPlaces.getString(i.toString()));
				newPlaceName = actPlace.getString(Place.nameTag);
				newPlaceID = actPlace.getString(Place.idTag);
				newPlaceDescription = actPlace.getString(Place.descriptionTag);
				newPlaceLocation = actPlace.getString(Place.locationTag);
				result.add(new Place(newPlaceName, newPlaceID, newPlaceDescription, newPlaceLocation));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	
}
