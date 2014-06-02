package hu.mep.datamodells;

//import com.google.gson.annotations.SerializedName;

public class chatContact {
	
	//@SerializedName("userId")
	private int userID;
	
	//@SerializedName("name")
	private String name;
	
	//@SerializedName("imageURL")
	private String imageURL;
	
	//@SerializedName("vanOlvasatlanTole")
	private int unreadedMessageNumber;
	
	//@SerializedName("online")
	private boolean isOnline;
	
	
	public chatContact(int userID, String name, String imageURL,
			int unreadedMessageNumber, boolean isOnline) {
		super();
		this.userID = userID;
		this.name = name;
		this.imageURL = imageURL;
		this.unreadedMessageNumber = unreadedMessageNumber;
		this.isOnline = isOnline;
	}


	

}
