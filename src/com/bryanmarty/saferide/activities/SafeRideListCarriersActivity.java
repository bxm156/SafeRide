package com.bryanmarty.saferide.activities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bryanmarty.saferide.R;
import com.bryanmarty.saferide.adapters.CarrierAdapter;
import com.bryanmarty.saferide.fmcsa.FMCSAScrapper;
import com.bryanmarty.saferide.fmcsa.PassengerCarrier;
import com.bryanmarty.saferide.fmcsa.FMCSAScrapper.VehicleType;
import com.bryanmarty.saferide.google.location.GoogleLocation;
import com.bryanmarty.saferide.interfaces.AsyncTaskListener;
import com.bryanmarty.saferide.interfaces.CarrierListItem;
import com.bryanmarty.saferide.managers.DataManager;
import com.bryanmarty.saferide.managers.GPSManager;
import com.bryanmarty.saferide.saferbus.SaferBus;
import com.bryanmarty.saferide.saferbus.SaferBusResponse;
import com.bryanmarty.saferide.tasks.FMCSADownloadTask;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrier_list);
        ListView listView = (ListView) findViewById(R.id.carrier_list_view);
        carrierList = new ArrayList<CarrierListItem>();
        DataManager manager = DataManager.getInstance();
        if( manager.getData() != null) {
        	carrierList = new ArrayList<CarrierListItem>(manager.getData());
        } else {
        	populateFromLocation(gpsManager_.getBestKnownLocation());
        }
        cAdapter = new CarrierAdapter(this, R.layout.carrier_list_item, carrierList);
        listView.setAdapter(cAdapter);
        listView.setOnItemClickListener(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
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
			zipcode = "44106"; //GoogleLocation.getZipcode(location);
		}
		if(zipcode == null) {
			return;
		}
		
		
		AsyncTaskListener<String, List<PassengerCarrier>, List<PassengerCarrier>> listener = new AsyncTaskListener<String, List<PassengerCarrier>, List<PassengerCarrier>>() {

			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPreExecute() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProgressUpdate(final List<PassengerCarrier>... progress) {
				SafeRideListCarriersActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(progress != null) {
							LinkedList<CarrierListItem> carrierListItems = new LinkedList<CarrierListItem>(progress[0]);
							cAdapter.clear();
							cAdapter.setCarrierList(carrierListItems);
						}
					}
				});
				
			}

			@Override
			public void onPostExecute(List<PassengerCarrier> result) {
				if(result != null) {
					LinkedList<CarrierListItem> carrierListItems = new LinkedList<CarrierListItem>(result);
					cAdapter.clear();
					cAdapter.setCarrierList(carrierListItems);
				}
			}
			
		};
		
		FMCSADownloadTask zipDownload = new FMCSADownloadTask(listener);
		zipDownload.execute(zipcode, VehicleType.Motorcoach.name(), null);
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
