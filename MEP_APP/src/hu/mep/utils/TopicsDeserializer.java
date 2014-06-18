package hu.mep.utils;

import hu.mep.datamodells.AllTopicsList;
import hu.mep.datamodells.Topic;
import hu.mep.datamodells.TopicCategory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class TopicsDeserializer implements JsonDeserializer<AllTopicsList>{

	@Override
	public AllTopicsList deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject jsonObject = element.getAsJsonObject();
		
		List<TopicCategory> allTopicCategories = new ArrayList<TopicCategory>();
		String categoryName;
		JsonObject object;
		for (Map.Entry<String, JsonElement> category : jsonObject.entrySet()) {
			object = null;
			object = category.getValue().getAsJsonObject();
			categoryName = object.get("nev").getAsString();
			
			JsonObject topicsJSONobj = object.get("temakorok").getAsJsonObject();
			List<Topic> topicListInCategory = new ArrayList<Topic>();
			Topic actTopic;
			for (Map.Entry<String, JsonElement> topic : topicsJSONobj.entrySet()) {
				actTopic = null;
				actTopic = context.deserialize(topic.getValue(), Topic.class);
				topicListInCategory.add(actTopic);
			}
			allTopicCategories.add(new TopicCategory(categoryName, topicListInCategory));
		}
		
		/* Ha normális JSON array lenne, ennyi lenne csak a dolog:
		TopicCategory newTopicCategory = context.deserialize(entry.getValue(),
		TopicCategory.class);
		allTopicCategories.add(newTopicCategory);*/
		return new AllTopicsList(allTopicCategories);
	}

}
