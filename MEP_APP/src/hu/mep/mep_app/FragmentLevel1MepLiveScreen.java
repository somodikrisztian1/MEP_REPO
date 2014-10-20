package hu.mep.mep_app;

import hu.alter.mep_app.R;
import hu.mep.datamodells.Session;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FragmentLevel1MepLiveScreen extends Fragment {

	private final static String MEP_LIVE_URL = "http://www.megujuloenergiapark.hu/mep-live-idojaras/?appMode=1";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_firstlevel_mep_live_screen,
				container, false);

		WebView myWebView = (WebView) v.findViewById(R.id.webviewMepLive);
		myWebView.loadUrl(MEP_LIVE_URL);

		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		myWebView.setWebViewClient(new WebViewClient() {
			 
			   public void onPageFinished(WebView view, String url) {
				   Session.dismissAndMakeNullProgressDialog();
			   } 
		});
		
		return v;
	};
	
}