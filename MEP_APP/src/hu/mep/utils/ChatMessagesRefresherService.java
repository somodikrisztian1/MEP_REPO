package hu.mep.utils;

import hu.mep.datamodells.ChatMessagesList;
import hu.mep.datamodells.Session;
import android.app.IntentService;
import android.content.Intent;

public class ChatMessagesRefresherService extends IntentService {
	
	public static final int MessagesNotChanged = 0;
	public static final int MessagesChanged = 1;
	
	public static final String RESULT = "result";
	public static final String NOTIFICATION = "hu.mep.mep_app";
	
	private static int refreshState = MessagesNotChanged;
	
	public ChatMessagesRefresherService() {
		super("ChatMessagesRefresherService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		ChatMessagesList before = Session.getChatMessagesList();
		Session.getActualCommunicationInterface().getChatMessages();
		ChatMessagesList after = Session.getChatMessagesList();
		if(before.getChatMessagesList().containsAll(after.getChatMessagesList())) {
			refreshState = MessagesNotChanged;
		} else {
			refreshState = MessagesChanged;
		}
		publishResult();
	}
	
	private void publishResult() {
		Intent intent = new Intent(NOTIFICATION);
		intent.putExtra(RESULT, refreshState);
		sendBroadcast(intent);
	}
}
