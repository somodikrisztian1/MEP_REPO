package hu.mep.datamodells.settings;

public class Slider {

	public int serialNumber;

	public double value;

	public double minValue;

	public double maxValue;

	public String name;

	public String label;

	public Slider(int serialNumber, double value, double minValue,
			double maxValue, String name, String label) {
		this.serialNumber = serialNumber;
		this.value = value;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.name = name;
		this.label = label;
	}

}
