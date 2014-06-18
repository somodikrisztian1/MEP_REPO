package hu.mep.mep_app;


import hu.mep.charts.TimeLineChartView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ActivityLevel3ShowTopic extends Activity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        TimeLineChartView mView = new TimeLineChartView(this);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(mView);
	    }

}
