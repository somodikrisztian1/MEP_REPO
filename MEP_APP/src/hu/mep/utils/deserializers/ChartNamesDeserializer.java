package hu.mep.utils.deserializers;

import hu.mep.datamodells.charts.AllChartNames;
import hu.mep.datamodells.charts.ChartName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ChartNamesDeserializer implements
		JsonDeserializer<AllChartNames> {

	@Override
	public AllChartNames deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {

		List<ChartName> allChartInfoContainer = new ArrayList<ChartName>();
		
		if (!element.isJsonArray()) {
			JsonObject jsonObject = element.getAsJsonObject();

			for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
				allChartInfoContainer.add((ChartName) context.deserialize(entry.getValue(), ChartName.class));
			}
		} else {
			allChartInfoContainer.clear();
		}
		return new AllChartNames(allChartInfoContainer);
	}
}
