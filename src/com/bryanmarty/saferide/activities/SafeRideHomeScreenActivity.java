package com.bryanmarty.saferide.activities;

import com.bryanmarty.saferide.R;
import com.bryanmarty.saferide.R.drawable;
import com.bryanmarty.saferide.R.layout;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class SafeRideHomeScreenActivity extends TabActivity {

	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.homescreen);
	        
	        Resources res = getResources();
	        TabHost tabHost = getTabHost();
	        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	        Intent intent;  // Reusable Intent for each tab
	        
	        // Create an Intent to launch an Activity for the tab (to be reused)
	        intent = new Intent().setClass(this, SafeRideListCarriersActivity.class);
	        
	        // Initialize a TabSpec for each tab and add it to the TabHost
	        spec = tabHost.newTabSpec("list").setIndicator("Lists",
	                          res.getDrawable(R.drawable.ic_tab_list_carriers))
	                      .setContent(intent);
	        tabHost.addTab(spec);
	        
	        // Create an Intent to launch an Activity for the tab (to be reused)
	        intent = new Intent().setClass(this, SafeRideSearchCarriersActivity.class);
	        
	        // Initialize a TabSpec for each tab and add it to the TabHost
	        spec = tabHost.newTabSpec("search").setIndicator("Search",
	                          res.getDrawable(R.drawable.ic_tab_search_carriers))
	                      .setContent(intent);
	        tabHost.addTab(spec);
	        
	        // Create an Intent to launch an Activity for the tab (to be reused)
	        intent = new Intent().setClass(this, SafeRideMapCarriersActivity.class);
	        
	        // Initialize a TabSpec for each tab and add it to the TabHost
	        spec = tabHost.newTabSpec("map").setIndicator("Map",
	                          res.getDrawable(R.drawable.ic_tab_map_carriers))
	                      .setContent(intent);
	        tabHost.addTab(spec);
	        
	        
	        
	        tabHost.setCurrentTab(0);
	        
	    }	 
	 
}
