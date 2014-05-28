package hu.mep.datamodells;

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
	
	String name;
	String ID;
	String description;
	String location;
	
	
	public static final String nameTag = "nev";
	public static final String idTag = "tsz1_id";
	public static final String descriptionTag = "leiras";
	public static final String locationTag = "helyszin";
	
	
	public Place(String name, String ID, String description, String location) {
		super();
		this.name = name;
		this.ID = ID;
		this.description = description;
		this.location = location;
		
		Log.d(TAG, "New Place For Remote Monitoring Has Been Succesfully Created With The Following Values:");
		Log.d(TAG, "name=" + name);
		Log.d(TAG, "id=" + ID);
		Log.d(TAG, "description=" + description);
		Log.d(TAG, "location=" + location);
	}

}
