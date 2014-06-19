package hu.mep.datamodells;

import java.util.List;

public class AllChartInfoContainer {

	private List<ChartInfoContainer> allChartInfoContainer;

	public List<ChartInfoContainer> getAllChartInfoContainer() {
		return allChartInfoContainer;
	}

	public AllChartInfoContainer(List<ChartInfoContainer> allChartInfoContainer) {
		super();
		this.allChartInfoContainer = allChartInfoContainer;
	}

	public void setAllChartInfoContainer(
			List<ChartInfoContainer> allChartInfoContainer) {
		this.allChartInfoContainer = allChartInfoContainer;
	}
	
	
}
