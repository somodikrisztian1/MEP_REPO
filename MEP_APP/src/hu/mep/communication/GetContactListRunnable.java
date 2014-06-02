package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class GetContactListRunnable implements Runnable {

	private Context context;

	public GetContactListRunnable(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		if (NetThread.isOnline(context)) {
			Session.getInstance().getActualCommunicationInterface()
					.getChatPartners();
		}
	}

}
