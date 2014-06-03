package hu.mep.datamodells;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hu.mep.communication.ICommunicator;




import com.google.gson.annotations.SerializedName;


public class ChatMessage implements Comparable<ChatMessage> {
	private static final SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
	
	@Override
	public int compareTo(ChatMessage another) {
		Date myDate = null;
		Date otherDate = null;
		try {
			myDate = myFormatter.parse(this.date);
			otherDate = myFormatter.parse(another.date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(myDate.before(otherDate))
			return -1;
		else if(myDate.after(otherDate)) 
			return 1;
		return 0;
	}
	
	@SerializedName("fromId")
	int fromID;
	
	@SerializedName("toId")
	int toID;
	
	@SerializedName("message")
	String message;
	
	@SerializedName("date")
	String date;

	public ChatMessage(int fromID, int toID, String message, String date) {
		super();
		this.fromID = fromID;
		this.toID = toID;
		this.message = message;
		this.date = date;
	}

	public int getFromID() {
		return fromID;
	}

	public int getToID() {
		return toID;
	}

	public String getMessage() {
		return message;
	}

	public String getDate() {
		return date;
	}


	
	
	
	
}
