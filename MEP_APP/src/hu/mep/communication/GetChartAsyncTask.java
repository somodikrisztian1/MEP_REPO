package hu.mep.communication;

import hu.mep.datamodells.Session;
import android.content.Context;
import android.os.AsyncTask;

public class GetChartAsyncTask extends AsyncTask<Void, Void, Void> {
	
	private Context context;
	private static String hostURI;
	private static String resourceURI;
	private static String fullURI;
	
	public GetChartAsyncTask(Context context, String catchedHostURI) {
		this.context = context;
		hostURI = catchedHostURI;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		resourceURI = "http://www.megujuloenergiapark.hu/ios_getChart.php?id=" +
				Session.getActualChartInfoContainer().getId();
		fullURI = hostURI + resourceURI;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
