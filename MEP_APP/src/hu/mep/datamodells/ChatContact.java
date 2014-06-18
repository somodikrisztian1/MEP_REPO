package hu.mep.datamodells;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class ChatContact {
	
	@SerializedName("userId")
	private int userID;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("imageURL")
	private String imageURL;
	
	@SerializedName("vanOlvasatlanTole")
	private int unreadedMessageNumber;
	
	@SerializedName("online")
	private int isOnline;
	
	private Bitmap profilePicture;
	
	public Bitmap getProfilePicture() {
		return profilePicture;
	}


	public void setProfilePicture(Bitmap profilePicture) {
		this.profilePicture = profilePicture;
	}


	public int getUserID() {
		return userID;
	}


	public String getName() {
		return name;
	}


	public String getImageURL() {
		return imageURL;
	}


	public int getUnreadedMessageNumber() {
		return unreadedMessageNumber;
	}


	public int isOnline() {
		return isOnline;
	}


	public ChatContact(int userID, String name, String imageURL,
			int unreadedMessageNumber, int isOnline) {
		super();
		this.userID = userID;
		this.name = name;
		this.imageURL = imageURL;
		this.unreadedMessageNumber = unreadedMessageNumber;
		this.isOnline = isOnline;
	}

	
	@Override
	public String toString() {
		return "(#" + userID + ")" + name + (isOnline == 0 ? " [off] "  : " [on] " ) + "olvasatlan: " + unreadedMessageNumber;
	}

	

}
