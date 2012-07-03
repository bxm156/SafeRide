package com.bryanmarty.saferide.managers;

import java.util.Collections;
import java.util.List;

import com.bryanmarty.saferide.fmcsa.PassengerCarrier;

public class DataManager {

	
	private static DataManager instance_;
	
	private List<PassengerCarrier> data_;
	
	public DataManager() {
		
	}
	
	public static DataManager getInstance() {
		if(instance_ == null) {
			instance_ = new DataManager();
		}
		return instance_;
	}
	
	public void setData(List<PassengerCarrier> data) {
		data_ = Collections.synchronizedList(data);
	}
	
	public List<PassengerCarrier> getData() {
		return data_;
	}
	
}
