package hu.mep.mep_app;

import hu.mep.communication.ICommunicator;
import hu.mep.communication.RealCommunicator;
import hu.mep.communication.NetThread;
import hu.mep.datamodells.Session;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FirstActivity extends FragmentActivity implements FragmentEventHandler {

	private static final String TAG = "FirstActivity";
	
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
	
	private int actualFragmentNumber;
	private FragmentManager fragmentManager;
	public EditText usernameEdittext;
	public EditText passwordEdittext;
	public Button loginButton;

	private Context c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Log.e(TAG, "onCreate 1");
		super.onCreate(savedInstanceState);
		c = getApplicationContext();
		fragmentManager = getSupportFragmentManager();
		setContentView(R.layout.activity_first);

		mainTitle = getResources().getString(
				R.string.fragment_main_screen_title);
		//Log.e("FirstActivity", "onCreate 2");
		drawerTitle = getTitle();
		firstActivityDrawerStrings = getResources().getStringArray(
				R.array.first_activity_drawer_items_list);
		/*
		for (String act : firstActivityDrawerStrings) { 
			Log.e("Strings:", act); 
		}
		*/
		firstActivityDrawerLayout = (DrawerLayout) findViewById(R.id.first_activity_drawer_layout);
		firstActivityDrawerListView = (ListView) findViewById(R.id.first_activity_drawer_listview);
		//Log.e("FirstActivity", "onCreate 3");
		firstActivityDrawerListView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, firstActivityDrawerStrings));

		firstActivityDrawerListView
				.setOnItemClickListener(new DrawerItemClickListener());
		//Log.e(TAG, "onCreate 4");
		drawerToggle = new ActionBarDrawerToggle(this,
				firstActivityDrawerLayout, R.drawable.ic_drawer,
				R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mainTitle);
				Log.e(TAG, "onDrawerClosed");
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(drawerTitle);
				//Log.e(TAG, "onDrawerOpened");
			}

		};

		// Set the drawer toggle as the DrawerListener
		firstActivityDrawerLayout.setDrawerListener(drawerToggle);
		
		

		//Log.e(TAG, "onCreate 5");
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);


		if (savedInstanceState == null) {
			//Log.e(TAG, "onCreate 6");
			actualFragmentNumber = DRAWER_LIST_MAIN_PAGE_NUMBER;
			handleDrawerClick(actualFragmentNumber);
		}
		//Log.e(TAG, "onCreate 7");
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			handleDrawerClick(position);
			//Log.e(TAG, "onItemClick 1");
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(actualFragmentNumber == DRAWER_LIST_LOGIN_NUMBER) {
			fragmentManager.popBackStack("login", 0);
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
		Toast waitForIt = Toast.makeText(c, "Under construction...Try it later! :)", Toast.LENGTH_SHORT);
		boolean readyForFragmentLoading = false;
		switch (actualFragmentNumber) {
		case DRAWER_LIST_LOGIN_NUMBER:
			newFragment = new FragmentLoginScreen();

			args = new Bundle();
			args.putInt(FragmentMainScreen.CLICKED_DRAWER_ITEM_NUMBER, actualFragmentNumber);

			newFragment.setArguments(args);
			ft.addToBackStack("login");
			readyForFragmentLoading = true;
			//Log.e("FirstActivity", "handleDrawerClick 1");
			break;
		case DRAWER_LIST_PRESENTATION_PARK_NUMBER:
			/* !TODO */
			waitForIt.show();
			//Log.e("FirstActivity", "handleDrawerClick 2");
			break;
		case DRAWER_LIST_RESEARCH_CENTER_NUMBER:
			/* !TODO */
			waitForIt.show();
			//Log.e("FirstActivity", "handleDrawerClick 3");
			break;
		case DRAWER_LIST_ABOUT_REMOTE_NUMBER:
			/* !TODO */
			waitForIt.show();
			//Log.e("FirstActivity", "handleDrawerClick 4");
			break;
		case DRAWER_LIST_CONTACTS_NUMBER:
			/* !TODO */
			waitForIt.show();
			//Log.e("FirstActivity", "handleDrawerClick 5");
			break;
		case DRAWER_LIST_MAIN_PAGE_NUMBER:
			newFragment = new FragmentMainScreen();

			args = new Bundle();
			args.putInt(FragmentMainScreen.CLICKED_DRAWER_ITEM_NUMBER,
					DRAWER_LIST_MAIN_PAGE_NUMBER);
			
			newFragment.setArguments(args);
			readyForFragmentLoading = true;
			//ft.addToBackStack("mainpage");
			//Log.e("FirstActivity", "handleDrawerClick -1");
			break;
		default:
			break;
		}
		if(readyForFragmentLoading) {
			ft.replace(R.id.first_activity_frame, newFragment);
			ft.commit();
		}
		// update selected item and title, then close the drawer
		if (position > -1) {
			firstActivityDrawerListView.setItemChecked(position, true);
			setTitle(firstActivityDrawerStrings[position]);
		}
		firstActivityDrawerLayout.closeDrawer(firstActivityDrawerListView);
		Log.e("FirstActivity", "handleDrawerClick finish");
	}

	@Override
	public void setTitle(CharSequence title) {
		//Log.e("FirstActivity", "setTitle");
		getActionBar().setTitle(title);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
		//Log.e("FirstActivity", "onPostCreate");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		drawerToggle.onConfigurationChanged(newConfig);
		//Log.e("FirstActivity", "onConfigurationChanged");
	}

	/*
	 * Ha a k�s�bbiekben gombokat akarunk tenni az ActionBar options men�j�ben
	 * akkor itt kell megtenni.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// MenuInflater inflater = getMenuInflater();
		// inflater.inflate(R.menu.main, menu);
		//Log.e("FirstActivity", "onCreateOptionsMenu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		//boolean drawerOpen = firstActivityDrawerLayout.isDrawerOpen(firstActivityDrawerListView);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		//Log.e("FirstActivity", "onPrepareOptionsMenu");
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.e("FirstActivity", "onOptionsItemSelected");
		if( drawerToggle.onOptionsItemSelected(item) ) {
			//handleDrawerClick(DRAWER_LIST_MAIN_PAGE_NUMBER);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onLoginButtonPressed(final String username, final String password) {
		
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Session.getInstance().getActualCommunicationInterface().authenticateUser(username, password);
           }
        };
        new NetThread(this, r).start();
		Toast toast = Toast.makeText(c, "Bejelentkezés folyamatban...", Toast.LENGTH_SHORT);
		toast.show();
		return false;
	}
	

}
