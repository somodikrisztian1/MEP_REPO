package hu.mep.communication;

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

import hu.mep.datamodells.Session;
import android.content.Context;
import android.os.AsyncTask;

public class SendChatMessageAsyncTask extends AsyncTask<Void, Void, Void> {

	String hostURI;
	String resourceURI;
	String fullURI;
	Context context;
	HashMap<String, String> postDatas;

	public SendChatMessageAsyncTask(Context context, String hostURI, HashMap<String, String> postDatas) {
		this.hostURI = hostURI;
		this.context = context;
		this.postDatas = postDatas;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		resourceURI = "ios_storeMessage.php";
		fullURI = hostURI + resourceURI;
		
	}

	@Override
	protected Void doInBackground(Void... params) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		HttpClient httpclient = new DefaultHttpClient();
		Iterator it = postDatas.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			nameValuePairs.add(new BasicNameValuePair((String) pairs.getKey(),
					(String) pairs.getValue()));
		}

		HttpPost httppost = new HttpPost(fullURI);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpclient.execute(httppost);
			String data = new BasicResponseHandler().handleResponse(response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}	
}
