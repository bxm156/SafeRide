package com.bryanmarty.saferide.adapters;

import java.util.ArrayList;
import com.bryanmarty.saferide.saferbus.Carrier;

import com.bryanmarty.saferide.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CarrierAdapter extends ArrayAdapter<Carrier> {
	
	private ArrayList<Carrier> items_;
	private Context context_;

	public CarrierAdapter(Context context, int textViewResourceId, ArrayList<Carrier> items) {
		super(context, textViewResourceId, items);
		items_ = items;
		context_ = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null) {
			LayoutInflater vi = (LayoutInflater) context_.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.carrier_list_item, null);
		}
		Carrier c = items_.get(position);
		if (c != null ) {
			TextView tv = (TextView) v.findViewById(R.id.carrier_list_carrier_name);
			if(tv != null) {
				tv.setText(c.legalName);
			}
		}
		return v;
	}
}
