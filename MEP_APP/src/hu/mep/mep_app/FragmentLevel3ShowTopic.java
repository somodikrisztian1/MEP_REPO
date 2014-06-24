package hu.mep.mep_app;

import hu.mep.charts.TimeLineChartView;
import hu.mep.datamodells.Session;
import hu.mep.utils.adapters.TimeSeriesAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

public class FragmentLevel3ShowTopic extends Fragment {

	public FragmentLevel3ShowTopic() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (Session.getActualChart().getSubCharts().isEmpty()) {
			Toast.makeText(getActivity(),
					"Ehhez a témához nem tartozik grafikon.",
					Toast.LENGTH_SHORT).show();
			getActivity().onBackPressed();
		} else {
			TimeLineChartView mView = new TimeLineChartView(getActivity(),
					new TimeSeriesAdapter());
			//getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);

			return mView;

		}
		return container;
	}

}
