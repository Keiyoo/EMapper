package com.piespace.pienavi.navi.gps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.emapper.activity.EApplication;
import com.emapper.util.Utils;

public class NaviGPS extends Observable {
	public static final int GPS_LOCATION_CHANGE = 198505;
	/** 纬度 **/
	public double dLatitude;
	/** 经度 **/
	public double dLongitude;
	/** 方位角，表示行进的方向，单位是度。正北为0，顺时针方向，值域为0-360 **/
	public double dBearing;
	/** 行进速度，单位是米/秒 **/
	public double dSpeed;
	/** 高程，用高于平均海平面即海拔表示。单位是米 **/
	public double dAltitude;

	public long lTime;
	/** 卫星数目 **/
	public int iSatellites;
	/** 精度 **/
	public float dAccuracy;

	

	private LocationManager locManager = null;
	private GPSListener gpsListener = null;
	private EApplication application;
	public ArrayList<Satellite> satellites = null;
	/** 卫星 **/
	public static class Satellite {
		public int iSatelliteID;
		public float fElevation;
		public float fAzimuth;
		public float fSignal;
		public boolean bFix;
	}

	public NaviGPS(Context context) {
		application = EApplication.getInstance();
		locManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		satellites = new ArrayList<Satellite>();
		for (int i = 0; i < 14; i++) {
			satellites.add(new Satellite());
		}
	}

	/**
	 * GPS 是否可用
	 */
	public boolean isGPSEnable() {
		return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/** 开启 GPS 定位 **/
	public boolean openGPSLocation() {
		if (gpsListener == null) {
			gpsListener = new GPSListener();
		}
		boolean isAdded = locManager.addGpsStatusListener(gpsListener);
		if (isAdded) {
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					1000L, 0f, gpsListener);
			return true;
		}
		return false;
	}

	/** 关闭 GPS 定位 **/
	public void closeGPSLocation() {
		if (locManager != null) {
			locManager.removeGpsStatusListener(gpsListener);
			locManager.removeUpdates(gpsListener);
		}
		gpsListener = null;
		deleteObservers();
	}

	private void setLocationValue(final Location location) {
		dLatitude = location.getLatitude();
		dLongitude = location.getLongitude();
		dBearing = location.getBearing();
		dSpeed = location.getSpeed();
		dAltitude = location.getAltitude();
		lTime = location.getTime();
		dAccuracy = location.getAccuracy();

	}

	// 更新卫星相关信息
	private void updateSatelliteInfo() {
		int count = 0;
		Iterable<GpsSatellite> gpsStatellites = locManager.getGpsStatus(null)
				.getSatellites();
		Iterator<GpsSatellite> iterator = gpsStatellites.iterator();
		GpsSatellite gpsSate = null;
		Satellite satellite = null;
		while (iterator.hasNext() && count < 14) {
			gpsSate = iterator.next();
			satellite = satellites.get(count++);
			// 获取GPS卫星信息
			satellite.bFix = gpsSate.usedInFix();
			satellite.iSatelliteID = gpsSate.getPrn();
			satellite.fAzimuth = gpsSate.getAzimuth();
			satellite.fElevation = gpsSate.getElevation();
			satellite.fSignal = gpsSate.getSnr();
		}
		iSatellites = count;
	}

	public void clear() {
		if (this.satellites != null) {
			this.satellites.clear();
			this.satellites = null;
		}
	}

	// 位置监听，GPS 状态监听
	private class GPSListener implements LocationListener, GpsStatus.Listener {

		@Override
		public void onGpsStatusChanged(int event) {
			updateSatelliteInfo();

			/** 添加GPS状态监听 lijun.xu */
			setChanged();
			notifyObservers();
		}

		@Override
		public void onLocationChanged(Location location) {
			if (Utils.isGpsDataValid(location.getLongitude(),
					location.getLatitude())) {
				setLocationValue(location);
				application.setLocationValue(location);
				setChanged();
				notifyObservers(location);
			}
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}
	}

}