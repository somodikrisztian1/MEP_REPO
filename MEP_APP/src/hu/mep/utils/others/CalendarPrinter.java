package hu.mep.utils.others;

import java.util.Calendar;

import android.util.Log;

public class CalendarPrinter {
	public static final void logCalendar(String TAG, Calendar date, Double value) {
		Log.e(TAG,
				date.get(Calendar.YEAR) + "-"
						+ (date.get(Calendar.MONTH)+1) + "-"
						+ date.get(Calendar.DAY_OF_MONTH) + " "
						+ date.get(Calendar.HOUR_OF_DAY) + ":"
						+ date.get(Calendar.MINUTE) + ":"
						+ date.get(Calendar.SECOND) + "#\t"
						+ value);
	}
}
