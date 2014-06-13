package hu.mep.mep_app;

import hu.mep.communication.ChatMessagesRefresherAsyncTask;
import hu.mep.datamodells.ChatMessage;
import hu.mep.datamodells.Session;
import hu.mep.utils.ChatMessagesListAdapter;
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

	private static ChatMessagesRefresherAsyncTask messageRefresher = new ChatMessagesRefresherAsyncTask();
	private static ListView chatMessagesListview;
	private static EditText chatInputTextView;
	private static final String TAG = "ActivityLevel3Chat";
	public static ArrayAdapter<ChatMessage> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate running");
		setContentView(R.layout.activity_thirdlevel_chat);
		chatMessagesListview = (ListView) findViewById(R.id.activity_thirdlevel_chat_listview);

		Session.getActualCommunicationInterface().getChatMessages();

		adapter = new ChatMessagesListAdapter(this,
				R.id.activity_thirdlevel_chat_listview, Session
						.getChatMessagesList().getChatMessagesList());
		chatMessagesListview
				.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		chatMessagesListview.setAdapter(adapter);

		chatInputTextView = (EditText) findViewById(R.id.activity_thirdlevel_chat_input_edittext);
		/*
		 * chatInputTextView.setOnEditorActionListener(new
		 * OnEditorActionListener() {
		 * 
		 * @Override public boolean onEditorAction(TextView v, int actionId,
		 * KeyEvent event) { if(actionId == EditorInfo.IME_ACTION_SEND) {
		 * Session
		 * .getActualCommunicationInterface().sendChatMessage(chatInputTextView
		 * .getText().toString()); } return false; } });
		 */

		chatInputTextView.setOnKeyListener(new OnKeyListener() {

			//TODO Ellenőrizni az üres sztring küldését más módon!
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					String message = chatInputTextView.getText().toString();
					if (message != null && message.length() != 0) {
						Session.getActualCommunicationInterface()
								.sendChatMessage(message);
						chatInputTextView.setText(null);
						Session.getActualCommunicationInterface().getChatMessages();
					}
				}
				return false;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume running");
		if(messageRefresher == null) {
			messageRefresher = new ChatMessagesRefresherAsyncTask();
			messageRefresher.execute(Long.valueOf(1000));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "onPause running");
		messageRefresher.cancel(true);
		messageRefresher = null;
	}

}
