package hu.mep.mep_app.activities;

import java.util.ArrayList;

import hu.mep.datamodells.ChatMessage;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.R;
import hu.mep.utils.adapters.ChatMessagesListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ActivityLevel3Chat extends Activity {


	private static EditText chatInputTextView;
	public static ArrayAdapter<ChatMessage> adapter;
	
	private static final String TAG = "ActivityLevel3Chat";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate running");
		setContentView(R.layout.activity_thirdlevel_chat);
		ListView chatMessagesListview = (ListView) findViewById(R.id.activity_thirdlevel_chat_listview);

		Session.getActualCommunicationInterface().getChatMessages();
		Session.startMessageRefresherThread();
		
		adapter = new ChatMessagesListAdapter(this, R.id.activity_thirdlevel_chat_listview, 
				Session.getChatMessagesList().getChatMessagesList());

		
		
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
						chatInputTextView.setText("");
						//Session.getActualCommunicationInterface().getChatMessages();
					}
				}
				return false;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		Session.startMessageRefresherThread();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Session.stopMessageRefresherThread();
	}

	/**
	 * Check the chat message before sending, if it has any alphabetic character, so it does not contain only whitespaces.
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
