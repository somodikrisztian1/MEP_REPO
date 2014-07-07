package hu.mep.mep_app;

import hu.mep.datamodells.Session;
import hu.mep.utils.adapters.TopicsExpandableListAdapter;
import hu.mep.utils.others.FragmentLevel2EventHandler;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class FragmentLevel2Topics extends Fragment {

	public static TopicsExpandableListAdapter adapter;
	private static FragmentLevel2EventHandler fragmentEventHandler;

	public FragmentLevel2Topics() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_secondlevel_topics, container, false);
		
		ExpandableListView listview = (ExpandableListView) v
				.findViewById(R.id.fragment_topics_expandable_listview);

		adapter = new TopicsExpandableListAdapter(getActivity(),
				Session.getTopicsList(),
				fragmentEventHandler);
		listview.setAdapter(adapter);

		return v;
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
