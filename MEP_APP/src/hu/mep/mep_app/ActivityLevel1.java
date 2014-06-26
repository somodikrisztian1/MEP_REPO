package hu.mep.mep_app;

import hu.mep.communication.NetThread;
import hu.mep.datamodells.Session;
import hu.mep.utils.others.FragmentLevel1EventHandler;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityLevel1 extends ActionBarActivity implements
		FragmentLevel1EventHandler {

	private static final String TAG = "FirstActivity";

	private String[] firstActivityDrawerStrings;
	private DrawerLayout firstActivityDrawerLayout;
	private ListView firstActivityDrawerListView;
	private ActionBarDrawerToggle drawerToggle;
	private ActionBar mActionBar;

	private CharSequence drawerTitle;
	private CharSequence mainTitle;

	private static final int DRAWER_LIST_MAIN_PAGE_NUMBER = -1;
	private static final int DRAWER_LIST_PRESENTATION_PARK_NUMBER = 0;
	private static final int DRAWER_LIST_RESEARCH_CENTER_NUMBER = 1;
	private static final int DRAWER_LIST_ABOUT_REMOTE_NUMBER = 2;
	private static final int DRAWER_LIST_CONTACTS_NUMBER = 3;
	private static final int DRAWER_LIST_LOGIN_LOGOUT_NUMBER = 4;

	private int actualFragmentNumber;
	private FragmentManager fragmentManager;
	public EditText usernameEdittext;
	public EditText passwordEdittext;
	public Button loginButton;
	private ArrayAdapter<String> drawerAdapter;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		fragmentManager = getSupportFragmentManager();
		setContentView(R.layout.activity_first);

		mainTitle = getResources().getString(
				R.string.fragment_main_screen_title);
		// Log.e("FirstActivity", "onCreate 2");
		drawerTitle = getTitle();
		firstActivityDrawerLayout = (DrawerLayout) findViewById(R.id.first_activity_drawer_layout);
		firstActivityDrawerListView = (ListView) findViewById(R.id.first_activity_drawer_listview);

		firstActivityDrawerStrings = getResources().getStringArray(
				R.array.activity_level1_drawer_items_list);

		drawerAdapter = new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, firstActivityDrawerStrings);
		firstActivityDrawerListView.setAdapter(drawerAdapter);

		firstActivityDrawerListView
				.setOnItemClickListener(new DrawerItemClickListener());

		mActionBar = getSupportActionBar();

		drawerToggle = new ActionBarDrawerToggle(this,
				firstActivityDrawerLayout, R.drawable.ic_drawer,
				R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				mActionBar.setTitle(mainTitle);
				Log.e(TAG, "onDrawerClosed");
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(drawerTitle);
				// Log.e(TAG, "onDrawerOpened");
			}

		};

		// Set the drawer toggle as the DrawerListener
		firstActivityDrawerLayout.setDrawerListener(drawerToggle);

		// Log.e(TAG, "onCreate 5");
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			// Log.e(TAG, "onCreate 6");
			actualFragmentNumber = DRAWER_LIST_MAIN_PAGE_NUMBER;
			/* handleDrawerClick(actualFragmentNumber); */
			Fragment newFragment = null;
			Bundle args;
			FragmentTransaction ft = fragmentManager.beginTransaction();

			newFragment = new FragmentLevel1MainScreen();
			args = new Bundle();
			args.putInt(FragmentLevel1MainScreen.CLICKED_DRAWER_ITEM_NUMBER,
					DRAWER_LIST_MAIN_PAGE_NUMBER);
			newFragment.setArguments(args);

			ft.replace(R.id.first_activity_frame, newFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.commit();

		}
		// Log.e(TAG, "onCreate 7");
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			handleDrawerClick(position);
			// Log.e(TAG, "onItemClick 1");
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (actualFragmentNumber == DRAWER_LIST_LOGIN_LOGOUT_NUMBER) {
			Log.e(TAG, "onBackPressed() and actualFragmentNumber is " + actualFragmentNumber);
			//fragmentManager.popBackStack("login", 0);
		}
	}

	private void handleDrawerClick(int position) {
		/**
		 * EGYÉB ACTIVITYK MEGNYITÁSA, A DRAWER ELEMEIRE KATTINTÁS LOGIKÁJA
		 * KERÜL IDE!!!!
		 */
		actualFragmentNumber = position;
		Fragment newFragment = null;
		Bundle args;
		FragmentTransaction ft = fragmentManager.beginTransaction();
		Toast waitForIt = Toast.makeText(context,
				"Under construction...Try it later! :)", Toast.LENGTH_SHORT);
		boolean readyForFragmentLoading = false;
		switch (actualFragmentNumber) {
		/*
		 * case DRAWER_LIST_LOGIN_LOGOUT_NUMBER: if (!isLoggedOnAnyUser) {
		 * newFragment = new FragmentLevel1LoginScreen();
		 * 
		 * args = new Bundle(); args.putInt(
		 * FragmentLevel1MainScreen.CLICKED_DRAWER_ITEM_NUMBER,
		 * actualFragmentNumber);
		 * 
		 * newFragment.setArguments(args); ft.addToBackStack("login");
		 * readyForFragmentLoading = true; // Log.e("FirstActivity",
		 * "handleDrawerClick 1"); } else { isLoggedOnAnyUser = false;
		 * newFragment = new FragmentLevel1MainScreen();
		 * 
		 * args = new Bundle(); args.putInt(
		 * FragmentLevel1MainScreen.CLICKED_DRAWER_ITEM_NUMBER,
		 * DRAWER_LIST_MAIN_PAGE_NUMBER);
		 * 
		 * newFragment.setArguments(args); readyForFragmentLoading = true; }
		 * break;
		 */
		case DRAWER_LIST_PRESENTATION_PARK_NUMBER:
			/* !TODO */
			waitForIt.show();
			Log.e("FirstActivity", "handleDrawerClick() -> Representation Park");
			break;
		case DRAWER_LIST_RESEARCH_CENTER_NUMBER:
			/* !TODO */
			waitForIt.show();
			Log.e("FirstActivity", "handleDrawerClick() -> Research Center");
			break;
		case DRAWER_LIST_ABOUT_REMOTE_NUMBER:
			/* !TODO */
			waitForIt.show();
			Log.e("FirstActivity", "handleDrawerClick() -> About Remote Monitorings");
			break;
		case DRAWER_LIST_CONTACTS_NUMBER:
			newFragment = new FragmentLevel1ContactsScreen();
			readyForFragmentLoading = true;
			Log.e("FirstActivity", "handleDrawerClick() -> Contacts");
			break;
		/*
		 * case DRAWER_LIST_MAIN_PAGE_NUMBER: newFragment = new
		 * FragmentLevel1MainScreen();
		 * 
		 * args = new Bundle();
		 * args.putInt(FragmentLevel1MainScreen.CLICKED_DRAWER_ITEM_NUMBER,
		 * DRAWER_LIST_MAIN_PAGE_NUMBER);
		 * 
		 * newFragment.setArguments(args); readyForFragmentLoading = true; //
		 * ft.addToBackStack("mainpage"); // Log.e("FirstActivity",
		 * "handleDrawerClick -1"); break;
		 */
		default:
			Log.e(TAG, "Case Not Found in handleDrawerClick()");
			break;
		}
		if (readyForFragmentLoading) {
			ft.replace(R.id.first_activity_frame, newFragment);
			ft.commit();
		}
		// update selected item and title, then close the drawer
		if (position > -1 /* && position < 4 */) {
			firstActivityDrawerListView.setItemChecked(position, true);
			setTitle(firstActivityDrawerStrings[position]);
		}
		firstActivityDrawerLayout.closeDrawer(firstActivityDrawerListView);
		Log.e("FirstActivity", "handleDrawerClick finish");
	}

	@Override
	public void setTitle(CharSequence title) {
		// Log.e("FirstActivity", "setTitle");
		getSupportActionBar().setTitle(title);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
		// Log.e("FirstActivity", "onPostCreate");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		drawerToggle.onConfigurationChanged(newConfig);
		// Log.e("FirstActivity", "onConfigurationChanged");
	}

	/*
	 * Ha a későbbiekben gombokat akarunk tenni az ActionBar options menüjében
	 * akkor itt kell megtenni.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_level1_menu, menu);
		// Log.e("FirstActivity", "onCreateOptionsMenu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = firstActivityDrawerLayout
				.isDrawerOpen(firstActivityDrawerListView);
		menu.findItem(R.id.action_login).setVisible(!drawerOpen);
		// Log.e("FirstActivity", "onPrepareOptionsMenu");
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.e("FirstActivity", "onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.action_login:
			if (Session.getActualUser() == null) {
				Fragment newFragment = null;
				Bundle args;
				FragmentTransaction ft = fragmentManager.beginTransaction();
				newFragment = new FragmentLevel1LoginScreen();

				args = new Bundle();
				actualFragmentNumber = DRAWER_LIST_LOGIN_LOGOUT_NUMBER;
				args.putInt(
						FragmentLevel1MainScreen.CLICKED_DRAWER_ITEM_NUMBER,
						actualFragmentNumber);

				newFragment.setArguments(args);
				ft.replace(R.id.first_activity_frame, newFragment);
				ft.addToBackStack("addLogin");
				ft.commit();
				Log.e(TAG, "onOptionsItemSelected ....\nfragments number is:" + getSupportFragmentManager().getBackStackEntryCount());
				// update selected item and title, then close the drawer

				// setTitle(firstActivityDrawerStrings[DRAWER_LIST_LOGIN_LOGOUT_NUMBER]);
			}
			else {
				Intent i = new Intent(this, ActivityLevel2NEW.class);
				startActivity(i);
			}
			break;

		default:
			break;
		}
		if (drawerToggle.onOptionsItemSelected(item)) {
			// handleDrawerClick(DRAWER_LIST_MAIN_PAGE_NUMBER);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onLoginButtonPressed(final String username,
			final String password) {

		if (NetThread.isOnline(context)) {
			Session.getInstance(context).getActualCommunicationInterface()
					.authenticateUser(username, password);
			if (Session.getInstance(context).getActualUser() == null) {
				Toast.makeText(
						context,
						"Sikertelen bejelentkezés!\nEllenőrizze a beírt adatok helyességét!",
						Toast.LENGTH_LONG).show();
			} else {
				getSupportFragmentManager().popBackStack("addLogin", FragmentManager.POP_BACK_STACK_INCLUSIVE);
				Log.e(TAG, "onLoginButtonPressed...\nfragments number is:" + getSupportFragmentManager().getBackStackEntryCount());
				Intent i = new Intent(this, ActivityLevel2NEW.class);
				startActivity(i);
			}
		} else {
			Toast.makeText(context, "Nincs internet kapcsolat!",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

}
