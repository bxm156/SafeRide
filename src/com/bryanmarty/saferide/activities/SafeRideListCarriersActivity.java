package com.bryanmarty.saferide.activities;

import java.util.ArrayList;
import java.util.LinkedList;

import com.bryanmarty.saferide.R;
import com.bryanmarty.saferide.adapters.CarrierAdapter;
import com.bryanmarty.saferide.fmcsa.FMCSAScrapper;
import com.bryanmarty.saferide.fmcsa.PassengerCarrier;
import com.bryanmarty.saferide.fmcsa.FMCSAScrapper.VehicleType;
import com.bryanmarty.saferide.google.location.GoogleLocation;
import com.bryanmarty.saferide.interfaces.CarrierListItem;
import com.bryanmarty.saferide.managers.GPSManager;
import com.bryanmarty.saferide.saferbus.SaferBus;
import com.bryanmarty.saferide.saferbus.SaferBusResponse;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class SafeRideListCarriersActivity extends Activity implements OnItemClickListener {
	
	private CarrierAdapter cAdapter;
	private ArrayList<CarrierListItem> carrierList;
	private GPSManager gpsManager_ = GPSManager.getInstance(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.carrier_list);
        ListView listView = (ListView) findViewById(R.id.carrier_list_view);
        SaferBusResponse res = SaferBus.getCarrierByName("greyhound");
        carrierList = new ArrayList<CarrierListItem>(res.Carriers.Carrier);
        cAdapter = new CarrierAdapter(this, R.layout.carrier_list_item, carrierList);
        listView.setAdapter(cAdapter);
        listView.setOnItemClickListener(this);
        super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Location location = gpsManager_.getBestKnownLocation();
		populateFromLocation(location);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(carrierList != null && position < carrierList.size()) {
			
		}
	}
	
	public void populateFromLocation(Location location) {
		String zipcode = null;
		if(location != null) {
			zipcode = GoogleLocation.getZipcode(location);
		}
		if(zipcode == null) {
			return;
		}
		LinkedList<PassengerCarrier> carrierList = FMCSAScrapper.query(zipcode, VehicleType.Motorcoach, null);
		LinkedList<CarrierListItem> carrierListItems = new LinkedList<CarrierListItem>(carrierList);
		cAdapter.clear();
		cAdapter.setCarrierList(carrierListItems);
		/*
		LinkedList<Carrier> listItems = new LinkedList<Carrier>();
		
		for(PassengerCarrier carrier : carrierList) {
			try {
				SaferBusResponse response = SaferBus.getCarrierByName(carrier.getCleanedName());
				if(response == null) { 
					continue;
				}
				if(response.Carriers.Carrier.size() == 1) {
					listItems.add(response.Carriers.Carrier.get(0));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		cAdapter.clear();
		cAdapter.setCarrierList(listItems);
		*/
	}

	
}
