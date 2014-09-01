package hu.mep.utils.adapters;

import hu.mep.datamodells.Place;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceListAdapter extends ArrayAdapter<Place> {

	private LayoutInflater inflater;
	
	public PlaceListAdapter(Context context, int listviewID,
			List<Place> listOfPlaces) {
		super(context, listviewID, listOfPlaces);
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.listitem_remote_monitorings, parent, false);
		}
		TextView textview = (TextView) convertView.findViewById(R.id.remote_monitorings_listitem_textview_for_name);
		String text = Session.getActualUser().getUsersPlaces().getPlaces().get(position).getName();
		textview.setText(text);
		textview.setSelected(true);
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.remote_monitorings_listitem_imageview_for_status);
		if( Session.getActualUser().getUsersPlaces().getPlaces().get(position).isWorkingProperly() ) {
			textview.setPadding(textview.getPaddingLeft(), textview.getPaddingTop(), 10, textview.getPaddingBottom());
			imageView.setVisibility(View.INVISIBLE);
		} else {
			textview.setPadding(textview.getPaddingLeft(), textview.getPaddingTop(), 50, textview.getPaddingBottom());
			imageView.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

}
