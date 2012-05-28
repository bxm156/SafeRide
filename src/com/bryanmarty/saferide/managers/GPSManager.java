package com.bryanmarty.saferide.managers;

import java.util.LinkedList;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GPSManager {


	private static GPSManager instance_;
	private static Context context_;
	private static LocationManager locationManager_;
	
	private static final int minTimeBetweenGPSUpdates =  1000;
	private static final int minDistanceBetweenGPSUpdates = 10;
	private static final int minTimeBetweenWiFiUpdates = 1000;
	private static final int minDistanceBetweenWifiUpdates = 10;
	
	private static final int timeBetweenBetterLocations = 2 * 60 * 1000;
	private static final int AUTO_STOP_LISTENING_METERS = 150;
	
	private boolean listening_ = false;
	
	private LocationListener gpsListener_;
	private LocationListener wifiListener_;
	
	private Location bestKnownLocation = null;
	
	private LinkedList<GPSListener> listeners_ = new LinkedList<GPSListener>();
	
	public static GPSManager getInstance(Context context) {
		if(instance_ == null ) {
			instance_ = new GPSManager(context);
		}
		return instance_;
	}
	
	public GPSManager(Context context) {
		context_ = context;
		locationManager_ = (LocationManager) context_.getSystemService(Context.LOCATION_SERVICE);
		
		Location lastKnownLocationGPS = locationManager_.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		Location lastKnownLocationWifi = locationManager_.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		if(lastKnownLocationGPS != null) {
			if(lastKnownLocationWifi != null) {
				if(lastKnownLocationGPS.getTime() >= lastKnownLocationWifi.getTime()) {
					bestKnownLocation = lastKnownLocationGPS;
				} else {
					bestKnownLocation = lastKnownLocationWifi;
				}
			} else {
				bestKnownLocation = lastKnownLocationGPS;
			}
		} else if(lastKnownLocationWifi != null) {
			bestKnownLocation = lastKnownLocationWifi;
		}
		
		
		
		gpsListener_ = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				onLocationReceived(location);
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
		};
		
		wifiListener_ = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				onLocationReceived(location);
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	public void starListening() {
		locationManager_.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeBetweenGPSUpdates, minDistanceBetweenGPSUpdates, gpsListener_);
		locationManager_.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTimeBetweenWiFiUpdates, minDistanceBetweenWifiUpdates, wifiListener_);
		listening_ = true;
		Log.i("Location","Started Listening");
	}
	
	public void stopListening() {
		locationManager_.removeUpdates(gpsListener_);
		locationManager_.removeUpdates(wifiListener_);
		listening_ = false;
		Log.i("Location","Stopped Listening");
	}
	
	public void addListener(GPSListener listener) {
		listeners_.add(listener);
		listener.onNewLocation(bestKnownLocation);
	}
	
	public void removeListener(GPSListener listener) {
		listeners_.remove(listener);
	}
	
	public boolean isListening() {
		return listening_;
	}
	
	private void onLocationReceived(Location location) {
		if(isBetterLocation(location)) {
			bestKnownLocation = location;
			Log.i("Location", "Best: " + location.toString());
			Log.i("Location accuracy:",String.valueOf(location.getAccuracy()));
			if(location.getAccuracy() <= AUTO_STOP_LISTENING_METERS) {
				stopListening();
			}
			for(GPSListener listener : listeners_) {
				listener.onNewLocation(bestKnownLocation);
			}
		}
	}
	
	private boolean isBetterLocation(Location newLocation) {
		if(bestKnownLocation == null) {
			return true;
		}
		
		long timeDelta = newLocation.getTime() - bestKnownLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > timeBetweenBetterLocations;
		boolean isSignificantlyOlder = timeDelta < timeBetweenBetterLocations;
		boolean isNewer = timeDelta > 0;
		
		if(isSignificantlyNewer) {
			return true;
		} else if(isSignificantlyOlder) {
			return false;
		}
		
		int accuracyDelta = (int) (newLocation.getAccuracy() - bestKnownLocation.getAccuracy());
	    boolean isLessAccurate = accuracyDelta > 0;
	    boolean isMoreAccurate = accuracyDelta < 0;
	    boolean isSignificantlyLessAccurate = accuracyDelta > 200;
	    
	    boolean isFromSameProvider = isSameProvider(newLocation.getProvider(), bestKnownLocation.getProvider());
	    
	    if (isMoreAccurate) {
	        return true;
	    } else if (isNewer && !isLessAccurate) {
	        return true;
	    } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
	        return true;
	    }
	    return false;	
	}
	
	private boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
	
	public Location getBestKnownLocation() {
		return bestKnownLocation;
	}
	
	public static GeoPoint convertToGeoPoint(Location loc) {
		int lat = (int) (loc.getLatitude() * 1E6);
		int log = (int) (loc.getLongitude() * 1E6);
		return new GeoPoint(lat,log);
	}
	
	
}
