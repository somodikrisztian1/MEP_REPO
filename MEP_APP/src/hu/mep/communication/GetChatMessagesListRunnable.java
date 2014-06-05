package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.util.Log;

public class GetChatMessagesListRunnable implements Runnable {
	
	private Context context;
	
	public GetChatMessagesListRunnable(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		if (NetThread.isOnline(context)) {
			//Log.e("GetChatMessagesListRunnable", "before");
			Session.getInstance(context).getActualCommunicationInterface().getChatMessages();
			//Log.e("GetChatMessagesListRunnable", "after");			
		}
	}

}
