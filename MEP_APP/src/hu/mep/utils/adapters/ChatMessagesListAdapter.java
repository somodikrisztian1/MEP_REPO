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
	public static final int OWN_MESSAGE = 0;
	public static final int OTHERS_MESSAGE = 1;
	
	public ChatMessagesListAdapter(Context context, int listviewID,
			List<ChatMessage> listOfMessages) {
		super(context, listviewID, listOfMessages);
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if(convertView == null) {
			
			holder = new ViewHolder();
			
			if(getItemViewType(position) == OWN_MESSAGE) {
				convertView = inflater.inflate(R.layout.listitem_chat_message_own, parent, false);
				holder.messageTextview = (TextView) convertView.findViewById(R.id.chat_listitem_textview);	
				holder.profilePictureImageview = (ImageView) convertView.findViewById(R.id.chat_listitem_imageview);
				} 
			else if (getItemViewType(position) == OTHERS_MESSAGE) {
				convertView = inflater.inflate(R.layout.listitem_chat_message_others, parent, false);
				holder.messageTextview  = (TextView) convertView.findViewById(R.id.chat_listitem_textview);	
				holder.profilePictureImageview = (ImageView) convertView.findViewById(R.id.chat_listitem_imageview);
			}
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.messageTextview.setText(Session.getChatMessagesList().getChatMessagesList().get(position).getMessage());
		if(getItemViewType(position) == OWN_MESSAGE) {
			holder.profilePictureImageview.setImageBitmap(Session.getActualUser().getProfilePicture());		
		} else if(getItemViewType(position) == OTHERS_MESSAGE) {
			holder.profilePictureImageview.setImageBitmap(Session.getActualChatPartner().getProfilePicture());
		}
		
		return convertView;
	}
	
	@Override
	public long getItemId(int position) {
		return Session.getChatMessagesList().getChatMessagesList().get(position).getOrder();
	}
	
	@Override
	public int getItemViewType(int position) {
		if(Session.getChatMessagesList().getChatMessagesList().get(position).getFromID() == Session.getActualUser().getMepID()) {
			return OWN_MESSAGE;
		}
		return OTHERS_MESSAGE;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	public static class ViewHolder {
		public TextView messageTextview;
		public ImageView profilePictureImageview;
	}
	
}
