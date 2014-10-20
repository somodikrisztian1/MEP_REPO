package hu.mep.mep_app;

import hu.alter.mep_app.R;
import hu.mep.charts.BarChartView;
import hu.mep.datamodells.charts.BarChart;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLevel3ShowBarChart extends Fragment {
	
	
	public BarChart chartValues;
	private boolean forRemoteMonitoring;
	
	public FragmentLevel3ShowBarChart(BarChart barChartValues, boolean forRemoteMonitoring) {
		this.chartValues = barChartValues;
		this.forRemoteMonitoring = forRemoteMonitoring;
		}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		chartValues.sortChartValues();
		if(chartValues.getChartValues().isEmpty()) {
			return inflater.inflate(R.layout.fragment_thirdlevel_no_chart_datas, container, false);
		} else {
			View rootView = new BarChartView(getActivity(), chartValues, forRemoteMonitoring);
			return rootView;			
		}
	}
}
