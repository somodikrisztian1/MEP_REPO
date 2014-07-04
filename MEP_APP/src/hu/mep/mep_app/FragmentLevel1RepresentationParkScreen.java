package hu.mep.mep_app;

import bluejamesbond.textviewjustifier.TextViewEx;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentLevel1RepresentationParkScreen extends Fragment {
	
	public FragmentLevel1RepresentationParkScreen() {
	}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {

	View rootView = inflater.inflate(R.layout.fragment_firstlevel_representation_park_screen, container, false);
    
    TextViewEx text_content_1_1 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_representation_park_textview_content_1_1);
    text_content_1_1.setText(getResources().getString(R.string.fragment_firstlevel_representation_park_text_content_1_1), true);
    text_content_1_1.setPadding(0, 0, 0, 0);
    text_content_1_1.setDrawingCacheEnabled(true);
    
    TextViewEx text_content_1_2 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_representation_park_textview_content_1_2);
    text_content_1_2.setText(getResources().getString(R.string.fragment_firstlevel_representation_park_text_content_1_2), true);
    text_content_1_2.setPadding(0, 0, 0, 0);
    text_content_1_2.setDrawingCacheEnabled(true);
    
    TextViewEx text_content_2_1 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_representation_park_textview_content_2_1);
    text_content_2_1.setText(getResources().getString(R.string.fragment_firstlevel_representation_park_text_content_2_1), true); 
    text_content_2_1.setPadding(0, 0, 0, 0);
    text_content_2_1.setDrawingCacheEnabled(true);
    
    TextViewEx text_content_2_2 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_representation_park_textview_content_2_2);
    text_content_2_2.setText(getResources().getString(R.string.fragment_firstlevel_representation_park_text_content_2_2), true);
    text_content_2_2.setPadding(0, 0, 0, 0);
    text_content_2_2.setDrawingCacheEnabled(true);
    
    TextViewEx text_content_3_1 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_representation_park_textview_content_3_1);
    text_content_3_1.setText(getResources().getString(R.string.fragment_firstlevel_representation_park_text_content_3_1), true);    
    text_content_3_1.setPadding(0, 0, 0, 0);
    text_content_3_1.setDrawingCacheEnabled(true);
    
    TextViewEx text_content_3_2 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_representation_park_textview_content_3_2);
    text_content_3_2.setText(getResources().getString(R.string.fragment_firstlevel_representation_park_text_content_3_2), true);
    text_content_3_2.setPadding(0, 0, 0, 0);
    text_content_3_2.setDrawingCacheEnabled(true);
    
    TextViewEx text_content_3_3 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_representation_park_textview_content_3_3);
    text_content_3_3.setText(getResources().getString(R.string.fragment_firstlevel_representation_park_text_content_3_3), true);
    text_content_3_3.setPadding(0, 0, 0, 0);
    text_content_3_3.setDrawingCacheEnabled(true);
    
    TextViewEx text_content_3_4 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_representation_park_textview_content_3_4);
    text_content_3_4.setText(getResources().getString(R.string.fragment_firstlevel_representation_park_text_content_3_4), true);
    text_content_3_4.setPadding(0, 0, 0, 8);
    text_content_3_4.setDrawingCacheEnabled(true);
    
    return rootView;
}
	
}
