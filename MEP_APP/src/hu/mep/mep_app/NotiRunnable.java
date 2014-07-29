package hu.mep.mep_app;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel1;

import java.util.Calendar;

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

	private NotiAsyncTask doBackgroundTask;

	public void stop() {
		this.running = false;
	}

	public void resume() {
		this.running = true;
	}

	@Override
	public void run() {

		while (this.running) {
			Log.e(TAG, "running run ()");

			// ha be van jelentkezve
			if ( Session.getActualUser() != null ) {
				Log.e(TAG, "noti, user logged in ______________________________");
				
				doBackgroundTask = new NotiAsyncTask();
				doBackgroundTask.execute(Integer.toString(Session
						.getActualUser().getMepID()));

				if (gotWrongRemote() && notiShown == false) {
					createNotification(
							Calendar.getInstance().getTimeInMillis(),
							"MepApp", "Nem logol az egyik távfelügyelet!!", "", Session.getContext());
					notiShown = true;
					Log.e(TAG, "notishown");

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

	public Boolean gotWrongRemote() {
		try {
			return doBackgroundTask.gotWrongRemotes();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return false;
		}
	}

	public void createNotification(long when, String notificationTitle,
			String notificationContent, String notificationUrl, Context ctx) {
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
			notificationManager.notify((int) when, notificationBuilder.build());
		} catch (Exception e) {
			Log.e("NotificationManager",
					"createNotification::" + e.getMessage());
		}

	}

}
