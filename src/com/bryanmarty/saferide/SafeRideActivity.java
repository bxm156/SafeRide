package com.bryanmarty.saferide;

import com.bryanmarty.saferide.R;
import com.bryanmarty.saferide.R.layout;
import com.bryanmarty.saferide.activities.SafeRideHomeScreenActivity;
import com.bryanmarty.saferide.fmcsa.Carrier;
import com.bryanmarty.saferide.fmcsa.PassengerCarrier;
import com.bryanmarty.saferide.fmcsa.PassengerCarrier.VehicleType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SafeRideActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	@Override
	protected void onStart() {
		super.onStart();
		PassengerCarrier.query("44691", VehicleType.Motorcoach, "");
		Intent i = new Intent(this, SafeRideHomeScreenActivity.class);
		startActivity(i);
	}
    
}