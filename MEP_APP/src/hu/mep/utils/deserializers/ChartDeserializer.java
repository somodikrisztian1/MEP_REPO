package hu.mep.utils.deserializers;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hu.mep.datamodells.Chart;
import hu.mep.datamodells.SubChart;
import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ChartDeserializer implements JsonDeserializer<Chart> {

	private static final String TAG = "ChartDeserializer";

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");

	private static Chart result;

	@Override
	public Chart deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject rootObject = null;
		result = null;

		rootObject = (element.isJsonArray() ? null : element.getAsJsonObject());

		if (rootObject == null) {
			result = new Chart("", "");
			result.setSubCharts(new ArrayList<SubChart>());
			return result;
		} else {
			rootObject = element.getAsJsonObject();
			Log.e(TAG, "JSON FOR GETCHART - FROM ROOT:" + rootObject.toString());

			String elapse = rootObject.get("elapse").getAsString();
			String y = rootObject.get("y").getAsString();

			result = new Chart(elapse, y);

			JsonObject charts = (rootObject.get("charts").isJsonArray() ? null
					: rootObject.get("charts").getAsJsonObject());
			
			deserializeCharts(charts);
			Log.e(TAG, "result.subcharts.size() = " + result.getSubCharts().size());
			return result;
		}
	}

	private void deserializeCharts(JsonObject charts) {
		JsonObject actualChartJSONObj;
		JsonObject actualChartDataJSONObj;

		String actualChartLabel;
		SubChart actualSubChart;
		Date actualDate = null;
		HashMap<Date, Double> actualChartDatas = new HashMap<Date, Double>();

		if (charts != null) {
			result.setSubCharts(new ArrayList<SubChart>());
			for (Map.Entry<String, JsonElement> entry : charts.entrySet()) {
				actualChartJSONObj = entry.getValue().getAsJsonObject();
				actualChartLabel = actualChartJSONObj.get("label")
						.getAsString();
				Log.e(TAG, "Label:" + actualChartLabel);

				actualChartDatas.clear();

				actualChartDataJSONObj = (actualChartJSONObj.get("adat").isJsonArray() ? null
						: actualChartJSONObj.get("adat").getAsJsonObject());

				
				if (actualChartDataJSONObj != null) {
					
					for (Map.Entry<String, JsonElement> actData : actualChartDataJSONObj
							.entrySet()) {
						try {
							actualDate = dateFormatter.parse(actData.getKey());
							//Log.e(TAG, "actData.getKey():" + actData.getKey());
							//Log.e(TAG, actualDate.toGMTString());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						actualChartDatas.put(actualDate, actData.getValue()
								.getAsDouble());
					}
					actualSubChart = new SubChart(actualChartLabel,
							actualChartDatas);
					result.getSubCharts().add(actualSubChart);
				}
			}
		}
	}

}
