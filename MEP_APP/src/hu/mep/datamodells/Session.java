package hu.mep.datamodells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import hu.mep.communication.ICommunicator;
import hu.mep.communication.RealCommunicator;

public class Session {

	private static Session instance;
	private static User actualUser;
	private static ICommunicator actualCommunicationInterface;
	private static ChatContactList actualChatContactList;
	private static ChatContact actualChatPartner;
	
	/* Megtöltjük az üzenetlistát jó nagy semmivel, hogy ne legyen nullpointerexception... */
	private static ChatMessagesList chatMessagesList; 
	private static ProgressDialog progressDialog;
	private static Context context;

	public static int getLastChatMessageOrder() {
		if (chatMessagesList == null || chatMessagesList.chatMessagesList.isEmpty()) {
			return 0;
		} else {
			return chatMessagesList.getChatMessagesList().get(
					chatMessagesList.getChatMessagesList().size() - 1).order;
		}
	}

	public static ChatMessagesList getChatMessagesList() {
		return chatMessagesList;
	}

	public static void setChatMessagesList(ChatMessagesList newChatMessagesList) {
		Log.e("Session", "setChatMessagesList()");
		chatMessagesList = newChatMessagesList;
		Session.sortChatMessagesList();
	}

	
	public static ChatContactList getActualChatContactList() {
		return actualChatContactList;
	}

	public static void setActualChatContactList(
			ChatContactList actualChatContactList) {
		Session.actualChatContactList = actualChatContactList;
	}
	
	public static void emptyChatMessagesList() {
		chatMessagesList = new ChatMessagesList(new ArrayList<ChatMessage>());
	}

	public ChatContact getActualChatPartner() {
		return this.actualChatPartner;
	}
	
	public static void setActualChatPartner(ChatContact newPartner) {
		Session.actualChatPartner = newPartner;
	}

	private Session(Context context) {
		this.context = context;
		actualCommunicationInterface = RealCommunicator.getInstance(context);
		emptyChatMessagesList();
	}

	public static Session getInstance(Context context) {
		if (instance == null) {
			instance = new Session(context);
		}
		return instance;
	}

	public static User getActualUser() {
		return actualUser;
	}

	public static void setActualUser(User newUser) {
		actualUser = newUser;
	}

	public static ICommunicator getActualCommunicationInterface() {
		if (actualCommunicationInterface == null) {
			actualCommunicationInterface = RealCommunicator.getInstance(context);
		}
		return actualCommunicationInterface;
	}

	public static void setProgressDialog(ProgressDialog progressDialog) {
		Session.progressDialog = progressDialog;
	}

	public static void dismissProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public static void showProgressDialog() {
		progressDialog.show();
	}


	public static void sortChatMessagesList() {
		Log.e("Session", "sortChatMessagesList()");
		Collections.sort(Session.chatMessagesList.chatMessagesList);
	}
}
