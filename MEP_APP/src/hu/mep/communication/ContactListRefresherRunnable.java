package hu.mep.communication;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel2Chat;

import java.util.List;

import android.util.Log;

public class ContactListRefresherRunnable implements Runnable {

	private static final String TAG = "ContactListRefresherRunnable";
	private static long WAIT_TIME = 5000L;
	private static ChatContactList before;
	private static ChatContactList after;
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
				/*before = null;
				after = null;*/
				/*if (!running) continue;
				before = new ChatContactList( Session.getActualChatContactList().getContacts() );
				for (ChatContact act : before.getContacts()) {
					if (!running) continue;
					Log.i("TESTbefore",	act.getName() + " isON?[" + act.isOnline() + "]");
				}*/
				if (!running) continue;
				if(Session.isAnyUserLoggedIn()) {
					Session.getActualCommunicationInterface().getChatPartners();
				}
				/*	if (!running) continue;
				after = new ChatContactList( Session.getActualChatContactList().getContacts() );
				for (ChatContact act : after.getContacts()) {
					if (!running) continue;
					Log.e("TESTafter",
							act.getName() + " isON?[" + act.isOnline() + "]");
				}
				if (before != null && after != null) {
					if (before.equals(after)) {
						if (!running) continue;
						Log.e(TAG, "NO PARTNERS CHANGED!!!");
					} else {
						Log.e(TAG, "PARTNERS CHANGED!!!");
						if (!running) continue;
						FragmentLevel2Chat.contactAdapter
								.notifyDataSetChanged();
					}*/
					/*FragmentLevel2Chat.contactAdapter.notifyDataSetChanged();*/
					try {
						Thread.sleep(WAIT_TIME);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

