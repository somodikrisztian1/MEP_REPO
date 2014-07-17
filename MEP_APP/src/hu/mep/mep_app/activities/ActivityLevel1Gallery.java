package hu.mep.mep_app.activities;

import hu.mep.datamodells.Session;
import hu.mep.mep_app.FragmentLevel1GalleryPicture;
import hu.mep.mep_app.R;
import hu.mep.utils.others.DepthPageTransformer;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

public class ActivityLevel1Gallery extends FragmentActivity {

	private static int NUM_PAGES;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_firstlevel_gallery);

		Session.getInstance(this).getActualCommunicationInterface().getGalleryURLsAndPictures();
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
