package hu.mep.datamodells;

import com.google.gson.annotations.SerializedName;

public class Topic {
	
	@SerializedName("tema_id")
	private int topicID;
	
	@SerializedName("nev")
	private String topicName;

	public int getTopicID() {
		return topicID;
	}

	public String getTopicName() {
		return topicName;
	}
	
	
	
}
