package hu.mep.mep_app;

import hu.mep.charts.TimeLineChartView;
import hu.mep.datamodells.charts.Chart;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentLevel3ShowTopic extends Fragment {

	public TimeLineChartView mView;
	public Chart mChart;
	private boolean forRemoteMonitoring;
	
		
	public FragmentLevel3ShowTopic(Chart chart, boolean forRemoteMonitoring) {
		this.mChart = chart;
		this.forRemoteMonitoring = forRemoteMonitoring;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if(mChart.getSubCharts() != null) {
			if (mChart.getSubCharts().isEmpty()) {
				/*Toast.makeText(getActivity(), "Nem állnak rendelkezésre adatok.", Toast.LENGTH_SHORT).show();*/
				return inflater.inflate(R.layout.fragment_thirdlevel_no_chart_datas, container, false);
			} else {
				mView = new TimeLineChartView(getActivity(), mChart, forRemoteMonitoring);
				return mView;
			}
		} else {
			return inflater.inflate(R.layout.fragment_thirdlevel_no_chart_datas, container, false);
		}
	}

}
