package hu.mep.utils.deserializers;

import hu.mep.datamodells.AllChartInfoContainer;
import hu.mep.datamodells.ChartInfoContainer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ChartInfoContainerDeserializer implements
		JsonDeserializer<AllChartInfoContainer> {

	@Override
	public AllChartInfoContainer deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {

		List<ChartInfoContainer> allChartInfoContainer = new ArrayList<ChartInfoContainer>();
		
		if (!element.isJsonArray()) {
			JsonObject jsonObject = element.getAsJsonObject();

			for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
				allChartInfoContainer.add(
						(ChartInfoContainer) context.deserialize(entry.getValue(), ChartInfoContainer.class));
			}
		}
		return new AllChartInfoContainer(allChartInfoContainer);
	}
/*
	private void logAllChartInfoContainer(AllChartInfoContainer all) {
		for (ChartInfoContainer actChartInfoContainer : all.getAllChartInfoContainer()) {
			actChartInfoContainer.getId();
		}
	}*/

}
