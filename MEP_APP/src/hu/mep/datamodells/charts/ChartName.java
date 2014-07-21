package hu.mep.datamodells.charts;

import com.google.gson.annotations.SerializedName;

public class ChartName {

	@SerializedName("id")
	private int id;
	
	@SerializedName("ssz")
	private int serialNumber;
	
	@SerializedName("name")
	private String name;

	public int getId() {
		return id;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public String getName() {
		return name;
	}
	
	
	
}
