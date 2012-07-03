package com.bryanmarty.saferide.tasks;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;

import com.bryanmarty.saferide.fmcsa.FMCSAScrapper;
import com.bryanmarty.saferide.fmcsa.PassengerCarrier;
import com.bryanmarty.saferide.fmcsa.FMCSAScrapper.VehicleType;
import com.bryanmarty.saferide.interfaces.AsyncTaskListener;
import com.bryanmarty.saferide.saferbus.SaferBus;
import com.bryanmarty.saferide.saferbus.SaferBusResponse;

import android.os.AsyncTask;
import android.text.TextUtils;

public class FMCSADownloadTask extends AsyncTask<String, List<PassengerCarrier>, List<PassengerCarrier>> {

	private AsyncTaskListener<String, List<PassengerCarrier>, List<PassengerCarrier>> listener_;
	
	public FMCSADownloadTask(AsyncTaskListener<String, List<PassengerCarrier>, List<PassengerCarrier>> listener) {
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
		
		onProgressUpdate(scrappedList);
		
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
			if(TextUtils.isEmpty(pc.getDOT())) {
				continue;
			}
			SaferBusResponse res = SaferBus.getCarrierByDOT(pc.getDOT());
		
		}
		
		return scrappedList;
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
	protected void onProgressUpdate(List<PassengerCarrier>... values) {
		listener_.onProgressUpdate(values[0]);
	}
	
	

}
