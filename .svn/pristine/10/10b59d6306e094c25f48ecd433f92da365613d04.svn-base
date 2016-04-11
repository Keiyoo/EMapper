package com.emapper.view;


import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.piespace.mapping.MapView;
import com.piespace.mapping.Observer;

public class LocView extends ImageView implements Observer {
	
	public LocView(Context context) {
		super(context);
	}

	public MapView.LayoutParams getMapViewLayoutParams() {
		LayoutParams params = getLayoutParams();
		if (params instanceof MapView.LayoutParams) {
			return (MapView.LayoutParams) params;
		}
		return null;
	}
	
	@Override
	public void update() {
		requestLayout();
	}
}
