package hu.mep.mep_app;

import hu.mep.datamodells.Place;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel1;

import java.io.IOException;
import java.util.Calendar;
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

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotiRunnable implements Runnable {
	private String TAG = "NotiRunnable";

	private Context context;
	private Boolean running = true;
	private long WAIT_TIME = 60000L;

	private int mepId = 0;

	public static int gotNewMessageNotificationID = 1;
	public static int remoteNotificationID = 2;

	private int today;
	private Calendar calendar;

	private String responseFromUnreadMessagesPHP = "";
	private String responseFromWrongRemotes = "";

	public void stop() {
		Log.e(TAG, "stop()");
		this.running = false;
	}

	public void setMepId(int newId) {
		this.mepId = newId;
	}

	public Boolean isRunning() {
		return this.running;
	}

	public NotiRunnable(int newMepId, Context newContext) {
		super();
		this.mepId = newMepId;
		this.context = newContext;
	}

	@Override
	public void run() {
		while (this.running == true) {
			calendar = Calendar.getInstance();
			Log.e(TAG, "__________NotiRunnable.run() ==> true__________");
			// Log.e(TAG,
			// "calendar, day (int): "
			// + calendar.get(Calendar.DAY_OF_MONTH));
			// Log.e(TAG, "today (int): " + today);

			// távfelügyeletekre:
			if (this.mepId != 0 && today != calendar.get(Calendar.DAY_OF_MONTH)
					&& !isNotificationVisible(remoteNotificationID)) {

				Log.e(TAG, "remoteNoti, user logged in, mepId: " + mepId);

				getRemotes(Integer.toString(mepId));

				if (gotWrongRemotes()) {
					try {
						createNotification(
								Calendar.getInstance().getTimeInMillis(),
								"Távfelügyelet",
								"Jelenleg nincs internet kapcsolata a távfelügyeleti rendszernek. Kérjük a részletekért lépjen be az alkalmazásba.",
								"", context, remoteNotificationID);
					} catch (Exception e) {
						Log.e(TAG, e.toString());
					}

				}

				today = calendar.get(Calendar.DAY_OF_MONTH);
			}

			// üzenetekre:
			if (this.mepId != 0
					&& !isNotificationVisible(gotNewMessageNotificationID)) {

				Log.e(TAG, "messageNoti, user logged in, mepId: " + mepId);

				getUnreadMessages(Integer.toString(mepId));

				if (gotUnreadMsg()) {
					try {
						createNotification(Calendar.getInstance()
								.getTimeInMillis(), "Új üzenet",
								"Új üzenete érkezett.", "", context,
								gotNewMessageNotificationID);
					} catch (Exception e) {
						Log.e(TAG, e.toString());
					}

				}
			}

			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// rossz távf. lekérése
	private void getRemotes(String dataToSend) {
		Log.e(TAG, "getRemotes, userId:  " + dataToSend);
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://www.megujuloenergiapark.hu/ios_getHibasTf.php?userId="
						+ dataToSend + "&type=daily");

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
				responseFromWrongRemotes = EntityUtils.toString(responseEntity);
			} else {
				responseFromWrongRemotes = "{\"NO DATA:\"NO DATA\"}";
			}
		} catch (ClientProtocolException e) {
			responseFromWrongRemotes = "{\"ERROR\":"
					+ e.getMessage().toString() + "}";
		} catch (IOException e) {
			responseFromWrongRemotes = "{\"ERROR\":"
					+ e.getMessage().toString() + "}";
		}

		// Log.e(TAG, "response: " + responseFromWrongRemotes.toString());

		// return responseFromUnreadMessagesPHP;
	}

	// van-e a rossz távf.
	public Boolean gotWrongRemotes() {
		Log.e(TAG, responseFromWrongRemotes);
		/*
		 try {
			if (responseFromWrongRemotes.compareTo("[]") != 0) {
				JSONObject json = new JSONObject(
						responseFromWrongRemotes.trim());
				Iterator<?> keys = json.keys();

				while (keys.hasNext()) {
					String key = (String) keys.next();

					if (json.get(key) instanceof JSONObject) {
						if (((JSONObject) json.get(key)).get("notify")
								.toString().compareTo("1") == 0) {
							count++;
						}
					}

				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(TAG, "gotWrongRemotes catch: " + e.toString());
		}
		 */
		// Log.e(TAG, "gotWrongRemotes: " + Integer.toString(count));

		if(Session.getActualUser().getUsersPlaces().getPlaces() != null)
		for (Place actPlace : Session.getActualUser().getUsersPlaces().getPlaces()) {
			if(actPlace.isNotify()) {
				Log.e(TAG, "TALÁLTUNK EGY NEM MŰKÖDŐ HELYET!!!!");
				return true;
			}
		}
		return false;
	}
	
	// olvasatlan üzenetek lekérése
	private void getUnreadMessages(String dataToSend) {
		Log.e(TAG, "getUnreadMessages, userId:  " + dataToSend);

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://www.megujuloenergiapark.hu/ios_getContactList.php?userId="
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
				responseFromUnreadMessagesPHP = EntityUtils
						.toString(responseEntity);
			} else {
				responseFromUnreadMessagesPHP = "{\"NO DATA:\"NO DATA\"}";
			}
		} catch (ClientProtocolException e) {
			responseFromUnreadMessagesPHP = "{\"ERROR\":"
					+ e.getMessage().toString() + "}";
		} catch (IOException e) {
			responseFromUnreadMessagesPHP = "{\"ERROR\":"
					+ e.getMessage().toString() + "}";
		}

		// Log.e(TAG, "response: " + responseFromUnreadMessagesPHP.toString());
	}

	// van-e olvasatlan üzenet
	private Boolean gotUnreadMsg() {
		int count = 0;
		try {
			if (responseFromUnreadMessagesPHP.compareTo("[]") != 0) {
				JSONObject json = new JSONObject(
						responseFromUnreadMessagesPHP.trim());
				Iterator<?> keys = json.keys();

				while (keys.hasNext()) {
					String key = (String) keys.next();

					if (json.get(key) instanceof JSONObject) {
						if (((JSONObject) json.get(key))
								.get("vanOlvasatlanTole").toString()
								.compareTo("1") == 0) {
							count++;
						}
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			// Log.e(TAG, "gotUnreadMsg catch: " + e.toString());
		}

		if (count > 0)
			return true;
		else
			return false;
	}

	public void createNotification(long when, String notificationTitle,
			String notificationContent, String notificationUrl, Context ctx,
			int notificationID) {
		try {
			Intent notificationIntent;
			if ("".equals(notificationTitle)) {
				notificationTitle = ctx.getResources().getString(
						R.string.app_name);
			}
			/* large icon for notification,normally use App icon */
			Bitmap largeIcon = BitmapFactory.decodeResource(ctx.getResources(),
					R.drawable.mep_logo);
			int smalIcon = R.drawable.mep_logo;
			/*
			 * create intent for show notification details when user clicks
			 * notification
			 */
			if (!"".equals(notificationUrl)) {
				notificationIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(notificationUrl));
			} else {
				// Intent to load Pin.class
				notificationIntent = new Intent(ctx, ActivityLevel1.class);
			}
			/*
			 * create new task for each notification with pending intent so we
			 * set Intent.FLAG_ACTIVITY_NEW_TASK
			 */
			PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0,
					notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

			/*
			 * get the system service that manage notification
			 * NotificationManager
			 */
			NotificationManager notificationManager = (NotificationManager) ctx
					.getSystemService(Context.NOTIFICATION_SERVICE);

			/* build the notification */
			NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
					ctx)
			.setWhen(when)
			.setContentText(notificationContent)
			.setContentTitle(notificationTitle)
			.setSmallIcon(smalIcon)
			.setAutoCancel(true)
			.setLargeIcon(largeIcon)
			.setContentIntent(pendingIntent);

			/*
			 * sending notification to system.Here we use unique id (when)for
			 * making different each notification if we use same id,then first
			 * notification replace by the last notification
			 */

			notificationManager.notify(notificationID,
					notificationBuilder.build());
			
			Log.e(TAG, "noti created");
		} catch (Exception e) {
			Log.e(TAG,
					"NotificationManager ==> createNotification::"
							+ e.getMessage());
		}
	}

	private boolean isNotificationVisible(int id) {
		Intent notificationIntent = new Intent(context, ActivityLevel1.class);
		PendingIntent test = PendingIntent.getActivity(context, id,
				notificationIntent, PendingIntent.FLAG_NO_CREATE);

		// if (test != null) {
		// Log.e(TAG, "test != null");
		// } else {
		// Log.e(TAG, "test == null");
		// }

		return test != null;
	}

}
