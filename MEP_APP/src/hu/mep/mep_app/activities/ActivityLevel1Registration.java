package hu.mep.mep_app.activities;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.R;
import hu.mep.utils.others.AlertDialogFactory;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLevel1Registration extends ActionBarActivity implements
		OnClickListener {

	private EditText fullNameEdittext;
	private EditText emailEdittext;
	private EditText userNameEdittext;
	private EditText passwordEdittext;
	private EditText passwordAgainEdittext;
	private Button registrateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level1_registration);
		setTitle("Regisztráció");
		fullNameEdittext = (EditText) findViewById(R.id.reg_fullname);
		emailEdittext = (EditText) findViewById(R.id.reg_email);
		userNameEdittext = (EditText) findViewById(R.id.reg_username);
		passwordEdittext = (EditText) findViewById(R.id.reg_password);
		passwordAgainEdittext = (EditText) findViewById(R.id.reg_passowrdagain);
		registrateButton = (Button) findViewById(R.id.registrate_button);
		registrateButton.setOnClickListener(this);
		ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(true);
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_level1_registration, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.homeAsUp) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.registrate_button) {
			String fullName = fullNameEdittext.getText().toString();
			String userName = userNameEdittext.getText().toString();
			String password = passwordEdittext.getText().toString();
			String passwordAgain = passwordAgainEdittext.getText().toString();
			String email = emailEdittext.getText().toString();
			String trimmedPassword = password.trim();
			String trimmedPasswordAgain = passwordAgain.trim();
			
			if( fullName.length() == 0 ||
				userName.length() == 0 ||
				password.length() == 0 ||
				passwordAgain.length() == 0) {
				Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForNoFullyLoadedCells(ActivityLevel1Registration.this));
				Session.showAlertDialog();
			}
			else if(userName.trim().equals("")) {
				Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForBadUsername(ActivityLevel1Registration.this));
				Session.showAlertDialog();
			}
			else if(!password.equals(passwordAgain)) {
				Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForNoMatchingPasswords(ActivityLevel1Registration.this));
				Session.showAlertDialog();
			}
			else if(trimmedPassword.equals("")) {
				Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForBadPasswords(ActivityLevel1Registration.this));
				Session.showAlertDialog();
			}
			else if(!password.equals(trimmedPassword)) {
				Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogForTrimmedPasswords(ActivityLevel1Registration.this, userName, trimmedPassword, email, fullName));
				Session.showAlertDialog();
			}
			else if(trimmedPassword.equals(trimmedPasswordAgain)) {
				Session.getActualCommunicationInterface().registrateUser(fullName, email, userName, trimmedPassword);
				
				if(Session.isSuccessfulRegistration()) {
					Session.getActualCommunicationInterface().authenticateUser(userName, trimmedPassword);
					
					Intent i = new Intent(this, ActivityLevel2NEW.class);
					startActivity(i);
				}
				else if(Session.isSuccessfulRegistration() == false) {
					Session.setAlertDialog(AlertDialogFactory.prepareAlertDialogWithText(ActivityLevel1Registration.this, Session.getUnsuccessfulRegistrationMessage()));
					Session.showAlertDialog();
				}
			}
		}
	}
	
}
