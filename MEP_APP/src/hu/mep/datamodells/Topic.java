package hu.mep.datamodells;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Topic {
	
	@SerializedName("tsz1_id")
	private int topicID;
	
	@SerializedName("nev")
	private String topicName;
	
	@SerializedName("sszs")
	private List<Integer> tabSerialNumbers;

	public int getTopicID() {
		return topicID;
	}

	public String getTopicName() {
		return topicName;
	}

	public List<Integer> getTabSerialNumbers() {
		return tabSerialNumbers;
	}
	
	
	
	
}
