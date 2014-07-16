package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.os.AsyncTask;

public class ChatMessagesRefresherAsyncTask extends AsyncTask<Long, Void, Void> {
	
	protected static final String TAG = "ChatMessagesRefresherAsyncTask";
	private static long WAIT_TIME;

	@Override
	protected Void doInBackground(Long... params) {
		WAIT_TIME = params[0];
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!isCancelled()) {
					
					Session.getActualCommunicationInterface().getChatMessages();
					
					try {
						Thread.sleep(WAIT_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		return null;
	}
	
}