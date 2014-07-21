package hu.mep.utils.deserializers;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hu.mep.datamodells.Place;
import hu.mep.datamodells.PlaceList;
import hu.mep.datamodells.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class UserDeserializer implements JsonDeserializer<User> {

	@Override
	public User deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {

		JsonObject root = element.getAsJsonObject();
		URL imgURL = null;
		try {
			imgURL = new URL(root.get("imageUrl").getAsString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		int mekut = root.get("mekut").getAsInt();
		int mep_id = root.get("mep_id").getAsInt();
		int moderator = root.get("moderator").getAsInt();
		String nev = root.get("nev").getAsString();
		int tanar = root.get("tanar").getAsInt();

		boolean isMekut = (mekut == 0 ? false : true);
		boolean isTanar = (tanar == 0 ? false : true);
		boolean isModerator = (moderator == 0 ? false : true);

		List<Place> places = new ArrayList<Place>();
		PlaceList placeList = null;
		if( root.get("tavfelugyeletek").isJsonObject() ) {
			for (Map.Entry<String, JsonElement> entry : root.get("tavfelugyeletek").getAsJsonObject().entrySet()) {
				/*Place place = context.deserialize(entry.getValue(), Place.class);*/
				JsonObject tavfelugyelet = entry.getValue().getAsJsonObject();
				
				String tsz1id = tavfelugyelet.get("tsz1_id").getAsString();
				String name =  tavfelugyelet.get("nev").getAsString();
				String description =  tavfelugyelet.get("leiras").getAsString();
				String location = tavfelugyelet.get("helyszin").getAsString();
				boolean isSolarPanel = (tavfelugyelet.get("is_napelem").getAsInt() != 0 ? true : false);
				
				Place place = new Place(name, tsz1id, description, location, isSolarPanel);
				places.add(place);
			}
			placeList = new PlaceList(places);
		}
		return new User(mep_id, nev, imgURL, isMekut, isTanar, isModerator,
				placeList);
	}
}
