package com.bryanmarty.saferide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapOverlay extends Overlay {
	
	private int rDrawable_;
	private GeoPoint point_;
	private Context context_;
	private Bitmap bmp_;
    Point screenPts = new Point();
	
	public MapOverlay(int rDrawable, GeoPoint point, Context context) {
		rDrawable_ = rDrawable;
		point_ = point;
		context_ = context;
		bmp_ = BitmapFactory.decodeResource(context_.getResources(), rDrawable_);            
	}

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
		
		super.draw(canvas, mapView, shadow, when);
		 
		//---translate the GeoPoint to screen pixels---

        mapView.getProjection().toPixels(point_, screenPts);

        //---add the marker---
        
        canvas.drawBitmap(bmp_, screenPts.x-(bmp_.getWidth()/2), screenPts.y-bmp_.getHeight(), null);         
        return true;
    }
	
	

}
