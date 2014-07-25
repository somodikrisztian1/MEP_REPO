package hu.mep.utils.adapters;

import org.afree.data.time.Minute;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.R;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SettingsExpandableListAdapter extends BaseExpandableListAdapter {

	private final String[] titles = { "Beállítások", "Relék", "Funkciók" };
	public LayoutInflater inflater;

	public SettingsExpandableListAdapter(Activity activity) {
		this.inflater = activity.getLayoutInflater();
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if(groupPosition == 0) {
			return Session.getActualSettings().getSliders().get(childPosition);
		} else if (groupPosition == 1) {
			return Session.getActualSettings().getRelays().get(childPosition);
		} else {
			return Session.getActualSettings().getFunctions().get(childPosition);
		}
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		if(groupPosition == 0) {
			
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.settings_listitem_slider, parent, false);
			}
			TextView nameTextView = (TextView)convertView.findViewById(R.id.fragment_thirdlevel_setting_slider_name);
			TextView valueTextView = (TextView)convertView.findViewById(R.id.fragment_thirdlevel_setting_slider_value);
			SeekBar valueSeekBar = (SeekBar)convertView.findViewById(R.id.fragment_thirdlevel_settings_slider_seekbar);
			
			nameTextView.setText(Session.getActualSettings().getSliders().get(childPosition).label);
			valueTextView.setText(Session.getActualSettings().getSliders().get(childPosition).value + " C°");
			final int lowerBound = (int)Session.getActualSettings().getSliders().get(childPosition).minValue;
			final int upperBound = (int)Session.getActualSettings().getSliders().get(childPosition).maxValue;
			int initProgressValues = (int)Session.getActualSettings().getSliders().get(childPosition).value;
			valueSeekBar.setMax(upperBound-lowerBound);
			valueSeekBar.setProgress(initProgressValues - lowerBound);
			valueSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO! ADATOK FELTÖLTÉSE!!!!					
					Log.e("onStopTrackingTouch", "itt kell majd feltölteni az adatokat");
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					Log.e("onStartTrackingTouch", childPosition + ". slider húzgálva...");
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					Session.getTempSettings().getSliders().get(childPosition).value = progress + lowerBound;
					Log.e("onProgressChanged", "new value: " + progress+lowerBound);
				}
			});
			
		} else if(groupPosition == 1) {
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.settings_listitem_relay, parent, false);
			}
			TextView nameTextView = (TextView) convertView.findViewById(R.id.fragment_thirdlevel_setting_relay_name);
			TextView statusTextView = (TextView) convertView.findViewById(R.id.fragment_thirdlevel_setting_relay_status);
			nameTextView.setText(Session.getActualSettings().getRelays().get(childPosition).name);
			
			boolean on = Session.getActualSettings().getRelays().get(childPosition).status;
			String statusText = ( on ? "on" : "off");
			if(on) {
				statusTextView.setTextColor(Color.rgb(255, 255, 255));
			} else {
				statusTextView.setTextColor(Color.rgb(47, 79, 79));
			}
			statusTextView.setText(statusText);
		}
		else {
			if(convertView == null) {
				convertView = inflater.inflate(R.layout.settings_listitem_function, parent, false);
			}
			TextView nameTextView = (TextView) convertView.findViewById(R.id.settings_listitem_function_name_textview);
			ToggleButton onOffButton = (ToggleButton) convertView.findViewById(R.id.settings_listitem_function_value_switch);
			nameTextView.setText(Session.getActualSettings().getFunctions().get(childPosition).label);
			onOffButton.setChecked(Session.getActualSettings().getFunctions().get(childPosition).status);
			onOffButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					Session.getTempSettings().getFunctions().get(childPosition).status = isChecked;					
					// TODO! KÜLDÉS!
				}
			});
		}
		return convertView;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		if(groupPosition == 0) {
			return Session.getActualSettings().getSliders().size();
		} else if(groupPosition == 1) {
			return Session.getActualSettings().getRelays().size();
		} else {
			return Session.getActualSettings().getFunctions().size();
		}
	}
	
	@Override
	public Object getGroup(int groupPosition) {
		if(groupPosition == 0) {
			return Session.getActualSettings().getSliders();
		} else if(groupPosition == 1) {
			return Session.getActualSettings().getRelays();
		} else {
			return Session.getActualSettings().getFunctions();
		}
	}
	
	@Override
	public int getGroupCount() {
		return titles.length;
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

		if (convertView == null) {
		      convertView = inflater.inflate(R.layout.settings_listitem_group, parent, false);
		}
		((CheckedTextView) convertView).setText(titles[groupPosition]);
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