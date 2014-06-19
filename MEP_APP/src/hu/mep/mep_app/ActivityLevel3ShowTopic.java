package hu.mep.mep_app;


import hu.mep.charts.TimeLineChartView;
import hu.mep.communication.GetAllChartInfoContainerAsyncTask;
import hu.mep.datamodells.Session;
import hu.mep.utils.adapters.TimeSeriesAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ActivityLevel3ShowTopic extends Activity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Session.getInstance(getApplicationContext()).getActualCommunicationInterface().getChartNames();
	        Session.getInstance(getApplicationContext()).setActualChartInfoContainer(
	        		Session.getAllChartInfoContainer().get(0));
	        TimeLineChartView mView = new TimeLineChartView(this, new TimeSeriesAdapter());
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(mView);
	    }

}
