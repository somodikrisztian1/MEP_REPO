package hu.mep.datamodells;

import java.net.URL;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * This class represent the user, after successful login. After authorization we
 * get back a JSON file, which from we can get the next attributes by the given
 * names.
 * 
 * mepID is coming from JSON attribute "mep_id" fullName is coming from JSON
 * attribute "nev" usersPlaces is coming from JSON attribute "tavfelugyeletek"
 * mekut is coming from JSON attribute "mekut" teacher is coming from JSON
 * attribute "tanar" moderator is coming from JSON attribute "moderator"
 * 
 * @author Tibor, Török
 */
public class User {

	private static final String TAG = "User.java";

	public static final String mepIDTag = "mep_id";
	public static final String nameTag = "nev";
	public static final String imageURLTag = "imageUrl";
	public static final String mekutTag = "mekut";
	public static final String teacherTag = "tanar";
	public static final String moderatorTag = "moderator";
	public static final String placesCountTag = "tavfelugyeletek_count";
	public static final String placesTag = "tavfelugyeletek";

	@SerializedName("mep_id")
	private int mepID;
	@SerializedName("nev")
	String fullName;
	@SerializedName("imageUrl")
	URL imageURL;
	@SerializedName("mekut")
	boolean mekut;
	@SerializedName("tanar")
	boolean teacher;
	@SerializedName("moderator")
	boolean moderator;

	@SerializedName("tavfelugyeletek")
	PlaceList usersPlaces;

	Bitmap profilePicture;

	public int getMepID() {
		return mepID;
	}

	public PlaceList getUsersPlaces() {
		return usersPlaces;
	}

	public void setUsersPlaces(PlaceList usersPlaces) {
		this.usersPlaces = usersPlaces;
	}

	public Bitmap getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Bitmap profilePicture) {
		this.profilePicture = profilePicture;
	}

	public URL getImageURL() {
		return imageURL;
	}

	public boolean isMekut() {
		return mekut;
	}

	public boolean isTeacher() {
		return teacher;
	}

	public User(int mepID, String fullName, URL imageURL, boolean mekut,
			boolean teacher, boolean moderator, PlaceList usersPlaces) {
		super();
		this.mepID = mepID;
		this.fullName = fullName;
		this.imageURL = imageURL;
		this.mekut = mekut;
		this.teacher = teacher;
		this.moderator = moderator;
		this.usersPlaces = usersPlaces;

		Log.e(TAG, "New User Has Been Created With The Following Values!");
		Log.e(TAG, "mepID=" + mepID);
		Log.e(TAG, "fullName=" + fullName);
		Log.e(TAG, "imageURL=" + imageURL);
		Log.e(TAG, "mekut=" + mekut);
		Log.e(TAG, "teacher=" + teacher);
		Log.e(TAG, "moderator=" + moderator);
		if(this.usersPlaces != null) {
			Log.d(TAG, "usersPlaces.count()=" + usersPlaces.places.size());
		}
		else {
			Log.d(TAG, "usersPlaces is null");
		}
	}

}
