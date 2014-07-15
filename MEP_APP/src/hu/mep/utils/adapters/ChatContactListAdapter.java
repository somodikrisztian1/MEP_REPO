package hu.mep.utils.adapters;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.R;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatContactListAdapter extends ArrayAdapter<ChatContact> {

	private static final String TAG = "ChatContactListAdapter";
	private Context context;
	private LayoutInflater inflater;
	
	public ChatContactListAdapter(Context context, int listviewID,
			List<ChatContact> listOfContacts) {
		super(context, listviewID, listOfContacts);
		this.context = context;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		HashMap<String, Integer> mIDMap = new HashMap<String, Integer>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View newRow = inflater.inflate(R.layout.activity_secondlevel_list_item_picture_and_textview, parent, false);
		TextView textview = (TextView) newRow.findViewById(R.id.activity_secondlevel_chat_textview_for_name);
		textview.setText(Session.getActualChatContactList().getContacts().get(position).getName());
		if(Session.getActualChatContactList().getContacts().get(position).getUnreadedMessageNumber() != 0 ) {
			textview.setTypeface(null, Typeface.BOLD);
		}
		else {
			textview.setTypeface(null, Typeface.NORMAL);
		}		
		
		ImageView profilePictureView = (ImageView) newRow.findViewById(R.id.activity_secondlevel_chat_imageview_for_profile_picture);
		profilePictureView.setImageBitmap(Session.getActualChatContactList().getContacts().get(position).getProfilePicture());
		
		ImageView statePictureView = (ImageView) newRow.findViewById(R.id.activity_secondlevel_chat_imageview_for_state);
		statePictureView.setImageResource(R.drawable.state_picture_online);
		if(Session.getActualChatContactList().getContacts().get(position).isOnline() != 0) {
			statePictureView.setVisibility(View.VISIBLE);
		}
		else {
			statePictureView.setVisibility(View.INVISIBLE);
		}
	return newRow;
	}
	
	@Override
	public long getItemId(int position) {
		return Session.getActualChatContactList().getContacts().get(position).getUserID();
	}
	
}
