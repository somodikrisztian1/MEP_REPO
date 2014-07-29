package hu.mep.mep_app.activities;

import hu.mep.datamodells.Place;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.Topic;
import hu.mep.mep_app.ActivityLevel2SectionsPagerAdapter;
import hu.mep.mep_app.NotificationService;
import hu.mep.mep_app.R;
import hu.mep.utils.others.FragmentLevel2EventHandler;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActivityLevel2NEW extends ActionBarActivity implements
		TabListener, FragmentLevel2EventHandler {

	private static final String TAG = "ActivityLevel2NEW";
	ActionBar mActionBar;
	Tab tabTopics;
	Tab tabRemoteMonitorings;
	Tab tabChat;

	ActivityLevel2SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// noti service elindítása
		startService(new Intent(this, NotificationService.class).putExtra("mepId", Session.getActualUser().getMepID()));
		Log.e("Noti activitiben", "Noti started, mepId: " + Session.getActualUser().getMepID());

		if (Session.getInstance(this).isTablet()) {
			Log.e(TAG, "IT'S A TABLET");
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			Log.e(TAG, "IT'S NOT A TABLET");
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		setContentView(R.layout.activity_secondlevel);

		mSectionsPagerAdapter = new ActivityLevel2SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.activity_secondlevel_pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						mActionBar.setSelectedNavigationItem(position);
					}
				});

		mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		addTabsForActionBar();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause running... stopContactRefresher()");
		Session.stopContactRefresherThread();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Session.getInstance(this);
		Session.dismissAndMakeNullProgressDialog();
		Session.startContactRefresherThread();
	}

	private void addTabsForActionBar() {
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			mActionBar.addTab(mActionBar.newTab()
					// .setText(mSectionsPagerAdapter.getPageTitle(i))
					.setIcon(mSectionsPagerAdapter.getPageIcon(i))
					.setTabListener(this));
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_secondlevel_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_logoff) {
			Session.setAnyUserLoggedIn(false);

			// noti service elindítása
			stopService(new Intent(this, NotificationService.class));

			NavUtils.navigateUpFromSameTask(this);
			return true;
		} else if (item.getItemId() == R.id.homeAsUp) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTopicSelected(Topic selectedTopic) {
		Session.setActualTopic(selectedTopic);
		Session.getActualCommunicationInterface().getChartNames(this, false);
	}

	@Override
	public void onRemoteMonitoringSelected(Place selectedPlace) {
		Session.setActualRemoteMonitoring(selectedPlace);
		if (selectedPlace.isSolarPanel()) {
			Session.getActualCommunicationInterface().getSolarPanelJson(this,
					null, null);
		} else {
			Session.getActualCommunicationInterface().getChartNames(this, true);
		}
	}

}
