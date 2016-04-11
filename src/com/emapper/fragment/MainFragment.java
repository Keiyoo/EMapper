package com.emapper.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.emapper.activity.AboutActivity;
import com.emapper.activity.AreaMeasurementActivity;
import com.emapper.activity.CalibrationPointActivity;
import com.emapper.activity.ExitingLineActivity;
import com.emapper.activity.MapActivity;
import com.emapper.activity.PathManagerFirstActivity;
import com.emapper.activity.PointManagerActivity;
import com.emapper.activity.R;
import com.emapper.activity.SetActivity;
import com.emapper.activity.TakePictureActivity;
import com.emapper.activity.TripCalculatorActivity;
import com.emapper.adapter.GridViewAdapter;
import com.emapper.entity.MainEntity;

public class MainFragment extends Fragment implements OnItemClickListener {
	private GridView gridview;
	private List<MainEntity> list;
	private GridViewAdapter adapter;

	private View view;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_first_old, container, false);
		initView();
		return view;
	}

	private void initView() {
		gridview = (GridView) view.findViewById(R.id.gridview_icon);
		gridview.setOnItemClickListener(this);
		initGridView();
		adapter = new GridViewAdapter(this.getActivity(), list);
		gridview.setAdapter(adapter);
	}

	private void initGridView() {
		list = new ArrayList<MainEntity>();
		list.add(new MainEntity(R.drawable.ico1, getString(R.string.icon1)));
		list.add(new MainEntity(R.drawable.ico2, getString(R.string.icon2)));
		list.add(new MainEntity(R.drawable.ico3, getString(R.string.icon3)));
		list.add(new MainEntity(R.drawable.ico4, getString(R.string.icon4)));
		list.add(new MainEntity(R.drawable.ico5, getString(R.string.icon5)));
		list.add(new MainEntity(R.drawable.ico6, getString(R.string.icon6)));
		list.add(new MainEntity(R.drawable.ico7, getString(R.string.icon7)));
		list.add(new MainEntity(R.drawable.ico8, getString(R.string.icon8)));
		list.add(new MainEntity(R.drawable.ico9, getString(R.string.icon9)));

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		MainEntity entity = list.get(position);
		if (entity.text.equals(getString(R.string.icon1))) {
			// 打开标定航点界面
			starActivity(this.getActivity(), CalibrationPointActivity.class,
					"main");
		} else if (entity.text.equals(getString(R.string.icon2))) {
			// 打开航点管理界面
			Intent intent = new Intent();
			intent.setClass(this.getActivity(), PointManagerActivity.class);
			intent.putExtra("type", "main");
			startActivity(intent);
		} else if (entity.text.equals(getString(R.string.icon3))) {
			// 打开航线管理界面
			openActivity(this.getActivity(), ExitingLineActivity.class);
		} else if (entity.text.equals(getString(R.string.icon4))) {
			// 打开航迹航迹管理界面
			openActivity(this.getActivity(), PathManagerFirstActivity.class);
		} else if (entity.text.equals(getString(R.string.icon5))) {
			// 打开地图界面
			Intent intent = new Intent();
			intent.setClass(this.getActivity(), MapActivity.class);
			intent.putExtra("type", "main");
			startActivity(intent);
		} else if (entity.text.equals(getString(R.string.icon6))) {
			// 打开拍照界面
			starActivity(this.getActivity(), TakePictureActivity.class, "main");
		} else if (entity.text.equals(getString(R.string.icon7))) {
			// 打开设置界面
			openActivity(this.getActivity(), SetActivity.class);
		} else if (entity.text.equals(getString(R.string.icon8))) {
			// 打开面积测量界面
			openActivity(this.getActivity(), AreaMeasurementActivity.class);
		} else if (entity.text.equals(getString(R.string.icon9))) {
			// 打开关于界面
			openActivity(this.getActivity(), AboutActivity.class);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void openActivity(Context context, Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		context.startActivity(intent);
	}

	public void starActivity(Context context, Class<?> cls, String type) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		intent.putExtra("type", type);
		context.startActivity(intent);
	}
}
