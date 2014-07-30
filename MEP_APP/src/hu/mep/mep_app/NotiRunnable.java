package hu.mep.mep_app;

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

	private Boolean running = true;
	private Boolean notiShown = false;
	private String TAG = "NotiRunnable";
	private long WAIT_TIME = 5000L;
	private String responseFromUnreadMessagesPHP = "";
	private int mepId = 0;
	private Context context;
	public static int gotNewMessageNotificationID = 1;
	public static int remoteNotificationID = 2;

	public void stop() {
		Log.e(TAG, "stop()");

		this.running = false;
	}

	public void resume() {
		this.running = true;
	}

	public NotiRunnable(int newMepId, Context newContext) {
		super();
		this.mepId = newMepId;
		this.context = newContext;
	}

	@Override
	public void run() {

		while (true) {
			if (this.running) {
				Log.e(TAG, "__________NotiRunnable.run() ==> true__________");

				// ha be van jelentkezve
				if (this.mepId != 0) {
					Log.e(TAG, "noti, user logged in");

					getRemotes(Integer.toString(mepId));

					if (!isNotificationVisible(gotNewMessageNotificationID)
							&& gotWrongRemotes() && notiShown == false) {
						createNotification(
								Calendar.getInstance().getTimeInMillis(),
								"MepApp",
								"Jelenleg nincs internet kapcsolata a távfelügyeleti rendszernek. Kérjük a részletekért lépjen be az alkalmazásba.",
								"", context, gotNewMessageNotificationID);
						notiShown = true;

					} else {
						notiShown = false;
					}
				}

				try {
					Thread.sleep(WAIT_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected String getRemotes(String dataToSend) {
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

		Log.e("response",
				"response: " + responseFromUnreadMessagesPHP.toString());

		return responseFromUnreadMessagesPHP;
	}

	public Boolean gotWrongRemotes() {
		int count = 0;
		try {
			if (responseFromUnreadMessagesPHP.compareTo("[]") != 0) {
				JSONObject json = new JSONObject(
						responseFromUnreadMessagesPHP.trim());
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

		Log.e(TAG, "gotWrongRemotes: " + Integer.toString(count));

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
					ctx).setWhen(when).setContentText(notificationContent)
					.setContentTitle(notificationTitle).setSmallIcon(smalIcon)
					.setAutoCancel(true).setTicker(notificationTitle)
					.setLargeIcon(largeIcon).setContentIntent(pendingIntent);

			/*
			 * sending notification to system.Here we use unique id (when)for
			 * making different each notification if we use same id,then first
			 * notification replace by the last notification
			 */
			notificationManager.notify(notificationID,
					notificationBuilder.build());
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

		return test != null;
	}

}
