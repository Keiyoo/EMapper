package com.emapper.activity;

import java.util.Observable;

import com.baidu.location.BDLocation;

public class LocationWatched extends Observable {
	private BDLocation loc = new BDLocation();

	public BDLocation getData() {
		return loc;
	}

	public void setData(BDLocation data) {

		if (!this.loc.equals(data)) {
			this.loc = data;
			setChanged();
		}
		notifyObservers();
	}
}
