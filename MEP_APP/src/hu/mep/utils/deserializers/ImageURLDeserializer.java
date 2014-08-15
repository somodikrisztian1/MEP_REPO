package hu.mep.utils.deserializers;

import hu.mep.datamodells.ImageURLList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ImageURLDeserializer implements JsonDeserializer<ImageURLList> {

	@Override
	public ImageURLList deserialize(JsonElement element, Type arg1,	JsonDeserializationContext arg2) throws JsonParseException {
		List<String> result = new ArrayList<String>();
		if (element.isJsonObject()) {
			for (Map.Entry<String, JsonElement> actualPictureURL : element.getAsJsonObject().entrySet()) {
				result.add(actualPictureURL.getValue().getAsString());
			}
		}
		return new ImageURLList(result);
	}

}
