package hu.mep.mep_app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FirstActivity extends FragmentActivity {

	private String[] firstActivityDrawerStrings;
	private DrawerLayout firstActivityDrawerLayout;
	private ListView firstActivityDrawerListView;
	private ActionBarDrawerToggle drawerToggle;

	private CharSequence drawerTitle;
	private CharSequence mainTitle;

	private static final int DRAWER_LIST_MAIN_PAGE_NUMBER = -1;

	private static final int DRAWER_LIST_LOGIN_NUMBER = 0;
	private static final int DRAWER_LIST_PRESENTATION_PARK_NUMBER = 1;
	private static final int DRAWER_LIST_RESEARCH_CENTER_NUMBER = 2;
	private static final int DRAWER_LIST_ABOUT_REMOTE_NUMBER = 3;
	private static final int DRAWER_LIST_CONTACTS_NUMBER = 4;

	private Context c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c = getApplicationContext();
		setContentView(R.layout.activity_first);

		mainTitle = getResources().getString(
				R.string.fragment_main_screen_title);

		drawerTitle = getTitle();
		firstActivityDrawerStrings = getResources().getStringArray(
				R.array.first_activity_drawer_items_list);
		/*
		 * for (String act : firstActivityDrawerStrings) { Log.d("Strings:",
		 * act); }
		 */
		firstActivityDrawerLayout = (DrawerLayout) findViewById(R.id.first_activity_drawer_layout);
		firstActivityDrawerListView = (ListView) findViewById(R.id.first_activity_drawer_listview);

		firstActivityDrawerListView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, firstActivityDrawerStrings));

		firstActivityDrawerListView
				.setOnItemClickListener(new DrawerItemClickListener());

		drawerToggle = new ActionBarDrawerToggle(this,
				firstActivityDrawerLayout, R.drawable.ic_launcher,
				R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mainTitle);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(drawerTitle);
			}

		};

		// Set the drawer toggle as the DrawerListener
		firstActivityDrawerLayout.setDrawerListener(drawerToggle);

		// getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		if (savedInstanceState == null) {
			handleDrawerClick(DRAWER_LIST_MAIN_PAGE_NUMBER);
		}

	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			handleDrawerClick(position);
		}
	}

	private void handleDrawerClick(int position) {
		/**
		 * EGYÉB ACTIVITYK MEGNYITÁSA, A DRAWER ELEMEIRE KATTINTÁS LOGIKÁJA
		 * KERÜL IDE!!!!
		 */
		Fragment newFragment = null;
		Bundle args;
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		Toast t = new Toast(c);
		switch (position) {
		case DRAWER_LIST_LOGIN_NUMBER:
			newFragment = new FragmentLoginScreen();

			args = new Bundle();
			args.putInt(FragmentMainScreen.CLICKED_DRAWER_ITEM_NUMBER, position);

			newFragment.setArguments(args);
			break;
		case DRAWER_LIST_PRESENTATION_PARK_NUMBER:
			/* !TODO */
			t.makeText(c, "Nem elérhetõ rész....", Toast.LENGTH_SHORT);
			t.setView(getCurrentFocus());
			t.show();
			break;
		case DRAWER_LIST_RESEARCH_CENTER_NUMBER:
			/* !TODO */
			t.makeText(c, "Nem elérhetõ rész....", Toast.LENGTH_SHORT);
			t.setView(getCurrentFocus());
			t.show();
			break;
		case DRAWER_LIST_ABOUT_REMOTE_NUMBER:
			/* !TODO */
			t.makeText(c, "Nem elérhetõ rész....", Toast.LENGTH_SHORT);
			t.setView(getCurrentFocus());
			t.show();
			break;
		case DRAWER_LIST_CONTACTS_NUMBER:
			/* !TODO */
			t.makeText(c, "Nem elérhetõ rész....", Toast.LENGTH_SHORT);
			t.setView(getCurrentFocus());
			t.show();
			break;
		case DRAWER_LIST_MAIN_PAGE_NUMBER:
			newFragment = new FragmentMainScreen();

			args = new Bundle();
			args.putInt(FragmentMainScreen.CLICKED_DRAWER_ITEM_NUMBER,
					DRAWER_LIST_MAIN_PAGE_NUMBER);

			newFragment.setArguments(args);
			break;
		default:
			break;
		}
		ft.replace(R.id.first_activity_frame, newFragment);
		ft.commit();

		// update selected item and title, then close the drawer
		if (position > -1) {
			firstActivityDrawerListView.setItemChecked(position, true);
			setTitle(firstActivityDrawerStrings[position]);
		}
		firstActivityDrawerLayout.closeDrawer(firstActivityDrawerListView);
	}

	/* Ennek hatására az Activity helyett az ActionBar címét állítjuk át. */
	@Override
	public void setTitle(CharSequence title) {
		getActionBar().setTitle(title);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		drawerToggle.onConfigurationChanged(newConfig);
	}

	/*
	 * Ha a késõbbiekben gombokat akarunk tenni az ActionBar options menüjében
	 * akkor itt kell megtenni.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/*
	 * Bármilyen gomb lenne az ActionBar-on, a drawer kinyitásakor el kell
	 * tüntetni. Az invalidateOptionsMenu() hatására hívódik meg. If the nav
	 * drawer is open, hide action items related to the content view
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// boolean drawerOpen =
		// firstActivityDrawerLayout.isDrawerOpen(firstActivityDrawerListView);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if( item.getItemId() == R.id.home ) {
			handleDrawerClick(DRAWER_LIST_MAIN_PAGE_NUMBER);
		}
		return super.onOptionsItemSelected(item);
	}
}
