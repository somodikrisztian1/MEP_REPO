package hu.mep.datamodells;

import java.util.List;

public class AllTopicsList {

	private List<TopicCategory> allTopics;

	public AllTopicsList(List<TopicCategory> allTopics) {
		super();
		this.allTopics = allTopics;
	}

	public List<TopicCategory> getAllTopics() {
		return allTopics;
	}
	
}
