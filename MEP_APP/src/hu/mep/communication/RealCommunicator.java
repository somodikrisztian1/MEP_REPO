package hu.mep.communication;

import hu.mep.datamodells.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;

public class RealCommunicator implements ICommunicator {

	private static final String TAG = "RealCommunicator.java";
	HttpClient httpclient;
	Context context;
	final String MainURL = "http://www.megujuloenergiapark.hu/";

	private static RealCommunicator instance = null;

	private RealCommunicator(Context context) {
		this.httpclient = new DefaultHttpClient(); // szükségtelen lesz az
													// Asynctaskok után
		this.context = context; // szükséges az AsyncTaskokhoz
	}

	public static synchronized RealCommunicator getInstance(Context ctx) {
		if (instance == null) {
			instance = new RealCommunicator(ctx);
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
	
	public static String dohttpGet(String fullURI) {
		String response = "";
		
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
		return response;
	}

	@Override
	public void getChatMessages() {

		GetChatMessagesListAsyncTask getMessagesAsyncTask = new GetChatMessagesListAsyncTask(
				context, MainURL);

		try {
			getMessagesAsyncTask.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		/* Az aktuális üzenetlista kiíratása... tesztkiíratás */
		/*
		 * Log.e("RealCommunicator.getChatMessages()","Minden bejövő üzenet:");
		 * for (ChatMessage actMessage :
		 * Session.getInstance(context).getChatMessagesList
		 * ().getChatMessagesList()) {
		 * Log.e("RealCommunicator.getChatMessages()", actMessage.toString()); }
		 */
	}

	// TODO! Az internet kapcsolat ellenőrzést megoldani.
	@Override
	public void authenticateUser(Activity act, String username, String password) {

		if (NetThread.isOnline(context)) {
			// Log.e("RealCommunicator", "ONLINE!!!!!!!!!");
		} else {
			// Log.e("RealCommunicator", "OFFLINE!!!!!!!!!");
		}
		AuthenticationAsyncTask authenticationAsyncTask = new AuthenticationAsyncTask(act,
				context, username, password, MainURL);
		try {
			authenticationAsyncTask.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		/* Log.e("RealCommunicator.authenticateUser()", "finished"); */

	}

	@Override
	public void getChatPartners() {
		/*GetContactListAsyncTask getContactListAsyncTask = new GetContactListAsyncTask(
				context, MainURL);*/
		GetContactListAsyncTaskNEW getContactListAsyncTask = new GetContactListAsyncTaskNEW(context, MainURL);
		getContactListAsyncTask.execute();
	}

	@Override
	public void getTopicList() {
		GetTopicListAsyncTask getTopicListAsyncTask = new GetTopicListAsyncTask(
				context, MainURL);
		getTopicListAsyncTask.execute();

	}

	@Override
	public void sendChatMessage(String messageText) {
		HashMap<String, String> postDatas = new HashMap<String, String>();
		Date date;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		postDatas.put("userId", "" + Session.getActualUser().getMepID());
		postDatas.put("toId", "" + Session.getActualChatPartner().getUserID());
		postDatas.put("msg", messageText);
		postDatas.put("date",
				dateFormat.format(Calendar.getInstance().getTime()));

		SendChatMessageAsyncTask sendChatMessage = new SendChatMessageAsyncTask(
				context, MainURL, postDatas);
		try {
			sendChatMessage.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void getChartNames() {
		GetAllChartInfoContainerAsyncTask chartNameGetter = new GetAllChartInfoContainerAsyncTask(
				context, MainURL);
		try {
			chartNameGetter.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getActualChart() {
		GetChartAsyncTask getChart = new GetChartAsyncTask(context, MainURL);
		try {
			getChart.execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
