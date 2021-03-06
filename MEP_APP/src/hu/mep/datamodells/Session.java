package hu.mep.datamodells;

import hu.mep.communication.ChatMessageRefresherRunnable;
import hu.mep.communication.ContactListRefresherRunnable;
import hu.mep.communication.GetNotWorkingPlacesListRunnable;
import hu.mep.communication.ICommunicator;
import hu.mep.communication.RealCommunicator;
import hu.mep.datamodells.charts.Chart;
import hu.mep.datamodells.charts.ChartName;
import hu.mep.datamodells.charts.OneLineAndTwoBarChartContainer;
import hu.mep.datamodells.charts.SubChart;
import hu.mep.datamodells.settings.Settings;
import hu.mep.mep_app.NotificationService;
import hu.alter.mep_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Session {

	public static final String USERNAME_TAG = "username";
	public static final String PASSWORD_TAG = "password";
	
	//private static final String TAG = "Session";
	private static volatile Session instance;
	
	private static volatile Context context;
	private static ICommunicator actualCommunicationInterface;

	private static List<String> galleryImageURLSList = new ArrayList<String>();
	private static List<Bitmap> galleryImagesList = new ArrayList<Bitmap>();
	private static Bitmap emptyProfilePicture;

	/**private static boolean successfulRegistration = false;
	private static String unsuccessfulRegistrationMessage = "";*/

	private static volatile User actualUser;
	private static boolean isAnyUserLoggedIn = false;

	private static List<TopicCategory> allTopicsList = new ArrayList<TopicCategory>();
	private static Topic actualTopic;

	private static Place actualRemoteMonitoring;
	private static Settings tempSettings;
	private static Settings actualSettings;
	
	private static OneLineAndTwoBarChartContainer actualLineAndBarChartContainer;

	private static volatile ChatContactList actualChatContactList = new ChatContactList(new ArrayList<ChatContact>());
	private static ChatContact actualChatPartner;
	private static volatile ChatMessagesList chatMessagesList;

	private static List<ChartName> allChartNames = new ArrayList<ChartName>();
	private static ChartName actualChartName;
	private static List<Chart> allCharts = new ArrayList<Chart>();
	private static Chart actualChart;
	
	private static GetNotWorkingPlacesListRunnable notWorkingPlacesRefresherRunnable = new GetNotWorkingPlacesListRunnable();
	private static Thread notWorkingPlacesRefresherThread = new Thread(notWorkingPlacesRefresherRunnable);

	private static ContactListRefresherRunnable contactRefresherRunnable = new ContactListRefresherRunnable();
	private static Thread contactRefresherThread = new Thread(contactRefresherRunnable);

	private static ChatMessageRefresherRunnable messageRefresherRunnable = new ChatMessageRefresherRunnable();
	private static Thread messageRefresherThread = new Thread(messageRefresherRunnable);

	private static ProgressDialog progressDialog;
	private static AlertDialog alertDialog;

	public static Calendar beginChartDate = Calendar.getInstance();
	public static Calendar endChartDate = Calendar.getInstance();
	public static double minimalChartValue;
	public static double maximalChartValue;

	// ==============================================================================
	// SESSION + COMMUNICATION + REGISTRATION
	// ==============================================================================
	private Session(Context newContext) {
		context = newContext;
		actualCommunicationInterface = RealCommunicator.getInstance(context);
		emptyProfilePicture = Bitmap.createScaledBitmap(BitmapFactory
				.decodeResource(context.getResources(),
						R.drawable.chat_profile_picture_empty), 250, 250, true);
		emptyChatMessagesList();
	}

	public static Session getInstance(Context newContext) {
		if (instance == null) {
			instance = new Session(newContext);
		} else if (context != newContext) {
			context = newContext;
		}
		return instance;
	}

	public static Context getContext() {
		return context;
	}

	public static ICommunicator getActualCommunicationInterface() {
		if (actualCommunicationInterface == null) {
			actualCommunicationInterface = RealCommunicator.getInstance(context);
		}
		return actualCommunicationInterface;
	}

	// ==============================================================================
	// GALLERY IMAGE URL + IMAGES
	// ==============================================================================
	public static List<Bitmap> getGalleryImagesList() {
		return galleryImagesList;
	}

	public static List<String> getGalleryImageURLSList() {
		return galleryImageURLSList;
	}

	public static void setGalleryImageURLSList(List<String> galleryImageURLSList) {
		Session.galleryImageURLSList = galleryImageURLSList;
	}

	public static void addPictureToGallery(Bitmap newPicture) {
		galleryImagesList.add(newPicture);
	}

	public static void setGalleryImagesList(List<Bitmap> galleryImagesList) {
		Session.galleryImagesList = galleryImagesList;
	}

	public static Bitmap getEmptyProfilePicture() {
		return emptyProfilePicture;
	}

	// ==============================================================================
	// ACTUAL USER
	// ==============================================================================
	public static synchronized User getActualUser() {
		return actualUser;
	}

	public static synchronized void setActualUser(User newUser) {
		actualUser = newUser;
	}

	public static boolean isAnyUserLoggedIn() {
		return isAnyUserLoggedIn;
	}

	public static void setAnyUserLoggedIn(boolean n_isAnyUserLoggedIn) {
		Session.isAnyUserLoggedIn = n_isAnyUserLoggedIn;
	}

	public static void logOffActualUser() {

		//noti service leállítása
		context.stopService(new Intent(context, NotificationService.class));
		
		setActualChart(null);

		setActualChartName(null);

		allChartNames.clear();
		
		//setAllChartNames(null);

		setActualTopic(null);

		allTopicsList.clear();
		
		//setAllTopicsList(null);

		emptyChatMessagesList();

		setActualChatPartner(null);
		
		if(actualChatContactList != null) {
			actualChatContactList.contacts.clear();
		}

		//actualChatContactList.contacts.clear();
		
		setActualChatContactList(null);

		setActualRemoteMonitoring(null);

		setActualUser(null);

	}

	// ==============================================================================
	// TOPICS LIST + ACTUAL TOPIC
	// ==============================================================================
	public static List<TopicCategory> getTopicsList() {
		return allTopicsList;
	}

	public static void setAllTopicsList(List<TopicCategory> allTopicsList) {
		Session.allTopicsList = allTopicsList;
	}

	public static Topic getActualTopic() {
		return actualTopic;
	}

	public static void setActualTopic(Topic actualTopic) {
		Session.actualTopic = actualTopic;
	}

	// ==============================================================================
	// ALL CHART INFO CONTAINAR + ACTUAL CHART INFO CONTAINER
	// ==============================================================================
	public static List<ChartName> getAllChartNames() {
		return allChartNames;
	}

	public static void setAllChartNames(List<ChartName> allChartInfoContainer) {
		Session.allChartNames = allChartInfoContainer;
		logAllChartNames();
	}

	public static ChartName getActualChartName() {
		return actualChartName;
	}

	public static void setActualChartName(ChartName actualChartInfoContainer) {
		Session.actualChartName = actualChartInfoContainer;
	}

	public static List<Chart> getAllCharts() {
		return allCharts;
	}

	public static void setAllCharts(List<Chart> allCharts) {
		Session.allCharts = allCharts;
	}

	public static OneLineAndTwoBarChartContainer getActualLineAndBarChartContainer() {
		return actualLineAndBarChartContainer;
	}

	public static void setActualLineAndBarChartContainer(
			OneLineAndTwoBarChartContainer actualLineAndBarChartContainer) {
		Session.actualLineAndBarChartContainer = actualLineAndBarChartContainer;
		Session.setActualChart(Session.actualLineAndBarChartContainer.getLineChart());
	}

	
	private static void logAllChartNames() {
		if (allChartNames != null) {
			for (ChartName actChartInfoContainer : allChartNames) {
				String s = "id=" + actChartInfoContainer.getId() + "\n";
				s += "name=" + actChartInfoContainer.getName() + "\n";
				s += "ssz=" + actChartInfoContainer.getSerialNumber() + "\n";
				//// // Log.e("Session.logAllChartInfoContainer()", s);
			}
		}
	}

	// ==============================================================================
	// ACTUAL REMOTE MONITORING + SETTINGS
	// ==============================================================================
	public static Place getActualRemoteMonitoring() {
		//// Log.e(TAG, "getActualRemoteMonitoring");
		return actualRemoteMonitoring;
	}

	public static void setActualRemoteMonitoring(Place actualRemoteMonitoring) {
		// Log.e(TAG, "setActualRemoteMonitoring");
		Session.actualRemoteMonitoring = actualRemoteMonitoring;
	}

	public static Settings getActualSettings() {
		return actualSettings;
	}

	public static void setActualSettings(Settings newActualSettings) {
		if(actualSettings != null) {
			Session.actualSettings = newActualSettings;
		} else {
			Session.actualSettings = new Settings(newActualSettings.getSliders(), newActualSettings.getRelays(), newActualSettings.getFunctions());
		}
	}

	public static Settings getTempSettings() {
		return tempSettings;
	}

	public static void setTempSettings(Settings newTempSettings) {
		if(tempSettings != null) {
			Session.tempSettings = newTempSettings;
		} else {
			Session.tempSettings = new Settings(newTempSettings.getSliders(), newTempSettings.getRelays(),
					newTempSettings.getFunctions());
		}
	}

	public static void startNotWorkingPlacesRefresherThread() {
		// Log.e(TAG, "startNotWorkingPlacesRefresherThread");
		if (notWorkingPlacesRefresherThread.getState().equals(Thread.State.NEW)) {
			notWorkingPlacesRefresherThread.start();
		}
		notWorkingPlacesRefresherRunnable.resume();
	}
	
	public static void stopNotWorkingPlacesRefresherThread() {
		// Log.e(TAG, "stopNotWorkingPlacesRefresherThread");
		notWorkingPlacesRefresherRunnable.pause();;
	}
	
	public static void startContactRefresherThread() {

		if (contactRefresherThread.getState().equals(Thread.State.NEW)) {
			contactRefresherThread.start();
		}
		contactRefresherRunnable.resume();
	}

	public static void stopContactRefresherThread() {
		contactRefresherRunnable.pause();
	}

	public static ChatContactList getActualChatContactList() {
		return actualChatContactList;
	}

	public static void setActualChatContactList(
			ChatContactList newChatContactList) {
		if (newChatContactList != null) {
			Session.actualChatContactList = new ChatContactList(
					newChatContactList.getContacts());
		}
	}

	public static ChatContact getActualChatPartner() {
		return actualChatPartner;
	}

	/**
	 * This method set the actualChatPartner to the given one by newPartner
	 * parameter. If the old and the new one are not equal, then we make the
	 * chatMessagesList empty to be sure, that the messages of different
	 * contacts will be not mixed.
	 */
	public static void setActualChatPartner(ChatContact newPartner) {
		// Log.e(TAG, "setActualChatPartner");
		if (actualChatPartner != null) {
			if (!(actualChatPartner.equals(newPartner))) {
				emptyChatMessagesList();
				Session.actualChatPartner = newPartner;
			}
		} else {
			Session.actualChatPartner = newPartner;
			emptyChatMessagesList();
		}
	}

	// ==============================================================================
	// MESSAGE REFRESHER + CHAT MESSAGE LIST
	// ==============================================================================

	public static void startMessageRefresherThread() {

		if (messageRefresherThread.getState().equals(Thread.State.NEW)) {
			messageRefresherThread.start();
		}
		messageRefresherRunnable.resume();
	}

	public static void stopMessageRefresherThread() {
		messageRefresherRunnable.pause();
	}

	public static ChatMessagesList getChatMessagesList() {
		if (chatMessagesList == null) {
			chatMessagesList = new ChatMessagesList(new ArrayList<ChatMessage>());
		}
		return chatMessagesList;
	}

	/**
	 * This method set the chatMessagesList to the given one by
	 * newChatMessagesList parameter if the list is empty. When the
	 * chatMessagesList is not empty, the new messages will be added to the
	 * list. After any kind of setting, the chatMessagesList will be sorted by
	 * the order value of the ChatMessages objects.
	 */
	public static void setChatMessagesList(ChatMessagesList newChatMessagesList) {
		// Log.e(TAG, "setChatMessagesList()");
		if(chatMessagesList == null) {
			if(newChatMessagesList == null) {
				chatMessagesList = new ChatMessagesList(new ArrayList<ChatMessage>());
			} else {
				chatMessagesList = newChatMessagesList;
			}
		} else if(chatMessagesList.getChatMessagesList().isEmpty()) {
			chatMessagesList.getChatMessagesList().addAll(newChatMessagesList.getChatMessagesList());
		} else {
			chatMessagesList.addFurtherMessages(newChatMessagesList);
		}
		Session.sortChatMessagesList();
	}

	/**
	 * This method makes the chatMessagesList empty. Call it, when new
	 * actualChatPartner choosed.
	 */
	public static void emptyChatMessagesList() {
		// Log.e(TAG, "emptyChatMessagesList");
		if (chatMessagesList == null) {
			chatMessagesList = new ChatMessagesList(new ArrayList<ChatMessage>());
		} else if ((chatMessagesList != null) && !(chatMessagesList.chatMessagesList.isEmpty())) {
			chatMessagesList.chatMessagesList.clear();
		}
	}

	public static void sortChatMessagesList() {
		// Log.e("Session", "sortChatMessagesList()");
		if (Session.chatMessagesList != null) {
			/* Egy kis hack. Kézi duplikáció kiszűrés... */
			Set<ChatMessage> tempSet = new LinkedHashSet<ChatMessage>(
					chatMessagesList.getChatMessagesList());
			Session.chatMessagesList.getChatMessagesList().clear();

			// Log.d(TAG, "after Clear");
			for (ChatMessage actMessage : chatMessagesList
					.getChatMessagesList()) {
				// Log.d(TAG, actMessage.toString());
			}

			Session.chatMessagesList.getChatMessagesList().addAll(tempSet);

			// Log.i(TAG, "after addAll from set");
			for (ChatMessage actMessage : chatMessagesList
					.getChatMessagesList()) {
				// Log.i(TAG, actMessage.toString());
			}

			Collections.sort(Session.chatMessagesList.chatMessagesList);
			// Log.e(TAG, "after sorting");
			for (ChatMessage actMessage : chatMessagesList
					.getChatMessagesList()) {
				// Log.e(TAG, actMessage.toString());
			}
		}
	}

	/**
	 * This method gives back the order value of the last ChatMessage object
	 * from chatMessagesList or zero if the chatMessagesList is empty.
	 */
	public synchronized static int getLastChatMessageOrder() {
		// Log.e(TAG, "getLastChatMessageOrder");
		if (chatMessagesList == null
				|| chatMessagesList.chatMessagesList.isEmpty()) {
			// Log.e("LastChatMessageOrder=", "" + 0);
			return 0;
		}
		// Log.e("LastChatMessageOrder=", "" + chatMessagesList.getChatMessagesList().get(chatMessagesList.getChatMessagesList().size() - 1).order);
		return chatMessagesList.getChatMessagesList().get(
				chatMessagesList.getChatMessagesList().size() - 1).order;
	}

	// ==============================================================================
	// ACTUAL CHART + CHART INTERVALS
	// ==============================================================================
	public static Chart getActualChart() {
		// Log.e(TAG, "getActualChart");
		return actualChart;
	}

	public static void setActualChart(Chart actualChart) {
		// Log.e(TAG, "setActualChart");
		Session.actualChart = actualChart;
		refreshChartIntervals();
	}

	public static Calendar getMinimalChartDate() {
		return beginChartDate;
	}

	public static Calendar getMaximalChartDate() {
		return endChartDate;
	}

	public static double getMinimalChartValue() {
		return minimalChartValue;
	}

	public static double getMaximalChartValue() {
		return maximalChartValue;
	}

	public static void refreshChartIntervals() {
		double minValue = Double.MAX_VALUE;
		double maxValue = Double.MIN_VALUE;
		Calendar minDate = Calendar.getInstance();;
		Calendar maxDate = Calendar.getInstance();
		try {
			maxDate.setTime(new SimpleDateFormat("yyyy.MM.dd hh:mm:ss").parse("1900.01.01 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map.Entry<Calendar, Double> keyValuePair;
		Calendar date;
		Double value;
		if (actualChart != null) {
			if (actualChart.getSubCharts() != null) {
				if (!actualChart.getSubCharts().isEmpty()) {
					for (SubChart actSubChart : Session.getActualChart()
							.getSubCharts()) {
						Iterator<Entry<Calendar, Double>> it = actSubChart
								.getChartValues().entrySet().iterator();
						while (it.hasNext()) {
							keyValuePair = it.next();
							date = keyValuePair.getKey();
							value = keyValuePair.getValue();
							if (date.after(maxDate)) {
								maxDate.setTime(date.getTime());
							}
							if (date.before(minDate)) {
								minDate.setTime(date.getTime());
							}
							if (value > maxValue) {
								maxValue = value;
							}
							if (value < minValue) {
								minValue = value;
							}
						}
					}
					endChartDate.setTime(maxDate.getTime());
					beginChartDate.setTime(minDate.getTime());
					maximalChartValue = maxValue;
					minimalChartValue = minValue;
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
					// Log.d("maximalChartDate", "" + formatter.format(endChartDate.getTime()));
					// Log.d("minimalChartDate", "" + formatter.format(beginChartDate.getTime()));
					// Log.d("maximalChartValue", "" + maximalChartValue);
					// Log.d("minimalChartValue", "" + minimalChartValue);
				}
			}
		}
	}

	// ==============================================================================
	// PROGRESS DIALOG + ALERT DIALOG
	// ==============================================================================
	public static void setProgressDialog(ProgressDialog p_ProgressDialog) {
		if (progressDialog != null) {
			// Log.e(TAG, "setProgressDialog");
			dismissAndMakeNullProgressDialog();
		}
		Session.progressDialog = p_ProgressDialog;

	}

	public static void showProgressDialog() {
		if (progressDialog != null) {
			// Log.e(TAG, "showProgressDialog");
			progressDialog.show();
		}
	}

	public static void dismissAndMakeNullProgressDialog() {
		if (progressDialog != null) {
			// Log.e(TAG, "Dismiss progress dialog");
			progressDialog.dismiss();
			progressDialog = null;
		}

	}

	public static void setAlertDialog(AlertDialog ad) {
		// Log.e(TAG, "setAlertDialog");
		Session.alertDialog = ad;
	}

	public static void showAlertDialog() {
		// Log.e(TAG, "showAlertDialog");
		alertDialog.show();
	}

	public static void dismissAndMakeNullAlertDialog() {
		if (alertDialog != null) {
			// Log.d(TAG, "Dismiss alert dialog");
			alertDialog.dismiss();
			alertDialog = null;
		}
	}

	public static boolean isTablet() {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

}
