package hu.mep.utils.deserializers;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.BarChart;
import hu.mep.datamodells.charts.Chart;
import hu.mep.datamodells.charts.OneLineAndTwoBarChartContainer;
import hu.mep.datamodells.charts.SubChart;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class BarChartDeserializer implements JsonDeserializer<OneLineAndTwoBarChartContainer> {

	private static final String TAG = "BarChartDeserializer";

	private static final SimpleDateFormat dateFormatter = 
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat monthlyFormatter = 
			new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat annualFormatter = 
			new SimpleDateFormat("yyyy-MM");

	@Override
	public OneLineAndTwoBarChartContainer deserialize(JsonElement element, Type type, JsonDeserializationContext context) 
			throws JsonParseException {
		
		Chart lineChart = new Chart("", "");
		BarChart monthly = new BarChart(new HashMap<Calendar, Double>());
		BarChart annual = new BarChart(new HashMap<Calendar, Double>());
		
		if(element.isJsonObject()) {
			JsonObject root = element.getAsJsonObject();
			
			JsonElement lineRoot = root.get("line");
			lineChart = readLine(lineRoot);
		
			JsonElement monthlyRoot = root.get("havi");
			monthly = readMonthly(monthlyRoot);
			
			JsonElement annualRoot = root.get("eves");
			annual = readAnnual(annualRoot);
			
		}
		
		OneLineAndTwoBarChartContainer result = new OneLineAndTwoBarChartContainer(lineChart, monthly, annual);
		return result;
	}

	private BarChart readAnnual(JsonElement annualRoot) {
		
		BarChart result = new BarChart(new HashMap<Calendar, Double>());
		
		if(annualRoot.isJsonObject()) {
			JsonObject annualJsonObj = annualRoot.getAsJsonObject();
			for (Map.Entry<String, JsonElement> actElement : annualJsonObj.entrySet()) {
				Calendar actDate = Calendar.getInstance();
				try {
					actDate.setTime(annualFormatter.parse(actElement.getKey()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				result.getChartValues().put(actDate, actElement.getValue().getAsJsonPrimitive().getAsDouble());
			}
		}
		return result;
	}

	private BarChart readMonthly(JsonElement monthlyRoot) {
		BarChart result = new BarChart(new HashMap<Calendar, Double>());
		
		if(monthlyRoot.isJsonObject()) {
			JsonObject annualJsonObj = monthlyRoot.getAsJsonObject();
			for (Map.Entry<String, JsonElement> actElement : annualJsonObj.entrySet()) {
				Calendar actDate = Calendar.getInstance();
				try {
					actDate.setTime(monthlyFormatter.parse(actElement.getKey()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				result.getChartValues().put(actDate, actElement.getValue().getAsJsonPrimitive().getAsDouble());
			}
		}
		return result;
	}

	private Chart readLine(JsonElement lineRoot) {
		Chart result = new Chart("", "Termelés (kW)");
		List<SubChart> subchartsForResult = new ArrayList<SubChart>();
		
		if (lineRoot.isJsonObject()) {
			JsonObject lineJsonObj = lineRoot.getAsJsonObject();
			
			result.setSubCharts(new ArrayList<SubChart>());
			
			for (Map.Entry<String, JsonElement> entry : lineJsonObj.entrySet()) {
				JsonObject actualChartJSONObj = entry.getValue().getAsJsonObject();
				String actualChartLabel = actualChartJSONObj.get("label").getAsString();
				//Log.e(TAG, "Label:" + actualChartLabel);
				//Log.e(TAG, "Label:" + actualChartLabel);
				HashMap<Calendar, Double> actualChartDatas = new HashMap<Calendar, Double>();

				JsonObject actualChartDataJSONObj = (actualChartJSONObj.get("adat")
						.isJsonArray() ? null : actualChartJSONObj.get("adat")
						.getAsJsonObject());

				if (actualChartDataJSONObj != null) {

					for (Map.Entry<String, JsonElement> actData : actualChartDataJSONObj
							.entrySet()) {
						Calendar actualDate = Calendar.getInstance();
						try {
							actualDate.setTime(dateFormatter.parse(actData.getKey()));
							//CalendarPrinter.logCalendar(TAG, actualDate, actData.getValue().getAsDouble());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						actualChartDatas.put(actualDate, actData.getValue()
								.getAsDouble());
					}
					/* actualSubChart = new SubChart(actualChartLabel, actualChartDatas); */
					subchartsForResult.add(new SubChart(actualChartLabel, actualChartDatas));
					actualChartDatas.clear();
				}
			}
			result.setSubCharts(subchartsForResult);
		}
		return result;
	}

	
	
	
}
