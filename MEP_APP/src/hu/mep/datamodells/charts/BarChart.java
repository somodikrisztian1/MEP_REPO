package hu.mep.datamodells.charts;

import java.util.Calendar;
import java.util.HashMap;

public class BarChart {

	HashMap<Calendar, Double> chartValues;

	public HashMap<Calendar, Double> getChartValues() {
		return chartValues;
	}

	public void setChartValues(HashMap<Calendar, Double> chartValues) {
		this.chartValues = chartValues;
	}

	public BarChart(HashMap<Calendar, Double> chartValues) {
		super();
		this.chartValues = chartValues;
	}
	
	
	
	
}
