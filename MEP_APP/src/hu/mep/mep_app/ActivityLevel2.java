package hu.mep.mep_app;

import hu.mep.datamodells.Session;
import hu.mep.utils.MyPlaceListAdapter;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ActivityLevel2 extends FragmentActivity implements
		OnMenuItemClickListener {

	public static final int TAB_MENU_NUMBER = 0;
	public static final int TAB_TOPICS_NUMBER = 1;
	public static final int TAB_REMOTE_MONITORINGS = 2;
	public static final int TAB_CHAT_NUMBER = 3;

	private int actualFragmentNumber;
	private FragmentManager fragmentManager;

	public void setActualFragmentNumber(int actualFragmentNumber) {
		this.actualFragmentNumber = actualFragmentNumber;
	}

	private ListView listview;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		// fragmentManager = getSupportFragmentManager();
		setContentView(R.layout.activity_secondlevel);
		// actualFragmentNumber = TAB_REMOTE_MONITORINGS;
		ActionBar ab = getActionBar();
		ab.setCustomView(R.layout.actionbar_secondlevel);
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		this.listview = (ListView) findViewById(R.id.activity_secondlevel_listview);

		/*
		 * ITT MÉG NINCS MEGTÖLTVE A USERSPLACE BAZZEG! :D MEG KELL VÁRNI A
		 * HÁTTÉRSZÁLON FUTÓ ADATLEKÉRÉST KÜLÖNBEN NULLPOINTEREXCEPTION LESZ!
		 */
		MyPlaceListAdapter adapter = new MyPlaceListAdapter(
				getApplicationContext(), R.id.activity_secondlevel_listview,
				Session.getInstance().getActualUser().getUsersPlaces());

		listview.setAdapter(adapter);
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
	public boolean onMenuItemClick(MenuItem item) {
		// Fragment newFragment;
		switch (item.getItemId()) {
		case R.id.actionbar_secondlevel_button_menu:

			break;
		case R.id.actionbar_secondlevel_button_topics:

			break;
		case R.id.actionbar_secondlevel_button_remote_monitorings:

			break;
		case R.id.actionbar_secondlevel_button_chat:

			break;

		default:
			break;
		}
		return false;
	}
}
