package hu.mep.mep_app;

import hu.mep.datamodells.ChatContact;
import hu.mep.datamodells.Session;
import hu.mep.utils.ChatContactListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentLevel2Chat extends Fragment implements OnItemClickListener {

	private static ListView listview;
	public static ArrayAdapter<ChatContact> contactAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Session.getInstance(getActivity()).getActualCommunicationInterface()
				.getChatPartners();

		View v = inflater.inflate(R.layout.fragment_chat, container);
		contactAdapter = new ChatContactListAdapter(getActivity(),
				R.id.fragment_chat_listview, Session.getInstance(getActivity())
						.getActualChatContactList().getContacts());

		listview = (ListView) v.findViewById(R.id.fragment_chat_listview);
		listview.setAdapter(contactAdapter);
		listview.setOnItemClickListener(this);

		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Session.getInstance(getActivity()).setActualChatPartner(
				Session.getInstance(getActivity()).getActualChatContactList()
						.getContacts().get(position));
		Intent intent = new Intent(getActivity(), ActivityLevel3Chat.class);
		startActivity(intent);

	}

}
