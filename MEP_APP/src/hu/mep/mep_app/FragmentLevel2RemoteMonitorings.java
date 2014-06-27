package hu.mep.mep_app;

import hu.mep.communication.ContactListRefresherAsyncTask;
import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.Place;
import hu.mep.datamodells.Session;
import hu.mep.utils.adapters.PlaceListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

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
				.getInstance(getActivity())
				.getActualUser().getUsersPlaces().getPlaces());
		
		listview = (ListView) v.findViewById(R.id.fragment_remote_monitorings_listview);
		listview.setOnItemClickListener(this);
		listview.setAdapter(placeAdapter);
		
		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Session.getInstance(getActivity()).setActualRemoteMonitoring(
				Session.getInstance(getActivity())
						.getActualUser().getUsersPlaces().getPlaces().get(position));

		Intent intent = new Intent(getActivity(),
				ActivityLevel3Chat.class);
		startActivity(intent);
	}
}
