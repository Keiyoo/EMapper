package com.emapper.activity;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.emapper.adapter.FrgtPagerAdapter;
import com.emapper.fragment.CompassFragment;
import com.emapper.fragment.MainFragment;
import com.emapper.fragment.SatelliteFragment;
import com.emapper.fragment.TripCalculatorFragment;
import com.emapper.util.SysConstant;
import com.piespace.pienavi.navi.gps.NaviGPS;

/**
 * 系统主界面
 * 
 * 系统主界面切换
 */
public class FirstActivity extends FragmentActivity implements
		OnItemClickListener, OnPageChangeListener {
	private ViewPager viewPager;
	private FrgtPagerAdapter pagerAdapter;
	// 退出系统时间
	private long exitTime = 0;
//	private MapInitHelper mapInitHelper;
	
	
	private EApplication appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);
		initView();

		appContext = EApplication.getInstance();
		if (!appContext.OpenWorkspace()) {
			appContext.showToast("打开工作空间失败");
		}
		//		appContext.setWorkspace(workspace);
//		mapInitHelper = new MapInitHelper(this);
//		mapInitHelper.workspaceInit();
//		mapInitHelper.onCreate();
//		appContext.setWorkspace(mapInitHelper.getWorkspace());
	}

	private void initView() {
		setContentView(R.layout.activity_first);
		viewPager = (ViewPager) findViewById(R.id.main_tab_pager);
		ArrayList<Fragment> fragments = getFragments();
		pagerAdapter = new FrgtPagerAdapter(getSupportFragmentManager(),
				fragments);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setOffscreenPageLimit(4); // 一共加载3页，如果此处不指定，默认只加载相邻页
		viewPager.setCurrentItem(2);
	}

	private ArrayList<Fragment> getFragments() {
		// 设置查询参数
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		MainFragment main = new MainFragment();
		SatelliteFragment satellite = new SatelliteFragment();
		TripCalculatorFragment trip = new TripCalculatorFragment();
		CompassFragment compass = new CompassFragment();
		fragments.add(trip);
		fragments.add(satellite);
		fragments.add(main);
		fragments.add(compass);
		return fragments;
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
				if (appContext.isOpen) {
					Calendar dt = Calendar.getInstance();
					Long time = dt.getTimeInMillis() / 1000;// 这就是距离1970年1月1日0点0分0秒的毫秒数
					appContext.trailEntity.setEndtime(time);
					appContext.trailDAO.Add(appContext.trailEntity);
				}
//				appContext.acache.put(SysConstant.CACHE_RELATE.LON, appContext
//						.getCurrentLoction().getLongitude() + "");
//				appContext.acache.put(SysConstant.CACHE_RELATE.LAT, appContext
//						.getCurrentLoction().getLatitude() + "");
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
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {

	}

}
