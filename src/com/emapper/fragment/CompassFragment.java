package com.emapper.fragment;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.emapper.activity.EApplication;
import com.emapper.activity.LocationWatched;
import com.emapper.activity.R;
import com.emapper.util.TransferUtils;
import com.emapper.view.CompassView;
import com.piespace.pienavi.navi.gps.NaviGPS;

public class CompassFragment extends Fragment implements Observer{

	EApplication application;
	private final float MAX_ROATE_DEGREE = 1.0f;
	private SensorManager mSensorManager;
	private Sensor mOrientationSensor;
	private float mDirection;
	private float mTargetDirection;
	private AccelerateInterpolator mInterpolator;
	protected final Handler mHandler = new Handler();
	private boolean mStopDrawing;
	CompassView mPointer;
	
	TextView tv_speed_value;
	TextView tv_dir_value;
	TextView tv_dis_value;
	TextView tv_alt_value;
	TextView tv_azimuth_value;
	TextView tv_yaw_value;
	
	private View view;
	static NaviGPS naviGPS;
	protected Runnable mCompassViewUpdater = new Runnable() {
		@Override
		public void run() {
			if (mPointer != null && !mStopDrawing) {
				if (mDirection != mTargetDirection) {

					// calculate the short routine
					float to = mTargetDirection;
					if (to - mDirection > 180) {
						to -= 360;
					} else if (to - mDirection < -180) {
						to += 360;
					}

					// limit the max speed to MAX_ROTATE_DEGREE
					float distance = to - mDirection;
					if (Math.abs(distance) > MAX_ROATE_DEGREE) {
						distance = distance > 0 ? MAX_ROATE_DEGREE
								: (-1.0f * MAX_ROATE_DEGREE);
					}

					// need to slow down if the distance is short
					mDirection = normalizeDegree(mDirection
							+ ((to - mDirection) * mInterpolator
									.getInterpolation(Math.abs(distance) > MAX_ROATE_DEGREE ? 0.4f
											: 0.3f)));
					mPointer.updateDirection(mDirection);
					//更新：
					updateValue(mDirection);
					
				}
				mHandler.postDelayed(mCompassViewUpdater, 20);
			}
		}
	};

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		application = EApplication.getInstance();
		//application.watched.addObserver(this);
		application.addGPSObserver(this);
		initResources();
		initServices();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_compass, container, false);
		setUpView();
		return view;
	}
	
	/**
	 * 更新信息
	 * @param dir
	 */
	private void updateValue(double dir){
		//罗盘方位角
		tv_dir_value.setText(TransferUtils.getFormat(dir, "0.0"));
	}
	
	private void updateLocation(BDLocation location){
		if(location!=null){
			tv_speed_value.setText(TransferUtils.getFormat(location.getSpeed(), "0.0"));
			tv_dis_value.setText("N/A");
			tv_alt_value.setText(TransferUtils.getFormat(location.getAltitude(), "0.0"));
			tv_azimuth_value.setText(TransferUtils.getFormat(location.getRadius(), "0.0"));
			tv_yaw_value.setText("N/A");
		}
	}
	private void updateLocation(){
		tv_speed_value.setText(TransferUtils.getFormat(this.naviGPS.dSpeed, "0.0"));
		tv_dis_value.setText("N/A");
		tv_alt_value.setText(TransferUtils.getFormat(this.naviGPS.dAltitude, "0.0"));
		tv_azimuth_value.setText(TransferUtils.getFormat(this.naviGPS.dBearing, "0.0"));
		tv_yaw_value.setText("N/A");
}
	private void setUpView() {
		TextView tv = (TextView) view.findViewById(R.id.tv_title);
		Button btn = (Button) view.findViewById(R.id.btn_back);
		btn.setVisibility(View.INVISIBLE);
		TextView tv_speed_title=(TextView)view.findViewById(R.id.compass_speed).findViewById(R.id.common_top);
		tv_speed_title.setText("航速：");
		TextView tv_speed_unit=(TextView)view.findViewById(R.id.compass_speed).findViewById(R.id.common_unit);
		tv_speed_unit.setText("km/s");
		tv_speed_value=(TextView)view.findViewById(R.id.compass_speed).findViewById(R.id.common_bottom);
		
		TextView tv_dir_title=(TextView)view.findViewById(R.id.compass_dir).findViewById(R.id.common_top);
		tv_dir_title.setText("航向：");
		TextView tv_dir_unit=(TextView)view.findViewById(R.id.compass_dir).findViewById(R.id.common_unit);
		tv_dir_unit.setText("度");
		tv_dir_value=(TextView)view.findViewById(R.id.compass_dir).findViewById(R.id.common_bottom);
		
		TextView tv_dis_title=(TextView)view.findViewById(R.id.compass_dis).findViewById(R.id.common_top);
		tv_dis_title.setText("距离：");
		TextView tv_dis_unit=(TextView)view.findViewById(R.id.compass_dis).findViewById(R.id.common_unit);
		tv_dis_unit.setText("km");
		tv_dis_value=(TextView)view.findViewById(R.id.compass_dis).findViewById(R.id.common_bottom);
		
		TextView tv_alt_title=(TextView)view.findViewById(R.id.compass_alt).findViewById(R.id.common_top);
		tv_alt_title.setText("高程：");
		TextView tv_alt_unit=(TextView)view.findViewById(R.id.compass_alt).findViewById(R.id.common_unit);
		tv_alt_unit.setText("m");
		tv_alt_value=(TextView)view.findViewById(R.id.compass_alt).findViewById(R.id.common_bottom);
		
		TextView tv_azimuth_title=(TextView)view.findViewById(R.id.compass_azimuth).findViewById(R.id.common_top);
		tv_azimuth_title.setText("方位角：");
		TextView tv_azimuth_unit=(TextView)view.findViewById(R.id.compass_azimuth).findViewById(R.id.common_unit);
		tv_azimuth_unit.setText("度");
		tv_azimuth_value=(TextView)view.findViewById(R.id.compass_azimuth).findViewById(R.id.common_bottom);
		
		TextView tv_yaw_title=(TextView)view.findViewById(R.id.compass_yaw).findViewById(R.id.common_top);
		tv_yaw_title.setText("偏航距：");
		TextView tv_yaw_unit=(TextView)view.findViewById(R.id.compass_yaw).findViewById(R.id.common_unit);
		tv_yaw_unit.setText("度");
		tv_yaw_value=(TextView)view.findViewById(R.id.compass_yaw).findViewById(R.id.common_bottom);

		
		tv.setText("罗盘");
	}

	private void initResources() {
		mDirection = 0.0f;
		mTargetDirection = 0.0f;
		mInterpolator = new AccelerateInterpolator();
		mStopDrawing = true;
		mPointer = (CompassView) view.findViewById(R.id.compass_pointer);
		mPointer.setImageResource(R.drawable.compass);
	}

	private void initServices() {
		mSensorManager = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);
		mOrientationSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}

	private SensorEventListener mOrientationSensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			float direction = event.values[0] * -1.0f;
			mTargetDirection = normalizeDegree(direction);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	private float normalizeDegree(float degree) {
		return (degree + 720) % 360;
	}

	@Override
	public void onResume() {
		super.onResume();
		  if (mOrientationSensor != null) {
	            mSensorManager.registerListener(mOrientationSensorEventListener, mOrientationSensor,
	                    SensorManager.SENSOR_DELAY_GAME);
	        }
	        mStopDrawing = false;
	        mHandler.postDelayed(mCompassViewUpdater, 20);
	}

	@Override
	public void onPause() {
		super.onPause();
		mStopDrawing = true;
        if (mOrientationSensor != null) {
            mSensorManager.unregisterListener(mOrientationSensorEventListener);
        }
	}
	
	

	@Override
	public void onStop() {
		super.onStop();
		mStopDrawing = true;
        if (mOrientationSensor != null) {
            mSensorManager.unregisterListener(mOrientationSensorEventListener);
        }
	}


	@Override
	public void update(Observable observable, Object data) {
//		if(observable instanceof LocationWatched) {
//			// 接受到GPS信息
//			BDLocation location=((LocationWatched)observable).getData();
//			updateLocation(location);
//		}
		if (observable instanceof NaviGPS) {
			this.naviGPS = (NaviGPS) observable;
			updateLocation();
		}
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		//application.watched.deleteObserver(this);
		application.deleteGPSObserver(this);
	}
	
	
}
