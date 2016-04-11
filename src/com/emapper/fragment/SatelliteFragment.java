package com.emapper.fragment;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.emapper.activity.EApplication;
import com.emapper.activity.LocationWatched;
import com.emapper.activity.R;
import com.emapper.activity.SatelliteActivity;
import com.emapper.util.Constant;
import com.emapper.util.TransferUtils;
import com.emapper.view.EphemerisView;
import com.emapper.view.SatelliteView;
import com.emapper.view.EphemerisView.SatellitePt;
import com.emapper.view.SatelliteView.SatelliteSig;
import com.piespace.pienavi.navi.gps.NaviGPS;
import com.piespace.pienavi.navi.gps.NaviGPS.Satellite;

public class SatelliteFragment extends Fragment implements Observer {

	static SatelliteView cView;
	 static EphemerisView eView;
	EApplication application;
	static NaviGPS naviGPS;

	private static final int MSG_GPS_UPDATE = 0x01;

	private GPSHandler handler;
	private TextView tv_lon;
	private TextView tv_lat;
	private TextView tv_alt;
	// private TextView tv_pricious;
	// private TextView tv_status;

	private View view;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_satellite, container, false);
		setUpView();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		application = EApplication.getInstance();
		//application.watched.addObserver(this);

		application.addGPSObserver(this);
		this.naviGPS = application.naviGPS;
		handler = new GPSHandler(getActivity());
	}

	private void setUpView() {
		TextView tv = (TextView) view.findViewById(R.id.tv_title);
		Button btn = (Button) view.findViewById(R.id.btn_back);
		btn.setVisibility(View.INVISIBLE);
		cView = (SatelliteView) view.findViewById(R.id.sview);
		eView = (EphemerisView) view.findViewById(R.id.sgloble);
		cView.setSiGap(30);
		tv_lon = (TextView) view.findViewById(R.id.et_e);
		tv_lat = (TextView) view.findViewById(R.id.et_n);
		tv_alt = (TextView) view.findViewById(R.id.et_h);
		// tv_pricious = (TextView) view.findViewById(R.id.et_pr);
		// tv_status = (TextView) view.findViewById(R.id.et_state);

		tv.setText("卫星状态");

	}

	/**
	 * 获取到GPS状态后续刷新界面
	 */
	public void refreshView() {
		if (handler != null) {
			handler.sendEmptyMessage(MSG_GPS_UPDATE);
		}
	}

	/**
	 * handler处理刷新
	 * 
	 * @author lijun.xu
	 * 
	 */
	static class GPSHandler extends Handler {
		WeakReference<Activity> mActivityReference;

		GPSHandler(Activity activity) {
			mActivityReference = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			final Activity activity = mActivityReference.get();
			if (activity != null) {
				int what = msg.what;
				switch (what) {
				case MSG_GPS_UPDATE: { // GPS信息刷新星历图
					NaviGPS naviGPS=SatelliteFragment.naviGPS;
					int count = naviGPS.iSatellites;
					// 添加gps卫星信息
					Satellite[] satellites = new Satellite[count];
					for (int i = 0; i < count; i++) {
						satellites[i] = naviGPS.satellites.get(i);
					}
					SatelliteSig[] sigs = new SatelliteSig[count];
					SatelliteSig sig;
					SatellitePt satellitePt;
					SatellitePt[] satellitePts = new SatellitePt[count];
					for (int i = 0; i < count; i++) {
						sig = new SatelliteSig();
						sig.setmPrn(satellites[i].iSatelliteID);
						sig.setmSnr(satellites[i].fSignal);
						sigs[i] = sig;
						satellitePt=new SatellitePt();
						satellitePt.setmAzimuth(satellites[i].fAzimuth);
						satellitePt.setmElevation(satellites[i].fElevation);
						satellitePt.setmSnr(satellites[i].fSignal);
						satellitePts[i]=satellitePt;
					}
					if(SatelliteFragment.cView != null){
						SatelliteFragment.cView.refreshSatelliteSigs(sigs);
						
					}
					
					if(SatelliteFragment.eView!=null){
						SatelliteFragment.eView.refreshEphemerisView(satellitePts);
					}
				}
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (this.eView != null) {
			this.eView.destory();
		}
		if (cView != null) {
			this.cView.destory();
		}
		application.deleteGPSObserver(this);
		//application.watched.deleteObserver(this);
		this.naviGPS = null;
	}

	@Override
	public void update(Observable observable, Object data) {
		if (observable instanceof NaviGPS) {
			this.naviGPS = (NaviGPS) observable;
			refreshView();
			updateLocation(naviGPS);
		} else if (observable instanceof LocationWatched) {
			// 接受到GPS信息
			BDLocation location = ((LocationWatched) observable).getData();
			updateLocation(location);
		}
	}

	/**
	 * 更新位置
	 * 
	 * @param location
	 */
	private void updateLocation(BDLocation location) {
		tv_lon.setText("E:" + location.getLongitude() + "°");
		tv_lat.setText("N:" + location.getLatitude() + "°");
		tv_alt.setText("H:" + location.getAltitude());
		// tv_pricious.setText("精度：" + (int) location.getRadius() + "米");
		// tv_status.setText("状态：3D");
	}

	private void updateLocation(NaviGPS naviGPS) {
		//tv_lon.setText("E:" +new BigDecimal(naviGPS.dLongitude).setScale(2, BigDecimal.ROUND_HALF_UP)  + "°");
		tv_lon.setText("E:" +TransferUtils.positionParser(Constant.UNIT_SET, naviGPS.dLongitude) );
		tv_lat.setText("N:" +TransferUtils.positionParser(Constant.UNIT_SET, naviGPS.dLatitude) );
		tv_alt.setText("H:" +new BigDecimal( naviGPS.dAltitude).setScale(2, BigDecimal.ROUND_HALF_UP) );
		// tv_pricious.setText("精度："+(int)location.getRadius()+"米");
		// tv_status.setText("状态：3D");
	}
}
