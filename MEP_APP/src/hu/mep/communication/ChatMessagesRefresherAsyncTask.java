package hu.mep.communication;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;
import hu.mep.datamodells.ChatMessage;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.ActivityLevel3Chat;

public class ChatMessagesRefresherAsyncTask extends AsyncTask<Long, Void, Void> {

	protected static final String TAG = "ChatMessagesRefresherAsyncTask";
	private static long WAIT_TIME;

	@Override
	protected Void doInBackground(Long... params) {
		WAIT_TIME = params[0];
		Log.d(TAG, "WAIT_TIME = " + WAIT_TIME);
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!isCancelled()) {
					List<ChatMessage> before = Session.getChatMessagesList().getChatMessagesList();
					Session.getActualCommunicationInterface().getChatMessages();
					List<ChatMessage> after = Session.getChatMessagesList().getChatMessagesList();
					if(after.containsAll(before)) { 
						Log.d(TAG, "NO DATA CHANGED SINCE LAST REFRESH.");
					} else {
						Log.d(TAG, "DATA HAS CHANGED SINCE LAST REFRESH.");
						ActivityLevel3Chat.adapter.notifyDataSetChanged();
					}
					try {
						Log.e(TAG, "WAITING...");
						Thread.sleep(WAIT_TIME);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		return null;
	}
	
}