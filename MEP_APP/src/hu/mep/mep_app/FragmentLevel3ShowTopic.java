package hu.mep.mep_app;

import hu.mep.charts.TimeLineChartView;
import hu.mep.datamodells.Session;
import hu.mep.datamodells.charts.Chart;
import hu.mep.utils.adapters.TimeSeriesAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentLevel3ShowTopic extends Fragment {

	public TimeLineChartView mView;
	public Chart mChart;
	
		
	public FragmentLevel3ShowTopic(Chart chart) {
		this.mChart = chart;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mChart.getSubCharts().isEmpty()) {
			Toast.makeText(getActivity(), "Ehhez a témához nem tartozik grafikon.", Toast.LENGTH_SHORT).show();
		} else {
			mView = new TimeLineChartView(getActivity(), mChart);
		}
		return mView;
	}

}
