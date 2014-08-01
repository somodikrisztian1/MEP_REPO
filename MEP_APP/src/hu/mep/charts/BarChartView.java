package hu.mep.charts;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.BarChart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map.Entry;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.axis.CategoryAxis;
import org.afree.chart.axis.CategoryLabelPositions;
import org.afree.chart.axis.NumberAxis;
import org.afree.chart.plot.CategoryPlot;
import org.afree.chart.plot.PlotOrientation;
import org.afree.chart.renderer.category.BarRenderer;
import org.afree.data.category.CategoryDataset;
import org.afree.data.category.DefaultCategoryDataset;
import org.afree.graphics.GradientColor;
import org.afree.graphics.SolidColor;

import android.content.Context;
import android.graphics.Color;

public class BarChartView extends DemoView {

	private BarChart barChart;
	private boolean forRemoteMonitoring;

	private static final SimpleDateFormat monthNameFormatter = new SimpleDateFormat("MMMM");
	private static final SimpleDateFormat dayFormatter = new SimpleDateFormat("MM.dd.");
	
	public BarChartView(Context context, BarChart barChart, boolean forRemoteMonitoring) {
        super(context);

        this.barChart = barChart;
        this.forRemoteMonitoring = forRemoteMonitoring;
        
        CategoryDataset dataset = createDataset();
        AFreeChart chart = createChart(dataset);
        setChart(chart);
    }

    private CategoryDataset createDataset() {

        // row keys...
        String series = (barChart.getOption() == BarChart.OPTION_MONTHLY ? "Havi" : "Éves");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Entry<Calendar, Double> act: barChart.getChartValues().entrySet()) {
        	String columnName = null;
        	if(barChart.getOption() == BarChart.OPTION_MONTHLY) {
				columnName = dayFormatter.format(act.getKey().getTime());
			} else if (barChart.getOption() == BarChart.OPTION_ANNUAL) {
				columnName = monthNameFormatter.format(act.getKey().getTime());
			}
            dataset.addValue(act.getValue(), series, columnName);		
		}
        
        return dataset;
    }

    private AFreeChart createChart(CategoryDataset dataset) {

        // create the chart...
    	String title = null;
    	if(forRemoteMonitoring) {
    		title = Session.getActualRemoteMonitoring().getName();
    	} else {
    		title = Session.getActualTopic().getTopicName();
    	}
    	
        AFreeChart chart = ChartFactory.createBarChart(
            title,      // chart title
            "",               // domain axis label
            "Termelés (kWh)",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            false,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaintType(new SolidColor(Color.WHITE));

        // get a reference to the plot for further customisation...
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setRangePannable(false);
        

        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        
        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        //GradientColor gp0 = new GradientColor(R.color.mep_color, Color.rgb(6, 26, 50));
        GradientColor gp0 = new GradientColor(Color.rgb(0, 0, 255), Color.rgb(8, 150, 245));
        renderer.setSeriesPaintType(0, gp0);
     
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;

    }
}
