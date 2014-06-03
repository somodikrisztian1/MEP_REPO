package hu.mep.datamodells;

import java.util.Collections;

import android.app.ProgressDialog;
import hu.mep.communication.ICommunicator;
import hu.mep.communication.RealCommunicator;

public class Session {

	private static Session instance;
	private static User actualUser;
	private static ICommunicator actualCommunicationInterface;
	private static ChatContactList actualChatContactList;
	private static ChatContact actualChatPartner;
	private static ChatMessagesList chatMessagesList;
	private static ProgressDialog progressDialog;

	public static String getLastChatMessageDate() {
		if (chatMessagesList == null || chatMessagesList.chatMessagesList.isEmpty()) {
			return "1970-01-01_00:00:00";
		} else {
			return chatMessagesList.getChatMessagesList().get(
					chatMessagesList.getChatMessagesList().size() - 1).date;
		}
	}

	public static ChatMessagesList getChatMessagesList() {
		return chatMessagesList;
	}

	public static void setChatMessagesList(ChatMessagesList chatMessagesList) {
		Session.chatMessagesList = chatMessagesList;
		Session.sortChatMessagesList();
	}

	
	public static ChatContactList getActualChatContactList() {
		return actualChatContactList;
	}

	public static void setActualChatContactList(
			ChatContactList actualChatContactList) {
		Session.actualChatContactList = actualChatContactList;
	}

	public ChatContact getActualChatPartner() {
		return this.actualChatPartner;
	}
	
	public static void setActualChatPartner(ChatContact newPartner) {
		Session.actualChatPartner = newPartner;
	}

	private Session() {
		actualCommunicationInterface = RealCommunicator.getInstance();
	}

	public static Session getInstance() {
		if (instance == null) {
			instance = new Session();
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
			actualCommunicationInterface = RealCommunicator.getInstance();
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
		Collections.sort(Session.chatMessagesList.chatMessagesList);
	}
}
