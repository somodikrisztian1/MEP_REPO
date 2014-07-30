package hu.mep.mep_app;

import hu.mep.datamodells.Session;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class NotificationService extends Service {

	private String TAG = "NotiFicationService";
	private NotiRunnable notiRunnable;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
//		Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
		
		Log.e(TAG, "onCreate");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.e(TAG, "onStart");
//		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();

		if (intent != null && intent.getExtras() != null) {

			Log.e(TAG, "onStartCommand, getExtra: "
					+ intent.getExtras().getInt("mepId"));
			notiRunnable = new NotiRunnable(intent.getExtras().getInt("mepId"),
					this);
		}

		Thread thread = new Thread(notiRunnable);
		thread.start();
		notiRunnable.resume();
	}

	@Override
	public void onDestroy() {
		notiRunnable.stop();

//		Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
		Log.e(TAG, "onDestroy");
	}

	// példa noti-ra (ctrl + c)
	// public void createNotification(long when, String notificationTitle,
	// String notificationContent, String notificationUrl, Context ctx) {
	// try {
	// Intent notificationIntent;
	// if ("".equals(notificationTitle)) {
	// notificationTitle = ctx.getResources().getString(
	// R.string.app_name);
	// }
	// /* large icon for notification,normally use App icon */
	// Bitmap largeIcon = BitmapFactory.decodeResource(ctx.getResources(),
	// R.drawable.mep_logo);
	// int smalIcon = R.drawable.mep_logo;
	// /*
	// * create intent for show notification details when user clicks
	// * notification
	// */
	// if (!"".equals(notificationUrl)) {
	// notificationIntent = new Intent(Intent.ACTION_VIEW,
	// Uri.parse(notificationUrl));
	// } else {
	// // Intent to load Pin.class
	// notificationIntent = new Intent(this, ActivityLevel1.class);
	// }
	// /*
	// * create new task for each notification with pending intent so we
	// * set Intent.FLAG_ACTIVITY_NEW_TASK
	// */
	// PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0,
	// notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
	//
	// /*
	// * get the system service that manage notification
	// * NotificationManager
	// */
	// NotificationManager notificationManager = (NotificationManager) ctx
	// .getSystemService(Context.NOTIFICATION_SERVICE);
	//
	// /* build the notification */
	// NotificationCompat.Builder notificationBuilder = new
	// NotificationCompat.Builder(
	// ctx).setWhen(when).setContentText(notificationContent)
	// .setContentTitle(notificationTitle).setSmallIcon(smalIcon)
	// .setAutoCancel(true).setTicker(notificationTitle)
	// .setLargeIcon(largeIcon).setContentIntent(pendingIntent);
	//
	// /*
	// * sending notification to system.Here we use unique id (when)for
	// * making different each notification if we use same id,then first
	// * notification replace by the last notification
	// */
	// notificationManager.notify((int) when, notificationBuilder.build());
	// } catch (Exception e) {
	// Log.e("NotificationManager",
	// "createNotification::" + e.getMessage());
	// }
	// }

}
