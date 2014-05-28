package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetThread extends Thread {
	public Context context;

	public NetThread(Context context, Runnable r) {
		super(r);
		this.context = context;
	}

	@Override
	public synchronized void start() {
		if (!isOnline(context)) {
			Toast.makeText(context, "Nincs internetkapcsolat!", Toast.LENGTH_LONG).show();
			return;
		}
		super.start();
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