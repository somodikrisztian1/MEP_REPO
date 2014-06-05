package hu.mep.utils;

import java.util.HashMap;
import java.util.List;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatContactListAdapter extends ArrayAdapter<ChatContact> {

	private Context context;
	private List<ChatContact> listOfContacts;
	
	public ChatContactListAdapter(Context context, int listviewID,
			List<ChatContact> listOfContacts) {
		super(context, listviewID, listOfContacts);
		this.context = context;
		this.listOfContacts = listOfContacts;
		HashMap<String, Integer> mIDMap = new HashMap<String, Integer>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View newRow = li.inflate(R.layout.activity_secondlevel_list_item_picture_and_textview, parent, false);
		TextView textview = (TextView) newRow.findViewById(R.id.activity_secondlevel_chat_textview_for_name);
		textview.setText(listOfContacts.get(position).getName());
		
		ImageView profilePictureView = (ImageView) newRow.findViewById(R.id.activity_secondlevel_chat_imageview_for_profile_picture);
		profilePictureView.setImageBitmap(Session.getInstance(context).getActualChatContactList().getContacts().get(position).getProfilePicture());
		
		ImageView statePictureView = (ImageView) newRow.findViewById(R.id.activity_secondlevel_chat_imageview_for_state);
		statePictureView.setImageResource(Session.getInstance(context).getActualChatContactList().getContacts().get(position).getStatePictureResID());
		return newRow;
	}
	
	@Override
	public long getItemId(int position) {
		return listOfContacts.get(position).getUserID();
	}
	
}
