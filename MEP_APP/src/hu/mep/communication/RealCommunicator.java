package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.Place;
import hu.mep.datamodells.PlaceList;
import hu.mep.datamodells.User;
import hu.mep.datamodells.Session;
import hu.mep.utils.ChatContactListDeserializer;
import hu.mep.utils.PlaceListDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Formatter.BigDecimalLayoutForm;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
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
		HashMap<String, String> post = new HashMap<String, String>();
		String data = null;
		try {
			String link = "iphonelogin_do.php?username=" + username
					+ "&password=" + encodePasswordWithMD5(password);
			data = httpPost(link, post);

		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(PlaceList.class, new PlaceListDeserializer());
		Gson gson = gsonBuilder.create();
		User newUser = gson.fromJson(data, User.class);
		Session.getInstance().setActualUser(newUser);
		downloadProfilePictureForActualUser();
	}
	
	private String encodePasswordWithMD5(String originalPassword) {
		
		final String MD5 = "MD5"; 
		try {
			MessageDigest digest = java.security.MessageDigest .getInstance(MD5);
			digest.update(originalPassword.getBytes()); 
			byte messageDigest[] = digest.digest(); 
			
			StringBuilder hexString = new StringBuilder(); 
			for (byte aMessageDigest : messageDigest) { 
				String h = Integer.toHexString(0xFF & aMessageDigest); 
				while (h.length() < 2) 
					h= "0" + h; 
					hexString.append(h); 
				} 
			return hexString.toString(); 
			} 
		catch (NoSuchAlgorithmException e) 
		{ 
			e.printStackTrace(); 
		} 
		return "";
	}	
	
	@Override
	public void getChatPartners() {
		HashMap<String, String> post = new HashMap<String, String>();

		String data = null;

		try {
			data = httpPost("ios_getContactList.php?userId="
					+ Session.getInstance().getActualUser().getMepID(), post);
			
			Log.e(TAG, MainURL + "ios_getContactList.php?userId=" +
					Session.getInstance().getActualUser().getMepID());
			
			Log.d(TAG, "getChatPartners() ==>" + data);
				} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
	
	GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(ChatContactList.class, new ChatContactListDeserializer());
	Gson gson = gsonBuilder.create();
	ChatContactList contacts = gson.fromJson(data, ChatContactList.class);
	Session.getInstance().setActualChatContactList(contacts);
	
	for (ChatContact actContact : Session.getInstance().getActualChatContactList().getContacts()) {
		Log.e("CHATTÁRSAK!", actContact.getName());
		Log.e("CHATTÁRSAK!", "Kép letöltése...");
		downloadProfilePictureForChatContact(actContact);
	}
	
	
	}
	
	public void downloadProfilePictureForActualUser() {
	    try {
	        
	        HttpURLConnection connection = (HttpURLConnection) Session.getInstance().getActualUser().getImageURL().openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Session.getInstance().getActualUser().setProfilePicture(BitmapFactory.decodeStream(input));

	    } catch (IOException e) {
	        e.printStackTrace();
	        Log.e("getBmpFromUrl error: ", e.getMessage().toString());
	        return;
	    }
	    return;
	}
	
	public void downloadProfilePictureForChatContact(ChatContact contact) {
	    try {
	    	Bitmap bmp;
	        URL imgURL = new URL(contact.getImageURL());
	        HttpURLConnection connection = (HttpURLConnection) imgURL.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        bmp = BitmapFactory.decodeStream(input);
	        int fixSize = ( bmp.getWidth() < bmp.getHeight() ? bmp.getWidth() : bmp.getHeight()); //Megnézzük, álló vagy fekvő tájolású-e.
	        bmp = Bitmap.createBitmap(bmp, 0, 0, fixSize, fixSize);	//A rövidebb oldal szerint vágunk egy nagy négyzetre.
	        bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);	//Skálázás 200×200-as négyzetre.
	        contact.setProfilePicture(bmp);

	    } catch (IOException e) {
	        e.printStackTrace();
	        Log.e("getBmpFromUrl error: ", e.getMessage().toString());
	        return;
	    }
	    return;
	}
	
	
	
}
