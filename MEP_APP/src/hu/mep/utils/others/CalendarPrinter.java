package hu.mep.utils.others;

import java.text.SimpleDateFormat;
import java.util.Calendar;



public class CalendarPrinter {

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH.mm");

	public static final void logCalendar(String TAG, Calendar date, Double value) {
		//Log.e(TAG, formatter.format(date.getTime()) + " " + value);
	}

	public static final void logCalendar(String TAG, String value, Calendar date) {

		//Log.e(TAG, value + formatter.format(date.getTime()));
	}
}
