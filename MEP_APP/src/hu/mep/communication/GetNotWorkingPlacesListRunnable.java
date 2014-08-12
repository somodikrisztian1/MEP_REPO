package hu.mep.communication;

import hu.mep.datamodells.Session;

public class GetNotWorkingPlacesListRunnable implements Runnable {

	private static final String TAG = "GetNotWorkingPlacesListRunnable";
	//private static long WAIT_TIME = 300000L;
	private static long WAIT_TIME = 5000L;
	private boolean running = true;

	public void pause() {
		running = false;
	}

	public void resume() {
		running = true;
	}

	@Override
	public void run() {

		while (true) {
			if (running) {		
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

