package hu.mep.mep_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentLevel3ShowSettings extends Fragment {
	
	ListView listview;
	
	// TODO! Listview feltöltése sliderekkel, stb.
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_thirdlevel_settings, container, false);
		listview = (ListView) rootView.findViewById(R.id.fragment_thirdlevel_settings_listview);
		
		//listview.setAdapter(); FOLYTATÁSA KÖVETKEZIK!
		
		return rootView;
	}

}
