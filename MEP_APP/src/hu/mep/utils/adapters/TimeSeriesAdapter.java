package hu.mep.utils.adapters;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.SubChart;

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
import android.widget.Toast;

public class TimeSeriesAdapter {

	private static final String TAG = "TimeSeriesAdapter2";

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

		for(int i = 0; i < s.length; ++i) {
			dataset.addSeries(s[i]);
		}
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
		
		if(Session.getActualChart().getSubCharts() == null) {
			Log.e(TAG, "getSubCharts is null");
			return null;
		}
		int howManyTimeSeries = Session.getActualChart().getSubCharts().size();
		Log.e(TAG, "Idősorok száma:" + howManyTimeSeries);
		TimeSeries[] ts = new TimeSeries[howManyTimeSeries];
		
		Map.Entry<Date, Double> keyValuePair;
		Date date;
		SubChart actSubChart;
		Calendar c = Calendar.getInstance();
		for (int i = 0; i < howManyTimeSeries; ++i) {
			actSubChart = Session.getActualChart().getSubCharts().get(i);
			Log.e(TAG, "actSubChart.getLabel() = " + actSubChart.getLabel());
			ts[i] = new TimeSeries(actSubChart.getLabel());
			Iterator<Entry<Date, Double>> it = actSubChart.getChartValues()
					.entrySet().iterator();
			while (it.hasNext()) {
				keyValuePair = it.next();
				date = keyValuePair.getKey();
				c.setTime(date);
				ts[i].add(new Second(
						c.get(Calendar.SECOND), 
						c.get(Calendar.MINUTE), 
						c.get(Calendar.HOUR_OF_DAY), 
						c.get(Calendar.DAY_OF_MONTH), 
						c.get(Calendar.MONTH), 
						c.get(Calendar.YEAR)), keyValuePair.getValue());
				
				
				/*Log.e(TAG,
						c.get(Calendar.YEAR) + "-" +
						c.get(Calendar.MONTH) + "-" +
						c.get(Calendar.DAY_OF_MONTH) + " " +								
						c.get(Calendar.HOUR_OF_DAY) + ":" +
						c.get(Calendar.MINUTE) + ":" +
						c.get(Calendar.SECOND) + "#"+ 
						keyValuePair.getValue().toString() );*/
			}
		}
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(int i = 0; i < howManyTimeSeries; ++i) {
			dataset.addSeries(ts[i]);
		}

		return dataset;
	}

}
