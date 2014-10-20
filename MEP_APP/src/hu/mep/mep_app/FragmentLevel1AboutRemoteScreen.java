package hu.mep.mep_app;

import bluejamesbond.textviewjustifier.TextViewEx;
import hu.alter.mep_app.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLevel1AboutRemoteScreen extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_firstlevel_about_remote_screen, container, false);

		TextViewEx text_content_0 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_about_remote_textview_content_0);
		text_content_0.setText(getResources().getString(R.string.fragment_firstlevel_about_remote_text_content_0), true);
		text_content_0.setPadding(0, 0, 0, 0);
		text_content_0.setDrawingCacheEnabled(true);

		TextViewEx text_content_1 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_about_remote_textview_content_1);
		text_content_1.setText(getResources().getString(R.string.fragment_firstlevel_about_remote_text_content_1), true);
		text_content_1.setPadding(0, 0, 0, 0);
		text_content_1.setDrawingCacheEnabled(true);
		
		TextViewEx text_content_2 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_about_remote_textview_content_2);
		text_content_2.setText(getResources().getString(R.string.fragment_firstlevel_about_remote_text_content_2), true);
		text_content_2.setPadding(0, 0, 0, 0);
		text_content_2.setDrawingCacheEnabled(true);

		TextViewEx text_content_3 = (TextViewEx) rootView.findViewById(R.id.fragment_firstlevel_about_remote_textview_content_3);
		text_content_3.setText(getResources().getString(R.string.fragment_firstlevel_about_remote_text_content_3), true);
		text_content_3.setPadding(0, 0, 0, 0);
		text_content_3.setDrawingCacheEnabled(true);
		
		return rootView;
	}

}
