package hu.mep.datamodells;

import android.app.ProgressDialog;
import hu.mep.communication.ICommunicator;
import hu.mep.communication.RealCommunicator;

public class Session {

	private static Session instance;
	private static User actualUser;
	private static ICommunicator actualCommunicationInterface;
	private static ChatContactList actualChatContactList;
	private static ProgressDialog progressDialog;
	
	public static ChatContactList getActualChatContactList() {
		return actualChatContactList;
	}

	public static void setActualChatContactList(
			ChatContactList actualChatContactList) {
		Session.actualChatContactList = actualChatContactList;
	}

	private Session() {
		actualCommunicationInterface = RealCommunicator.getInstance();
	}
	
	public static Session getInstance() {
		if(instance == null) {
			instance = new Session();
		}
		return instance;
	}
	
	public static User getActualUser() {
		return actualUser;
	}
	
	public static void setActualUser(User newUser) {
		actualUser = newUser;
		if(newUser != null)
			actualUser.downloadProfilePicture();
	}
	
	public static ICommunicator getActualCommunicationInterface() {
		if(actualCommunicationInterface == null) {
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
	
}
