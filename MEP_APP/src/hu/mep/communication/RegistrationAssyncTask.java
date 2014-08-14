package hu.mep.communication;

import hu.mep.datamodells.Session;
import hu.mep.utils.others.AlertDialogFactory;
import hu.mep.utils.others.MD5Encoder;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class RegistrationAssyncTask extends AsyncTask<Void, Void, HashMap<String, String>> {

	Activity activity;
	String resourceURI;
	HashMap<String, String> postDatas;
	ProgressDialog pd;
	private static final String STATUS_TAG = "status";
	private static final String MESSAGE_TAG = "message";
	private static final String SUCCESS = "successful_registration";
	private static final String UNSUCCESS = "unsuccessful_registration";
	
	private static final String TAG = "RegistrationAssyncTask";
	
	
	public RegistrationAssyncTask(Activity activity, HashMap<String, String> postDatas) {
		this.activity = activity;
		this.postDatas = postDatas;
		
		this.pd = new ProgressDialog(activity);
		this.pd.setMessage("Adatok továbbítása a szerver felé...");
		this.pd.setCancelable(false);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Session.getInstance(activity).setProgressDialog(pd);
		Session.showProgressDialog();
		resourceURI = "ios_reg.php";
	}

	@Override
	protected HashMap<String, String> doInBackground(Void... params) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		String response = "";
		String notEncodedPassword = postDatas.get("password");
		postDatas.put("password", MD5Encoder.encodePasswordWithMD5(notEncodedPassword));
		
		Log.e(TAG, "not encoded: " + notEncodedPassword);
		Log.e(TAG, "encoded: " + postDatas.get("password"));
		
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
				result.put(STATUS_TAG, SUCCESS);
			}
			else {
				String errorMessage = root.getString("error");
				result.put(STATUS_TAG, UNSUCCESS);
				result.put(MESSAGE_TAG, errorMessage);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		postDatas.put("notEncodedPassword", notEncodedPassword);
		return result;
	}
	
	@Override
	protected void onPostExecute(HashMap<String, String> result) {
		super.onPostExecute(result);
		Session.dismissAndMakeNullProgressDialog();

		if(result.get(STATUS_TAG).equals(SUCCESS)) {
			Session.getActualCommunicationInterface().authenticateUser(activity, postDatas.get("username"), postDatas.get("notEncodedPassword"));
		}
		else if(result.get(STATUS_TAG).equals(UNSUCCESS)) {
			Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogWithText(activity, result.get(MESSAGE_TAG)));
			Session.showAlertDialog();
		}
	}
}
