package com.emapper.fragment;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.Bundle;
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
import com.emapper.util.CalculateUtils;
import com.emapper.util.TransferUtils;
import com.piespace.pienavi.navi.gps.NaviGPS;

public class TripCalculatorFragment extends Fragment implements Observer {
	private Button btn_back;
	private View view;
	EApplication application;
	static NaviGPS naviGPS;

	private TextView tv_Trip_dis;// 累计行程
	private TextView tv_Trip_maxSpeed;// 最大速度
	private TextView tv_Trip_moveTime;// 移动世界
	private TextView tv_Trip_aveSpeed;// 平均移动速度
	private TextView tv_Trip_endTime;// 结束世界
	private TextView tv_Trip_totalSpeed;//
	private TextView tv_Trip_Height;
	private TextView tv_Trip_naviDis;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		application = EApplication.getInstance();
		//application.watched.addObserver(this);

		application.addGPSObserver(this);
		this.naviGPS = application.naviGPS;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_trip_calculator, container,
				false);
		initView();
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void initView() {
		TextView text_title = (TextView) view.findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_trip_calculator));
		btn_back = (Button) view.findViewById(R.id.btn_back);
		btn_back.setVisibility(View.INVISIBLE);

		tv_Trip_dis = (TextView) view.findViewById(R.id.text_tripcalculator);
		tv_Trip_maxSpeed = (TextView) view.findViewById(R.id.text_maxspeed);
		tv_Trip_moveTime = (TextView) view.findViewById(R.id.text_movetime);
		tv_Trip_aveSpeed = (TextView) view.findViewById(R.id.text_avespeed);
		tv_Trip_endTime = (TextView) view.findViewById(R.id.text_end_time);
		tv_Trip_totalSpeed = (TextView) view.findViewById(R.id.text_allspeed);
		tv_Trip_Height = (TextView) view.findViewById(R.id.text_heigth);
		tv_Trip_naviDis = (TextView) view.findViewById(R.id.text_navi_dis);
	}

	@Override
	public void update(Observable observable, Object o) {
		if (observable instanceof NaviGPS) {
			this.naviGPS = (NaviGPS) observable;
			if (EApplication.isOpen) {
				updateLocation();
			}
		} else if (observable instanceof LocationWatched) {
			// 接受到GPS信息
			BDLocation location = ((LocationWatched) observable).getData();
			if (EApplication.isOpen) {
				updateLocation(location);
			}
		}
	}

	/**
	 * 更新位置 a
	 * 
	 * @param location
	 */
	private void updateLocation(BDLocation location) {
		tv_Trip_dis
				.setText(TransferUtils.getFormat(
						CalculateUtils.getDistance(application.getWorkspace()),
						"0.00"));
		tv_Trip_maxSpeed.setText(TransferUtils.getFormat(
				application.getMaxspeed(), "0.00"));
		tv_Trip_moveTime.setText(TransferUtils.getTimeSpan(
				EApplication.trailEntity.getStarttime(),
				TransferUtils.getLongTime()));
		tv_Trip_aveSpeed.setText(TransferUtils.getFormat(
				application.getAvespeed(), "0.00"));
		tv_Trip_endTime.setText("N/A");
		tv_Trip_totalSpeed.setText("N/A");
		tv_Trip_Height.setText("N/A");
		tv_Trip_naviDis.setText("N/A");
	}
	private void updateLocation() {
		tv_Trip_dis
				.setText(TransferUtils.getFormat(
						CalculateUtils.getDistance(application.getWorkspace()),
						"0.00"));
		tv_Trip_maxSpeed.setText(TransferUtils.getFormat(
				application.getMaxspeed(), "0.00"));
		tv_Trip_moveTime.setText(TransferUtils.getTimeSpan(
				EApplication.trailEntity.getStarttime(),
				TransferUtils.getLongTime()));
		tv_Trip_aveSpeed.setText(TransferUtils.getFormat(
				application.getAvespeed(), "0.00"));
		tv_Trip_endTime.setText("N/A");
		tv_Trip_totalSpeed.setText("N/A");
		tv_Trip_Height.setText("N/A");
		tv_Trip_naviDis.setText("N/A");
	}
}
