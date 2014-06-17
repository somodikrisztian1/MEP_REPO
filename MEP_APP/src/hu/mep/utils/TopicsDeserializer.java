package hu.mep.utils;

import hu.mep.datamodells.AllTopicsList;
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
		
		for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			TopicCategory newTopicCategory = context.deserialize(entry.getValue(),
					TopicCategory.class);
			allTopicCategories.add(newTopicCategory);
		}
		return new AllTopicsList(allTopicCategories);
	}

}
