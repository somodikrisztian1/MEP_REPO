package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.util.Log;

public class ContactListRefresherRunnable implements Runnable {

	private static final String TAG = "ContactListRefresherRunnable";
	private static long WAIT_TIME = 5000L;
	private boolean running = true;

	public void pause() {
		Log.i(TAG, "pause");
		running = false;
	}

	public void resume() {
		Log.i(TAG, "resume");
		running = true;
	}

	@Override
	public void run() {

		while (true) {
			if (running) {
				Log.i(TAG, "running");
				
				if (!running) continue;
				if(Session.isAnyUserLoggedIn()) {
					Session.getActualCommunicationInterface().getChatPartners();
				}
				
					try {
						Thread.sleep(WAIT_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

