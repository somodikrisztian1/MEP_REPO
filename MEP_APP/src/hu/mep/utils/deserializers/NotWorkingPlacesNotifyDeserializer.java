package hu.mep.utils.deserializers;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class NotWorkingPlacesNotifyDeserializer implements
		JsonDeserializer<Integer> {

	//private static final String TAG = "NotWorkingPlacesNotifyDeserializer";

	@Override
	public Integer deserialize(JsonElement element, Type type,	JsonDeserializationContext context) throws JsonParseException {

		int result = 0;
		
		if (element.isJsonObject()) {
			JsonObject root = element.getAsJsonObject();
			
			for (Entry<String, JsonElement> placeRoot : root.entrySet()) {

				JsonObject place = placeRoot.getValue().getAsJsonObject();
				
				String tsz1_id = place.get("tsz1_id").getAsString();
				String notify = place.get("notify").getAsString();
				if(notify.equals("1")) {
					++result;
					// Log.e(TAG, "#" + result + " place, where notify is 1.");
				} else {
					// Log.e(TAG, "Now notify is 0");
				}
			}
		}

		return result;
	}

}
