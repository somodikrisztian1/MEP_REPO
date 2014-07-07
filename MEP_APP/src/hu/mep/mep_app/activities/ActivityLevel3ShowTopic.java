package hu.mep.mep_app.activities;

import java.util.Calendar;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel3ShowTopic;
import hu.mep.mep_app.R;
import hu.mep.mep_app.R.id;
import hu.mep.mep_app.R.layout;
import hu.mep.utils.others.CalendarPrinter;
import android.app.Dialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class ActivityLevel3ShowTopic extends ActionBarActivity implements
		TabListener {

	private static final String TAG = "ActivityLevel3ShowTopic";
	private static ActionBar mActionBar;
	private static SectionsPagerAdapter mSectionsPagerAdapter;
	private static ViewPager mViewPager;
	private Calendar beginDate = Calendar.getInstance();
	private Calendar endDate = Calendar.getInstance();

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
					// .setIcon(mSectionsPagerAdapter.getPageIcon(i))
					.setTabListener(this));
		}
		//Session.refreshChartIntervals();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_thirdlevel_showtopic_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == R.id.action_datetime_begin) {
			Dialog d = new Dialog(ActivityLevel3ShowTopic.this);
			
			d.setContentView(R.layout.date_and_time_picker);
			d.setTitle("Kezdő időpont");
			
			DatePicker dp = (DatePicker) d.findViewById(R.id.datePicker);
			TimePicker tp = (TimePicker) d.findViewById(R.id.timePicker);
			
			dp.init(Session.beginChartDate.get(Calendar.YEAR),
					Session.beginChartDate.get(Calendar.MONTH) + 1,
					Session.beginChartDate.get(Calendar.DAY_OF_MONTH),
					new OnDateChangedListener() {

						@Override
						public void onDateChanged(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							beginDate.set(Calendar.YEAR, year);
							beginDate.set(Calendar.MONTH, monthOfYear);
							beginDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
							CalendarPrinter.logCalendar(TAG, beginDate, null);
							/*Toast.makeText(ActivityLevel3ShowTopic.this,
									beginDate.toString(), Toast.LENGTH_LONG)
									.show();*/
						}
					});
			tp.setIs24HourView(true);
			tp.setCurrentHour(beginDate.get(Calendar.HOUR_OF_DAY));
			tp.setCurrentMinute(beginDate.get(Calendar.MINUTE));
			tp.setOnTimeChangedListener(new OnTimeChangedListener() {

				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay,
						int minute) {
					beginDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
					beginDate.set(Calendar.MINUTE, minute);
					CalendarPrinter.logCalendar(TAG, beginDate, null);
					/*Toast.makeText(ActivityLevel3ShowTopic.this,
							beginDate.toString(), Toast.LENGTH_LONG).show();*/
				}
			});
			d.show();
			
		}
		else if(item.getItemId() == R.id.action_datetime_end ) {
			Dialog d = new Dialog(ActivityLevel3ShowTopic.this);
			d.setContentView(R.layout.date_and_time_picker);
			d.setTitle("Záró időpont");
			
			DatePicker dp = (DatePicker) d.findViewById(R.id.datePicker);
			TimePicker tp = (TimePicker) d.findViewById(R.id.timePicker);

			dp.init(Session.endChartDate.get(Calendar.YEAR),
					Session.endChartDate.get(Calendar.MONTH) + 1,
					Session.endChartDate.get(Calendar.DAY_OF_MONTH),
					new OnDateChangedListener() {

						@Override
						public void onDateChanged(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							endDate.set(Calendar.YEAR, year);
							endDate.set(Calendar.MONTH, monthOfYear);
							endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
							CalendarPrinter.logCalendar(TAG, endDate, null);
							/*Toast.makeText(ActivityLevel3ShowTopic.this,
									beginDate.toString(), Toast.LENGTH_LONG)
									.show();*/
						}
					});
			tp.setIs24HourView(true);
			tp.setCurrentHour(endDate.get(Calendar.HOUR_OF_DAY));
			tp.setCurrentMinute(endDate.get(Calendar.MINUTE));
			tp.setOnTimeChangedListener(new OnTimeChangedListener() {

				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay,
						int minute) {
					endDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
					endDate.set(Calendar.MINUTE, minute);
					CalendarPrinter.logCalendar(TAG, endDate, null);
					/*Toast.makeText(ActivityLevel3ShowTopic.this,
							beginDate.toString(), Toast.LENGTH_LONG).show();*/
				}
			});
			d.show();
		} else if( item.getItemId() ==  R.id.action_datetime_refresh_button) {
			Toast.makeText(getApplicationContext(), "Itt kell frissíteni", Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Session.setActualChartInfoContainer(Session
					.getAllChartInfoContainer().get(position));
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
