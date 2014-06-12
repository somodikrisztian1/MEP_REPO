package hu.mep.mep_app;

import hu.mep.datamodells.Session;
import hu.mep.utils.ChatMessagesListAdapter;
import hu.mep.utils.ChatMessagesRefresherService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

public class ActivityLevel3Chat extends FragmentActivity {

	private ListView listview;
	private static final String TAG = "ActivityLevel3Chat";
	private Intent serviceIntent;
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int resultCode = bundle
						.getInt(ChatMessagesRefresherService.RESULT);
				if (resultCode == ChatMessagesRefresherService.MessagesChanged) {
					listview.setAdapter(new ChatMessagesListAdapter(
							getApplicationContext(),
							R.id.activity_thirdlevel_chat_listview, Session
									.getChatMessagesList()
									.getChatMessagesList()));
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		serviceIntent = new Intent(getApplicationContext(),
				ChatMessagesRefresherService.class);
		startService(serviceIntent);
		registerReceiver(receiver, new IntentFilter(ChatMessagesRefresherService.NOTIFICATION));
		Log.d(TAG, "onCreate 1");
		setContentView(R.layout.activity_thirdlevel_chat);
		this.listview = (ListView) findViewById(R.id.activity_thirdlevel_chat_listview);
		Log.d(TAG, "onCreate 2");
		listview.setAdapter(new ChatMessagesListAdapter(
				getApplicationContext(),
				R.id.activity_thirdlevel_chat_listview, Session
						.getChatMessagesList().getChatMessagesList()));
		Log.d(TAG, "onCreate 3");
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, new IntentFilter(ChatMessagesRefresherService.NOTIFICATION));
	}
	
	@Override
	  protected void onPause() {
	    super.onPause();
	    unregisterReceiver(receiver);
	  }
	
}
