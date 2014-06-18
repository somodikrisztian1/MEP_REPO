package hu.mep.datamodells;

import java.util.Date;
import java.util.HashMap;

import com.google.gson.annotations.SerializedName;

public class SubChart {

	@SerializedName("label")
	private String label;
	
	@SerializedName("adat")
	private HashMap<Date, Double> chartValues;
	
	
}
