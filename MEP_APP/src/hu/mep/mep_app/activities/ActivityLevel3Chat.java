package hu.mep.mep_app.activities;

import hu.mep.communication.ChatMessagesRefresherAsyncTask;
import hu.mep.datamodells.ChatMessage;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.R;
import hu.mep.mep_app.R.id;
import hu.mep.mep_app.R.layout;
import hu.mep.utils.adapters.ChatMessagesListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ActivityLevel3Chat extends Activity {

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
		
		chatMessagesListview.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		chatMessagesListview.setStackFromBottom(true);
		chatMessagesListview.setAdapter(adapter);
		chatMessagesListview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				final int lastVisibleItem = firstVisibleItem + visibleItemCount;
				if(lastVisibleItem == totalItemCount) {
					view.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
				}
				else {
					view.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
		});

		chatInputTextView = (EditText) findViewById(R.id.activity_thirdlevel_chat_input_edittext);

		chatInputTextView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					String message = chatInputTextView.getText().toString();
					if ((message != null) && (checkChatMessage(message))) {
						Session.getActualCommunicationInterface().sendChatMessage(message);
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
		Session.startMessageRefresher();
		//chatMessagesListview.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Session.stopMessageRefresher();		
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
