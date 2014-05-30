package hu.mep.mep_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ActionBarLevel2 implements OnClickListener {

	private ActionBarActivity activity;

	public ActionBarLevel2(ActionBarActivity activity) {
		this.activity = activity;
	}

	public void setLayout() {
		/*
		 * ViewGroup actionBarLevel2Layout = (ViewGroup)
		 * activity.getLayoutInflater().inflate(R.layout.actionbar_secondlevel,
		 * null);
		 * 
		 * ActionBar ab = activity.getSupportActionBar();
		 * ab.setDisplayShowHomeEnabled(false);
		 * ab.setDisplayShowTitleEnabled(false);
		 * ab.setDisplayShowCustomEnabled(true);
		 * ab.setCustomView(actionBarLevel2Layout);
		 * ab.setSelectedNavigationItem(
		 * R.id.actionbar_secondlevel_button_remote_monitorings);
		 * //actionBarLevel2Layout
		 * .findViewById(R.id.actionbar_secondlevel_button_remote_monitorings
		 * ).performClick();
		 */

	}

	@Override
	public void onClick(View v) {
		boolean readyForFragmentLoading = false;
		switch (v.getId()) {
		case R.id.actionbar_secondlevel_button_menu:
			/*
			 * Megfelelő új adapter példányosítása és a listview-hoz
			 * hozzárendelés!!!
			 */
			break;
		case R.id.actionbar_secondlevel_button_topics:
			break;
		case R.id.actionbar_secondlevel_button_remote_monitorings:
			break;
		case R.id.actionbar_secondlevel_button_chat:
			break;
		default:
			break;
		}
	}

}
