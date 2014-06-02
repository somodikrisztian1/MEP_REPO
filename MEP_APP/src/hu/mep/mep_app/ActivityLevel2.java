package hu.mep.mep_app;

import hu.mep.communication.GetContactListRunnable;
import hu.mep.datamodells.Session;
import hu.mep.utils.MyPlaceListAdapter;

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

public class ActivityLevel2 extends FragmentActivity {

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
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}*/

	public void menuItemClickListener(View v) {

		switch (v.getId()) {
		case R.id.actionbar_secondlevel_button_menu:
			actualFragmentNumber = TAB_MENU_NUMBER;
			
			break;
		case R.id.actionbar_secondlevel_button_topics:
			actualFragmentNumber = TAB_TOPICS_NUMBER;
			
			break;
		case R.id.actionbar_secondlevel_button_remote_monitorings:
			actualFragmentNumber = TAB_REMOTE_MONITORINGS;
			
			break;
		case R.id.actionbar_secondlevel_button_chat:
			actualFragmentNumber = TAB_CHAT_NUMBER;
			Toast.makeText(getApplicationContext(),
					"Chat partnerek betöltése...\nKérem várjon!",
					Toast.LENGTH_SHORT).show();

			Thread t = new Thread(new GetContactListRunnable(
					getApplicationContext()));
			t.start();
			while (!t.getState().equals(State.TERMINATED)) {
				Log.d(TAG, "Waiting for datas from chat partners...");
			}

			/*
			 * TODO! ITT JÖN A LÉNYEG! A LISTAADAPTERT MEGTÖLTENI A HELYEK
			 * ADATAI HELYETT A CHAT PARTNEREK LISTÁJÁVAL!!!!!!
			 */
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
			resultAdapter = new MyPlaceListAdapter(getApplicationContext(),
					R.id.activity_secondlevel_listview, Session.getInstance()
							.getActualUser().getUsersPlaces());
			break;
		case TAB_CHAT_NUMBER:
			break;
		default:
			break;
		}
		return resultAdapter;
	}

}
