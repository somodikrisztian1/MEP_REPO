package hu.mep.mep_app;

import hu.mep.datamodells.Place;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel3Chat;
import hu.mep.mep_app.activities.ActivityLevel3RemoteMonitoring;
import hu.mep.utils.adapters.PlaceListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentLevel2RemoteMonitorings extends Fragment implements OnItemClickListener {
	
	private static ListView listview;
	public static ArrayAdapter<Place> placeAdapter;
	
	public FragmentLevel2RemoteMonitorings() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_secondlevel_remote_monitorings, container, false);
		placeAdapter = new PlaceListAdapter(getActivity(),
				R.id.fragment_remote_monitorings_listview, Session
				.getActualUser().getUsersPlaces().getPlaces());
		
		listview = (ListView) v.findViewById(R.id.fragment_remote_monitorings_listview);
		listview.setOnItemClickListener(this);
		listview.setAdapter(placeAdapter);
		
		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Session.setActualRemoteMonitoring(
				Session.getActualUser().getUsersPlaces().getPlaces().get(position));

		Session.getActualCommunicationInterface().getChartNames();
		
		Intent intent = new Intent(getActivity(),
				ActivityLevel3RemoteMonitoring.class);
		startActivity(intent);
	}
}
