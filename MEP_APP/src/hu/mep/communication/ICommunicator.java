package hu.mep.communication;

import hu.mep.datamodells.User;

public interface ICommunicator {
	
	public void authenticateUser(String username, String password);
	public void getChatPartners();
	public void getChatMessages();
	public void sendChatMessage(String text);
	public void getTopicList();
}
