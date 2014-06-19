package hu.mep.charts;

import hu.mep.utils.adapters.TimeSeriesAdapter;

import java.text.SimpleDateFormat;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.axis.DateAxis;
import org.afree.chart.plot.XYPlot;
import org.afree.chart.renderer.xy.XYItemRenderer;
import org.afree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.afree.data.time.Month;
import org.afree.data.time.TimeSeries;
import org.afree.data.time.TimeSeriesCollection;
import org.afree.data.xy.XYDataset;
import org.afree.graphics.SolidColor;
import org.afree.ui.RectangleInsets;

import android.content.Context;
import android.graphics.Color;

public class TimeLineChartView extends DemoView {

	TimeSeriesAdapter adapter;

	public TimeLineChartView(Context context, TimeSeriesAdapter adapter) {
		super(context);
		this.adapter = adapter;
		final AFreeChart chart = createChart(adapter.getTimeSeriesFromTheFly(),
				"Puffertartály", "Időpont", "Hőmérséklet");

		setChart(chart);
	}

	private static AFreeChart createChart(XYDataset dataset, String title,
			String xAxisLabel, String yAxisLabel) {

		AFreeChart chart = ChartFactory.createTimeSeriesChart(title, // title
				xAxisLabel, // x-axis label
				yAxisLabel, // y-axis label
				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaintType(new SolidColor(Color.WHITE));

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaintType(new SolidColor(Color.LTGRAY));
		plot.setDomainGridlinePaintType(new SolidColor(Color.WHITE));
		plot.setRangeGridlinePaintType(new SolidColor(Color.WHITE));
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("yyyy.MM.dd. hh:mm::ss"));

		return chart;

	}

	private static XYDataset createDataset() {

		TimeSeries s1 = new TimeSeries("Egy szíp gőőbe");
		s1.add(new Month(2, 2001), 181.8);
		s1.add(new Month(3, 2001), 167.3);
		s1.add(new Month(4, 2001), 153.8);
		s1.add(new Month(5, 2001), 167.6);
		s1.add(new Month(6, 2001), 158.8);
		s1.add(new Month(7, 2001), 148.3);
		s1.add(new Month(8, 2001), 153.9);
		s1.add(new Month(9, 2001), 142.7);
		s1.add(new Month(10, 2001), 123.2);
		s1.add(new Month(11, 2001), 131.8);
		s1.add(new Month(12, 2001), 139.6);
		s1.add(new Month(1, 2002), 142.9);
		s1.add(new Month(2, 2002), 138.7);
		s1.add(new Month(3, 2002), 137.3);
		s1.add(new Month(4, 2002), 143.9);
		s1.add(new Month(5, 2002), 139.8);
		s1.add(new Month(6, 2002), 137.0);
		s1.add(new Month(7, 2002), 132.8);
		/*
		 * TimeSeries s2 = new TimeSeries("Másik gyönnnyörrrű görbe");
		 * s2.add(new Month(2, 2001), 129.6); s2.add(new Month(3, 2001), 123.2);
		 * s2.add(new Month(4, 2001), 117.2); s2.add(new Month(5, 2001), 124.1);
		 * s2.add(new Month(6, 2001), 122.6); s2.add(new Month(7, 2001), 119.2);
		 * s2.add(new Month(8, 2001), 116.5); s2.add(new Month(9, 2001), 112.7);
		 * s2.add(new Month(10, 2001), 101.5); s2.add(new Month(11, 2001),
		 * 106.1); s2.add(new Month(12, 2001), 110.3); s2.add(new Month(1,
		 * 2002), 111.7); s2.add(new Month(2, 2002), 111.0); s2.add(new Month(3,
		 * 2002), 109.6); s2.add(new Month(4, 2002), 113.2); s2.add(new Month(5,
		 * 2002), 111.6); s2.add(new Month(6, 2002), 108.8); s2.add(new Month(7,
		 * 2002), 101.6);
		 */

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);
		// dataset.addSeries(s2);

		return dataset;

	}

}
