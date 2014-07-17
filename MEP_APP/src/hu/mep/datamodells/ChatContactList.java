package hu.mep.datamodells;

import java.util.List;

import android.graphics.Bitmap;

public class ChatContactList {
	
	List<ChatContact> contacts;
	private static final String TAG = "ChatContactList";
	
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

	public String getNameFromContactID(int id) {
		String result = null;
		for (ChatContact actContact : this.contacts) {
			if(actContact.getUserID() == id) {
				return actContact.getName();
			}
		}
		
		return result;
	}
	
	public Bitmap getImageFromContactID(int id) {
		Bitmap result = null;
		for (ChatContact actContact : this.contacts) {
			if(actContact.getUserID() == id) {
				result = actContact.getProfilePicture();
				return result;
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ChatContactList)) {
			return false;
		}
		List<ChatContact> own = this.contacts;
		List<ChatContact> other = ((ChatContactList) o).getContacts();
		
		if (own.size() != other.size()) {
			return false;
		}

		for (int i = 0; i < own.size(); ++i) {
			if (own.get(i).getName().equals(other.get(i).getName())) {
				/*Log.i(TAG, own.get(i).getName() + "==" + other.get(i).getName());*/
				/*Log.i(TAG, own.get(i).isOnline() + "==" + other.get(i).isOnline());*/
				if (own.get(i).isOnline() != other.get(i).isOnline()) {
					/* Log.i(TAG, own.get(i).isOnline() + "==" + other.get(i).isOnline());*/
					return false;
				}
			} else {
				/*Log.e(TAG, own.get(i).getName() + "==" + other.get(i).getName());*/
				return false;
			}
		}
		return true;
		
	};
	
	
	
	
}
