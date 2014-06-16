package hu.mep.mep_app;

import hu.mep.communication.ContactListRefresherAsyncTask;
import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.ChatMessage;
import hu.mep.datamodells.Place;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.Topic;
import hu.mep.utils.ChatContactListAdapter;
import hu.mep.utils.PlaceListAdapter;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ActivityLevel2 extends FragmentActivity implements
		OnItemClickListener {

	public static final int TAB_MENU_NUMBER = 0;
	public static final int TAB_TOPICS_NUMBER = 1;
	public static final int TAB_REMOTE_MONITORINGS = 2;
	public static final int TAB_CHAT_NUMBER = 3;
	private static final String TAG = "ActivityLevel2";

	private static final long CONTACTS_REFRESH_TIME_INTERVAL = 5000;

	private static ContactListRefresherAsyncTask contactRefresher = new ContactListRefresherAsyncTask();

	private ListView listview;
	public static ArrayAdapter<?> actualAdapter;

	private int actualFragmentNumber;
	private FragmentManager fragmentManager;

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
		Session.getInstance(getApplicationContext());
		Session.getActualCommunicationInterface().getChatPartners();
		refreshActualAdapter();
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("ActivityLevel2.java", "Távfelügyelet  #" + position
						+ " jól megtapicskolva! :)");
			}

		});

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (contactRefresher != null) {
			contactRefresher.cancel(true);
			contactRefresher = null;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (actualFragmentNumber == TAB_CHAT_NUMBER) {
			if (contactRefresher == null) {
				contactRefresher = new ContactListRefresherAsyncTask();
				contactRefresher.execute(CONTACTS_REFRESH_TIME_INTERVAL);
			}
		}
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
			// TODO! Kérdés kell-e mindig újratölteni a távfelügyeletet a
			// szerverről?
			// Annyira sűrűn nem változik a lista, viszont nem túl sok idő.
			refreshActualAdapter();
			break;
		case R.id.actionbar_secondlevel_button_chat:
			// TODO! Ez nem csupán egyszeri alkalom. A chat partnerek listáját
			// egy erre dedikált szálon időközönként frissíteni kell, mondjuk. 5
			// másodpercenként.
			actualFragmentNumber = TAB_CHAT_NUMBER;
			refreshActualAdapter();
			listview.setOnItemClickListener(this);
			break;

		default:
			break;
		}
	}

	private void refreshActualAdapter() {
		switch (actualFragmentNumber) {
		case TAB_MENU_NUMBER:

			break;
		case TAB_TOPICS_NUMBER:
			break;
		case TAB_REMOTE_MONITORINGS:
			actualAdapter = new PlaceListAdapter(getApplicationContext(),
					R.id.activity_secondlevel_listview, Session
							.getInstance(getApplicationContext())
							.getActualUser().getUsersPlaces().getPlaces());
			break;
		case TAB_CHAT_NUMBER:
			actualAdapter = new ChatContactListAdapter(getApplicationContext(),
					R.id.activity_secondlevel_listview, Session
							.getInstance(getApplicationContext())
							.getActualChatContactList().getContacts());
			if (contactRefresher == null) {
				contactRefresher = new ContactListRefresherAsyncTask();
				contactRefresher.execute(CONTACTS_REFRESH_TIME_INTERVAL);
			}
			break;
		default:
			break;
		}
		listview.setAdapter(actualAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.e("CLICKED POSITION", "#" + position);
		if (actualFragmentNumber == TAB_CHAT_NUMBER) {
			Session.getInstance(getApplicationContext()).setActualChatPartner(
					Session.getInstance(getApplicationContext())
							.getActualChatContactList().getContacts()
							.get(position));

			Log.e("Actual Chat Partner Is:",
					Session.getInstance(getApplicationContext())
							.getActualChatPartner().toString());

			Intent i = new Intent(getApplicationContext(),
					ActivityLevel3Chat.class);
			startActivity(i);
		} else if (actualFragmentNumber == TAB_REMOTE_MONITORINGS) {

		}
	}

	public void setActualFragmentNumber(int actualFragmentNumber) {
		this.actualFragmentNumber = actualFragmentNumber;
	}

}
