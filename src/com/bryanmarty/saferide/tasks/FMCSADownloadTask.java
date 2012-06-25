package com.bryanmarty.saferide.tasks;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.bryanmarty.saferide.fmcsa.FMCSAScrapper;
import com.bryanmarty.saferide.fmcsa.PassengerCarrier;
import com.bryanmarty.saferide.fmcsa.FMCSAScrapper.VehicleType;
import com.bryanmarty.saferide.interfaces.AsyncTaskListener;
import com.bryanmarty.saferide.saferbus.SaferBus;
import com.bryanmarty.saferide.saferbus.SaferBusResponse;

import android.os.AsyncTask;

public class FMCSADownloadTask extends AsyncTask<String, Integer, List<PassengerCarrier>> {

	private AsyncTaskListener<String, Integer, List<PassengerCarrier>> listener_;
	
	public FMCSADownloadTask(AsyncTaskListener<String, Integer, List<PassengerCarrier>> listener) {
		listener_ = listener;
	}
	
	@Override
	protected List<PassengerCarrier> doInBackground(String... params) {
		String zipcode = params[0];
		VehicleType vehicleType = VehicleType.valueOf(params[1]);
		String keyword = params[2];
		LinkedList<PassengerCarrier> scrappedList = FMCSAScrapper.query(zipcode, vehicleType, keyword);
		
		if(isCancelled()) {
			return scrappedList;
		}
		
		ListIterator<PassengerCarrier> litr = scrappedList.listIterator();
		while(litr.hasNext()) {
			PassengerCarrier pc = litr.next();
			PassengerCarrier populatedCarrier = FMCSAScrapper.getDetails(pc);
			if(populatedCarrier != null) {
				litr.set(populatedCarrier);
			}
			if(isCancelled()) {
				break;
			}
		}
		
		if(isCancelled()) {
			return scrappedList;
		}
		
		litr = scrappedList.listIterator();
		while(litr.hasNext()) {
			if(isCancelled()) {
				break;
			}
			PassengerCarrier pc = litr.next();
			if(pc.getDOT() == null || pc.getDOT().isEmpty()) {
				continue;
			}
			SaferBusResponse res = SaferBus.getCarrierByDOT(pc.getDOT());
			
		}
		
		
		return null;
	}

	@Override
	protected void onCancelled() {
		listener_.onCancelled();
	}

	@Override
	protected void onPostExecute(List<PassengerCarrier> result) {
		listener_.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		listener_.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		listener_.onProgressUpdate(values);
	}
	
	

}
