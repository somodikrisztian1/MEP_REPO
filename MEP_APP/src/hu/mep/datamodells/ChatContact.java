package hu.mep.datamodells;

import hu.mep.mep_app.R;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

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


	public int getStatePictureResID() {
		if(isOnline != 0) {
			return R.drawable.state_picture_online;
		}
		return R.drawable.state_picture_offline;
	}

	
	@Override
	public String toString() {
		return "(#" + userID + ")" + name + (isOnline == 0 ? " [off] "  : " [on] " ) + "olvasatlan: " + unreadedMessageNumber;
	}

	

}
