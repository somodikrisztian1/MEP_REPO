package hu.mep.datamodells;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

public class SubChart {

	@SerializedName("label")
	private String label;
	
	@SerializedName("adat")
	private HashMap<Calendar, Double> chartValues;

	public String getLabel() {
		return label;
	}

	public HashMap<Calendar, Double> getChartValues() {
		return chartValues;
	}

	public SubChart(String label, HashMap<Calendar, Double> chartValues) {
		super();
		this.label = label;
		this.chartValues = chartValues;
	}
	
	
	
	
	
}
