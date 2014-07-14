package hu.mep.communication;

import java.util.Calendar;

import android.app.Activity;
import hu.mep.datamodells.User;

public interface ICommunicator {

	public void getChatPartners();

	public void getChatMessages();

	public void sendChatMessage(String text);

	public void getTopicList();

	public void getChartNames();

	public void authenticateUser(Activity act, String username, String password);

	public void getGalleryURLsAndPictures();

	public void getActualChart(Calendar beginDate, Calendar endDate);

	public void getActualChart();
	
	public String registrateUser(String fullName, String email, String userName, String password);
	
	
}
