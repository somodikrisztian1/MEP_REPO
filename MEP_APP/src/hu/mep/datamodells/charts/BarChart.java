package hu.mep.datamodells.charts;

import java.util.Calendar;
import java.util.HashMap;

public class BarChart {

	HashMap<Calendar, Float> chartValues;

	public HashMap<Calendar, Float> getChartValues() {
		return chartValues;
	}

	public void setChartValues(HashMap<Calendar, Float> chartValues) {
		this.chartValues = chartValues;
	}

	public BarChart(HashMap<Calendar, Float> chartValues) {
		super();
		this.chartValues = chartValues;
	}
	
	
	
	
}
