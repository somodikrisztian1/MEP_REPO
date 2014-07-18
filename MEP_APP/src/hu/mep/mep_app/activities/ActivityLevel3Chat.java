package hu.mep.mep_app.activities;

import hu.mep.datamodells.ChatMessage;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.R;
import hu.mep.utils.adapters.ChatMessagesListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ActivityLevel3Chat extends ActionBarActivity {


	private static EditText chatInputTextView;
	public static ArrayAdapter<ChatMessage> adapter;
	
	private static final String TAG = "ActivityLevel3Chat";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate running");
		setContentView(R.layout.activity_thirdlevel_chat);
		setTitle("Chat (" + Session.getActualChatPartner().getName() + ")");
		
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
					String message = chatInputTextView.getText().toString().trim();
					if ((message != null) && (checkChatMessage(message))) {
						Session.getActualCommunicationInterface().sendChatMessage(message);
						chatInputTextView.setText(null);
					}
					
				}
				return false;
			}
		});
		
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.empty_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.homeAsUp) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
