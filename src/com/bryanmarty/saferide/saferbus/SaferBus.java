package com.bryanmarty.saferide.saferbus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class SaferBus {
	
	private static final String WEB_KEY = "69b76ede9a9c46494a3e3f64248b82badfb5ee4f";
	private static final String FMCSA_URL = "https://mobile.fmcsa.dot.gov/saferbus/resource/v1";
	private static final int DEFAULT_SIZE = 25;
	
	private static Gson gson = new Gson();
	
	public static SaferBusResponse getCarrierByName(String carrierName) {
		return getCarrierByName(carrierName, DEFAULT_SIZE);
	}
	
	public static SaferBusResponse getCarrierByName(String carrierName, int size) {
		return getCarrierByName(carrierName, 0, size);
	}
	
	public static SaferBusResponse getCarrierByName(String carrierName, int start, int size) {
		String url = FMCSA_URL + "/carriers/" + carrierName + ".json?start=" + start + "&size=" + size + "&webKey=" + WEB_KEY;
		return performQuery(url);
	}
	
	private static SaferBusResponse performQuery(String connectionString) {
		HttpURLConnection connection = null;
		SaferBusResponse res = null;
		BufferedReader br = null;
		try {
			URL url = new URL(connectionString);
			connection = (HttpURLConnection) url.openConnection();
			InputStream is = new BufferedInputStream(connection.getInputStream());
			br = new BufferedReader(new InputStreamReader(is));
			res = (SaferBusResponse) gson.fromJson(br, SaferBusResponse.class);
			br.close();
		} catch (MalformedURLException mue) {
			
		} catch (IOException ioe) {
			
		} finally {
			if(connection != null) {
				connection.disconnect();
			}
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					
				}
			}
		}
		return res;
	}

}
