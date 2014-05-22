package hu.mep.communication;

import hu.mep.datamodells.User;

import java.io.IOException;
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
import org.json.JSONObject;

import android.util.Log;

public class RealCommunicator implements ICommunicator {

	HttpClient httpclient;
	final String MainURL = "http://xx.yy.zz.ww/";

	private static RealCommunicator instance = null;

	public RealCommunicator() {
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
	public User authenticateUser(String username, String password) {
		Log.d("AUTHENTICATE - USERNAME", username);
		Log.d("AUTHENTICATE - PASSWORD", password);
		HashMap<String, String> post = new HashMap<String, String>();
		// post.put("action", "GET");
		// post.put("UserName", username);
		// post.put("Password", password);

		try {
			String data = httpPost("user.php", post);
			JSONArray jsonArray = new JSONArray(data);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			int user_id = Integer.parseInt(jsonObject.getString("id"));
			String nick_name2 = jsonObject.getString("nick_name");
			String password2 = jsonObject.getString("password");
			String email = jsonObject.getString("email");
			int sex = Integer.parseInt(jsonObject.getString("sex"));
			String birthday = jsonObject.getString("birthday");
			int type = Integer.parseInt(jsonObject.getString("type"));

			User newUser = new User(/* jön ide valami, de mi!? :D */);
			return newUser;
		} catch (Exception e) {
			Log.e(getClass().getName(), "authenticateUser() failed!!!");
			return null;
		}

	}
}
