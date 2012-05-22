package com.bryanmarty.saferide.activities;

import java.util.ArrayList;

import com.bryanmarty.saferide.R;
import com.bryanmarty.saferide.adapters.CarrierAdapter;
import com.bryanmarty.saferide.saferbus.Carrier;
import com.bryanmarty.saferide.saferbus.SaferBus;
import com.bryanmarty.saferide.saferbus.SaferBusResponse;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SafeRideListCarriersActivity extends Activity {
	
	private CarrierAdapter cAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.carrier_list);
        ListView listView = (ListView) findViewById(R.id.carrier_list_view);
        SaferBusResponse res = SaferBus.getCarrierByName("greyhound");
        ArrayList<Carrier> carrierList = new ArrayList<Carrier>(res.Carriers.Carrier);
        cAdapter = new CarrierAdapter(this, R.layout.carrier_list_item, carrierList);
        listView.setAdapter(cAdapter);
        super.onCreate(savedInstanceState);
	}

	
}
