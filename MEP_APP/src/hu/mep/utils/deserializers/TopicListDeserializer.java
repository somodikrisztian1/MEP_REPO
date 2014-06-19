package hu.mep.utils.deserializers;

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

public class TopicListDeserializer implements JsonDeserializer<AllTopicsList> {

	@Override
	public AllTopicsList deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		
		List<TopicCategory> allTopicCategories = new ArrayList<TopicCategory>();
		String categoryName;
		
		JsonObject rootObject = element.getAsJsonObject();
		JsonObject object;
		JsonObject topicsJSONobj;

		for (Map.Entry<String, JsonElement> category : rootObject.entrySet()) {

			object = category.getValue().getAsJsonObject();
			categoryName = object.get("nev").getAsString();

			topicsJSONobj = object.get("temakorok")
					.getAsJsonObject();
			List<Topic> topicListInCategory = new ArrayList<Topic>();
			Topic actTopic;

			for (Map.Entry<String, JsonElement> topic : topicsJSONobj
					.entrySet()) {
				actTopic = null;
				actTopic = context.deserialize(topic.getValue(), Topic.class);
				topicListInCategory.add(actTopic);
			}
			allTopicCategories.add(new TopicCategory(categoryName,
					topicListInCategory));

			object = null;
		}
		return new AllTopicsList(allTopicCategories);
	}
}
