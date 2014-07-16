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
import android.util.Log;

public class RealCommunicator implements ICommunicator {

	private static final String TAG = "RealCommunicator.java";
	private static HttpClient httpclient = new DefaultHttpClient();
	Context context;
	final static String MainURL = "http://www.megujuloenergiapark.hu/";

	private static RealCommunicator instance = null;

	private RealCommunicator(Context context) {
		this.context = context; // szükséges az AsyncTaskokhoz
	}

	public static synchronized RealCommunicator getInstance(Context ctx) {
		if (instance == null) {
			instance = new RealCommunicator(ctx);
		}

		return instance;
	}

	public static String httpPost(String resourceURI, HashMap<String, String> post)
			throws ClientProtocolException, IOException {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		Iterator it = post.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			nameValuePairs.add(new BasicNameValuePair((String) pairs.getKey(),
					(String) pairs.getValue()));
		}

		HttpPost httppost = new HttpPost(MainURL + resourceURI);
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
	public void getGalleryURLsAndPictures() {
		GetGalleryURLsAsyncTask urlGetter = new GetGalleryURLsAsyncTask(MainURL, context);
		GetGalleryImagesAsyncTask imageGetter = new GetGalleryImagesAsyncTask(MainURL, context);
		try {
			urlGetter.execute().get();
			imageGetter.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void getChatMessages() {

		GetChatMessagesListAsyncTask getMessagesAsyncTask = new GetChatMessagesListAsyncTask(MainURL);

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

	@Override
	public void authenticateUser(String username, String password) {

		AuthenticationAsyncTask authenticationAsyncTask = new AuthenticationAsyncTask(username, password, MainURL);
		try {
			authenticationAsyncTask.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getChatPartners() {
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

		postDatas.put("userId", "" + Session.getActualUser().getMepID());
		postDatas.put("toId", "" + Session.getActualChatPartner().getUserID());
		postDatas.put("msg", messageText);

		SendChatMessageAsyncTask sendChatMessage = new SendChatMessageAsyncTask(
				context, MainURL, postDatas);
		try {
			sendChatMessage.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
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
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void getActualChart(Calendar beginDate, Calendar endDate) {
		GetChartAsyncTask getChart = new GetChartAsyncTask(context, MainURL, beginDate, endDate);
		try {
			getChart.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void registrateUser(String fullName, String email, String userName, String password)
	{
		HashMap<String, String> postDatas = new HashMap<String, String>();
		
		postDatas.put("name", "" + fullName);
		postDatas.put("username", "" + userName);
		postDatas.put("password", password);
		postDatas.put("email", email);

		RegistrationAssyncTask sendRegistration = new RegistrationAssyncTask(MainURL, postDatas);
		try {
			sendRegistration.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return;
	}

	

}
