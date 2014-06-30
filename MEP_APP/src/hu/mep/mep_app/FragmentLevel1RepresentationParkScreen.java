package hu.mep.mep_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLevel1RepresentationParkScreen extends Fragment {
	
	public FragmentLevel1RepresentationParkScreen() {
	}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

	View rootView = inflater.inflate(R.layout.fragment_firstlevel_representation_park_screen, container, false);
	
    getActivity().setTitle(getResources().getString(R.string.fragment_presentation_park_title));
    return rootView;
}
	
}