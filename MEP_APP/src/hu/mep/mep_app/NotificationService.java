package hu.mep.mep_app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {
	
//	private String TAG = "NotiFicationService";
	private NotiRunnable notiRunnable;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// Log.e(TAG, "onCreate");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// Log.e(TAG, "onStart");

		if (intent != null && intent.getExtras() != null
				&& notiRunnable == null) {

//			Log.e(TAG, "onStart, getExtra: " + intent.getExtras().getInt("mepId"));
			
			notiRunnable = new NotiRunnable(intent.getExtras().getInt("mepId"),
					this);
		}

		Thread thread = new Thread(notiRunnable);
		thread.start();
	}

	@Override
	public void onDestroy() {
		notiRunnable.stop();
		notiRunnable = null;

		super.onDestroy();
		// Log.e(TAG, "onDestroy");
	}
}
