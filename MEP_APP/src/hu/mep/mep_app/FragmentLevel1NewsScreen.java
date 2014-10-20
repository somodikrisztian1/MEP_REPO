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

public class FragmentLevel1NewsScreen extends Fragment {

	private final static String NEWS_URL = "http://www.megujuloenergiapark.hu/hirek/?appMode=1";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_firstlevel_news_screen,
				container, false);

		WebView myWebView = (WebView) v.findViewById(R.id.webviewNews);
		myWebView.loadUrl(NEWS_URL);

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
