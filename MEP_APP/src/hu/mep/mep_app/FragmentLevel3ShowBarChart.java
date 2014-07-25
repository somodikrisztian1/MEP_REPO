package hu.mep.mep_app;

import hu.mep.charts.BarChartView;
import hu.mep.datamodells.charts.BarChart;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLevel3ShowBarChart extends Fragment {
	
	public BarChart chartValues;
	
	public FragmentLevel3ShowBarChart(BarChart barChartValues) {
		this.chartValues = barChartValues;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = new BarChartView(getActivity(), chartValues);
		
		return rootView;
	}
	
}
