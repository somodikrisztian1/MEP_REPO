package hu.mep.mep_app;

import hu.mep.charts.TimeLineChartView;
import hu.mep.datamodells.charts.Chart;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Fragment3ShowRemoteChart extends Fragment {
	
	public TimeLineChartView mView;
	public Chart mChart;
	
		
	public Fragment3ShowRemoteChart(Chart chart) {
		this.mChart = chart;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mChart.getSubCharts().isEmpty()) {
			Toast.makeText(getActivity(),
					"Ehhez a távfelügyelethez nem tartozik grafikon.", Toast.LENGTH_SHORT).show();
			//getActivity().onBackPressed();
		} else {
			mView = new TimeLineChartView(getActivity(), mChart);
			//getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		return mView;
	}

}
