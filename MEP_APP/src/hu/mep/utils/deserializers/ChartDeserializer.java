package hu.mep.utils.deserializers;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hu.mep.datamodells.Chart;
import hu.mep.datamodells.SubChart;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ChartDeserializer implements JsonDeserializer<Chart> {

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	@Override
	public Chart deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {

		JsonObject rootObject = element.getAsJsonObject();

		String elapse = rootObject.get("elapse").getAsString();
		String y = rootObject.get("y").getAsString();
		JsonObject charts = rootObject.get("charts").getAsJsonObject();

		Chart result = new Chart(elapse, y);

		JsonObject actualChartJSONObj;
		JsonObject actualChartDataJSONObj;

		String actualChartLabel;
		SubChart actualSubChart;
		Date actualDate = null;
		HashMap<Date, Double> actualChartDatas = new HashMap<Date, Double>();

		for (Map.Entry<String, JsonElement> entry : charts.entrySet()) {
			actualChartJSONObj = entry.getValue().getAsJsonObject();
			actualChartLabel = actualChartJSONObj.get("label").getAsString();
			actualChartDataJSONObj = actualChartJSONObj.get("adat")
					.getAsJsonObject();
			actualChartDatas.clear();

			if (!(actualChartDataJSONObj.getAsString().equals("[]"))) {
				for (Map.Entry<String, JsonElement> actData : actualChartDataJSONObj
						.entrySet()) {
					try {
						actualDate = dateFormatter.parse(actData.getKey());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					actualChartDatas.put(actualDate, actData.getValue()
							.getAsDouble());
				}
			}
			actualSubChart = new SubChart(actualChartLabel, actualChartDatas);
			result.getSubCharts().add(actualSubChart);
		}

		return result;
	}

}
