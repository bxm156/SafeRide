package com.bryanmarty.saferide.managers;

import android.location.Location;

public interface GPSListener {
	public void onNewLocation(Location location);
}
