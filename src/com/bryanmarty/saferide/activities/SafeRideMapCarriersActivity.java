package com.bryanmarty.saferide.activities;

import com.bryanmarty.saferide.MapOverlay;
import com.bryanmarty.saferide.R;
import com.bryanmarty.saferide.google.location.GoogleLocation;
import com.bryanmarty.saferide.managers.GPSListener;
import com.bryanmarty.saferide.managers.GPSManager;
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
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class SafeRideMapCarriersActivity extends MapActivity implements GPSListener {

	private MapController mapController_;
	private MapView mapView_;
	private GPSManager gpsManager_ = GPSManager.getInstance(this);;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		mapView_ = (MapView) findViewById(R.id.carrierMap);
		mapController_ = mapView_.getController();
		mapController_.setZoom(9);
		mapView_.setSatellite(true);
		mapView_.setBuiltInZoomControls(true);
		gpsManager_.addListener(this);
		
		Location loc = gpsManager_.getBestKnownLocation();
		if(loc != null) {
			setLocation(loc);
			mapView_.getOverlays().add(new MapOverlay(R.drawable.kingpin, GPSManager.convertToGeoPoint(loc), this));
		} else {
			gpsManager_.startListening();
		}
	}

	@Override
	protected void onDestroy() {
		gpsManager_.removeListener(this);
		super.onDestroy();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public void setLocation(Location loc) {
		mapView_.getOverlays().add(new MapOverlay(R.drawable.kingpin, GPSManager.convertToGeoPoint(loc), this));
		mapController_.animateTo(GPSManager.convertToGeoPoint(loc));
		mapController_.setZoom(19);
	}

	@Override
	public void onNewLocation(Location location) {
		if(location != null) {
			setLocation(location);	
		}
	}

	@Override
	protected void onPause() {
		gpsManager_.stopListening();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		gpsManager_.startListening();
		super.onResume();
	}
	
	
	
	

}
