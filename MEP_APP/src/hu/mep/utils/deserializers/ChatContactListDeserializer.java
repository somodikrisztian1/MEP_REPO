package hu.mep.utils.deserializers;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatContactList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


public class ChatContactListDeserializer implements JsonDeserializer<ChatContactList> {

	@Override
	public ChatContactList deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
		List<ChatContact> contacts = new ArrayList<ChatContact>();
		
		if(element.isJsonObject()) {
			JsonObject jsonObject = element.getAsJsonObject();
			
			for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
				ChatContact contact = context.deserialize(entry.getValue(), ChatContact.class);
				contacts.add(contact);
			}
		}
		return new ChatContactList(contacts);

	}

}
