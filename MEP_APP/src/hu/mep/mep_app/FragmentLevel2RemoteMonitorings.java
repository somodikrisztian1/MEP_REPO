package hu.mep.mep_app;

import hu.alter.mep_app.R;
import hu.mep.datamodells.Place;
import hu.mep.datamodells.Session;
import hu.mep.utils.adapters.PlaceListAdapter;
import hu.mep.utils.others.FragmentLevel2EventHandler;
import android.app.Activity;
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
	private static FragmentLevel2EventHandler fragmentEventHandler;
	
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
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		fragmentEventHandler.onRemoteMonitoringSelected(Session.getActualUser().getUsersPlaces().getPlaces().get(position));
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			fragmentEventHandler = (FragmentLevel2EventHandler) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement FragmentEventHandler interface...");
		}
	}
}
