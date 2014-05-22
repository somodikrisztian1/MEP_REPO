package hu.mep.mep_app;

import hu.mep.communication.ICommunicator;
import hu.mep.communication.SillyCommunicator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout.Directions;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentLoginScreen extends Fragment implements OnClickListener{

	private static final String TAG = "FragmentLoginScreen";
	private FragmentEventHandler fragmentEventHandler;
	private EditText usernameEdittext;
	private EditText passwordEdittext;
	private Button loginButton;
	private ICommunicator comm;

	private Context context;

	public FragmentLoginScreen() {
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, " before onCreate");
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		Log.d(TAG, " after onCreate");
		comm = SillyCommunicator.getInstance();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, " before onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_login_screen,
				container, false);
		int index = getArguments().getInt(
				FragmentMainScreen.CLICKED_DRAWER_ITEM_NUMBER);
		String newTitle = getResources().getStringArray(
				R.array.first_activity_drawer_items_list)[index];
		Log.d(TAG, " inside onCreateView 1");

		getActivity().setTitle(newTitle);

		usernameEdittext = (EditText)rootView.findViewById(
				R.id.fragment_login_screen_username_edittext);
		passwordEdittext = (EditText) rootView.findViewById(
				R.id.fragment_login_screen_password_edittext);
		loginButton = (Button) rootView.findViewById(
				R.id.fragment_login_screen_login_button);
		
		usernameEdittext.setFocusable(true);
		usernameEdittext.requestFocus();
		passwordEdittext.setFocusable(true);
		
		loginButton.setOnClickListener(this);
		
		/*//Így kellene valahogy a jelszó begépelése utáni Enter ütésre bejelentkeztetni ...
		TextView.OnEditorActionListener clickToLogin = new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE && event.getAction() == KeyEvent.ACTION_DOWN) {
						loginButton.callOnClick();
						//vagy
						loginButton.performClick();
					return true;
				}
				return false;
			}
		};

		passwordEdittext.setOnEditorActionListener(clickToLogin);
		*/
		Log.d(TAG, " after onCreateView");
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			fragmentEventHandler = (FragmentEventHandler) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement FragmentEventHandler interface...");
		}
	}

	@Override
	public void onClick(View v) {
		Log.d(TAG, "before onClick");
		switch (v.getId()) {
		case R.id.fragment_login_screen_login_button:
			Log.d(TAG, "onClick - loginbutton");
			String username_for_send = usernameEdittext.getText()
					.toString();
			String password_for_send = passwordEdittext.getText()
					.toString();

			// !TODO Kommunikáció - bejelentkeztetés!!!!!
			comm.authenticateUser(username_for_send, password_for_send);
			fragmentEventHandler.onLoginButtonPressed(
					username_for_send, password_for_send);

			break;

		default:
			Log.d(TAG, "onClick - default");
			break;
		}
		Log.d(TAG, "after onClick");
	}
}
