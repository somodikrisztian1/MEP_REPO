package hu.mep.communication;

import hu.mep.datamodells.Session;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonElement;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class RegistrationAssyncTask extends AsyncTask<Void, Void, Void> {

	String hostURI;
	String resourceURI;
	String fullURI;
	HashMap<String, String> postDatas;

	public RegistrationAssyncTask(String hostURI, HashMap<String, String> postDatas) {
		this.hostURI = hostURI;
		this.postDatas = postDatas;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		resourceURI = "ios_reg.php";
		fullURI = hostURI + resourceURI;
	}

	@Override
	protected Void doInBackground(Void... params) {
		String response = "";
		
		try {
			response = RealCommunicator.httpPost(resourceURI, postDatas);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			Log.e("RESPONSE FOR REGISTRATION:", response);
			JSONObject jsonObject = new JSONObject(response);
			JSONObject root =  jsonObject.getJSONObject("reg_status");
			if(root.getInt("status") == 1 ) {
				Session.setSuccessfulRegistration(true);
			}
			else {
				String errorMessage = root.getString("error");
				Session.setSuccessfulRegistration(false);
				Session.setUnsuccessfulRegistrationMessage(errorMessage);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
