package hu.mep.utils.adapters;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.SubChart;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.afree.data.time.Millisecond;
import org.afree.data.time.Second;
import org.afree.data.time.TimeSeries;
import org.afree.data.time.TimeSeriesCollection;
import org.afree.data.xy.XYDataset;

public class TimeSeriesAdapter {

	public XYDataset getTimeSeriesFromTheFly() {
		TimeSeries s1 = new TimeSeries("Egyik szíp görbe");
		s1.add(new Millisecond(00, 00, 01, 16, 18, 06, 2014), 56.6);
		s1.add(new Millisecond(00, 10, 01, 16, 18, 06, 2014), 58.0);
		s1.add(new Millisecond(00, 20, 01, 16, 18, 06, 2014), 60.6);
		s1.add(new Millisecond(00, 30, 01, 16, 18, 06, 2014), 54.6);
		s1.add(new Millisecond(00, 40, 01, 16, 18, 06, 2014), 50.6);
		s1.add(new Millisecond(00, 50, 01, 16, 18, 06, 2014), 43.6);
		s1.add(new Millisecond(00, 00, 02, 16, 18, 06, 2014), 36.6);
		s1.add(new Millisecond(00, 10, 02, 16, 18, 06, 2014), 42.6);
		s1.add(new Millisecond(00, 20, 02, 16, 18, 06, 2014), 40.6);
		s1.add(new Millisecond(00, 30, 02, 16, 18, 06, 2014), 39.6);
		s1.add(new Millisecond(00, 40, 02, 16, 18, 06, 2014), 45.6);
		s1.add(new Millisecond(00, 50, 02, 16, 18, 06, 2014), 46.6);
		s1.add(new Millisecond(00, 00, 03, 16, 18, 06, 2014), 47.6);
		s1.add(new Millisecond(00, 10, 03, 16, 18, 06, 2014), 48.6);
		s1.add(new Millisecond(00, 20, 03, 16, 18, 06, 2014), 50.6);
		s1.add(new Millisecond(00, 30, 03, 16, 18, 06, 2014), 60.6);
		s1.add(new Millisecond(00, 40, 03, 16, 18, 06, 2014), 55.6);
		s1.add(new Millisecond(00, 50, 03, 16, 18, 06, 2014), 53.6);

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);

		return dataset;
	}

	public XYDataset getTimeSeriesFromChart() {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries ts;
		
		Map.Entry<Date, Double> keyValuePair;
		Date date;
		
		for (SubChart actSubChart : Session.getActualChart().getSubCharts()) {
			ts = new TimeSeries(actSubChart.getLabel());
			Iterator<Entry<Date, Double>> it = actSubChart.getChartValues()
					.entrySet().iterator();
			while (it.hasNext()) {
				keyValuePair = it.next();
				date = keyValuePair.getKey();
				ts.add(new Second(date.getSeconds(), date.getMinutes(), date
						.getHours(), date.getDay(), date.getMonth(), date
						.getYear() + 1900), keyValuePair.getValue());
			}
			dataset.addSeries(ts);
			ts = null;
		}

		return dataset;
	}
	
	public XYDataset getTimeSeriesFromChartNEW() {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		int howManyTimeSeries = Session.getActualChart().getSubCharts().size();
		TimeSeries[] ts = new TimeSeries[howManyTimeSeries];
		
		Map.Entry<Date, Double> keyValuePair;
		Date date;
		SubChart actSubChart;
		for (int i = 0; i < howManyTimeSeries; ++i) {
			actSubChart = Session.getActualChart().getSubCharts().get(i);
			ts[i] = new TimeSeries(actSubChart.getLabel());
			Iterator<Entry<Date, Double>> it = actSubChart.getChartValues()
					.entrySet().iterator();
			while (it.hasNext()) {
				keyValuePair = it.next();
				date = keyValuePair.getKey();
				ts[i].add(new Second(date.getSeconds(), date.getMinutes(), date
						.getHours(), date.getDay(), date.getMonth(), date
						.getYear() + 1900), keyValuePair.getValue());
			}
			dataset.addSeries(ts[i]);
		}

		return dataset;
	}

}
