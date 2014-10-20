package hu.mep.mep_app.activities;

import hu.mep.communication.NetThread;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel1AboutRemoteScreen;
import hu.mep.mep_app.FragmentLevel1ContactsScreen;
import hu.mep.mep_app.FragmentLevel1LoginScreen;
import hu.mep.mep_app.FragmentLevel1MainScreen;
import hu.mep.mep_app.FragmentLevel1MepLiveScreen;
import hu.mep.mep_app.FragmentLevel1NewsScreen;
import hu.mep.mep_app.FragmentLevel1RepresentationParkScreen;
import hu.mep.mep_app.FragmentLevel1ResearchCenterScreen;
import hu.mep.mep_app.R;
import hu.mep.utils.others.AlertDialogFactory;
import hu.mep.utils.others.FragmentLevel1EventHandler;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
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

@SuppressLint("NewApi") public class ActivityLevel1 extends ActionBarActivity implements
		FragmentLevel1EventHandler {

	// private static final String TAG = "FirstActivity";

	public String[] firstActivityDrawerStrings;
	public static HashMap<Integer, String> titleStrings = new HashMap<Integer, String>();
	private DrawerLayout firstActivityDrawerLayout;
	private ListView firstActivityDrawerListView;
	private ActionBarDrawerToggle drawerToggle;
	private ActionBar mActionBar;

	private CharSequence drawerTitle;

	public static final int DRAWER_LIST_MAIN_PAGE_NUMBER = -1;
	public static final int DRAWER_LIST_PRESENTATION_PARK_NUMBER = 0;
	public static final int DRAWER_LIST_GALLERY_NUMBER = 1;
	public static final int DRAWER_LIST_ABOUT_REMOTE_NUMBER = 2;
	public static final int DRAWER_LIST_RESEARCH_CENTER_NUMBER = 3;
	public static final int DRAWER_LIST_NEWS_NUMBER = 4;
	public static final int DRAWER_LIST_MEP_LIVE_NUMBER = 5;
	public static final int DRAWER_LIST_CONTACTS_NUMBER = 6;
	public static final int DRAWER_LIST_LOGIN_LOGOUT_NUMBER = 7;

	public int actualFragmentNumber;
	private FragmentManager fragmentManager;
	public EditText usernameEdittext;
	public EditText passwordEdittext;
	public Button loginButton;
	private ArrayAdapter<String> drawerAdapter;
	private MenuItem loginMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Session.getInstance(this).isTablet()) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		
		firstActivityDrawerStrings = getResources().getStringArray(R.array.activity_level1_drawer_items_list);
		
		titleStrings.put(DRAWER_LIST_MAIN_PAGE_NUMBER, getResources().getString(R.string.fragment_main_screen_title));
		titleStrings.put(DRAWER_LIST_PRESENTATION_PARK_NUMBER, firstActivityDrawerStrings[DRAWER_LIST_PRESENTATION_PARK_NUMBER]);
		titleStrings.put(DRAWER_LIST_ABOUT_REMOTE_NUMBER, firstActivityDrawerStrings[DRAWER_LIST_ABOUT_REMOTE_NUMBER]);
		titleStrings.put(DRAWER_LIST_RESEARCH_CENTER_NUMBER, firstActivityDrawerStrings[DRAWER_LIST_RESEARCH_CENTER_NUMBER]);
		titleStrings.put(DRAWER_LIST_CONTACTS_NUMBER, firstActivityDrawerStrings[DRAWER_LIST_CONTACTS_NUMBER]);
		titleStrings.put(DRAWER_LIST_NEWS_NUMBER, firstActivityDrawerStrings[DRAWER_LIST_NEWS_NUMBER]);
		titleStrings.put(DRAWER_LIST_MEP_LIVE_NUMBER, firstActivityDrawerStrings[DRAWER_LIST_MEP_LIVE_NUMBER]);
		titleStrings.put(DRAWER_LIST_LOGIN_LOGOUT_NUMBER, getResources().getString(R.string.login));

		fragmentManager = getSupportFragmentManager();
		setContentView(R.layout.activity_first);

		drawerTitle = getTitle();
		firstActivityDrawerLayout = (DrawerLayout) findViewById(R.id.first_activity_drawer_layout);
		firstActivityDrawerListView = (ListView) findViewById(R.id.first_activity_drawer_listview);

		drawerAdapter = new ArrayAdapter<String>(this, R.layout.listitem_drawer, firstActivityDrawerStrings);
		firstActivityDrawerListView.setAdapter(drawerAdapter);

		firstActivityDrawerListView.setOnItemClickListener(new DrawerItemClickListener());

		mActionBar = getSupportActionBar();

		drawerToggle = new ActionBarDrawerToggle(this, firstActivityDrawerLayout, R.drawable.ic_drawer,	R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				if(actualFragmentNumber != DRAWER_LIST_GALLERY_NUMBER) {
					setTitle( titleStrings.get(actualFragmentNumber) );
				}
				invalidateOptionsMenu();
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				setTitle(drawerTitle);
				invalidateOptionsMenu();
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
			args.putInt(FragmentLevel1MainScreen.CLICKED_DRAWER_ITEM_NUMBER, DRAWER_LIST_MAIN_PAGE_NUMBER);
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

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
			int previousFragmentNumber = actualFragmentNumber;
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
				actualFragmentNumber = previousFragmentNumber;
				if (NetThread.isOnline(ActivityLevel1.this)) {
					Session.getActualCommunicationInterface().getGalleryURLsAndPictures(ActivityLevel1.this);
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
			case DRAWER_LIST_NEWS_NUMBER:
				if (NetThread.isOnline(ActivityLevel1.this)) {
					ProgressDialog progressDialog = new ProgressDialog(ActivityLevel1.this);
					progressDialog.setCancelable(false);
					progressDialog.setMessage("Kérem várjon...");
					Session.setProgressDialog(progressDialog);
					Session.showProgressDialog();
					newFragment = new FragmentLevel1NewsScreen();
					readyForFragmentLoading = true;
				} else {
					Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForNoConnection(ActivityLevel1.this));
					Session.showAlertDialog();
				}
				break;
			case DRAWER_LIST_MEP_LIVE_NUMBER:
				if (NetThread.isOnline(ActivityLevel1.this)) {
					ProgressDialog progressDialog = new ProgressDialog(ActivityLevel1.this);
					progressDialog.setCancelable(false);
					progressDialog.setMessage("Kérem várjon...");
					Session.setProgressDialog(progressDialog);
					Session.showProgressDialog();
					newFragment = new FragmentLevel1MepLiveScreen();
					readyForFragmentLoading = true;
				} else {
					Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForNoConnection(ActivityLevel1.this));
					Session.showAlertDialog();
				}
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
			}
			if(position != DRAWER_LIST_GALLERY_NUMBER) {
				setTitle(titleStrings.get(position));
			}
			firstActivityDrawerLayout.closeDrawer(firstActivityDrawerListView);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		getSupportActionBar().setTitle(title);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
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
		loginMenu = menu.findItem(R.id.action_login);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = firstActivityDrawerLayout.isDrawerOpen(firstActivityDrawerListView);
		if(actualFragmentNumber != DRAWER_LIST_LOGIN_LOGOUT_NUMBER) {
			menu.findItem(R.id.action_login).setVisible(!drawerOpen);
		} else {
			menu.findItem(R.id.action_login).setVisible(false);
		}
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
				//Bundle args;
				FragmentTransaction ft = fragmentManager.beginTransaction();
				newFragment = new FragmentLevel1LoginScreen();

				//args = new Bundle();
				//actualFragmentNumber = DRAWER_LIST_LOGIN_LOGOUT_NUMBER;
				//args.putInt(FragmentLevel1MainScreen.CLICKED_DRAWER_ITEM_NUMBER, actualFragmentNumber);
				//newFragment.setArguments(args);
				
				ft.replace(R.id.first_activity_frame, newFragment, "LOGIN");
				ft.addToBackStack("addLogin");
				ft.commit();
			} else {
				/*Bundle bundle = new Bundle();
				bundle.putInt(TagHolder.userMepIDTAG, Session.getActualUser().getMepID());
				bundle.putString(TagHolder.userNameTAG, Session.getActualUser().getFullName());
				bundle.putBoolean(TagHolder.userIsTeacherTag, Session.getActualUser().isTeacher());
				
				bundle.putBoolean(TagHolder.userIsMekutTag, Session.getActualUser().isMekut());
				bundle.putBoolean(TagHolder.userIsModeratorTag, Session.getActualUser().isModerator());
				bundle.putString(TagHolder.userImageURLTag, Session.getActualUser().getImageURL().toString());
				
				if(Session.getActualUser().getUsersPlaces() != null) {
					bundle.putBoolean(TagHolder.userHasAnyPlacesTag, (Session.getActualUser().getUsersPlaces().getPlaces().size() > 0 ? true : false));
				} else {
					bundle.putBoolean(TagHolder.userHasAnyPlacesTag, false);
				}*/
				Intent i = new Intent(this, ActivityLevel2NEW.class);
				//i.putExtras(bundle);
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

	public void invalidateOptionsMenu() {
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			supportInvalidateOptionsMenu();
		} else {
			if(loginMenu != null) {
				boolean drawerOpen = firstActivityDrawerLayout.isDrawerOpen(firstActivityDrawerListView);
				if(actualFragmentNumber != DRAWER_LIST_LOGIN_LOGOUT_NUMBER) {
					loginMenu.setVisible(!drawerOpen);
				} else {
					loginMenu.setVisible(false);
				}
			}
//			invalidateOptionsMenu(); //TODO
		}
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
