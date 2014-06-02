package hu.mep.datamodells;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

		Log.d(TAG, "New User Has Been Created With The Following Values!");
		Log.d(TAG, "mepID=" + mepID);
		Log.d(TAG, "fullName=" + fullName);
		Log.d(TAG, "imageURL=" + imageURL);
		Log.d(TAG, "mekut=" + mekut);
		Log.d(TAG, "teacher=" + teacher);
		Log.d(TAG, "moderator=" + moderator);

	}
	
	public void downloadProfilePicture() {
		    try {
		        
		        HttpURLConnection connection = (HttpURLConnection) this.imageURL
		                .openConnection();
		        connection.setDoInput(true);
		        connection.connect();
		        InputStream input = connection.getInputStream();
		        this.profilePicture = BitmapFactory.decodeStream(input);

		    } catch (IOException e) {
		        e.printStackTrace();
		        Log.e("getBmpFromUrl error: ", e.getMessage().toString());
		        return;
		    }
		}
}
