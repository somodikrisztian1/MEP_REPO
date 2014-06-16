package hu.mep.mep_app;

import android.R.anim;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar.Tab;

public class TabListener<T extends Fragment> implements android.support.v7.app.ActionBar.TabListener {

	private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

	
    public TabListener(Activity activity, String tag, Class<T> clz) {
    	mActivity = activity;
    	mTag = tag;
    	mClass = clz;
    }
    
    @Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
    	if(mFragment == null) {
    		mFragment = Fragment.instantiate(mActivity, mClass.getName());
    		ft.add(android.R.id.content, mFragment, mTag);
    	} else {
    		ft.attach(mFragment);
    	}
		
	}
    
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }

	}

}
