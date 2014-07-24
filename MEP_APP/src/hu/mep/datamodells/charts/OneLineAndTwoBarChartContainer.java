package hu.mep.datamodells.charts;

public class OneLineAndTwoBarChartContainer {
	
	Chart lineChart;
	
	BarChart monthly;
	
	BarChart annually;

	public Chart getLineChart() {
		return lineChart;
	}

	public void setLineChart(Chart lineChart) {
		this.lineChart = lineChart;
	}

	public BarChart getMonthly() {
		return monthly;
	}

	public void setMonthly(BarChart monthly) {
		this.monthly = monthly;
	}

	public BarChart getAnnually() {
		return annually;
	}

	public void setAnnually(BarChart annually) {
		this.annually = annually;
	}

	public OneLineAndTwoBarChartContainer(Chart lineChart, BarChart monthly,
			BarChart annually) {
		super();
		this.lineChart = lineChart;
		this.monthly = monthly;
		this.annually = annually;
	}
	
	
	

}
