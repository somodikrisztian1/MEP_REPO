package hu.mep.utils.deserializers;

import java.lang.reflect.Type;

import hu.mep.datamodells.Chart;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ChartDeserializer implements JsonDeserializer<Chart> {

	@Override
	public Chart deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
