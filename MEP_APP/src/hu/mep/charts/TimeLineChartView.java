package hu.mep.charts;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.Chart;
import hu.mep.utils.adapters.TimeSeriesAdapter;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.LegendItem;
import org.afree.chart.LegendItemCollection;
import org.afree.chart.axis.DateAxis;
import org.afree.chart.axis.ValueAxis;
import org.afree.chart.plot.Plot;
import org.afree.chart.plot.XYPlot;
import org.afree.chart.renderer.xy.XYItemRenderer;
import org.afree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.afree.data.xy.XYDataset;
import org.afree.graphics.SolidColor;
import org.afree.ui.RectangleInsets;

import android.content.Context;
import android.graphics.Color;

public class TimeLineChartView extends DemoView {

	//private static final String TAG = "TimeLineChartView";
	
	private Chart mChart;
	private boolean forRemoteMonitoring;
	
	private static final LegendItemCollection myColorPalette = new LegendItemCollection();
	private static final SolidColor[] colorPalette = new SolidColor[] {
	/* http://www.rapidtables.com/web/color/RGB_Color.htm */
			new SolidColor(Color.rgb(0, 0, 205)), // MEDIUM BLUE
			new SolidColor(Color.rgb(255, 140, 0)), // DARK ORANGE
			new SolidColor(Color.rgb(0, 100, 0)), // DARK GREEN
			new SolidColor(Color.rgb(255, 0, 0)), // RED
			new SolidColor(Color.rgb(139, 0, 139)), // DARK MAGENTA
			new SolidColor(Color.rgb(47, 79, 79)), // DARK SLATE GREY
			new SolidColor(Color.rgb(184, 134, 11)), // DARK GOLDEN ROD
			new SolidColor(Color.rgb(25, 25, 112)), // MIDNIGHT BLUE
			new SolidColor(Color.rgb(255, 20, 147)), // DEEP PINK
			new SolidColor(Color.rgb(139, 69, 19)), // SADDLE BROWN
			new SolidColor(Color.rgb(75, 0, 130)), // INDIGO
			new SolidColor(Color.rgb(128, 128, 0)), // OLIVE
			new SolidColor(Color.rgb(0, 128, 128)), // TEAL
			new SolidColor(Color.rgb(0, 255, 0)) // LIME

	};

	public TimeLineChartView(Context context, Chart chart, boolean forRemoteMonitoring) {
		super(context);
		
		this.mChart = chart;
		this.forRemoteMonitoring = forRemoteMonitoring;

		if(forRemoteMonitoring) {
			final AFreeChart aChart = createChart(
					TimeSeriesAdapter.getTimeSeriesFromChart(mChart), Session.getActualRemoteMonitoring().getName(), "",
					mChart.getyAxisTitle());
			setChart(aChart);
		} else {
			final AFreeChart aChart = createChart(
					TimeSeriesAdapter.getTimeSeriesFromChart(mChart), Session.getActualTopic().getTopicName(), "",
					mChart.getyAxisTitle());
			setChart(aChart);
		}
	}

	private AFreeChart createChart(XYDataset dataset, String title,
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
		plot.setRangeGridlinePaintType(new SolidColor(Color.LTGRAY));

		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setAutoRange(true);

		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(false);
			// renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
			renderer.setBaseLinesVisible(true);

			LegendItemCollection legend = new LegendItemCollection();
			for (int i = 0; i < mChart.getSubCharts().size(); ++i) {
				renderer.setSeriesLinesVisible(i, true);
				renderer.setSeriesItemLabelsVisible(0, true);
				renderer.setSeriesVisibleInLegend(i, true);
				renderer.setSeriesPaintType(i, colorPalette[i]);

				LegendItem li = new LegendItem(mChart.getSubCharts().get(i)
						.getLabel(), "-", null, null,
						Plot.DEFAULT_LEGEND_ITEM_BOX, colorPalette[i]);
				li.setFillPaintType(colorPalette[i]);
				li.setLabelPaintType(colorPalette[i]);

				legend.add(li);
			}
			plot.setFixedLegendItems(legend);
			/*
			 * for(int i = 0; i < mChart.getSubCharts().size(); ++i) {
			 * renderer.setLegendTextPaintType(i, colorPalette[i]); }
			 */
		}

		DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
		/*
		 * domainAxis.setRange(Session.getMinimalChartDate(),
		 * Session.getMaximalChartDate()); domainAxis.setDateFormatOverride(new
		 * SimpleDateFormat("MMM dd. hh:mm", Locale.getDefault()));
		 */
		domainAxis.setAutoRange(true);
		return chart;

	}
}
