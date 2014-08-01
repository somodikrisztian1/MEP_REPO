package hu.mep.utils.others;

import java.lang.reflect.Field;

import android.view.View;
import android.widget.DatePicker;

public class DatePickerHacker {
	
	//private static final String TAG = "DatePickerHacker";

	public static void hideCalendarView(DatePicker dp) {
		Field fields[] = dp.getClass().getDeclaredFields();
		for (Field actField : fields) {
			if(actField.getName().equals("mCalendarView")) {
				actField.setAccessible(true);
				Object calendarView = new Object();
				try {
					calendarView = actField.get(dp);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				((View)calendarView).setVisibility(View.GONE);
			}
		}
	}
	
}
