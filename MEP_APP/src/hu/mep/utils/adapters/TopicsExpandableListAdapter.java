package hu.mep.utils.adapters;

import hu.mep.datamodells.Session;
import hu.mep.datamodells.Topic;
import hu.mep.datamodells.TopicCategory;
import hu.mep.mep_app.R;
import hu.mep.utils.others.FragmentLevel2EventHandler;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class TopicsExpandableListAdapter extends BaseExpandableListAdapter {

	private final List<TopicCategory> groups;
	public LayoutInflater inflater;
	public Activity activity;
	private FragmentLevel2EventHandler fragmentEventHandler;

	public TopicsExpandableListAdapter(Activity activity,
			List<TopicCategory> groups, 
			FragmentLevel2EventHandler fragmentEventHandler) {
		this.activity = activity;
		this.inflater = activity.getLayoutInflater();
		this.groups = groups;
		this.fragmentEventHandler = fragmentEventHandler;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).getTopics().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		//return groups.get(groupPosition).getTopics().get(childPosition).getTopicID();
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final Topic children = (Topic) getChild(groupPosition, childPosition);
		TextView text = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.topics_listitem_detail,
					null);
		}
		text = (TextView) convertView.findViewById(R.id.textView1);
		text.setText(children.getTopicName());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(activity, children.getTopicName(),
						Toast.LENGTH_SHORT).show();
				Session.setProgressDialog(prepareProgressDialogForLoading());
				Session.showProgressDialog();
				
				fragmentEventHandler.onTopicSelected(children);
				
				
				
			}
		});
		return convertView;
	}
	
	private ProgressDialog prepareProgressDialogForLoading() {
		ProgressDialog pd = new ProgressDialog(Session.getContext());
		pd.setCancelable(false);
		pd.setTitle("Kérem várjon!");
		pd.setMessage("Gráf adatok letöltése folyamatban...");
		return pd;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).getTopics().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TopicCategory group = (TopicCategory) getGroup(groupPosition);
		if (convertView == null) {
		      convertView = inflater.inflate(R.layout.topics_listitem_group, null);
		    }
		((CheckedTextView) convertView).setText(group.getCategoryName());
		((CheckedTextView) convertView).setChecked(isExpanded);
		return convertView;
	}
	
	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
