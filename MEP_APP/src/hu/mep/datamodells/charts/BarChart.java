package hu.mep.datamodells.charts;

import hu.mep.utils.others.CalendarPrinter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BarChart {
	
	public static final int OPTION_MONTHLY = 0;
	public static final int OPTION_ANNUAL = 1;
	private static final String TAG = "BarChart";
	
	String title;
	int option;
	HashMap<Calendar, Double> chartValues;
	
	public BarChart(HashMap<Calendar, Double> chartValues, int option) {
		super();
		this.option = option;
		this.chartValues = chartValues;
	}
	
	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public HashMap<Calendar, Double> getChartValues() {
		return chartValues;
	}

	public void setChartValues(HashMap<Calendar, Double> chartValues) {
		this.chartValues = chartValues;
	}
	
	public void sortChartValues() {
		Map<Calendar, Double> map = chartValues;
		
		List<Map.Entry<Calendar, Double>> list = new LinkedList<Map.Entry<Calendar, Double>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<Calendar, Double>>() {
		@Override
		public int compare(Entry<Calendar, Double> lhs, Entry<Calendar, Double> rhs) {
			if(lhs.getKey().before(rhs.getKey())) {
				return 1;
			}
			else if(lhs.getKey().after(rhs.getKey())) {
				return -1;
			} else {
				return 0;
			}
		}
		});
		
		chartValues.clear();
		for (Entry<Calendar, Double> entry : list) {
			chartValues.put(entry.getKey(), entry.getValue());
		}
		
		for (Entry<Calendar, Double> act : chartValues.entrySet()) {
			CalendarPrinter.logCalendar(TAG, act.getKey(), act.getValue());
		}
	}
	
}
