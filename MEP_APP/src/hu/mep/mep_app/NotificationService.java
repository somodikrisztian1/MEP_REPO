package hu.mep.mep_app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {

	private NotiRunnable notiRunnable;
	private Thread thread;

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

		if (intent != null && intent.getExtras() != null) {

			// Log.e(TAG, "onStart, getExtra: " +
			// intent.getExtras().getInt("mepId"));

			if (notiRunnable != null && thread != null) {

				if (notiRunnable.isRunning())
					notiRunnable.setMepId(intent.getExtras().getInt("mepId"));
				else {
					initThreadWithRunnable(intent);
				}
			}
			else {
				initThreadWithRunnable(intent);
			}
		}
	}
	
	//Thread, Runnable deklarálása és elindítása
	private void initThreadWithRunnable(Intent intent) {
		notiRunnable = null;
		thread = null;
		
		notiRunnable = new NotiRunnable(intent.getExtras().getInt(
				"mepId"), this);

		thread = new Thread(notiRunnable);
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
