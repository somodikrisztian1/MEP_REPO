package hu.mep.mep_app;

import hu.mep.datamodells.Session;

import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ActivityLevel2SectionsPagerAdapter extends FragmentPagerAdapter {

	private static final String TAG = "ActivityLevel2SectionsPagerAdapter";

	public ActivityLevel2SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public int getCount() {
		boolean mekutos = Session.getActualUser().isMekut();
		boolean tavfelugyeletes = (Session.getActualUser().getUsersPlaces() != null);
		// MEKUT-os és van távfelügyelete...
		if (mekutos && tavfelugyeletes) {
			return 3;
		}
		// MEKUT-os és nincs távfelügyelete....
		else if (mekutos && !tavfelugyeletes) {
			return 2;
			
		}
		// Nem MEKUT-os, de van távfelügyelete...
		else if (!mekutos && tavfelugyeletes) {
			return 2;
		}
		// Nem MEKUT-os és nincs távfelügyelete sem...
		else if (!mekutos && !tavfelugyeletes) {
			return 1;
		}
		return 2;
	}

	@Override
	public Fragment getItem(int position) {
		boolean mekutos = Session.getActualUser().isMekut();
		boolean tavfelugyeletes = (Session.getActualUser().getUsersPlaces() != null);
		// MEKUT-os és van távfelügyelete is...
		if (mekutos && tavfelugyeletes) {
			return getItemForMekutAndOwner(position);
		}
		// MEKUT-os és nincs távfelügyelete....
		else if (mekutos && !tavfelugyeletes) {
			return getItemForMekutButNotOwner(position);
		}
		// Nem MEKUT-os, de van távfelügyelete...
		else if (!mekutos && tavfelugyeletes) {
			return getItemForNotMekutButOwner(position);
		}
		// Nem MEKUT-os, és nincs távfelügyelete sem...
		else if (!mekutos && !tavfelugyeletes) {
			return getItemForNotMekutAndNotOwner(position);
		}
		return null;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		boolean mekutos = Session.getActualUser().isMekut();
		boolean tavfelugyeletes = (Session.getActualUser().getUsersPlaces() != null);

		// MEKUT-os és van távfelügyelete is...
		if (mekutos && tavfelugyeletes) {
			Log.e(TAG, "getPageTitleForMekutAndOwner(" + position + ")");
			return getPageTitleForMekutAndOwner(position);
		}
		// MEKUT-os és nincs távfelügyelete....
		else if (mekutos && !tavfelugyeletes) {
			Log.e(TAG, "getPageTitleForMekutButNotOwner(" + position + ")");
			return getPageTitleForMekutButNotOwner(position);
		}
		// Nem MEKUT-os, de van távfelügyelete...
		else if (!mekutos && tavfelugyeletes) {
			Log.e(TAG, "getPageTitleForNotMekutButOwner(" + position + ")");
			return getPageTitleForNotMekutButOwner(position);
		}
		// Nem MEKUT-os, és nincs távfelügyelete sem...
		else if (!mekutos && !tavfelugyeletes) {
			Log.e(TAG, "getPageTitleForNotMekutAndNotOwner(" + position + ")");
			return getPageTitleForNotMekutAndNotOwner(position);
		}
		return null;
	}

	public int getPageIcon(int position) {
		boolean mekutos = Session.getActualUser().isMekut();
		boolean tavfelugyeletes = (Session.getActualUser().getUsersPlaces() != null);

		// MEKUT-os és van távfelügyelete is...
		if (mekutos && tavfelugyeletes) {
			Log.e(TAG, "getPageIconForMekutAndOwner(" + position + ")");
			return getPageIconForMekutAndOwner(position);
		}
		// MEKUT-os és nincs távfelügyelete....
		else if (mekutos && !tavfelugyeletes) {
			Log.e(TAG, "getPageIconForMekutButNotOwner(" + position + ")");
			return getPageIconForMekutButNotOwner(position);
		}
		// Nem MEKUT-os, de van távfelügyelete...
		else if (!mekutos && tavfelugyeletes) {
			Log.e(TAG, "getPageIconForNotMekutButOwner(" + position + ")");
			return getPageIconForNotMekutButOwner(position);
		}
		// Nem MEKUT-os, és nincs távfelügyelete sem...
		else if (!mekutos && !tavfelugyeletes) {
			Log.e(TAG, "getPageIconForNotMekutAndNotOwner(" + position + ")");
			return getPageIconForNotMekutAndNotOwner(position);
		}
		Log.e(TAG, "getPageIcon now returning 0");
		return 0;
	}

	// ==============================================================================
	// MEKUT AND OWNER
	// ==============================================================================
	private Fragment getItemForMekutAndOwner(int position) {
		switch (position) {
		case 0:
			return new FragmentLevel2Topics();
		case 1:
			return new FragmentLevel2RemoteMonitorings();
		case 2:
			return new FragmentLevel2Chat();
		default:
			return null;
		}

	}

	private CharSequence getPageTitleForMekutAndOwner(int position) {
		switch (position) {
		case 0:
			return "Témakörök";
		case 1:
			return "Távfelügyelet";
		case 2:
			return "Chat";
		default:
			return null;
		}
	}

	public int getPageIconForMekutAndOwner(int position) {
		switch (position) {
		case 0:
			return R.drawable.topics_icon;
		case 1:
			return R.drawable.remote_monitorings_icon;
		case 2:
			return R.drawable.chat_icon;
		default:
			return 0;
		}
	}

	// ==============================================================================
	// MEKUT BUT NOT OWNER
	// ==============================================================================
	private Fragment getItemForMekutButNotOwner(int position) {
		switch (position) {
		case 0:
			return new FragmentLevel2Topics();
		case 1:
			return new FragmentLevel2Chat();
		default:
			return null;
		}
	}

	private CharSequence getPageTitleForMekutButNotOwner(int position) {
		switch (position) {
		case 0:
			return "Témakörök";
		case 1:
			return "Chat";
		default:
			return null;
		}
	}

	private int getPageIconForMekutButNotOwner(int position) {
		switch (position) {
		case 0:
			return R.drawable.topics_icon;
		case 1:
			return R.drawable.chat_icon;
		default:
			return 0;
		}
	}

	// ==============================================================================
	// NOT MEKUT BUT OWNER
	// ==============================================================================
	private Fragment getItemForNotMekutButOwner(int position) {
		switch (position) {
		case 0:
			return new FragmentLevel2RemoteMonitorings();
		case 1:
			return new FragmentLevel2Chat();
		default:
			return null;
		}
	}

	private CharSequence getPageTitleForNotMekutButOwner(int position) {
		switch (position) {
		case 0:
			return "Távfelügyelet";
		case 1:
			return "Chat";
		default:
			return null;
		}
	}

	private int getPageIconForNotMekutButOwner(int position) {
		switch (position) {
		case 0:
			return R.drawable.remote_monitorings_icon;
		case 1:
			return R.drawable.chat_icon;
		default:
			return 0;
		}
	}

	// ==============================================================================
	// NOT MEKUT AND NOT OWNER
	// ==============================================================================
	private Fragment getItemForNotMekutAndNotOwner(int position) {
		switch (position) {
		case 0:
			return new FragmentLevel2Chat();
		default:
			return null;
		}
	}

	private CharSequence getPageTitleForNotMekutAndNotOwner(int position) {
		switch (position) {
		case 0:
			return "Chat";
		default:
			return null;
		}
	}

	private int getPageIconForNotMekutAndNotOwner(int position) {
		switch (position) {
		case 0:
			return R.drawable.chat_icon;
		default:
			return 0;
		}
	}

}
