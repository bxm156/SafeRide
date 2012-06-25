package com.bryanmarty.saferide.adapters;

import java.util.ArrayList;
import java.util.List;

import com.bryanmarty.saferide.interfaces.CarrierListItem;

import com.bryanmarty.saferide.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CarrierAdapter extends ArrayAdapter<CarrierListItem> {
	
	private ArrayList<CarrierListItem> items_;
	private Context context_;

	public CarrierAdapter(Context context, int textViewResourceId, ArrayList<CarrierListItem> items) {
		super(context, textViewResourceId, items);
		items_ = items;
		context_ = context;
	}
	
	public void setCarrierList(List<CarrierListItem> items) {
		items_.clear();
		items_.addAll(items);
		setNotifyOnChange(true);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null) {
			LayoutInflater vi = (LayoutInflater) context_.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.carrier_list_item, null);
		}
		CarrierListItem c = items_.get(position);
		if (c != null ) {
			TextView tv = (TextView) v.findViewById(R.id.carrier_list_carrier_name);
			if(tv != null) {
				tv.setText(c.getName());
			}
		}
		return v;
	}
}
