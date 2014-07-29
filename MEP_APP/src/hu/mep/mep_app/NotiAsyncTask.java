package hu.mep.mep_app;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class NotiAsyncTask extends AsyncTask<String, String, String> {

	private int count = 0;
	private String response = "";

	@Override
	protected String doInBackground(String... params) {

		String dataToSend = params[0];
		Log.e("FROM STATS SERVICE DoBackgroundTask", dataToSend);
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://www.megujuloenergiapark.hu/ios_getHibasTf.php?userId="
						+ dataToSend);

		try {
			// httpPost.setEntity(new StringEntity(dataToSend, "UTF-8"));

			// Set up the header types needed to properly transfer JSON
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("Accept-Encoding", "application/json");
			httpPost.setHeader("Accept-Language", "en-US");

			// Execute POST
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity responseEntity = httpResponse.getEntity();
			if (responseEntity != null) {
				response = EntityUtils.toString(responseEntity);
			} else {
				response = "{\"NO DATA:\"NO DATA\"}";
			}
		} catch (ClientProtocolException e) {
			response = "{\"ERROR\":" + e.getMessage().toString() + "}";
		} catch (IOException e) {
			response = "{\"ERROR\":" + e.getMessage().toString() + "}";
		}

		// createNotification(Calendar.getInstance().getTimeInMillis(),
		// " my app",
		// "response", "", getApplicationContext());

		Log.e("response", "response: " + response.toString());

		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	public Boolean gotWrongRemotes() {
		try {
			JSONObject json = new JSONObject(response.trim());
			Iterator<?> keys = json.keys();

			while (keys.hasNext()) {
				String key = (String) keys.next();
				
				if (json.get(key) instanceof JSONObject) {
					if (((JSONObject) json.get(key)).get("notify").toString().compareTo("1") == 0 )
					{
						count++;
						
						Log.e("count", count + Integer.toString(count));
					}
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (count > 0)
			return true;
		else
			return false;
	}

}
