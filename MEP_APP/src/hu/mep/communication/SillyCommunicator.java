package hu.mep.communication;

import hu.mep.datamodells.User;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class SillyCommunicator implements ICommunicator {

	private static SillyCommunicator instance = null;
	private static Context context;
	
	public SillyCommunicator() {
	}
	
	public static void setContext(Context newContext) {
		context = newContext;
	}
	
	public static synchronized SillyCommunicator getInstance() {
		if(instance == null) {
			instance = new SillyCommunicator();
		}
		return instance;
	}
	
	@Override
	public User authenticateUser(String username, String password) {
			Log.d("AUTHENTICATE - USERNAME", username);
			Log.d("AUTHENTICATE - PASSWORD", password);
			if(username.equals("a") && password.equals("a")) {
				Toast t = Toast.makeText(context, "Sikeres belépés!", Toast.LENGTH_SHORT);
			}
			else {
				Toast t = Toast.makeText(context, "Sikertelen belépés! (Próbáld \"a\"-\"a\" párossal!).", Toast.LENGTH_SHORT);
			}
		return new User();
	}

}
