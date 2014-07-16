package hu.mep.mep_app.activities;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel3ShowTopic;
import hu.mep.mep_app.R;
import hu.mep.utils.others.CalendarPrinter;

import java.util.Calendar;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class ActivityLevel3RemoteMonitoring extends ActionBarActivity implements
		TabListener {

	private static final String TAG = "ActivityLevel3RemoteMonitoring";
	private static ActionBar mActionBar;
	private static SectionsPagerAdapter mSectionsPagerAdapter;
	private static ViewPager mViewPager;
	private Calendar beginDate;
	private Calendar endDate;
	private Calendar tempDate = Calendar.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Session.getActualCommunicationInterface().getChartNames();
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
		// Session.refreshChartIntervals();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_thirdlevel_showtopic_menu, menu);
		if (beginDate == null) {
			beginDate = Calendar.getInstance();
			beginDate.set(Calendar.YEAR,
					Session.beginChartDate.get(Calendar.YEAR));
			beginDate.set(Calendar.MONTH,
					Session.beginChartDate.get(Calendar.MONTH));
			beginDate.set(Calendar.DAY_OF_MONTH,
					Session.beginChartDate.get(Calendar.DAY_OF_MONTH));
			beginDate.set(Calendar.HOUR_OF_DAY,
					Session.beginChartDate.get(Calendar.HOUR_OF_DAY));
			beginDate.set(Calendar.MINUTE,
					Session.beginChartDate.get(Calendar.MINUTE));
		}
		if (endDate == null) {
			endDate = Calendar.getInstance();
			endDate.set(Calendar.YEAR, Session.endChartDate.get(Calendar.YEAR));
			endDate.set(Calendar.MONTH,
					Session.endChartDate.get(Calendar.MONTH));
			endDate.set(Calendar.DAY_OF_MONTH,
					Session.endChartDate.get(Calendar.DAY_OF_MONTH));
			endDate.set(Calendar.HOUR_OF_DAY,
					Session.endChartDate.get(Calendar.HOUR_OF_DAY));
			endDate.set(Calendar.MINUTE,
					Session.endChartDate.get(Calendar.MINUTE));
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_datetime_begin) {
			final Dialog d = new Dialog(ActivityLevel3RemoteMonitoring.this);

			d.setContentView(R.layout.date_and_time_picker);
			d.setTitle("Kezdő időpont");

			DatePicker dp = (DatePicker) d.findViewById(R.id.datePicker);
			TimePicker tp = (TimePicker) d.findViewById(R.id.timePicker);
			Button cancelButton = (Button) d
					.findViewById(R.id.datePickerCancelButton);
			Button setButton = (Button) d
					.findViewById(R.id.datePickerSettedButton);

			cancelButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					d.dismiss();
				}
			});

			setButton.setOnClickListener(new OnClickListener() {

				Calendar actDate = Calendar.getInstance();

				@Override
				public void onClick(View v) {
					if (tempDate.after(actDate)) {
						Toast.makeText(
								ActivityLevel3RemoteMonitoring.this,
								"Rossz időintervallum!\nNem lehet a jelenlegi dátumnál újabb dátumot megadni!",
								Toast.LENGTH_SHORT).show();
					} else if (tempDate.after(endDate)) {
						Toast.makeText(
								ActivityLevel3RemoteMonitoring.this,
								"Rossz időintervallum!\nA kezdő időpont a zárónál későbbi!",
								Toast.LENGTH_SHORT).show();
					} else {
						beginDate = (Calendar) tempDate.clone();
						d.dismiss();
					}
				}
			});

			dp.init(beginDate.get(Calendar.YEAR),
					beginDate.get(Calendar.MONTH),
					beginDate.get(Calendar.DAY_OF_MONTH),
					new OnDateChangedListener() {

						@Override
						public void onDateChanged(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							tempDate.set(Calendar.YEAR, year);
							tempDate.set(Calendar.MONTH, monthOfYear);
							tempDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
							// CalendarPrinter.logCalendar(TAG, "tempDate",
							// tempDate);
						}
					});
			// dp.setMaxDate(actDate.getTimeInMillis());
			tp.setIs24HourView(true);
			tp.setCurrentHour(beginDate.get(Calendar.HOUR_OF_DAY));
			tp.setCurrentMinute(beginDate.get(Calendar.MINUTE));
			tp.setOnTimeChangedListener(new OnTimeChangedListener() {

				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay,
						int minute) {
					tempDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
					tempDate.set(Calendar.MINUTE, minute);
					// CalendarPrinter.logCalendar(TAG, "tempDate", tempDate);
				}
			});
			d.show();

		} else if (item.getItemId() == R.id.action_datetime_end) {
			final Dialog d = new Dialog(ActivityLevel3RemoteMonitoring.this);
			d.setContentView(R.layout.date_and_time_picker);
			d.setTitle("Záró időpont");

			DatePicker dp = (DatePicker) d.findViewById(R.id.datePicker);
			TimePicker tp = (TimePicker) d.findViewById(R.id.timePicker);
			Button cancelButton = (Button) d
					.findViewById(R.id.datePickerCancelButton);
			Button setButton = (Button) d
					.findViewById(R.id.datePickerSettedButton);

			cancelButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					d.dismiss();
				}
			});

			setButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Calendar actDate = Calendar.getInstance();
					if (tempDate.after(actDate)) {
						Toast.makeText(
								ActivityLevel3RemoteMonitoring.this,
								"Rossz időintervallum!\nNem lehet a jelenlegi dátumnál újabb dátumot megadni!",
								Toast.LENGTH_SHORT).show();
					} else if (tempDate.before(beginDate)) {
						Toast.makeText(
								ActivityLevel3RemoteMonitoring.this,
								"Rossz időintervallum!\nA kezdő időpont a zárónál későbbi!",
								Toast.LENGTH_SHORT).show();
					} else {
						endDate = (Calendar) tempDate.clone();
						d.dismiss();
					}
				}
			});

			dp.init(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH),
					endDate.get(Calendar.DAY_OF_MONTH),
					new OnDateChangedListener() {

						@Override
						public void onDateChanged(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							tempDate.set(Calendar.YEAR, year);
							tempDate.set(Calendar.MONTH, monthOfYear);
							tempDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
							// CalendarPrinter.logCalendar(TAG, "tempDate",
							// tempDate);
							/*
							 * Toast.makeText(ActivityLevel3ShowTopic.this,
							 * beginDate.toString(), Toast.LENGTH_LONG) .show();
							 */
						}
					});
			tp.setIs24HourView(true);
			tp.setCurrentHour(endDate.get(Calendar.HOUR_OF_DAY));
			tp.setCurrentMinute(endDate.get(Calendar.MINUTE));
			tp.setOnTimeChangedListener(new OnTimeChangedListener() {

				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay,
						int minute) {
					tempDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
					tempDate.set(Calendar.MINUTE, minute);
					// CalendarPrinter.logCalendar(TAG, "endDate", endDate);
				}
			});
			d.show();
		} else if (item.getItemId() == R.id.action_datetime_refresh_button) {
			CalendarPrinter.logCalendar(TAG, "fromDate", beginDate);
			CalendarPrinter.logCalendar(TAG, "toDate", endDate);
			// Session.setProgressDialog(prepareProgressDialogForLoading());
			// Session.showProgressDialog();
			Session.getActualCommunicationInterface().getActualChart(beginDate,
					endDate);
			// Session.dismissAndMakeNullProgressDialog();
			Toast.makeText(ActivityLevel3RemoteMonitoring.this, "Kész!",
					Toast.LENGTH_LONG).show();
			mSectionsPagerAdapter.notifyDataSetChanged();
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

		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return POSITION_NONE;
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

	}

	public static void refreshFragments() {
		Log.e(TAG, "refreshFragments: fragments number is "
				+ mSectionsPagerAdapter.getCount());

		mViewPager.setAdapter(mSectionsPagerAdapter);
		mSectionsPagerAdapter.notifyDataSetChanged();

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); ++i) {
			FragmentLevel3ShowTopic actFragment = (FragmentLevel3ShowTopic) mSectionsPagerAdapter
					.getItem(i);
			actFragment.mChart = Session.getActualChart();
			Log.e(TAG, "fragment No." + i);

		}

	}

}
