package hu.mep.datamodells;

import com.google.gson.annotations.SerializedName;

public class ChartInfoContainer {

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
