package hu.mep.mep_app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentLoginScreen extends Fragment {

	private static final String TAG = "FragmentLoginScreen";
	private EditText usernameEdittext;
	private EditText passwordEdittext;
	private Button loginButton;
	
	private static Context context;
	
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
    	context = getActivity();
      	Log.d(TAG, " after onCreate");
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Log.d(TAG, " before onCreateView");
    	View rootView = inflater.inflate(R.layout.fragment_login_screen, container, false);
        int index = getArguments().getInt(FragmentMainScreen.CLICKED_DRAWER_ITEM_NUMBER);
        String newTitle = getResources().getStringArray(R.array.first_activity_drawer_items_list)[index];
    	Log.d(TAG, " inside onCreateView 1");
        
        usernameEdittext = (EditText) container.findViewById(R.id.fragment_login_screen_username_edittext);
        passwordEdittext = (EditText) container.findViewById(R.id.fragment_login_screen_password_edittext);
        loginButton = (Button) container.findViewById(R.id.fragment_login_screen_login_button);
        
    	Log.d(TAG, " inside onCreateView 2");
        loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(TAG, "before onClick");
				switch (v.getId()) {
				case R.id.fragment_login_screen_login_button:
					Log.d(TAG, "onClick - loginbutton");
					String username_for_send = usernameEdittext.getText().toString();
					String password_for_send = passwordEdittext.getText().toString();

					Log.d("LOGIN - USERNAME", username_for_send);
					Log.d("LOGIN _ PASSWORD", password_for_send);
					
					/* !TODO Kommunikáció - bejelentkeztetés!!!!! */
					/*
					Toast toast = new Toast(context);
					toast.makeText(context, "Bejelentkezés folyamatban...", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					*/
					break;

				default:
					Log.d(TAG, "onClick - default");
					break;
				}
		      	Log.d(TAG, "after onClick");
			}
		});
        getActivity().setTitle(newTitle);
      	Log.d(TAG, " after onCreateView");
        return rootView;
    }

    /*
	@Override
	public void onClick(View v) {
		Log.d(TAG, "before onClick");
		switch (v.getId()) {
		case R.id.fragment_login_screen_login_button:
			Log.d(TAG, "onClick - loginbutton");
			String username_for_send = usernameEdittext.getText().toString();
			String password_for_send = passwordEdittext.getText().toString();

			Log.d("LOGIN - USERNAME", username_for_send);
			Log.d("LOGIN _ PASSWORD", password_for_send);
			
			// !TODO Kommunikáció - bejelentkeztetés!!!!! 
			
			Toast toast = new Toast(context);
			toast.makeText(context, "Bejelentkezés folyamatban...", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			
			break;

		default:
			Log.d(TAG, "onClick - default");
			break;
		}
      	Log.d(TAG, "after onClick");
		
	}
 */   
}
