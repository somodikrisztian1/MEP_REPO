package hu.mep.datamodells;

import java.util.List;

import android.util.Log;

public class ChatMessagesList {
	private static final String TAG = "ChatMessagesList";
	List<ChatMessage> chatMessagesList;

	public ChatMessagesList(List<ChatMessage> newChatMessagesList) {
		super();
		this.chatMessagesList = newChatMessagesList;
	}

	public List<ChatMessage> getChatMessagesList() {
		return chatMessagesList;
	}

	public void setChatMessagesList(List<ChatMessage> newChatMessagesList) {
		this.chatMessagesList = newChatMessagesList;
	}

	public void addFurtherMessages(ChatMessagesList newChatMessagesList) {
		//this.chatMessagesList.removeAll(newChatMessagesList.getChatMessagesList());
		if (newChatMessagesList != null) {
			this.chatMessagesList.addAll(newChatMessagesList.getChatMessagesList());
		}
		/*for (ChatMessage actMessage : chatMessagesList) {
			Log.e(TAG, actMessage.toString());
		}*/
	}

}
