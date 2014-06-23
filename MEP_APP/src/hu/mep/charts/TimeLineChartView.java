package hu.mep.charts;

import hu.mep.datamodells.Session;
import hu.mep.utils.adapters.TimeSeriesAdapter;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.axis.DateAxis;
import org.afree.chart.axis.ValueAxis;
import org.afree.chart.plot.XYPlot;
import org.afree.chart.renderer.xy.XYItemRenderer;
import org.afree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.afree.data.time.Month;
import org.afree.data.time.TimeSeries;
import org.afree.data.time.TimeSeriesCollection;
import org.afree.data.xy.XYDataset;
import org.afree.graphics.SolidColor;
import org.afree.graphics.geom.Shape;
import org.afree.ui.RectangleInsets;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

public class TimeLineChartView extends DemoView {

	private static final String TAG = "TimeLineChartView";
	TimeSeriesAdapter adapter;
	

	public TimeLineChartView(Context context, TimeSeriesAdapter adapter) {
		super(context);
		this.adapter = adapter;
		final AFreeChart chart = createChart(adapter.getTimeSeriesFromChartNEW(),
				Session.getActualChartInfoContainer().getName(), "Időpont", Session.getActualChart().getyAxisTitle());

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
		plot.setBackgroundPaintType(new SolidColor(Color.WHITE));
		plot.setDomainGridlinePaintType(new SolidColor(Color.WHITE));
		plot.setRangeGridlinePaintType(new SolidColor(Color.GRAY));
		
		ValueAxis rangeAxis = plot.getRangeAxis();
		/*rangeAxis.setRange(Session.getMinimalChartValue(), Session.getMaximalChartValue());*/
		rangeAxis.setAutoRange(true);
		
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(false);
		plot.setRangeCrosshairVisible(true);
		
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
			renderer.setBaseLinesVisible(true);
			for(int i = 0; i < dataset.getSeriesCount(); ++i) {
				renderer.setSeriesLinesVisible(i, true);
				renderer.setSeriesItemLabelsVisible(0, true);
				//Log.e(TAG, "VISIBLE?!" + renderer.getSeriesLinesVisible(i));
			}
		}

		DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
		domainAxis.setRange(Session.getMinimalChartDate(), Session.getMaximalChartDate());
		//domainAxis.setDateFormatOverride(new SimpleDateFormat("MMM dd. hh:mm", Locale.getDefault()));
		domainAxis.setAutoRange(true);		
		return chart;

	}
}
