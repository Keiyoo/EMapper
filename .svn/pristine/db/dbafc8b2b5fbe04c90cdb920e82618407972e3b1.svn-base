package com.emapper.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.emapper.adapter.GridViewAdapter;
import com.emapper.entity.MainEntity;
import com.emapper.util.SysConstant;
import com.piespace.pienavi.navi.gps.NaviGPS;

public class MainActivity extends BaseActivity implements OnItemClickListener {
	private GridView gridview;
	private List<MainEntity> list;
	private GridViewAdapter adapter;
//	private MapInitHelper mapInitHelper = null;
	private EApplication appContext;
	// 退出系统时间
	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
		appContext.addActivity(this);
//		mapInitHelper = new MapInitHelper(this);
//		mapInitHelper.OpenWorkspace();
//		appContext.setWorkspace(mapInitHelper.getWorkspace());
		appContext.OpenWorkspace();
		// MapHelper.setDatasourcePrj(appContext.getWorkspace(),
		// SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME);
	}

	private void initView() {
		setContentView(R.layout.activity_first_old);
		gridview = (GridView) findViewById(R.id.gridview_icon);
		gridview.setOnItemClickListener(this);
		initGridView();
		adapter = new GridViewAdapter(this, list);
		gridview.setAdapter(adapter);
		appContext = EApplication.getInstance();
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
			starActivity(this, CalibrationPointActivity.class, "main");
		} else if (entity.text.equals(getString(R.string.icon2))) {
			// 打开航点管理界面
			Intent intent = new Intent();
			intent.setClass(this, PointManagerActivity.class);
			intent.putExtra("type", "main");
			startActivity(intent);
			// openActivity(this, PointManagerActivity.class);
		} else if (entity.text.equals(getString(R.string.icon3))) {
			// 打开航线管理界面
			openActivity(this, ExitingLineActivity.class);
		} else if (entity.text.equals(getString(R.string.icon4))) {
			// 打开航迹航迹管理界面
			openActivity(this, PathManagerFirstActivity.class);
		} else if (entity.text.equals(getString(R.string.icon5))) {
			// 打开地图界面
			Intent intent = new Intent();
			intent.setClass(this, MapActivity.class);
			intent.putExtra("type", "main");
			startActivity(intent);
		} else if (entity.text.equals(getString(R.string.icon6))) {
			// 打开拍照界面
			starActivity(this, TakePictureActivity.class, "main");
		} else if (entity.text.equals(getString(R.string.icon7))) {
			// 打开设置界面
			openActivity(this, SetActivity.class);
		} else if (entity.text.equals(getString(R.string.icon8))) {
			// 打开面积测量界面
			openActivity(this, AreaMeasurementActivity.class);
		} else if (entity.text.equals(getString(R.string.icon9))) {
			// 打开关于界面
			// openActivity(this, TripCalculatorActivity.class);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		appContext.onDestory();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
				exitTime = System.currentTimeMillis();
			} else {
				// appContext.acache.put(SysConstant.CACHE_RELATE.LON,
				// appContext.getCurrentLoction().getLongitude()
				// + "");
				// appContext.acache.put(SysConstant.CACHE_RELATE.LAT,
				// appContext.getCurrentLoction().getLatitude()
				// + "");
				NaviGPS naviGPS = appContext.naviGPS;
				if (naviGPS != null) {
					appContext.acache.put(SysConstant.CACHE_RELATE.LON,
							naviGPS.dLongitude + "");
					appContext.acache.put(SysConstant.CACHE_RELATE.LAT,
							naviGPS.dLatitude + "");
				}

				this.finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
