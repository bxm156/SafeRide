package com.bryanmarty.saferide.google.location;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.bryanmarty.saferide.saferbus.SaferBusResponse;
import com.google.gson.Gson;

import android.location.Location;

public class GoogleLocation {

	public static String getZipcode(Location location) {
		
		HttpURLConnection connection = null;
		SaferBusResponse res = null;
		BufferedReader br = null;
		
		try {
			URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + location.getLatitude() +"," + location.getLongitude() +"&sensor=true");
			connection = (HttpURLConnection) url.openConnection();   
			 
			InputStream is = new BufferedInputStream(connection.getInputStream());
			br = new BufferedReader(new InputStreamReader(is));
			
			Gson gson = new Gson();
		    GoogleGeocode objs = gson.fromJson(br, GoogleGeocode.class);
		    
		    if(objs.getStatus().equals("OK")) {
	        	List<Results> results = objs.getResults();
	        	Results result = results.get(0);
	        	List<AddressComponents> components = result.getAddress_components();
	        	for(AddressComponents c : components) {
	        		if(c.getTypes().contains("postal_code")) {
	        			return c.getLong_name();
	        		}
	        	}
		    }
		} catch (Exception e) {
		}
		return null;
	}
}
