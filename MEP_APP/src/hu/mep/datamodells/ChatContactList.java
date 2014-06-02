package hu.mep.datamodells;

import java.util.List;

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

	
}
