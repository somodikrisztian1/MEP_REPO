package hu.mep.mep_app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;

public class ActivityLevel2NEW extends ActionBarActivity {

	ActionBar mActionBar;
	Tab tabTopics;
	Tab tabRemoteMonitorings;
	Tab tabChat;
	TabListener tabListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowTitleEnabled(false);

		tabRemoteMonitorings = mActionBar
				.newTab()
				.setText("Témakörök")
				.setTabListener(
						new hu.mep.mep_app.TabListener<FragmentLevel2Topics>(
								this, "topics",
								FragmentLevel2Topics.class));
		
		
		tabRemoteMonitorings = mActionBar
				.newTab()
				.setText("Távfelügyelet")
				.setTabListener(
						new hu.mep.mep_app.TabListener<FragmentLevel2RemoteMonitorings>(
								this, "remotemonitorings",
								FragmentLevel2RemoteMonitorings.class));

		tabChat = mActionBar
				.newTab()
				.setText("Chat")
				.setTabListener(
						new hu.mep.mep_app.TabListener<FragmentLevel2Chat>(
								this, "chat", FragmentLevel2Chat.class));

		mActionBar.addTab(tabTopics);
		mActionBar.addTab(tabRemoteMonitorings);
		mActionBar.addTab(tabChat);
	}
}
