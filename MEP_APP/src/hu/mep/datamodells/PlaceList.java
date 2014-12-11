package hu.mep.datamodells;

import java.util.List;

public class PlaceList {
	List<Place> places;

	public List<Place> getPlaces() {
		return places;
	}

	public PlaceList(List<Place> places) {
		super();
		this.places = places;
	}
	
	public Place findPlaceByID(String id) {
		for (Place actPlace : places) {
			if(actPlace.getID().equals(id)) {
				return actPlace;
			}
		}
		return null;
	}
	
}
