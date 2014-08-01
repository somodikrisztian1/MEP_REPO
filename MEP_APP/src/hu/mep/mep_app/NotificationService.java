package hu.mep.mep_app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NotificationService extends Service {

	private String TAG = "NotiService";
	private NotiRunnable notiRunnable;
	private Thread thread;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "onCreate");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.e(TAG, "onStart");

		if (intent != null && intent.getExtras() != null) {

			if (notiRunnable != null && thread != null) {

				if (notiRunnable.isRunning()) {
					notiRunnable.setMepId(intent.getExtras().getInt("mepId"));
					Log.e(TAG, "notiRUnnable is running");
				} else {
					Log.e(TAG, "notiRUnnable not running 1");
					initThreadWithRunnable(intent);
				}
			} else {
				Log.e(TAG, "notiRUnnable not running 2");
				initThreadWithRunnable(intent);
			}
		}
	}

	// Thread, Runnable deklarálása és elindítása
	private void initThreadWithRunnable(Intent intent) {
		try {
			notiRunnable = null;
			thread = null;

			notiRunnable = new NotiRunnable(intent.getExtras().getInt("mepId"),
					this);

			thread = new Thread(notiRunnable);
			thread.start();
		} catch (Exception e) {
			Log.e(TAG, "elkaptam");
		}
	}

	@Override
	public void onDestroy() {
		notiRunnable.stop();
		notiRunnable = null;

		super.onDestroy();
		// Log.e(TAG, "onDestroy");
	}
}
