package hu.mep.datamodells;

import java.util.List;

import android.graphics.Bitmap;

public class ChatContactList {
	List<ChatContact> contacts;

	public ChatContactList(List<ChatContact> contacts) {
		super();
		this.contacts = contacts;
	}

	public List<ChatContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<ChatContact> contacts) {
		this.contacts = contacts;
	}

	public Bitmap getImageFromContactID(int id) {
		Bitmap result = null;
		for (ChatContact actContact : this.contacts) {
			if(actContact.getUserID() == id) {
				result = actContact.getProfilePicture();
			}
		}
		return result;
	}
	
	
}
