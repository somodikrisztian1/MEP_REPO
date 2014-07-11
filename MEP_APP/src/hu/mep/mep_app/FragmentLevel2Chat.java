package hu.mep.mep_app;

import hu.mep.communication.ContactListRefresherAsyncTask;
import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel3Chat;
import hu.mep.utils.adapters.ChatContactListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentLevel2Chat extends Fragment implements OnItemClickListener {

	public static ListView listview;
	public static ArrayAdapter<ChatContact> contactAdapter;
	
	ContactListRefresherAsyncTask refresher;
	//private static final long REFRESH_WAIT_TIME = 3000;
	private static final String TAG = "FragmentLevel2Chat";
	
	public FragmentLevel2Chat() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Session.getInstance(getActivity()).getActualCommunicationInterface()
				.getChatPartners();

		View v = inflater.inflate(R.layout.fragment_secondlevel_chat, container, false);
		contactAdapter = new ChatContactListAdapter(getActivity(),
				R.id.fragment_chat_listview, Session.getActualChatContactList().getContacts());

		listview = (ListView) v.findViewById(R.id.fragment_chat_listview);
		listview.setAdapter(contactAdapter);
		listview.setOnItemClickListener(this);
		
		
		//Session.startContactRefresher();
		
		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Session.setActualChatPartner(Session.getActualChatContactList().getContacts().get(position));
		
		Intent intent = new Intent(getActivity(), ActivityLevel3Chat.class);
		startActivity(intent);

	}
	
	@Override
	public void onDestroyView() {

		//Session.stopContactRefresher();
		super.onDestroyView();
	}

}
