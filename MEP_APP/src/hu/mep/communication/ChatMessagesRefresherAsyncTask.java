package hu.mep.communication;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;
import hu.mep.datamodells.ChatMessage;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel3Chat;

public class ChatMessagesRefresherAsyncTask extends AsyncTask<Long, Void, Void> {
	
	private List<ChatMessage> before;
	private List<ChatMessage> after;

	protected static final String TAG = "ChatMessagesRefresherAsyncTask";
	private static long WAIT_TIME;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		before = Session.getChatMessagesList().getChatMessagesList();
	}
	
	@Override
	protected Void doInBackground(Long... params) {
		WAIT_TIME = params[0];
		Log.d(TAG, "WAIT_TIME = " + WAIT_TIME);
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!isCancelled()) {
					
					Session.getActualCommunicationInterface().getChatMessages();
					
					try {
						Log.e(TAG, "WAITING...");
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
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		after = Session.getChatMessagesList().getChatMessagesList();
		
		if(after.containsAll(before)) { 
			Log.d(TAG, "NO DATA CHANGED SINCE LAST REFRESH.");
		} else {
			Log.d(TAG, "DATA HAS CHANGED SINCE LAST REFRESH.");
			ActivityLevel3Chat.adapter.notifyDataSetChanged();
		}
		
	}
	
}