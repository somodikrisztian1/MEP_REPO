package hu.mep.datamodells;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TopicCategory {

	@SerializedName("nev")
	private String categoryName;
	
	@SerializedName("temakorok")
	private List<Topic> topics;

	public String getCategoryName() {
		return categoryName;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public TopicCategory(String categoryName, List<Topic> topics) {
		super();
		this.categoryName = categoryName;
		this.topics = topics;
	}
	
	
	
	
}
