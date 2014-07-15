package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2Chat;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

public class ContactListRefresherAsyncTask extends AsyncTask<Long, Void, Void> {
	protected static final String TAG = "ContactListRefresherAsyncTask";
	private static long WAIT_TIME;

	private List<ChatContact> before = null;
	private List<ChatContact> after = null;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Session.getActualCommunicationInterface().getChatPartners();
		before = Session.getActualChatContactList().getContacts();
	}

	@Override
	protected Void doInBackground(Long... params) {
		WAIT_TIME = params[0];
		 Log.d(TAG, "WAIT_TIME = " + WAIT_TIME);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!isCancelled()) {
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
						if (isEqualsBeforeAndAfterList()) {
							Log.e(TAG, "NO PARTNERS CHANGED!!!");
						} else {
							Log.e(TAG, "PARTNERS CHANGED!!!");
							FragmentLevel2Chat.contactAdapter.notifyDataSetChanged();
						}
						try {
							Thread.sleep(WAIT_TIME);
						} catch (InterruptedException e) {
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

		// FragmentLevel2Chat.listview.invalidateViews();
	}

	private boolean isEqualsBeforeAndAfterList() {
		String TAG = "isEqualBeforeAndAfterList";
		if (before.size() != after.size()) {
			Log.e(TAG, "1");
			return false;
		}

		for (int i = 0; i < before.size(); ++i) {
			if (!isCancelled()) {
				
				if (before.get(i).getName().equals(after.get(i).getName())) {
					Log.i(TAG, before.get(i).getName() + "==" + after.get(i).getName());
					if (before.get(i).isOnline() != after.get(i).isOnline()) {
						Log.e(TAG, "2");
						return false;
					}
				} else {
					Log.e(TAG, before.get(i).getName() + "==" + after.get(i).getName());
					return false;
				}
			}
			else {
				Log.e(TAG, "cancelled");
				return false;
			}
		}
		return true;
	}
}
