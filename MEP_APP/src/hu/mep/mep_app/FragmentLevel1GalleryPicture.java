package hu.mep.mep_app;

import hu.alter.mep_app.R;
import hu.mep.datamodells.Session;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentLevel1GalleryPicture extends Fragment {
	
	private int number;
	
	public FragmentLevel1GalleryPicture() {
	}
	
	public FragmentLevel1GalleryPicture(int number) {
		this.number = number;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_firstlevel_gallery_picture, container, false);
		ImageView imageView = (ImageView) rootView.findViewById(R.id.fragment_firstlevel_gallery_picture_imageview);
		imageView.setImageBitmap(Session.getGalleryImagesList().get(number));
		
		return rootView;
	}
	

}
