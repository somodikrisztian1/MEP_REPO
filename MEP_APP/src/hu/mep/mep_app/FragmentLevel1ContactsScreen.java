package hu.mep.mep_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLevel1ContactsScreen extends Fragment{
	//private static final String TAG = "FragmentContactsScreen";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_firstlevel_contacts_screen, container, false);
		
		return v;
	};


}
