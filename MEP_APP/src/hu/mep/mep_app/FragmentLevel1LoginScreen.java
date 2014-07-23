package hu.mep.mep_app;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.activities.ActivityLevel1;
import hu.mep.mep_app.activities.ActivityLevel1Registration;
import hu.mep.utils.others.FragmentLevel1EventHandler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentLevel1LoginScreen extends Fragment implements
		OnClickListener {

	private static final String TAG = "FragmentLoginScreen";
	private FragmentLevel1EventHandler fragmentEventHandler;

	private EditText usernameEdittext;
	private EditText passwordEdittext;
	Button loginButton;
	private Button regButton;

	public FragmentLevel1LoginScreen() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_firstlevel_login_screen, container, false);

		getActivity().setTitle(getResources().getString(R.string.login));

		usernameEdittext = (EditText) rootView
				.findViewById(R.id.fragment_login_screen_username_edittext);
		passwordEdittext = (EditText) rootView
				.findViewById(R.id.fragment_login_screen_password_edittext);
		loginButton = (Button) rootView
				.findViewById(R.id.fragment_login_screen_login_button);
		regButton = (Button) rootView.findViewById(R.id.fragment_login_screen_registration_button);

		usernameEdittext.setFocusable(true);
		// usernameEdittext.requestFocus();
		passwordEdittext.setFocusable(true);

		loginButton.setOnClickListener(this);
		regButton.setOnClickListener(this);

		passwordEdittext
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						/* Talán szebb lenne a billentyűzetet is eltűntetni, de egyenlőre jó így... :D */
						loginButton.performClick();
						return true;
					}
				});
		
		// getActivity().getSupportFragmentManager().popBackStack();
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			fragmentEventHandler = (FragmentLevel1EventHandler) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentEventHandler interface...");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_login_screen_login_button:
			
			String username_for_send = usernameEdittext.getText().toString();
			String password_for_send = passwordEdittext.getText().toString();
			fragmentEventHandler.onLoginButtonPressed(username_for_send,
					password_for_send);
			break;
		case R.id.fragment_login_screen_registration_button:
			Intent myIntent = new Intent(getActivity(), ActivityLevel1Registration.class);
			myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(myIntent);
		default:
			break;
		}
	}
	
	private ProgressDialog prepareProgressDialogForLoading1() {
		ProgressDialog pd = new ProgressDialog(getActivity());
		pd.setCancelable(false);
		pd.setTitle("Kérem várjon!");
		pd.setMessage("Felhasználói adatok ellenőrzése folyamatban...");
		return pd;
	}

}
