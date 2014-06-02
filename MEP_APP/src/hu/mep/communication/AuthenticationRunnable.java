package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

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
		if(NetThread.isOnline(context)) {
			Session.getInstance().getActualCommunicationInterface().authenticateUser(username, password);
		}

	}

	
}
