package hu.mep.mep_app;

import hu.mep.datamodells.Session;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;

public class ActivityLevel3ShowTopic extends ActionBarActivity implements TabListener {

	private static final String TAG = "ActivityLevel3ShowTopic";
	private static ActionBar mActionBar;
	private static SectionsPagerAdapter mSectionsPagerAdapter;
	private static ViewPager mViewPager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Session.getInstance(getApplicationContext())
				.getActualCommunicationInterface().getChartNames();
		setContentView(R.layout.activity_thirdlevel_charts);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		
		mViewPager = (ViewPager) findViewById(R.id.activity_thirdlevel_charts_pager);
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
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					//.setIcon(mSectionsPagerAdapter.getPageIcon(i))
					.setTabListener(this));
		}
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Session.setActualChartInfoContainer(Session.getAllChartInfoContainer().get(position));
			Session.getActualCommunicationInterface().getActualChart();
			return new FragmentLevel3ShowTopic(Session.getActualChart());
		}

		@Override
		public int getCount() {
			return Session.getAllChartInfoContainer().size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return Session.getAllChartInfoContainer().get(position).getName();
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

}
