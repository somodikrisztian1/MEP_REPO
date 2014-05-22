package hu.mep.communication;

import android.util.Log;
import android.widget.Toast;

public class SillyCommunicator implements ICommunicator {

	private static SillyCommunicator instance = null;
	
	public SillyCommunicator() {
	}
	
	public static synchronized SillyCommunicator getInstance() {
		if(instance == null) {
			instance = new SillyCommunicator();
		}
		return instance;
	}
	
	@Override
	public boolean authenticateUser(String username, String password) {
			Log.d("AUTHENTICATE - USERNAME", username);
			Log.d("AUTHENTICATE - PASSWORD", password);
		return false;
	}

}
