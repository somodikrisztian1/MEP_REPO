package hu.mep.datamodells;

import hu.mep.communication.ChatMessagesRefresherAsyncTask;
import hu.mep.communication.ContactListRefresherRunnable;
import hu.mep.communication.ContactListRefresherAsyncTask;
import hu.mep.communication.ICommunicator;
import hu.mep.communication.RealCommunicator;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class Session {

	private static final String TAG = "Session";
	private static Session instance;
	private static Context context;
	private static ICommunicator actualCommunicationInterface;

	private static List<String> galleryImageURLSList = new ArrayList<String>();
	private static List<Bitmap> galleryImagesList = new ArrayList<Bitmap>();

	private static User actualUser;
	private static boolean isAnyUserLoggedIn = false;

	private static List<TopicCategory> allTopicsList;
	private static Topic actualTopic;

	private static Place actualRemoteMonitoring;

	private static volatile ChatContactList actualChatContactList;
	private static ChatContact actualChatPartner;
	private static ChatMessagesList chatMessagesList;

	private static List<ChartInfoContainer> allChartInfoContainer;
	private static ChartInfoContainer actualChartInfoContainer;
	private static Chart actualChart;

	private static ContactListRefresherAsyncTask contactRefresherAsyncTask;
	private static ContactListRefresherRunnable contactRefresherRunnable = new ContactListRefresherRunnable();
	private static Thread contactRefresherThread = new Thread(
			contactRefresherRunnable);

	private static ChatMessagesRefresherAsyncTask messageRefresher = new ChatMessagesRefresherAsyncTask();

	private static ProgressDialog progressDialog;
	private static AlertDialog alertDialog;

	public static Calendar beginChartDate;
	public static Calendar endChartDate;
	public static double minimalChartValue;
	public static double maximalChartValue;

	// ==============================================================================
	// SESSION + COMMUNICATION
	// ==============================================================================
	private Session(Context context) {
		this.context = context;
		actualCommunicationInterface = RealCommunicator.getInstance(context);
		emptyChatMessagesList();
	}

	public static Session getInstance(Context context) {
		if (instance == null) {
			instance = new Session(context);
		} else if (instance.context != context) {
			instance.context = context;
		}
		return instance;
	}

	public static ICommunicator getActualCommunicationInterface() {
		if (actualCommunicationInterface == null) {
			actualCommunicationInterface = RealCommunicator
					.getInstance(context);
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

	// ==============================================================================
	// ACTUAL USER
	// ==============================================================================
	public static User getActualUser() {
		// Log.e(TAG, "getActualUser");
		return actualUser;
	}

	public static void setActualUser(User newUser) {
		Log.e(TAG, "setActualUser");
		actualUser = newUser;
	}

	public static boolean isAnyUserLoggedIn() {
		return isAnyUserLoggedIn;
	}

	public static void logOffActualUser() {
		isAnyUserLoggedIn = false;

		Log.e(TAG, "Stopping refresher tasks");
		/* stopContactRefresherAsyncTask(); */
		stopContactRefresherThread();
		// contactRefresherThread.stop();

		Log.e(TAG, "setActualChart(null);");
		setActualChart(null);

		Log.e(TAG, "setActualChartInfoContainer(null);");
		setActualChartInfoContainer(null);

		Log.e(TAG, "setAllChartInfoContainer(null);");
		setAllChartInfoContainer(null);

		Log.e(TAG, "setActualTopic(null);");
		setActualTopic(null);

		Log.e(TAG, "setAllTopicsList(null);");
		setAllTopicsList(null);

		Log.e(TAG, "setChatMessagesList(null);");
		setChatMessagesList(null);

		Log.e(TAG, "setActualChatPartner(null);");
		setActualChatPartner(null);

		Log.e(TAG, "setActualChatContactList(null);");
		setActualChatContactList(null);

		Log.e(TAG, "setActualRemoteMonitoring(null);");
		setActualRemoteMonitoring(null);

		Log.e(TAG, "setActualUser(null);");
		setActualUser(null);

	}

	// ==============================================================================
	// TOPICS LIST + ACTUAL TOPIC
	// ==============================================================================
	public static List<TopicCategory> getTopicsList() {
		/*
		 * int counter = 0; for (TopicCategory actTopicCategory : allTopicsList)
		 * { for (Topic actTopic : actTopicCategory.getTopics()) { Log.d(TAG,
		 * "Topic#" + counter + "= " + actTopic.getTopicName()); counter++; } }
		 */
		Log.e(TAG, "getTopicsList");
		return allTopicsList;
	}

	public static void setAllTopicsList(List<TopicCategory> allTopicsList) {
		Session.allTopicsList = allTopicsList;
		int counter = 0;
		if (allTopicsList != null) {
			for (TopicCategory actTopicCategory : allTopicsList) {
				for (Topic actTopic : actTopicCategory.getTopics()) {
					Log.e(TAG,
							"Topic#" + counter + "= " + actTopic.getTopicName());
					counter++;
				}
			}
			Log.e(TAG, "setAllTopicsList");
			Log.e(TAG, "Összesen megkapott témakörök száma: " + counter);
		}
	}

	public static Topic getActualTopic() {
		Log.e(TAG, "getActualTopic");
		return actualTopic;
	}

	public static void setActualTopic(Topic actualTopic) {
		Log.e(TAG, "setActualTopic");
		Session.actualTopic = actualTopic;
	}

	// ==============================================================================
	// ALL CHART INFO CONTAINAR + ACTUAL CHART INFO CONTAINER
	// ==============================================================================
	public static List<ChartInfoContainer> getAllChartInfoContainer() {
		Log.e(TAG, "getAllChartInfoContainer");
		return allChartInfoContainer;
	}

	public static void setAllChartInfoContainer(
			List<ChartInfoContainer> allChartInfoContainer) {
		Log.e(TAG, "setAllChartInfoContainer");
		Session.allChartInfoContainer = allChartInfoContainer;
		// logAllChartInfoContainer();
	}

	public static ChartInfoContainer getActualChartInfoContainer() {
		Log.e(TAG, "getActualChartInfoContainer");
		return actualChartInfoContainer;
	}

	public static void setActualChartInfoContainer(
			ChartInfoContainer actualChartInfoContainer) {
		Log.e(TAG, "setActualChartInfoContainer");
		Session.actualChartInfoContainer = actualChartInfoContainer;
	}

	private static void logAllChartInfoContainer() {
		for (ChartInfoContainer actChartInfoContainer : getAllChartInfoContainer()) {
			String s = "id=" + actChartInfoContainer.getId() + "\n";
			s += "name=" + actChartInfoContainer.getName() + "\n";
			s += "ssz=" + actChartInfoContainer.getSerialNumber() + "\n";
			Log.e("Session.logAllChartInfoContainer()", s);
		}
	}

	// ==============================================================================
	// ACTUAL REMOTE MONITORING
	// ==============================================================================
	public static Place getActualRemoteMonitoring() {
		Log.e(TAG, "getActualRemoteMonitoring");
		return actualRemoteMonitoring;
	}

	public static void setActualRemoteMonitoring(Place actualRemoteMonitoring) {
		Log.e(TAG, "setActualRemoteMonitoring");
		Session.actualRemoteMonitoring = actualRemoteMonitoring;
	}

	// ==============================================================================
	// CONTACT REFRESHER + CHAT CONTACT LIST + ACTUAL CHAT PARTNER
	// ==============================================================================
	public static void startContactRefresherAsyncTask() {
		Log.e(TAG, "startContactRefresherAsyncTask");

		if (contactRefresherAsyncTask == null) {
			contactRefresherAsyncTask = new ContactListRefresherAsyncTask();
			contactRefresherAsyncTask.execute(5000L);
		}

	}

	public static void stopContactRefresherAsyncTask() {
		Log.e(TAG, "stopContactRefresherAsyncTask");
		if (contactRefresherAsyncTask != null) {
			contactRefresherAsyncTask.cancel(true);
			contactRefresherAsyncTask = null;
		}
	}

	public static void startContactRefresherThread() {

		if (contactRefresherThread.getState().equals(Thread.State.NEW)) {
			Log.e(TAG, "startContactRefresherThread NEW");
			contactRefresherThread.start();
		}
		contactRefresherRunnable.resume();
	}

	public static void stopContactRefresherThread() {
		Log.e(TAG, "stopContactRefresherThread");
		contactRefresherRunnable.pause();
		/*
		 * if (contactRefresherThread.getState().equals(Thread.State.RUNNABLE))
		 * { try { Log.i(TAG, "stopContactRefresherThread");
		 * contactRefresherRunnable.pause(); contactRefresherThread.wait(); }
		 * catch (InterruptedException e) { e.printStackTrace(); } }
		 */
	}

	public static ChatContactList getActualChatContactList() {
		// Log.e(TAG, "getActualChatContactList");
		return actualChatContactList;
	}

	public static void setActualChatContactList(
			ChatContactList newChatContactList) {
		Log.e(TAG, "setActualChatContactList");
		if (newChatContactList.getContacts() != null) {
			Session.actualChatContactList = new ChatContactList(
					newChatContactList.getContacts());
		}
	}

	public static ChatContact getActualChatPartner() {
		Log.e(TAG, "getActualChatPartner");
		return actualChatPartner;
	}

	/**
	 * This method set the actualChatPartner to the given one by newPartner
	 * parameter. If the old and the new one are not equal, then we make the
	 * chatMessagesList empty to be sure, that the messages of different
	 * contacts will be not mixed.
	 */
	public static void setActualChatPartner(ChatContact newPartner) {
		Log.e(TAG, "setActualChatPartner");
		if (actualChatPartner != null) {
			if (!(actualChatPartner.equals(newPartner))) {
				Log.e("Session.setActualChatPartner()", "Különbözik.");
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
	public static void startMessageRefresher() {
		Log.e(TAG, "startMessageRefresher");
		if (messageRefresher == null) {
			messageRefresher = new ChatMessagesRefresherAsyncTask();
			messageRefresher.execute(1500l);
		}
	}

	public static void stopMessageRefresher() {
		Log.e(TAG, "stopMessageRefresher");
		if (messageRefresher != null) {
			messageRefresher.cancel(true);
			messageRefresher = null;
		}
	}

	public static ChatMessagesList getChatMessagesList() {
		Log.e(TAG, "getChatMessagesList");
		if (chatMessagesList == null) {
			chatMessagesList = new ChatMessagesList(
					new ArrayList<ChatMessage>());
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
		Log.e(TAG, "setChatMessagesList()");
		if (newChatMessagesList == null) {
			Log.e("Session.setChatmessagesList()",
					"The newChatMessagesList parameter IS NULL!!!!");
		}
		if (chatMessagesList == null
				|| chatMessagesList.getChatMessagesList().isEmpty()) {
			chatMessagesList = newChatMessagesList;
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
		Log.e(TAG, "emptyChatMessagesList");
		if (chatMessagesList == null) {
			chatMessagesList = new ChatMessagesList(
					new ArrayList<ChatMessage>());
		} else if ((chatMessagesList != null)
				&& !(chatMessagesList.chatMessagesList.isEmpty())) {
			chatMessagesList.chatMessagesList.clear();
		}
	}

	public static void sortChatMessagesList() {
		Log.e("Session", "sortChatMessagesList()");
		if (Session.chatMessagesList != null) {
			Collections.sort(Session.chatMessagesList.chatMessagesList);
		}
	}

	/**
	 * This method gives back the order value of the last ChatMessage object
	 * from chatMessagesList or zero if the chatMessagesList is empty.
	 */
	public static int getLastChatMessageOrder() {
		Log.e(TAG, "getLastChatMessageOrder");
		if (chatMessagesList == null
				|| chatMessagesList.chatMessagesList.isEmpty()) {
			Log.e("LastChatMessageOrder=", "" + 0);
			return 0;
		}
		Log.e("LastChatMessageOrder=",
				""
						+ chatMessagesList.getChatMessagesList()
								.get(chatMessagesList.getChatMessagesList()
										.size() - 1).order);
		return chatMessagesList.getChatMessagesList().get(
				chatMessagesList.getChatMessagesList().size() - 1).order;
	}

	// ==============================================================================
	// ACTUAL CHART
	// ==============================================================================
	public static Chart getActualChart() {
		Log.e(TAG, "getActualChart");
		return actualChart;
	}

	public static void setActualChart(Chart actualChart) {
		Log.e(TAG, "setActualChart");
		Session.actualChart = actualChart;
		refreshChartIntervals();
	}

	// ==============================================================================
	// PROGRESS DIALOG + ALERT DIALOG
	// ==============================================================================
	public static void setProgressDialog(ProgressDialog p_ProgressDialog) {
		if (progressDialog == null) {
			Log.e(TAG, "setProgressDialog");
			Session.progressDialog = p_ProgressDialog;
		}
	}

	public static void showProgressDialog() {
		if (progressDialog != null) {
			Log.e(TAG, "showProgressDialog");
			progressDialog.show();
		}
	}

	public static void dismissAndMakeNullProgressDialog() {
		progressDialog.dismiss();
		progressDialog = null;
		/*
		 * if (progressDialog != null) { Log.e(TAG, "Dismiss progress dialog");
		 * progressDialog.dismiss(); progressDialog = null; }
		 */
	}

	public static void setAlertDialog(AlertDialog ad) {
		Log.e(TAG, "setAlertDialog");
		Session.alertDialog = ad;
	}

	public static void showAlertDialog() {
		Log.e(TAG, "showAlertDialog");
		alertDialog.show();
	}

	public static void dismissAndMakeNullAlertDialog() {
		if (alertDialog != null) {
			Log.d(TAG, "Dismiss alert dialog");
			alertDialog.dismiss();
			alertDialog = null;
		}
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
		Calendar minDate = null;
		Calendar maxDate = null;
		try {
			minDate = Calendar.getInstance();
			maxDate = Calendar.getInstance();
			maxDate.setTime(new SimpleDateFormat("yyyy.MM.dd hh:mm:ss")
					.parse("1900.01.01 00:00:00"));
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
								maxDate = date;
							}
							if (date.before(minDate)) {
								minDate = date;
							}
							if (value > maxValue) {
								maxValue = value;
							}
							if (value < minValue) {
								minValue = value;
							}
						}
					}
					endChartDate = maxDate;
					beginChartDate = minDate;
					maximalChartValue = maxValue;
					minimalChartValue = minValue;
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd_HH:mm");
					Log.d("maximalChartDate",
							""
									+ formatter.format(endChartDate
											.getTimeInMillis()));
					Log.d("minimalChartDate",
							""
									+ formatter.format(beginChartDate
											.getTimeInMillis()));
					Log.d("maximalChartValue", "" + maximalChartValue);
					Log.d("minimalChartValue", "" + minimalChartValue);
				}
			}
		}
	}

}
