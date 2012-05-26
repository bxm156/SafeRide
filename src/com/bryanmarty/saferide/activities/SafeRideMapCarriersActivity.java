package com.bryanmarty.saferide.activities;

import com.bryanmarty.saferide.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class SafeRideMapCarriersActivity extends MapActivity {

	private MapController mapController_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.map);
		
		MapView mapView = (MapView) findViewById(R.id.carrierMap);
		mapController_ = mapView.getController();
		mapController_.setZoom(9);
		
		mapView.setBuiltInZoomControls(true);
		
		
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				setLocation(location);
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
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public void setLocation(Location loc) {
		int lat = (int) (loc.getLatitude() * 1E6);
		int log = (int) (loc.getLongitude() * 1E6);
		mapController_.animateTo(new GeoPoint(lat,log));		
	}

}
