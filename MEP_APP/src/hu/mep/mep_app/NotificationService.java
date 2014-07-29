package hu.mep.mep_app;

import hu.mep.mep_app.activities.ActivityLevel1;

import java.util.Calendar;

import org.afree.chart.plot.dial.DialPointer.Pin;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class NotificationService extends Service {

	private String TAG = "Noti Service";
	
	private NotiRunnable notiRunnable;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		notiRunnable = new NotiRunnable();
		
		Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_SHORT)
				.show();
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_SHORT).show();

//		createNotification(Calendar.getInstance().getTimeInMillis()," my app","click to load Pin.class","",getApplicationContext());
		notiRunnable.run();
		
		
		Log.d(TAG, "onStart");
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "MyService Stopped", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onDestroy");
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
				notificationIntent = new Intent(this, ActivityLevel1.class);
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
