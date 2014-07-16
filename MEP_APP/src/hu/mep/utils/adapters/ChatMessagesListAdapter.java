package hu.mep.utils.adapters;

import hu.mep.datamodells.ChatMessage;
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

public class ChatMessagesListAdapter extends ArrayAdapter<ChatMessage> { 

	private LayoutInflater inflater;
	
	public ChatMessagesListAdapter(Context context, int listviewID,
			List<ChatMessage> listOfMessages) {
		super(context, listviewID, listOfMessages);
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
			View newRow;
			if(Session.getChatMessagesList().getChatMessagesList().get(position).getFromID() == Session.getActualUser().getMepID()) {
				newRow = inflater.inflate(R.layout.chat_listitem_own_message, parent, false);
				TextView textview = (TextView) newRow.findViewById(R.id.chat_listitem_textview);	
				ImageView profilePictureView = (ImageView) newRow.findViewById(R.id.chat_listitem_imageview);
				textview.setText(Session.getChatMessagesList().getChatMessagesList().get(position).getMessage());
				profilePictureView.setImageBitmap(Session.getActualUser().getProfilePicture());
			} else {
				newRow = inflater.inflate(R.layout.chat_listitem_others_message, parent, false);
				TextView textview = (TextView) newRow.findViewById(R.id.chat_listitem_textview);	
				ImageView profilePictureView = (ImageView) newRow.findViewById(R.id.chat_listitem_imageview);
				textview.setText(Session.getChatMessagesList().getChatMessagesList().get(position).getMessage());
				profilePictureView.setImageBitmap(Session.getActualChatPartner().getProfilePicture());
			}
			return newRow;
	}
	
	@Override
	public long getItemId(int position) {
		return Session.getChatMessagesList().getChatMessagesList().get(position).getOrder();
	}
	
}
