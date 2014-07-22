package hu.mep.datamodells.settings;

import java.util.List;

public class Settings {

	private List<Slider> sliders;
	
	private List<Relay> relays;
	
	private List<Function> functions;
	
	
	public Settings(List<Slider> sliders, List<Relay> relays,
			List<Function> functions) {
		super();
		this.sliders = sliders;
		this.relays = relays;
		this.functions = functions;
	}

	public List<Slider> getSliders() {
		return sliders;
	}

	public List<Relay> getRelays() {
		return relays;
	}

	public List<Function> getFunctions() {
		return functions;
	}
	
}
