package hu.mep.utils.adapters;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.Chart;
import hu.mep.datamodells.charts.SubChart;

import java.util.Calendar;
import java.util.Map;

import org.afree.data.time.Second;
import org.afree.data.time.TimeSeries;
import org.afree.data.time.TimeSeriesCollection;
import org.afree.data.xy.XYDataset;

public class TimeSeriesAdapter {

	//private static final String TAG = "TimeSeriesAdapter";

	public XYDataset getTimeSeriesFromActualChart() {

		if (Session.getActualChart().getSubCharts() == null) {
			return null;
		}
		int i = -1;
		int howManyTimeSeries = Session.getActualChart().getSubCharts().size();
		// Log.e(TAG, "Idősorok száma:" + howManyTimeSeries);
		TimeSeries[] ts = new TimeSeries[howManyTimeSeries];

		for (SubChart actSubChart : Session.getActualChart().getSubCharts()) {
			ts[++i] = new TimeSeries(actSubChart.getLabel());
			// Log.e(TAG, "Label:" + actSubChart.getLabel());

			for (Map.Entry<Calendar, Double> actualValues : actSubChart
					.getChartValues().entrySet()) {
				Calendar date = actualValues.getKey();
				ts[i].add(new Second(date.getTime()), actualValues.getValue());
				// CalendarPrinter.logCalendar(TAG, date, actualValues.getValue());
			}
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (int i2 = 0; i2 < howManyTimeSeries; ++i2) {
			dataset.addSeries(ts[i2]);
		}

		return dataset;
	}

	public static XYDataset getTimeSeriesFromChart(Chart cchart) {

		if (cchart.getSubCharts() == null) {
			// Log.e(TAG, "getSubCharts is null");
			return null;
		}
		int i = -1;
		int howManyTimeSeries = cchart.getSubCharts().size();
		// Log.e(TAG, "Idősorok száma:" + howManyTimeSeries);
		TimeSeries[] ts = new TimeSeries[howManyTimeSeries];

		for (SubChart actSubChart : cchart.getSubCharts()) {
			ts[++i] = new TimeSeries(actSubChart.getLabel());
			//Log.e(TAG, "Label:" + actSubChart.getLabel());

			for (Map.Entry<Calendar, Double> actualValues : actSubChart
					.getChartValues().entrySet()) {
				Calendar date = actualValues.getKey();
				ts[i].add( new Second(date.getTime()),	actualValues.getValue());
				//CalendarPrinter.logCalendar(TAG, date, actualValues.getValue());
			}
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (int i2 = 0; i2 < howManyTimeSeries; ++i2) {
			dataset.addSeries(ts[i2]);
		}
		return dataset;
	}
}
