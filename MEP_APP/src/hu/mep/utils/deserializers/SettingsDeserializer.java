package hu.mep.utils.deserializers;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.settings.Function;
import hu.mep.datamodells.settings.Relay;
import hu.mep.datamodells.settings.Settings;
import hu.mep.datamodells.settings.Slider;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class SettingsDeserializer implements JsonDeserializer<Settings> {

	private static final String TAG = "SettingsDeserializer";

	@Override
	public Settings deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {

		List<Slider> sliders = null;
		List<Relay> relays = null;
		List<Function> functions = null;

		if (element.isJsonObject()) {
			JsonObject rootObject = element.getAsJsonObject();

			sliders = readSliders(rootObject);
			relays = readRelays(rootObject);
			functions = readFunctions(rootObject);
		}
		Settings result = new Settings(sliders, relays, functions);
		Session.setActualSettings(result);
		return null;
	}
	
	private List<Slider> readSliders(JsonObject rootObject) {

		List<Slider> sliders = new ArrayList<Slider>();

		if (rootObject.get("slides").isJsonObject()) {
			JsonObject slidesRoot = rootObject.get("slides").getAsJsonObject();

			for (Map.Entry<String, JsonElement> slideElement : slidesRoot.entrySet()) {
				int serialNumber = Integer.valueOf(slideElement.getKey());
				JsonObject actSliderJson = slideElement.getValue()
						.getAsJsonObject();
				double value = actSliderJson.get("value").getAsDouble();
				double minValue = actSliderJson.get("min").getAsDouble();
				double maxValue = actSliderJson.get("max").getAsDouble();
				String name = actSliderJson.get("name").getAsString();
				String label = actSliderJson.get("label").getAsString();
				Log.e("SLIDER", label + " " + minValue + " < " + value + " < " + maxValue);
				Slider newSlider = new Slider(serialNumber, value, minValue,
						maxValue, name, label);
				sliders.add(newSlider);
			}
		}

		return sliders;
	}
	
	private List<Relay> readRelays(JsonObject rootObject) {

		List<Relay> relays = new ArrayList<Relay>();

		if (rootObject.get("relays").isJsonObject()) {
			JsonObject relaysRoot = rootObject.get("relays").getAsJsonObject();
			for (Entry<String, JsonElement> relayElement : relaysRoot.entrySet()) {
				String name = relayElement.getKey();
				String value = relayElement.getValue().getAsJsonPrimitive().getAsString();
				boolean status = (value.equals("0") ? false : true);
				Log.e("RELAY", "name - value: " + name + " - " + (status ? "on" : "off"));
				Relay newRelay = new Relay(name, status);
				relays.add(newRelay);
			}
		}

		return relays;
	}
	
	private List<Function> readFunctions(JsonObject rootObject) {

		List<Function> functions = new ArrayList<Function>();

		if (rootObject.get("functions").isJsonObject()) {
			JsonObject functionsRoot = rootObject.get("functions")
					.getAsJsonObject();

			for (Map.Entry<String, JsonElement> functionElement : functionsRoot.entrySet()) {
				int serialNumber = Integer.valueOf(functionElement.getKey());
				JsonObject actFunctionJson = functionElement.getValue().getAsJsonObject();

				boolean status = (actFunctionJson.get("value").getAsInt() == 0 ? false : true);
				String name = actFunctionJson.get("name").getAsString();
				String label = actFunctionJson.get("label").getAsString();

				Log.e("FUNCTION", "name - value: " + name + " - " + (status ? "on" : "off"));
				Function newFunction = new Function(serialNumber, status, name, label);
				functions.add(newFunction);
			}
		}

		return functions;
	}

}
