package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2Chat;

import java.util.List;

import android.util.Log;

public class ContactListRefresherRunnable implements Runnable {

	private static final String TAG = "ContactListRefresherRunnable";
	private static long WAIT_TIME = 5000L;
	private static List<ChatContact> before;
	private static List<ChatContact> after;

	@Override
	public void run() {

		while (true) {
			before = null;
			after = null;
			before = Session.getActualChatContactList().getContacts();
			for (ChatContact act : before) {
				Log.i("TESTbefore", act.getName() + " isON?[" + act.isOnline() + "]");
			}
			Session.getActualCommunicationInterface().getChatPartners();
			after = Session.getActualChatContactList().getContacts();
			for (ChatContact act : before) {
				Log.e("TESTafter", act.getName() + " isON?[" + act.isOnline() + "]");
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

	private boolean isEqualsBeforeAndAfterList() {
		String TAG = "isEqualBeforeAndAfterList";
		if (before.size() != after.size()) {
			Log.e(TAG, "1");
			return false;
		}

		for (int i = 0; i < before.size(); ++i) {
			if (before.get(i).getName().equals(after.get(i).getName())) {
				/*Log.i(TAG, before.get(i).getName() + "==" + after.get(i).getName());*/
				/*Log.i(TAG, before.get(i).isOnline() + "==" + after.get(i).isOnline());*/
				if (before.get(i).isOnline() != after.get(i).isOnline()) {
/*					Log.i(TAG, before.get(i).isOnline() + "==" + after.get(i).isOnline());
					Log.e(TAG, "2");
*/					return false;
				}
			} else {
				Log.e(TAG, before.get(i).getName() + "==" + after.get(i).getName());
				return false;
			}
		}
		return true;
	}

}
