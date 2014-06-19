package hu.mep.datamodells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import hu.mep.communication.ICommunicator;
import hu.mep.communication.RealCommunicator;

public class Session {

	private static final String TAG = "Session";
	private static Session instance;
	private static User actualUser;
	private static Context context;

	private static ICommunicator actualCommunicationInterface;
	
	private static ChatContactList actualChatContactList;
	private static ChatContact actualChatPartner;

	private static ChatMessagesList chatMessagesList;
	private static Place actualRemoteMonitoring;

	private static ProgressDialog progressDialog;
	
	private static List<TopicCategory> allTopicsList;
	private static Topic actualTopic;

	private static List<ChartInfoContainer> allChartInfoContainer;
	private static ChartInfoContainer actualChartInfoContainer;

	private static Chart actualChart;
	
	

	/**
	 * This method gives back the order value of the last ChatMessage object
	 * from chatMessagesList or zero if the chatMessagesList is empty.
	 */
	public static int getLastChatMessageOrder() {
		if (chatMessagesList == null
				|| chatMessagesList.chatMessagesList.isEmpty()) {
			Log.e("LastChatMessageOrder=", "" + 0);
			return 0;
		}
		Log.e("LastChatMessageOrder=", "" + chatMessagesList.getChatMessagesList().get(
				chatMessagesList.getChatMessagesList().size() - 1).order);
		return chatMessagesList.getChatMessagesList().get(
				chatMessagesList.getChatMessagesList().size() - 1).order;
	}

	public static ChartInfoContainer getActualChartInfoContainer() {
		return actualChartInfoContainer;
	}

	public static void setActualChartInfoContainer(
			ChartInfoContainer actualChartInfoContainer) {
		Session.actualChartInfoContainer = actualChartInfoContainer;
	}

	public static ChatMessagesList getChatMessagesList() {
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
		Log.e("Session", "setChatMessagesList()");
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

	public static ChatContactList getActualChatContactList() {
		return actualChatContactList;
	}

	public static void setActualChatContactList(
			ChatContactList newChatContactList) {
		Session.actualChatContactList = newChatContactList;
	}

	/**
	 * This method makes the chatMessagesList empty. Call it, when new
	 * actualChatPartner choosed.
	 */
	public static void emptyChatMessagesList() {
		if (chatMessagesList == null) {
			chatMessagesList = new ChatMessagesList(
					new ArrayList<ChatMessage>());
		} else if ((chatMessagesList != null)
				&& !(chatMessagesList.chatMessagesList.isEmpty())) {
			chatMessagesList.chatMessagesList.clear();
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

	private Session(Context context) {
		this.context = context;
		actualCommunicationInterface = RealCommunicator.getInstance(context);
		emptyChatMessagesList();
	}

	public static Session getInstance(Context context) {
		if (instance == null) {
			instance = new Session(context);
		}
		else if( instance.context != context) {
			instance.context = context;
		}
		return instance;
	}

	public static User getActualUser() {
		return actualUser;
	}

	public static void setActualUser(User newUser) {
		actualUser = newUser;
	}
	
	public static Place getActualRemoteMonitoring() {
		return actualRemoteMonitoring;
	}

	public static void setActualRemoteMonitoring(Place actualRemoteMonitoring) {
		Session.actualRemoteMonitoring = actualRemoteMonitoring;
	}

	public static ICommunicator getActualCommunicationInterface() {
		if (actualCommunicationInterface == null) {
			actualCommunicationInterface = RealCommunicator
					.getInstance(context);
		}
		return actualCommunicationInterface;
	}

	public static void setProgressDialog(ProgressDialog progressDialog) {
		Log.d(TAG, "Set progress dialog");
		Session.progressDialog = progressDialog;
	}

	public static void dismissProgressDialog() {
		if (progressDialog != null) {
			Log.d(TAG, "Dismiss progress dialog");
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public static void showProgressDialog() {
		Log.d(TAG, "Show progress dialog");
		progressDialog.show();
	}

	public static void sortChatMessagesList() {
		Log.e("Session", "sortChatMessagesList()");
		Collections.sort(Session.chatMessagesList.chatMessagesList);
	}

	public static List<TopicCategory> getTopicsList() {
		/*int counter = 0;
		for (TopicCategory actTopicCategory : allTopicsList) {
			for (Topic actTopic : actTopicCategory.getTopics()) {
				Log.d(TAG, "Topic#" + counter + "= " + actTopic.getTopicName());
				counter++;
			}	
		}*/
		return allTopicsList;
	}

	public static void setAllTopicsList(List<TopicCategory> allTopicsList) {
		Session.allTopicsList = allTopicsList;
		int counter = 0;
		for (TopicCategory actTopicCategory : allTopicsList) {
			for (Topic actTopic : actTopicCategory.getTopics()) {
				Log.e(TAG, "Topic#" + counter + "= " + actTopic.getTopicName());
				counter++;
			}	
		}
		Log.e(TAG, "Összesen megkapott témakörök száma: " + counter);
	}
	
	public static Topic getActualTopic() {
		return actualTopic;
	}

	public static void setActualTopic(Topic actualTopic) {
		Session.actualTopic = actualTopic;
	}
	
	public static List<ChartInfoContainer> getAllChartInfoContainer() {
		return allChartInfoContainer;
	}

	public static void setAllChartInfoContainer(
			List<ChartInfoContainer> allChartInfoContainer) {
		Session.allChartInfoContainer = allChartInfoContainer;
	}
	
	public static Chart getActualChart() {
		return actualChart;
	}

	public static void setActualChart(Chart actualChart) {
		Session.actualChart = actualChart;
	}
	
}
