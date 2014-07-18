package hu.mep.mep_app.activities;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel1GalleryPicture;
import hu.mep.mep_app.R;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

public class ActivityLevel1Gallery extends FragmentActivity {

	private static final String TAG = "ActivityLevel1Gallery";
	private static int NUM_PAGES;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_firstlevel_gallery);
		
		if (Session.getInstance(this).isTablet()) {
			Log.e(TAG, "IT'S A TABLET");
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
		}
		else {
			Log.e(TAG, "IT'S NOT A TABLET");
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		Session.getActualCommunicationInterface().getGalleryURLsAndPictures();

		NUM_PAGES = Session.getGalleryImageURLSList().size();
		
		mPager = (ViewPager) findViewById(R.id.activity_firstlevel_gallery_viewpager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		//mPager.setPageTransformer(true, new DepthPageTransformer());
		
		mPager.setAdapter(mPagerAdapter);
		
	}

	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new FragmentLevel1GalleryPicture(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

	
}
