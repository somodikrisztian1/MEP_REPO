package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AuthenticationRunnable implements Runnable {

	private static Context context;
	private static String username;
	private static String password;
	
	public AuthenticationRunnable(Context context, String username, String password) {
		this.context = context;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public void run() {
		if(isOnline(context)) {
			Session.getInstance().getActualCommunicationInterface().authenticateUser(username, password);
		}
	}

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
}
