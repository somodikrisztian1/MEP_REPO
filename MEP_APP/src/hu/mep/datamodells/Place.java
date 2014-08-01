package hu.mep.datamodells;

import java.util.Calendar;

import com.google.gson.annotations.SerializedName;

import android.util.Log;

/** This class represent the places for remote monitoring of the user.
 * After authorization we get back a JSON file, which from we can get the next attributes by the given names.
 * 
 * name is coming from JSON attribute named by the value of nameTag
 * ID is coming from JSON attribute named by the value of idTag
 * description is coming from JSON attribute named by the value of descriptionTag
 * location is coming from JSON attribute named by the locationTag
 * 
 * @author Tibor, Török
 */
public class Place {

	private static final String TAG = "Place.java";
	
	public static final String nameTag = "nev";
	public static final String idTag = "tsz1_id";
	public static final String descriptionTag = "leiras";
	public static final String locationTag = "helyszin";
	public static final String solarPanelTag = "is_napelem";
	
	@SerializedName(nameTag)
	private String name;
	
	@SerializedName(idTag)
	private String ID;
	
	@SerializedName(descriptionTag)
	private String description;
	
	@SerializedName(locationTag)
	private String location;
	
	@SerializedName(solarPanelTag)
	private boolean solarPanel;
	
	private boolean workingProperly;
	
	private String lastWorkingText;
	
	public Place(String name, String ID, String description, String location, boolean solarPanel) {
		super();
		this.name = name;
		this.ID = ID;
		this.description = description;
		this.location = location;
		this.solarPanel = solarPanel;
		this.workingProperly = true;
	
		Log.d(TAG, "New Place For Remote Monitoring Has Been Succesfully Created With The Following Values:");
		Log.d(TAG, "name=" + name);
		Log.d(TAG, "id=" + ID);
		Log.d(TAG, "description=" + description);
		Log.d(TAG, "location=" + location);
		Log.d(TAG, "solarPanel=" + solarPanel);
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getLocation() {
		return location;
	}	
	
	public String getID() {
		return ID;
	}

	public boolean isSolarPanel() {
		return solarPanel;
	}

	public boolean isWorkingProperly() {
		return workingProperly;
	}

	public void setWorkingProperly(boolean workingProperly) {
		this.workingProperly = workingProperly;
	}

	public String getLastWorkingText() {
		return lastWorkingText;
	}

	public void setLastWorkingText(String lastWorkingText) {
		this.lastWorkingText = lastWorkingText;
	}
	
	
	
}
