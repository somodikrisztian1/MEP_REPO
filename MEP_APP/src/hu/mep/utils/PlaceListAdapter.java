package hu.mep.utils;

import java.util.HashMap;
import java.util.List;

import hu.mep.datamodells.Place;
import hu.mep.mep_app.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaceListAdapter extends ArrayAdapter<Place> {

	private Context context;
	private List<Place> listOfPlaces;
	
	public PlaceListAdapter(Context context, int listviewID,
			List<Place> listOfPlaces) {
		
		super(context, listviewID, listOfPlaces);
		this.context = context;
		this.listOfPlaces = listOfPlaces;
		HashMap<String, Integer> mIDMap = new HashMap<String, Integer>();

		for (int i = 0; i < listOfPlaces.size(); ++i) {
			mIDMap.put(listOfPlaces.get(i).nameTag, i);
		}
	}
	
	@Override
	public long getItemId(int position) {
		Log.d("MYPLACELISTADAPTER", "#" + position + " listaelem megnyomkodva!");
		return super.getItemId(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View newRow = li.inflate(R.layout.activity_secondlevel_list_item_only_a_textview, parent, false);
		TextView textview = (TextView) newRow.findViewById(R.id.activity_secondlevel_list_item_textview);
		textview.setText(listOfPlaces.get(position).getName());
		return newRow;
	}

}
