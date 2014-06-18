package hu.mep.datamodells;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Chart {

	@SerializedName("elapse")
	private String elapse;
	
	@SerializedName("y")
	private String yAxisTitle;
	
	@SerializedName("charts")
	private List<SubChart> subCharts;
	
	
}
