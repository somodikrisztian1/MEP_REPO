package hu.mep.datamodells.charts;

import java.util.List;

public class AllChartNames {

	private List<ChartName> allChartNames;

	public List<ChartName> getAllChartNames() {
		return this.allChartNames;
	}

	public AllChartNames(List<ChartName> newAllChartNames) {
		super();
		this.allChartNames = newAllChartNames;
	}

	public void setAllChartNames(List<ChartName> newAllChartNames) {
		this.allChartNames = newAllChartNames;
	}
	
}
