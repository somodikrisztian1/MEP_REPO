package hu.mep.mep_app;

import hu.mep.charts.TimeLineChartView;
import hu.mep.communication.GetAllChartInfoContainerAsyncTask;
import hu.mep.datamodells.Session;
import hu.mep.utils.adapters.TimeSeriesAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class ActivityLevel3ShowTopic extends Activity {

	private static final String TAG = "ActivityLevel3ShowTopic";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Session.getInstance(getApplicationContext())
				.getActualCommunicationInterface().getChartNames();
		Session.getInstance(getApplicationContext())
				.setActualChartInfoContainer(
						Session.getAllChartInfoContainer().get(0));
		Session.getActualCommunicationInterface().getActualChart();
		if (Session.getActualChart().getSubCharts() == null) {
			Toast.makeText(getApplicationContext(),
					"Ehhez a témához nem tartozik grafikon.",
					Toast.LENGTH_SHORT).show();;
			onBackPressed();
		} else {
			TimeLineChartView mView = new TimeLineChartView(this,
					new TimeSeriesAdapter());
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			Log.e(TAG, mView.getInfo().toString());
			setContentView(mView);
		}
	}

}
