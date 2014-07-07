package hu.mep.communication;

import android.app.Activity;
import hu.mep.datamodells.User;

public interface ICommunicator {
	
	public void getChatPartners();
	public void getChatMessages();
	public void sendChatMessage(String text);
	public void getTopicList();
	public void getChartNames();
	public void getActualChart();
	public void authenticateUser(Activity act, String username, String password);
	public void getGalleryURLsAndPictures();
}
