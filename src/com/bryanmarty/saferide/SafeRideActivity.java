package com.bryanmarty.saferide;

import java.util.List;

import com.bryanmarty.saferide.R;
import com.bryanmarty.saferide.R.layout;
import com.bryanmarty.saferide.activities.SafeRideHomeScreenActivity;
import com.bryanmarty.saferide.fmcsa.PassengerCarrier;
import com.bryanmarty.saferide.fmcsa.FMCSAScrapper;
import com.bryanmarty.saferide.fmcsa.FMCSAScrapper.VehicleType;
import com.bryanmarty.saferide.interfaces.AsyncTaskListener;
import com.bryanmarty.saferide.managers.DataManager;
import com.bryanmarty.saferide.managers.GPSManager;
import com.bryanmarty.saferide.tasks.StartupTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SafeRideActivity extends Activity implements AsyncTaskListener<String, Integer, List<PassengerCarrier>> {
    /** Called when the activity is first created. */
	
	private TextView statusText_;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        statusText_ = (TextView) findViewById(R.id.loadingStatus);
    }
	@Override
	protected void onStart() {
		super.onStart();
		GPSManager gps = GPSManager.getInstance(this);
		gps.startListening();
        StartupTask task = new StartupTask(this,this);
        task.execute();
	}

	@Override
	public void onCancelled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressUpdate(final Integer... progress) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				switch(progress[0]) {
				case StartupTask.GPS_LOCATION:
					statusText_.setText("Determining Location...");
					break;
				case StartupTask.SCRAPE_ZIP_LIST:
					statusText_.setText("Scraping Data...");
					break;
				default:
				}
			}
		});
		
	}

	@Override
	public void onPostExecute(List<PassengerCarrier> result) {
		if(result != null) {
			DataManager.getInstance().setData(result);
		}
		Intent i = new Intent(this, SafeRideHomeScreenActivity.class);
		startActivity(i);
		finish();
	}
    
}