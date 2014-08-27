package hu.mep.utils.others;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class Slider extends SeekBar {

private Drawable mThumb;

	public Slider(Context context) {
	    super(context);
	
	}
	
	public Slider(Context context, AttributeSet attrs) {
	    super(context, attrs);
	
	}
	
	@Override
	public void setThumb(Drawable thumb) {
	    super.setThumb(thumb);
	    mThumb = thumb;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	
	        if (event.getX() >= mThumb.getBounds().left
	                && event.getX() <= mThumb.getBounds().right
	                && event.getY() <= mThumb.getBounds().bottom
	                && event.getY() >= mThumb.getBounds().top) {
	
	            super.onTouchEvent(event);
	        } else {
	            return false;
	        }
	    } else if (event.getAction() == MotionEvent.ACTION_UP) {
	        super.onTouchEvent(event);
	    } else {
	        super.onTouchEvent(event);
	    }
	
	    return true;
	}
	
}