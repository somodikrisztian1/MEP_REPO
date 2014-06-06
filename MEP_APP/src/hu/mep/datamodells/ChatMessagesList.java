package hu.mep.datamodells;

import java.util.Collections;
import java.util.List;

public class ChatMessagesList {
	List<ChatMessage> chatMessagesList;

	public ChatMessagesList(List<ChatMessage> newChatMessagesList) {
		super();
		this.chatMessagesList = newChatMessagesList;
	}

	public List<ChatMessage> getChatMessagesList() {
		return chatMessagesList;
	}

	public void setChatMessagesList(List<ChatMessage> newChatMessagesList) {
		this.chatMessagesList = chatMessagesList;
	}
	
	public void addFurtherMessages(ChatMessagesList newChatMessagesList) {
		//this.chatMessagesList.removeAll(newChatMessagesList.getChatMessagesList());
		this.chatMessagesList.addAll(newChatMessagesList.getChatMessagesList());
	}
	
}
