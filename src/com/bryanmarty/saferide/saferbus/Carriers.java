package com.bryanmarty.saferide.saferbus;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Carriers {
	@SerializedName("Carrier")
	public List<Carrier> Carrier = new ArrayList<Carrier>();
	
	@SerializedName("@size")
	public String size;
}
