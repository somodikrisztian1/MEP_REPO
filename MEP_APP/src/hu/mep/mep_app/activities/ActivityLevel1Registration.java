package hu.mep.mep_app.activities;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLevel1Registration extends ActionBarActivity implements OnClickListener {

	private EditText fullName;
	private EditText email;
	private EditText userName;
	private EditText password;
	private EditText passwordAgain;
	private Button regButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level1_registration);
		
		fullName = (EditText) findViewById(R.id.reg_fullname);
		email = (EditText) findViewById(R.id.reg_email);
		userName = (EditText) findViewById(R.id.reg_username);
		password = (EditText) findViewById(R.id.reg_password);
		passwordAgain = (EditText) findViewById(R.id.reg_passowrdagain);
		
		regButton = (Button) findViewById(R.id.registrate_button);
		
		regButton.setOnClickListener(this);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//TODO:
	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.registrate_button)
		{
			if ( fullName.getText().toString().length() > 0 && email.getText().toString().length() > 0 && 
					userName.getText().toString().length() > 0 && password.getText().toString().length() > 0 && 
						passwordAgain.getText().toString().length() > 0)
			{
				if (password.getText().toString().compareTo(passwordAgain.getText().toString()) == 0)
				{
					if (Session.getActualCommunicationInterface().registrateUser(fullName.getText().toString(), email.getText().toString(),
						userName.getText().toString(), password.getText().toString() ).compareTo("Success") == 0 )
					{
						Intent i = new Intent(this, ActivityLevel2NEW.class);
						startActivity(i);
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Nem egyeznek a jelszavak.", Toast.LENGTH_LONG).show();
				}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Minden mező kitöltése kötelező.", Toast.LENGTH_LONG).show();
			}
		}
		
	}
}
