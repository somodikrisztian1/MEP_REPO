package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.util.Log;

public class GetNotWorkingPlacesListRunnable implements Runnable {

	private static final String TAG = "GetNotWorkingPlacesListRunnable";
	private static long WAIT_TIME = 5000L; // 60000
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
					if(Session.getActualUser().getUsersPlaces() != null) {
						Session.getActualCommunicationInterface().getNotWorkingPlacesList();
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
	}

