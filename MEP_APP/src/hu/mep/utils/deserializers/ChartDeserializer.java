package hu.mep.utils.deserializers;

import hu.mep.datamodells.Chart;
import hu.mep.datamodells.SubChart;
import hu.mep.utils.others.CalendarPrinter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ChartDeserializer implements JsonDeserializer<Chart> {

	private static final String TAG = "ChartDeserializer";

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public Chart deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject rootObject = null;
		Chart result = null;
		List<SubChart> subchartsForResult = new ArrayList<SubChart>();
		rootObject = (element.isJsonArray() ? null : element.getAsJsonObject());

		if (rootObject == null) {
			result = new Chart("", "");
			result.setSubCharts(new ArrayList<SubChart>());
			return result;
		} else {
			rootObject = element.getAsJsonObject();
			
			String elapse = rootObject.get("elapse").getAsString();
			String y = rootObject.get("y").getAsString();

			result = new Chart(elapse, y);

			JsonObject charts = (rootObject.get("charts").isJsonArray() ? null
					: rootObject.get("charts").getAsJsonObject());

			deserializeCharts(charts, subchartsForResult, result);
			//Log.e(TAG, "result.subcharts.size() = "	+ result.getSubCharts().size());
			return result;
		}
	}

	private void deserializeCharts(JsonObject charts, List<SubChart> subchartsForResult, Chart result) {

		if (charts != null) {
			result.setSubCharts(new ArrayList<SubChart>());
			for (Map.Entry<String, JsonElement> entry : charts.entrySet()) {
				JsonObject actualChartJSONObj = entry.getValue().getAsJsonObject();
				String actualChartLabel = actualChartJSONObj.get("label").getAsString();
				//Log.e(TAG, "Label:" + actualChartLabel);
				Log.e(TAG, "Label:" + actualChartLabel);
				HashMap<Calendar, Double> actualChartDatas = new HashMap<Calendar, Double>();

				JsonObject actualChartDataJSONObj = (actualChartJSONObj.get("adat")
						.isJsonArray() ? null : actualChartJSONObj.get("adat")
						.getAsJsonObject());

				if (actualChartDataJSONObj != null) {

					for (Map.Entry<String, JsonElement> actData : actualChartDataJSONObj
							.entrySet()) {
						Calendar actualDate = Calendar.getInstance();
						try {
							actualDate.setTime(dateFormatter.parse(actData
									.getKey()));
							CalendarPrinter.logCalendar(TAG, actualDate, actData.getValue().getAsDouble());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						actualChartDatas.put(actualDate, actData.getValue()
								.getAsDouble());
					}
					/* actualSubChart = new SubChart(actualChartLabel, actualChartDatas); */
					subchartsForResult.add(new SubChart(actualChartLabel, actualChartDatas));
					actualChartDatas = null;

				}
			}
			result.setSubCharts(subchartsForResult);
		}
	}

}
