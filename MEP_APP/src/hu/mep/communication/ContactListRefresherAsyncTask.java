package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2Chat;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

public class ContactListRefresherAsyncTask extends AsyncTask<Long, Void, Void> {
	protected static final String TAG = "ChatMessagesRefresherAsyncTask";
	private static long WAIT_TIME;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Session.getActualCommunicationInterface().getChatPartners();
	}

	@Override
	protected Void doInBackground(Long... params) {
		WAIT_TIME = params[0];
		// Log.d(TAG, "WAIT_TIME = " + WAIT_TIME);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!isCancelled()) {
					List<ChatContact> before = null;
					List<ChatContact> after = null;
					if (!isCancelled()) {
						before = Session.getActualChatContactList().getContacts();
					}
					if (!isCancelled()) {
					Session.getActualCommunicationInterface().getChatPartners();
					}
					if (!isCancelled()) {
						after = Session.getActualChatContactList().getContacts();
					}
					if (before != null && after != null) {
						if (after.containsAll(before)) {
							// Log.d(TAG,
							// "NO PARTNERS CHANGED SINCE LAST REFRESH.");
						} else {
							// Log.d(TAG,
							// "PARTNERS HAS CHANGED SINCE LAST REFRESH.");
							// ActivityLevel2.actualAdapter.notifyDataSetChanged();
						}
						try {
							// Log.e(TAG, "WAITING...");
							Thread.sleep(WAIT_TIME);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
		FragmentLevel2Chat.contactAdapter.notifyDataSetChanged();
		// FragmentLevel2Chat.listview.invalidateViews();
	}
}
