package com.bryanmarty.saferide.tasks;

import java.util.LinkedList;
import java.util.List;

import com.bryanmarty.saferide.fmcsa.FMCSAScrapper;
import com.bryanmarty.saferide.fmcsa.PassengerCarrier;
import com.bryanmarty.saferide.fmcsa.FMCSAScrapper.VehicleType;
import com.bryanmarty.saferide.google.location.GoogleLocation;
import com.bryanmarty.saferide.interfaces.AsyncTaskListener;
import com.bryanmarty.saferide.managers.GPSListener;
import com.bryanmarty.saferide.managers.GPSManager;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;

public class StartupTask extends AsyncTask<String, Integer, List<PassengerCarrier>> implements GPSListener {
	
	private static final int minimumTimeSinceGPSLocation = 5000;

	private AsyncTaskListener<String, Integer, List<PassengerCarrier>> listener_;
	private Context context_;
	private Object gpsSync_ = new Object();
	
	public static final int GPS_LOCATION = 1;
	public static final int SCRAPE_ZIP_LIST = 2;
	
	public StartupTask(Context context_, AsyncTaskListener<String, Integer, List<PassengerCarrier>> listener) {
		listener_ = listener;
	}

	@Override
	protected List<PassengerCarrier> doInBackground(String... arg0) {
		onProgressUpdate(GPS_LOCATION);
		GPSManager gps = GPSManager.getInstance(context_);
		gps.addListener(this);
		Location bestKnown = gps.getBestKnownLocation();
		if(bestKnown.getTime() < System.currentTimeMillis() - minimumTimeSinceGPSLocation) {
			synchronized (gpsSync_) {
				try {
					gpsSync_.wait(10*1000);
					bestKnown = gps.getBestKnownLocation();
				} catch (InterruptedException e) {
					Thread.interrupted();
					return null;
				}
			}
		}
		if(bestKnown == null) {
			return null;
		}
		
		onProgressUpdate(SCRAPE_ZIP_LIST);
		String zipCode = GoogleLocation.getZipcode(bestKnown);
		if(TextUtils.isEmpty(zipCode)) {
			return null;
		}
		LinkedList<PassengerCarrier> scrappedList = FMCSAScrapper.query(zipCode, VehicleType.Motorcoach, null);
		return scrappedList;
	}

	@Override
	protected void onCancelled() {
		listener_.onCancelled();
	}

	@Override
	protected void onPostExecute(List<PassengerCarrier> result) {
		listener_.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		listener_.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		listener_.onProgressUpdate(values);
	}

	@Override
	public void onNewLocation(Location location) {
		synchronized(gpsSync_) {
			gpsSync_.notifyAll();
		}
	}
	
	

}
