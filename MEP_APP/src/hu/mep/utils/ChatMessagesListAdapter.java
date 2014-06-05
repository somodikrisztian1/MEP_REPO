package hu.mep.utils;

import hu.mep.datamodells.ChatMessage;
import hu.mep.datamodells.Session;

import java.util.ArrayList;
import java.util.TreeSet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ChatMessagesListAdapter extends BaseAdapter { 
	
	private static final int TYPE_MY_MESSAGE = 0;
	private static final int TYPE_OTHER_ONES_MESSAGE = 1;
	private static final int TYPE_MAX_COUNT = 2;
	
	private ArrayList<ChatMessage> mData = new ArrayList<ChatMessage>();
	private LayoutInflater mInflater;
	private TreeSet mMyMessagesSet = new TreeSet();
	
	public ChatMessagesListAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void addMessage(ChatMessage message) {
		if(message.getFromID() == Session.getActualUser().getMepID()) {
			//TODO
		}
		else {
			//TODO
		}
	}
	
	@Override
	public int getCount() {
		return Session.getChatMessagesList().getChatMessagesList().size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
