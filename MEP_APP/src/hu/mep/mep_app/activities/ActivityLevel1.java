package hu.mep.mep_app.activities;

import hu.mep.communication.NetThread;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel1AboutRemoteScreen;
import hu.mep.mep_app.FragmentLevel1ContactsScreen;
import hu.mep.mep_app.FragmentLevel1LoginScreen;
import hu.mep.mep_app.FragmentLevel1MainScreen;
import hu.mep.mep_app.FragmentLevel1RepresentationParkScreen;
import hu.mep.mep_app.FragmentLevel1ResearchCenterScreen;
import hu.mep.mep_app.R;
import hu.mep.utils.others.AlertDialogFactory;
import hu.mep.utils.others.FragmentLevel1EventHandler;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ActivityLevel1 extends ActionBarActivity implements
		FragmentLevel1EventHandler {

	private static final String TAG = "FirstActivity";

	private String[] firstActivityDrawerStrings;
	private DrawerLayout firstActivityDrawerLayout;
	private ListView firstActivityDrawerListView;
	private ActionBarDrawerToggle drawerToggle;
	private ActionBar mActionBar;

	private CharSequence drawerTitle;

	private static final int DRAWER_LIST_MAIN_PAGE_NUMBER = -1;
	private static final int DRAWER_LIST_PRESENTATION_PARK_NUMBER = 0;
	private static final int DRAWER_LIST_GALLERY_NUMBER = 1;
	private static final int DRAWER_LIST_ABOUT_REMOTE_NUMBER = 2;
	private static final int DRAWER_LIST_RESEARCH_CENTER_NUMBER = 3;
	private static final int DRAWER_LIST_CONTACTS_NUMBER = 4;
	private static final int DRAWER_LIST_LOGIN_LOGOUT_NUMBER = 5;

	private int actualFragmentNumber;
	private FragmentManager fragmentManager;
	public EditText usernameEdittext;
	public EditText passwordEdittext;
	public Button loginButton;
	private ArrayAdapter<String> drawerAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Session.getInstance(this).isTablet()) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
		}
		else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		fragmentManager = getSupportFragmentManager();
		setContentView(R.layout.activity_first);

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
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(drawerTitle);
			}

		};

		// Set the drawer toggle as the DrawerListener
		firstActivityDrawerLayout.setDrawerListener(drawerToggle);

		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
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
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (actualFragmentNumber == DRAWER_LIST_LOGIN_LOGOUT_NUMBER) {
			fragmentManager.popBackStack("addLogin", FragmentManager.POP_BACK_STACK_INCLUSIVE);
			actualFragmentNumber = DRAWER_LIST_MAIN_PAGE_NUMBER;
		}
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
			handleDrawerClick(position);
		}
	}
/*
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (actualFragmentNumber == DRAWER_LIST_LOGIN_LOGOUT_NUMBER) {
			Log.e(TAG, "onBackPressed() and actualFragmentNumber is " + actualFragmentNumber);
			// fragmentManager.popBackStack("login", 0);
		}
	}*/

	private void handleDrawerClick(int position) {

		actualFragmentNumber = position;
		Fragment newFragment = null;
		FragmentTransaction ft = fragmentManager.beginTransaction();
		
		boolean readyForFragmentLoading = false;
		switch (actualFragmentNumber) {

		case DRAWER_LIST_PRESENTATION_PARK_NUMBER:
			newFragment = new FragmentLevel1RepresentationParkScreen();
			readyForFragmentLoading = true;
			break;
		case DRAWER_LIST_GALLERY_NUMBER:
			if (NetThread.isOnline(this)) {
				Session.getActualCommunicationInterface().getGalleryURLsAndPictures(this);
			} else {
				Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForNoConnection(ActivityLevel1.this));
				Session.showAlertDialog();
			}
			break;
		case DRAWER_LIST_RESEARCH_CENTER_NUMBER:
			newFragment = new FragmentLevel1ResearchCenterScreen();
			readyForFragmentLoading = true;
			break;
		case DRAWER_LIST_ABOUT_REMOTE_NUMBER:
			newFragment = new FragmentLevel1AboutRemoteScreen();
			readyForFragmentLoading = true;
			break;
		case DRAWER_LIST_CONTACTS_NUMBER:
			newFragment = new FragmentLevel1ContactsScreen();
			readyForFragmentLoading = true;
			break;
		default:
			break;
		}
		if (readyForFragmentLoading) {
			ft.replace(R.id.first_activity_frame, newFragment);
			ft.commit();
		}

		if (position > -1 && position != DRAWER_LIST_GALLERY_NUMBER) {
			firstActivityDrawerListView.setItemChecked(position, true);
			setTitle(firstActivityDrawerStrings[position]);
		}
		firstActivityDrawerLayout.closeDrawer(firstActivityDrawerListView);
	}

	@Override
	public void setTitle(CharSequence title) {
		getSupportActionBar().setTitle(title);
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
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_firstlevel_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = firstActivityDrawerLayout.isDrawerOpen(firstActivityDrawerListView);
		menu.setGroupVisible(R.id.firstlevel_menu_buttons_group, !drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_login:
			if(!Session.isAnyUserLoggedIn()) {
				Session.getInstance(this);
				
				Session.logOffActualUser();
			}

			if (Session.getActualUser() == null) {
				Fragment newFragment = null;
				Bundle args;
				FragmentTransaction ft = fragmentManager.beginTransaction();
				newFragment = new FragmentLevel1LoginScreen();

				args = new Bundle();
				actualFragmentNumber = DRAWER_LIST_LOGIN_LOGOUT_NUMBER;
				args.putInt(FragmentLevel1MainScreen.CLICKED_DRAWER_ITEM_NUMBER, actualFragmentNumber);
				newFragment.setArguments(args);
				
				ft.replace(R.id.first_activity_frame, newFragment);
				ft.addToBackStack("addLogin");
				ft.commit();
			} else {
				Intent i = new Intent(this, ActivityLevel2NEW.class);
				startActivity(i);
			}
			break;

		default:
			break;
		}
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onLoginButtonPressed(final String username, final String password) {
		
		if (NetThread.isOnline(this)) {
			if((username != null) && (password != null)) {
				Session.getActualCommunicationInterface().authenticateUser(ActivityLevel1.this, username, password);
			}
		} else {
			Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForNoConnection(ActivityLevel1.this));
			Session.showAlertDialog();
		}
		return false;
	}
}
