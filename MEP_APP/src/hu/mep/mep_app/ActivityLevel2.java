package hu.mep.mep_app;

import hu.mep.communication.GetChatMessagesListRunnable;
import hu.mep.communication.GetContactListRunnable;
import hu.mep.datamodells.Session;
import hu.mep.utils.ChatContactListAdapter;
import hu.mep.utils.PlaceListAdapter;

import java.lang.Thread.State;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityLevel2 extends FragmentActivity implements OnItemClickListener {

	public static final int TAB_MENU_NUMBER = 0;
	public static final int TAB_TOPICS_NUMBER = 1;
	public static final int TAB_REMOTE_MONITORINGS = 2;
	public static final int TAB_CHAT_NUMBER = 3;
	private static final String TAG = "ActivityLevel2";

	private int actualFragmentNumber;
	private FragmentManager fragmentManager;

	public void setActualFragmentNumber(int actualFragmentNumber) {
		this.actualFragmentNumber = actualFragmentNumber;
	}

	private ListView listview;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_secondlevel);
		actualFragmentNumber = TAB_REMOTE_MONITORINGS;
		View actionbarView = getLayoutInflater().inflate(
				R.layout.actionbar_secondlevel, null);
		ActionBar ab = getActionBar();
		ab.setCustomView(actionbarView);
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		this.listview = (ListView) findViewById(R.id.activity_secondlevel_listview);

		listview.setAdapter(getActualAdapter());
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("ActivityLevel2.java", "Távfelügyelet  #" + position
						+ " jól megtapicskolva! :)");

			}

		});
	}

	public void menuItemClickListener(View v) {

		Thread t;
		switch (v.getId()) {
		case R.id.actionbar_secondlevel_button_menu:
			actualFragmentNumber = TAB_MENU_NUMBER;

			break;
		case R.id.actionbar_secondlevel_button_topics:
			actualFragmentNumber = TAB_TOPICS_NUMBER;

			break;
		case R.id.actionbar_secondlevel_button_remote_monitorings:
			actualFragmentNumber = TAB_REMOTE_MONITORINGS;
			// TODO Kérdés kell-e mindig újratölteni a távfelügyeletet a
			// szerverről?
			// Annyira sűrűn nem változik a lista, viszont nem túl sok idő.
			listview.setAdapter(getActualAdapter());
			break;
		case R.id.actionbar_secondlevel_button_chat:
			actualFragmentNumber = TAB_CHAT_NUMBER;
			t = new Thread(new GetContactListRunnable(getApplicationContext()));
			t.start();
			while (!t.getState().equals(State.TERMINATED)) {
				Log.d(TAG, "Waiting for datas from chat partners...");
			}
			listview.setAdapter(getActualAdapter());
			listview.setOnItemClickListener(this);
			break;

		default:
			break;
		}
	}

	private ListAdapter getActualAdapter() {
		ListAdapter resultAdapter = null;
		switch (actualFragmentNumber) {
		case TAB_MENU_NUMBER:

			break;
		case TAB_TOPICS_NUMBER:
			break;
		case TAB_REMOTE_MONITORINGS:
			resultAdapter = new PlaceListAdapter(getApplicationContext(),
					R.id.activity_secondlevel_listview, Session.getInstance(getApplicationContext())
							.getActualUser().getUsersPlaces().getPlaces());
			break;
		case TAB_CHAT_NUMBER:
			resultAdapter = new ChatContactListAdapter(getApplicationContext(),
					R.id.activity_secondlevel_listview, Session.getInstance(getApplicationContext())
							.getActualChatContactList().getContacts());
			break;
		default:
			break;
		}
		return resultAdapter;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.e("CLICKED POSITION", "#" + position);
		// Log.e("CLICKED ID", "#" + id);
		Session.getInstance(getApplicationContext()).setActualChatPartner(
				Session.getInstance(getApplicationContext()).getActualChatContactList()
						.getContacts().get(position));
		
		Log.e("Actual Chat Partner Is:" , Session.getInstance(getApplicationContext()).getActualChatPartner().toString());
		/*
		Log.e("ONCLICKLISTENER", "Before the new thread..." );
		Thread t2 = new Thread(new GetChatMessagesListRunnable(
				getApplicationContext()));
		t2.run();
		while (!t2.getState().equals(State.TERMINATED)) {
			Log.d("GetChatMessagesListRunnable running...",
					"waitforit! :D");
		}*/
		Session.getActualCommunicationInterface().getChatMessages();
		
	}

}
