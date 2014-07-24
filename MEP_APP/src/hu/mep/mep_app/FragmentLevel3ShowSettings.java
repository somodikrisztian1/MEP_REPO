package hu.mep.mep_app;

import hu.mep.utils.adapters.SettingsExpandableListAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class FragmentLevel3ShowSettings extends Fragment {
	
	public static SettingsExpandableListAdapter adapter;
	
	public FragmentLevel3ShowSettings() {
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_thirdlevel_settings, container, false);
		
		ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.fragment_thirdlevel_settings_listview);
		adapter  = new SettingsExpandableListAdapter(getActivity());
		listView.setAdapter(adapter);
		
		return rootView;
	}

}
