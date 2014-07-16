package hu.mep.datamodells;

import com.google.gson.annotations.SerializedName;


public class ChatMessage implements Comparable<ChatMessage> {
	
	@Override
	public String toString() {
		return "order("+ order +") from:(#" + fromID + ") to(#" + toID + ") @: " + date + "\nMessage: \"" + message + "\"";
	}
	
	@SerializedName("fromId")
	int fromID;
	
	@SerializedName("toId")
	int toID;
	
	@SerializedName("message")
	String message;
	
	@SerializedName("date")
	String date;

	@SerializedName("order")
	int order;
	
	public ChatMessage(int fromID, int toID, String message, String date, int order) {
		super();
		this.fromID = fromID;
		this.toID = toID;
		this.message = message;
		this.date = date;
		this.order = order;
	}

	public int getFromID() {
		return fromID;
	}

	public int getToID() {
		return toID;
	}
	
	@Override
	public int compareTo(ChatMessage another) {
		if(order < another.order)
			return -1;
		else if(order > another.order) 
			return 1;
		return 0;
	}
	

	public String getMessage() {
		return message;
	}

	public String getDate() {
		return date;
	}

	public int getOrder() {
		return order;
	}


	
	
	
	
}
