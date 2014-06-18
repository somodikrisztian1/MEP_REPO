package hu.mep.mep_app;

import hu.mep.datamodells.Session;
import hu.mep.utils.TopicsExpandableListAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class FragmentLevel2Topics extends Fragment {

	public static TopicsExpandableListAdapter adapter;

	public FragmentLevel2Topics() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_topics, container, false);
		
		ExpandableListView listview = (ExpandableListView) v
				.findViewById(R.id.fragment_topics_expandable_listview);

		adapter = new TopicsExpandableListAdapter(getActivity(),
				Session.getInstance(getActivity()).getTopicsList());
		listview.setAdapter(adapter);

		return v;
	}

}
