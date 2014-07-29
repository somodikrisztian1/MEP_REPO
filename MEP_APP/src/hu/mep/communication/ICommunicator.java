package hu.mep.communication;

import java.util.Calendar;

import android.app.Activity;

public interface ICommunicator {
	
	public void getGalleryURLsAndPictures(Activity activity);
	
	public void registrateUser(Activity activity, String fullName, String email, String userName, String password);

	public void authenticateUser(Activity activity, String username, String password);

	public void getTopicList();

	public void getChartNames(Activity activity, boolean forRemoteMonitoring);

	public void getAllCharts(Activity activity,	boolean forRemoteMonitoring, Calendar beginDate, Calendar endDate);
	
	public void getActualRemoteMonitoringSettings(Activity activity);
	
	public void getSolarPanelJson(Activity activity, Calendar beginDate, Calendar endDate);
	
	public void getChatPartners();

	public void getChatMessages();

	public void sendChatMessage(String text);
	
	public void sendSettings(Activity activity);
	
}
