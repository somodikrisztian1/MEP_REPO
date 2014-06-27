package hu.mep.mep_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLevel1MainScreen extends Fragment {
	
	public static final String CLICKED_DRAWER_ITEM_NUMBER = "options_number";

    public FragmentLevel1MainScreen() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_firstlevel_contacts_screen, container, false);
        int index = getArguments().getInt(CLICKED_DRAWER_ITEM_NUMBER);
        String newTitle = "";
        if(index == -1) {
        	newTitle = getResources().getString(R.string.fragment_main_screen_title);
        }

        getActivity().setTitle(newTitle);
        return rootView;
    }
}
