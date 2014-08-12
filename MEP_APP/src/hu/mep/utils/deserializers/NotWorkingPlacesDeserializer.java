package hu.mep.utils.deserializers;

import hu.mep.datamodells.Place;
import hu.mep.datamodells.Session;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class NotWorkingPlacesDeserializer implements JsonDeserializer<HashMap<String, String>> {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat logFormatter = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
	private static final String TAG = "NotWorkingPlacesDeserializer";

	@Override
	public HashMap<String, String> deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(element.isJsonObject()) {
			JsonObject root = element.getAsJsonObject();
			
			for (Entry<String, JsonElement> placeRoot : root.entrySet()) {
				
				JsonObject place = placeRoot.getValue().getAsJsonObject();
				
				Calendar lastWorkingDate = Calendar.getInstance();
				String tsz1_id = place.get("tsz1_id").getAsString();
				String lastWorkingText = null;
				try {
					String dateString = place.get("last_working_date").getAsString();
					if(!dateString.equals("null")) {
						lastWorkingDate.setTime(formatter.parse(dateString));
						lastWorkingText = "A rendszer legutóbb ekkor volt elérhető: " + 
						logFormatter.format(lastWorkingDate.getTime());
					} else {
						lastWorkingText = "A rendszer több mint egy hónapja nem elérhető.";
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if( place.get("notify").getAsString().equals("1")) {
					Place temp = Session.getActualUser().getUsersPlaces().findPlaceByID(tsz1_id);
					if(temp != null) {
						temp.setNotify(true);
					}
				}
				result.put(tsz1_id, lastWorkingText);
				Log.e(TAG,"tsz1_id=" + tsz1_id + " " + lastWorkingText );
			}
		}
		
		return result;
	}

}
