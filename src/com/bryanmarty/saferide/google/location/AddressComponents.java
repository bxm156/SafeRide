package com.bryanmarty.saferide.google.location;

import java.util.List;

public class AddressComponents {
	private List<String> types;
	private String long_name;
	private String short_name;
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public String getLong_name() {
		return long_name;
	}
	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
}