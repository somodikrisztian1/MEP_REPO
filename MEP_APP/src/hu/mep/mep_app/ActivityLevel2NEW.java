package hu.mep.mep_app;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.Topic;
import hu.mep.utils.others.FragmentLevel2EventHandler;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

public class ActivityLevel2NEW extends ActionBarActivity implements
		ActionBar.TabListener, FragmentLevel2EventHandler {

	ActionBar mActionBar;
	Tab tabTopics;
	Tab tabRemoteMonitorings;
	Tab tabChat;

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secondlevel);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.activity_secondlevel_pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						mActionBar.setSelectedNavigationItem(position);
					}
				});

		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

			mActionBar.addTab(mActionBar.newTab()
					//.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setIcon(mSectionsPagerAdapter.getPageIcon(i))
					.setTabListener(this));
		}
		Session.getInstance(getApplicationContext())
				.getActualCommunicationInterface().getTopicList();
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment result = null;
			switch (position) {
			case 0:
				result = new FragmentLevel2Topics();
				break;
			case 1:
				result = new FragmentLevel2RemoteMonitorings();
				break;
			case 2:
				result = new FragmentLevel2Chat();
				break;
			default:
				break;
			}
			return result;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "Témakörök";
				// getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return "Távfelügyelet";
				// getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return "Chat";
				// getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}

		public int getPageIcon(int position) {
			switch (position) {
			case 0:
				return R.drawable.topics_icon;
			case 1:
				return R.drawable.remote_monitorings_icon;
			case 2:
				return R.drawable.chat_icon;
			default:
				break;
			}
			return 0;
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
	public void onTopicSelected(Topic selectedTopic) {
		Session.setActualTopic(selectedTopic);
		Intent i = new Intent(this, ActivityLevel3ShowTopic.class);
		startActivity(i);
		
	}

}
