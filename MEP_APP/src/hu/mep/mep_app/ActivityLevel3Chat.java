package hu.mep.mep_app;

import hu.mep.communication.ChatMessagesRefresherAsyncTask;
import hu.mep.datamodells.ChatMessage;
import hu.mep.datamodells.Session;
import hu.mep.utils.adapters.ChatMessagesListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ActivityLevel3Chat extends Activity {

	//private static ChatMessagesRefresherAsyncTask messageRefresher = new ChatMessagesRefresherAsyncTask();
	private static ListView chatMessagesListview;
	private static EditText chatInputTextView;
	private static final String TAG = "ActivityLevel3Chat";
	public static ArrayAdapter<ChatMessage> adapter;
	//private static final long MESSAGE_REFRESH_TIME_INTERVAL = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate running");
		setContentView(R.layout.activity_thirdlevel_chat);
		chatMessagesListview = (ListView) findViewById(R.id.activity_thirdlevel_chat_listview);

		Session.getInstance(getApplicationContext())
				.getActualCommunicationInterface().getChatMessages();

		adapter = new ChatMessagesListAdapter(this,
				R.id.activity_thirdlevel_chat_listview, Session
						.getChatMessagesList().getChatMessagesList());
		
		chatMessagesListview
				.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		chatMessagesListview.setStackFromBottom(true);
		chatMessagesListview.setAdapter(adapter);

		chatInputTextView = (EditText) findViewById(R.id.activity_thirdlevel_chat_input_edittext);

		chatInputTextView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					String message = chatInputTextView.getText().toString();
					if ((message != null) && (checkChatMessage(message))) {
						Session.getActualCommunicationInterface()
								.sendChatMessage(message);
						chatInputTextView.setText(null);
						Session.getActualCommunicationInterface()
								.getChatMessages();
					}
				}
				return false;
			}
		});
		/*
		messageRefresher = new ChatMessagesRefresherAsyncTask();
		messageRefresher.execute(MESSAGE_REFRESH_TIME_INTERVAL);
		*/
		//Session.startMessageRefresher();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume running");
		/*if (messageRefresher == null) {
			messageRefresher = new ChatMessagesRefresherAsyncTask();
			messageRefresher.execute(MESSAGE_REFRESH_TIME_INTERVAL);
		}*/
		Session.startMessageRefresher();
		chatMessagesListview
		.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "onPause running");
		/*messageRefresher.cancel(true);
		messageRefresher = null;
*/	Session.stopMessageRefresher();		
	}

	/**
	 * Check the chat message before sending, if it has any alphabetic
	 * character, so it does not contain only whitespaces.
	 */
	private boolean checkChatMessage(String message) {
		for (Character actChar : message.toCharArray()) {
			if (Character.isLetterOrDigit(actChar)) {
				return true;
			}
		}
		return false;
	}

}
