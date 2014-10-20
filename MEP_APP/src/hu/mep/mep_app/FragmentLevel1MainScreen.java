package hu.mep.mep_app;

import bluejamesbond.textviewjustifier.TextViewEx;
import hu.alter.mep_app.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLevel1MainScreen extends Fragment {
	
	public static final String CLICKED_DRAWER_ITEM_NUMBER = "options_number";

    public FragmentLevel1MainScreen() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_firstlevel_main_screen, container, false);
        int index = getArguments().getInt(CLICKED_DRAWER_ITEM_NUMBER);
        String newTitle = "";
        if(index == -1) {
        	newTitle = getResources().getString(R.string.fragment_main_screen_title);
        }
        
        TextViewEx welcomeText = (TextViewEx) rootView.findViewById(R.id.fragment_main_screen_welcome_text);
        welcomeText.setText(getResources().getString(R.string.fragment_firstlevel_mainscreen_welcome_text));
        welcomeText.setPadding(0, 0, 0, 0);
        welcomeText.setDrawingCacheEnabled(true);
        
        getActivity().setTitle(newTitle);
        return rootView;
    }

    
    
}
