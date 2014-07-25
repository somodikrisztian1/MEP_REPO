package hu.mep.mep_app.activities;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel3ShowBarChart;
import hu.mep.mep_app.FragmentLevel3ShowSettings;
import hu.mep.mep_app.FragmentLevel3ShowTopic;
import hu.mep.mep_app.R;
import hu.mep.utils.others.DatePickerHacker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
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

public class ActivityLevel3ShowRemoteMonitoring extends ActionBarActivity
		implements TabListener {

	private static final String TAG = "ActivityLevel3ShowTopic";
	private static ActionBar mActionBar;
	public static SectionsPagerAdapter mSectionsPagerAdapter;
	private static ViewPager mViewPager;
	private Calendar beginDate;
	private Calendar endDate;
	private Calendar tempDate = Calendar.getInstance();
	private Menu menu;
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy.MM.dd. HH:mm");
	private static final SimpleDateFormat shortFormatter = new SimpleDateFormat(
			"MMMdd. HH:mm");
	private static final SimpleDateFormat veryShortFormatter = new SimpleDateFormat(
			"HH:mm");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Session.getInstance(this);

		setContentView(R.layout.activity_thirdlevel_charts);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.activity_thirdlevel_charts_pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						mActionBar.setSelectedNavigationItem(position);
					}
				});

		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(false);

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

			mActionBar.addTab(mActionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		this.menu = menu;
		inflater.inflate(R.menu.activity_thirdlevel_showtopic_menu, menu);
		if (beginDate == null) {
			beginDate = Calendar.getInstance();
			beginDate.setTime(Session.beginChartDate.getTime());
			/*beginDate.set(Calendar.YEAR,
					Session.beginChartDate.get(Calendar.YEAR));
			beginDate.set(Calendar.MONTH,
					Session.beginChartDate.get(Calendar.MONTH));
			beginDate.set(Calendar.DAY_OF_MONTH,
					Session.beginChartDate.get(Calendar.DAY_OF_MONTH));
			beginDate.set(Calendar.HOUR_OF_DAY,
					Session.beginChartDate.get(Calendar.HOUR_OF_DAY));
			beginDate.set(Calendar.MINUTE,
					Session.beginChartDate.get(Calendar.MINUTE));*/
		}
		if (endDate == null) {
			endDate = Calendar.getInstance();
			endDate.setTime(Session.endChartDate.getTime());
			/*endDate.set(Calendar.YEAR, Session.endChartDate.get(Calendar.YEAR));
			endDate.set(Calendar.MONTH,
					Session.endChartDate.get(Calendar.MONTH));
			endDate.set(Calendar.DAY_OF_MONTH,
					Session.endChartDate.get(Calendar.DAY_OF_MONTH));
			endDate.set(Calendar.HOUR_OF_DAY,
					Session.endChartDate.get(Calendar.HOUR_OF_DAY));
			endDate.set(Calendar.MINUTE,
					Session.endChartDate.get(Calendar.MINUTE));
			 */
		}

		((MenuItem) menu.findItem(R.id.action_datetime_begin)).setTitle("ma "
				+ veryShortFormatter.format(beginDate.getTime()));
		((MenuItem) menu.findItem(R.id.action_datetime_end)).setTitle("ma "
				+ veryShortFormatter.format(endDate.getTime()));

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.homeAsUp) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}

		final Calendar actDate = Calendar.getInstance();

		if (item.getItemId() == R.id.action_datetime_begin) {
			final Dialog d = new Dialog(ActivityLevel3ShowRemoteMonitoring.this);

			d.setContentView(R.layout.date_and_time_picker);
			d.setTitle("Kezdő időpont");

			DatePicker dp = (DatePicker) d.findViewById(R.id.datePicker);
			TimePicker tp = (TimePicker) d.findViewById(R.id.timePicker);
			Button cancelButton = (Button) d
					.findViewById(R.id.datePickerCancelButton);
			Button setButton = (Button) d
					.findViewById(R.id.datePickerSettedButton);

			DatePickerHacker.hideCalendarView(dp);

			cancelButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					d.dismiss();
				}
			});

			setButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (tempDate.after(actDate)) {
						Toast.makeText(
								ActivityLevel3ShowRemoteMonitoring.this,
								"Rossz időintervallum!\nNem lehet a jelenlegi dátumnál újabb dátumot megadni!",
								Toast.LENGTH_SHORT).show();
					} else if (tempDate.after(endDate)) {
						Toast.makeText(
								ActivityLevel3ShowRemoteMonitoring.this,
								"Rossz időintervallum!\nA kezdő időpont a zárónál későbbi!",
								Toast.LENGTH_SHORT).show();
					} else {
						beginDate.setTime(tempDate.getTime());
						if (actDate.get(Calendar.YEAR) == beginDate
								.get(Calendar.YEAR)) {

							if ((actDate.get(Calendar.MONTH) == beginDate
									.get(Calendar.MONTH))
									&& (actDate.get(Calendar.DAY_OF_MONTH) == beginDate
											.get(Calendar.DAY_OF_MONTH))) {
								((MenuItem) menu
										.findItem(R.id.action_datetime_begin))
										.setTitle("ma "
												+ veryShortFormatter
														.format(beginDate
																.getTime()));
							} else {
								((MenuItem) menu
										.findItem(R.id.action_datetime_begin))
										.setTitle(shortFormatter
												.format(beginDate.getTime()));
							}
						} else {
							((MenuItem) menu
									.findItem(R.id.action_datetime_begin))
									.setTitle(formatter.format(beginDate
											.getTime()));
						}
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
						}
					});
			tp.setIs24HourView(true);
			tp.setCurrentHour(beginDate.get(Calendar.HOUR_OF_DAY));
			tp.setCurrentMinute(beginDate.get(Calendar.MINUTE));
			tp.setOnTimeChangedListener(new OnTimeChangedListener() {

				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay,
						int minute) {
					tempDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
					tempDate.set(Calendar.MINUTE, minute);
				}
			});
			d.show();

		} else if (item.getItemId() == R.id.action_datetime_end) {
			final Dialog d = new Dialog(ActivityLevel3ShowRemoteMonitoring.this);
			d.setContentView(R.layout.date_and_time_picker);
			d.setTitle("Záró időpont");

			DatePicker dp = (DatePicker) d.findViewById(R.id.datePicker);
			TimePicker tp = (TimePicker) d.findViewById(R.id.timePicker);
			Button cancelButton = (Button) d
					.findViewById(R.id.datePickerCancelButton);
			Button setButton = (Button) d
					.findViewById(R.id.datePickerSettedButton);

			DatePickerHacker.hideCalendarView(dp);

			cancelButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					d.dismiss();
				}
			});

			setButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (tempDate.after(actDate)) {
						Toast.makeText(
								ActivityLevel3ShowRemoteMonitoring.this,
								"Rossz időintervallum!\nNem lehet a jelenlegi dátumnál újabb dátumot megadni!",
								Toast.LENGTH_SHORT).show();
					} else if (tempDate.before(beginDate)) {
						Toast.makeText(
								ActivityLevel3ShowRemoteMonitoring.this,
								"Rossz időintervallum!\nA kezdő időpont a zárónál későbbi!",
								Toast.LENGTH_SHORT).show();
					} else {
						endDate.setTime(tempDate.getTime());
						if (actDate.get(Calendar.YEAR) == endDate
								.get(Calendar.YEAR)) {

							if ((actDate.get(Calendar.MONTH) == endDate
									.get(Calendar.MONTH))
									&& (actDate.get(Calendar.DAY_OF_MONTH) == endDate
											.get(Calendar.DAY_OF_MONTH))) {
								((MenuItem) menu
										.findItem(R.id.action_datetime_end))
										.setTitle("ma "
												+ veryShortFormatter
														.format(endDate
																.getTime()));
							} else {
								((MenuItem) menu
										.findItem(R.id.action_datetime_end))
										.setTitle(shortFormatter.format(endDate
												.getTime()));
							}
						} else {
							((MenuItem) menu.findItem(R.id.action_datetime_end))
									.setTitle(formatter.format(endDate
											.getTime()));
						}
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
				}
			});
			d.show();
		} else if (item.getItemId() == R.id.action_datetime_refresh_button) {
			if (Session.getActualRemoteMonitoring().isSolarPanel()) {
				Session.getActualCommunicationInterface().getSolarPanelJson(
						this, beginDate, endDate);
			} else {
				Session.getActualCommunicationInterface().getAllCharts(this,
						true, beginDate, endDate);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (Session.getActualRemoteMonitoring().isSolarPanel()) {
				if (position == 0) {
					return new FragmentLevel3ShowTopic(Session
							.getActualLineAndBarChartContainer().getLineChart());
				} else if (position == 1) {
					return new FragmentLevel3ShowBarChart(Session
							.getActualLineAndBarChartContainer().getMonthly());
				} else {
					return new FragmentLevel3ShowBarChart(Session
							.getActualLineAndBarChartContainer().getAnnually());
				}
			} else {
				if (position < getCount() - 1) {
					Session.setActualChart(Session.getAllCharts().get(position));
					return new FragmentLevel3ShowTopic(Session.getActualChart());
				} else {
					return new FragmentLevel3ShowSettings();
				}
			}
		}

		@Override
		public int getCount() {
			if (Session.getActualRemoteMonitoring().isSolarPanel()) {
				return 3;
			} else {
				return Session.getAllChartNames().size();
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (Session.getActualRemoteMonitoring().isSolarPanel()) {
				if (position == 0) {
					return "Napi";
				} else if (position == 1) {
					return "Havi";
				} else {
					return "Éves";
				}
			} else {
				return Session.getAllChartNames().get(position).getName();
			}
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		if(menu != null) {
			if (Session.getActualRemoteMonitoring().isSolarPanel()) {
				if (tab.getPosition() == 0) {
					menu.setGroupVisible(R.id.thirdlevel_menu_buttons_group, true);
				} else {
					menu.setGroupVisible(R.id.thirdlevel_menu_buttons_group, false);
				}
			}
			else if( mSectionsPagerAdapter.getPageTitle(tab.getPosition()).toString().equals("Rendszerállapot")) {
				menu.setGroupVisible(R.id.thirdlevel_menu_buttons_group, false);
			}
			else {
				menu.setGroupVisible(R.id.thirdlevel_menu_buttons_group, true);
				}
			}
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(menu != null) {
			if (Session.getActualRemoteMonitoring().isSolarPanel()) {
				if (tab.getPosition() == 0) {
					menu.setGroupVisible(R.id.thirdlevel_menu_buttons_group, true);
				} else {
					menu.setGroupVisible(R.id.thirdlevel_menu_buttons_group, false);
				}
			}
			else if( mSectionsPagerAdapter.getPageTitle(tab.getPosition()).toString().equals("Rendszerállapot")) {
				menu.setGroupVisible(R.id.thirdlevel_menu_buttons_group, false);
			}
			else {
				menu.setGroupVisible(R.id.thirdlevel_menu_buttons_group, true);
				}
			}
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

}
