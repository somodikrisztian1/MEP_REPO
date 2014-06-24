package hu.mep.utils.adapters;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.SubChart;
import hu.mep.utils.others.CalendarPrinter;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.afree.data.time.Millisecond;
import org.afree.data.time.Second;
import org.afree.data.time.TimeSeries;
import org.afree.data.time.TimeSeriesCollection;
import org.afree.data.xy.XYDataset;

import android.util.Log;

public class TimeSeriesAdapter {

	private static final String TAG = "TimeSeriesAdapter2";
	
	//TODO Ez csak egy teszt metódus! Végül ki kell majd venni!!!
	public XYDataset getTimeSeriesFromTheFly() {

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries[] s = new TimeSeries[2];

		s[0] = new TimeSeries("Első görbe");
		s[0].add(new Millisecond(00, 00, 01, 16, 18, 06, 2014), 56.6);
		s[0].add(new Millisecond(00, 10, 01, 16, 18, 06, 2014), 58.0);
		s[0].add(new Millisecond(00, 20, 01, 16, 18, 06, 2014), 60.6);
		s[0].add(new Millisecond(00, 30, 01, 16, 18, 06, 2014), 54.6);
		s[0].add(new Millisecond(00, 40, 01, 16, 18, 06, 2014), 50.6);
		s[0].add(new Millisecond(00, 50, 01, 16, 18, 06, 2014), 43.6);
		s[0].add(new Millisecond(00, 00, 02, 16, 18, 06, 2014), 36.6);
		s[0].add(new Millisecond(00, 10, 02, 16, 18, 06, 2014), 42.6);

		s[1] = new TimeSeries("Másik görbe");
		s[1].add(new Millisecond(00, 20, 02, 16, 18, 06, 2014), 40.6);
		s[1].add(new Millisecond(00, 30, 02, 16, 18, 06, 2014), 39.6);
		s[1].add(new Millisecond(00, 40, 02, 16, 18, 06, 2014), 45.6);
		s[1].add(new Millisecond(00, 50, 02, 16, 18, 06, 2014), 46.6);
		s[1].add(new Millisecond(00, 00, 03, 16, 18, 06, 2014), 47.6);
		s[1].add(new Millisecond(00, 10, 03, 16, 18, 06, 2014), 48.6);
		s[1].add(new Millisecond(00, 20, 03, 16, 18, 06, 2014), 50.6);
		s[1].add(new Millisecond(00, 30, 03, 16, 18, 06, 2014), 60.6);
		s[1].add(new Millisecond(00, 40, 03, 16, 18, 06, 2014), 55.6);
		s[1].add(new Millisecond(00, 50, 03, 16, 18, 06, 2014), 53.6);

		for (int i = 0; i < s.length; ++i) {
			dataset.addSeries(s[i]);
		}
		return dataset;
	}

	public XYDataset getTimeSeriesFromActualChart() {

		if (Session.getActualChart().getSubCharts() == null) {
			//Log.e(TAG, "getSubCharts is null");
			return null;
		}
		int i = -1;
		int howManyTimeSeries = Session.getActualChart().getSubCharts().size();
		//Log.e(TAG, "Idősorok száma:" + howManyTimeSeries);
		TimeSeries[] ts = new TimeSeries[howManyTimeSeries];

		for (SubChart actSubChart : Session.getActualChart().getSubCharts()) {
			ts[++i] = new TimeSeries(actSubChart.getLabel());
			//Log.e(TAG, "Label:" + actSubChart.getLabel());

			for (Map.Entry<Calendar, Double> actualValues : actSubChart
					.getChartValues().entrySet()) {
				Calendar date = actualValues.getKey();
				ts[i].add(
						new Second
								(
								date.get(Calendar.SECOND), 
								date.get(Calendar.MINUTE), 
								date.get(Calendar.HOUR_OF_DAY), 
								date.get(Calendar.DAY_OF_MONTH), 
								date.get(Calendar.MONTH), 
								date.get(Calendar.YEAR)
								),
						actualValues.getValue()
						);

				//CalendarPrinter.logCalendar(TAG, date, actualValues.getValue());
			}
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (int i2 = 0; i2 < howManyTimeSeries; ++i2) {

			/*for (int j = 0; j < ts[i2].getItemCount(); ++j) {
				Log.e(TAG + " ts[" + i2 + "][" + j + "]",
						"" + ts[i2].getTimePeriod(j) + "#\t" + ts[i2].getValue(j));
			}
			 */
			dataset.addSeries(ts[i2]);
		}

		return dataset;
	}

	public XYDataset getTimeSeriesFromChartNEW() {

		if (Session.getActualChart().getSubCharts() == null) {
			Log.e(TAG, "getSubCharts is null");
			return null;
		}
		int howManyTimeSeries = Session.getActualChart().getSubCharts().size();
		Log.e(TAG, "Idősorok száma:" + howManyTimeSeries);
		TimeSeries[] ts = new TimeSeries[howManyTimeSeries];

		Map.Entry<Calendar, Double> keyValuePair;
		SubChart actSubChart;
		Calendar date;
		Iterator<Entry<Calendar, Double>> it = null;
		for (int i = 0; i < howManyTimeSeries; ++i) {
			Log.d(TAG, "i = " + i);
			actSubChart = null;
			actSubChart = new SubChart(Session.getActualChart().getSubCharts()
					.get(i).getLabel(), Session.getActualChart().getSubCharts()
					.get(i).getChartValues());
			// actSubChart = Session.getActualChart().getSubCharts().get(i);
			// Log.e(TAG, "actSubChart.getLabel() = " + actSubChart.getLabel());
			ts[i] = new TimeSeries(actSubChart.getLabel());
			Log.e(TAG, "Label:" + actSubChart.getLabel());
			it = null;

			it = actSubChart.getChartValues().entrySet().iterator();
			while (it.hasNext()) {
				Log.d(TAG, "while(it.hasnext()");
				keyValuePair = it.next();
				/*
				 * Log.e(TAG, keyValuePair.getKey() + "#" +
				 * keyValuePair.getValue());
				 */date = keyValuePair.getKey();

				ts[i].add(
						new Second(date.get(Calendar.SECOND), date
								.get(Calendar.MINUTE), date
								.get(Calendar.HOUR_OF_DAY), date
								.get(Calendar.DAY_OF_MONTH), date
								.get(Calendar.MONTH), date.get(Calendar.YEAR)),
						keyValuePair.getValue());
				Log.e(TAG,
						date.get(Calendar.YEAR) + "-"
								+ date.get(Calendar.MONTH) + "-"
								+ date.get(Calendar.DAY_OF_MONTH) + " "
								+ date.get(Calendar.HOUR_OF_DAY) + ":"
								+ date.get(Calendar.MINUTE) + ":"
								+ date.get(Calendar.SECOND) + "#\t"
								+ keyValuePair.getValue().toString());

			}
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (int i = 0; i < howManyTimeSeries; ++i) {

			for (int j = 0; j < ts[i].getItemCount(); ++j) {
				Log.e(TAG + " ts[" + i + "][" + j + "]",
						"" + ts[i].getTimePeriod(j) + "#\t" + ts[i].getValue(j));
			}

			dataset.addSeries(ts[i]);
		}

		return dataset;
	}
}
