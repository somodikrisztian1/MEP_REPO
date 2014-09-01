package hu.mep.communication;

import hu.mep.datamodells.Session;

public class ContactListRefresherRunnable implements Runnable {

	// private static final String TAG = "ContactListRefresherRunnable";
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
				if (Session.isAnyUserLoggedIn()) {
					Session.getActualCommunicationInterface().getChatPartners();
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
