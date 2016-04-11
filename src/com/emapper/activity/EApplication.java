package com.emapper.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import android.app.Activity;
import android.app.Application;
import android.location.Location;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.widget.Toast;

import com.android.gis.Point2D;
import com.android.gis.Workspace;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.emapper.dao.PositionDAO;
import com.emapper.dao.TrailDAO;
import com.emapper.entity.PositionEntity;
import com.emapper.entity.TrailEntity;
import com.emapper.util.ACache;
import com.emapper.util.CalculateUtils;
import com.emapper.util.Constant;
import com.emapper.util.ResPathCenter;
import com.emapper.util.SysConstant;
import com.piespace.pienavi.navi.gps.NaviGPS;

public class EApplication extends Application {

	private static EApplication application = null;
	public NaviGPS naviGPS;
	public volatile boolean flag = false;
	public ResPathCenter resPathCenter; // 资源路径中心
	private Workspace workspace = null;
	private boolean m_bWskOpened = false;       //判断工作空间是否已经打开
	//
	// public LocationClient mLocationClient = null;
	// public MyLocationListenner myListener = new MyLocationListenner();
	// private BDLocation mCurrentLoction = new BDLocation();

	public static boolean isOpen = false;
	public PositionDAO dao = null;
	public static TrailEntity trailEntity = null;
	public TrailDAO trailDAO = null;

	public ACache acache;

	private double maxspeed = 0;
	private double avespeed = 0;

	// 消息观察者
	//public LocationWatched watched = null;

	// 航迹点
	public static List<Point2D> points_trail = new ArrayList();
	private Point2D curlocPt; // 当前位置

	// activity列表
	private List<Activity> activityList = new LinkedList<Activity>();

	public boolean isFirst = true;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		//watched = new LocationWatched();
		init();
	}

	public void init() {
		flag = true;
		application = this;
		resPathCenter = ResPathCenter.getInstance();
		naviGPS = new NaviGPS(getApplicationContext());
		naviGPS.openGPSLocation();
		// 开启定位
		// startBDLocService();
		dao = new PositionDAO(getApplicationContext());
		trailDAO = new TrailDAO(getApplicationContext());

		acache = ACache.get(new File(ResPathCenter.getCachePath()));
		initCoordinateSet();
	}
	
	public boolean OpenWorkspace() {
		if (m_bWskOpened) {
			return true;
		}

		if (workspace == null) {
			workspace = new Workspace();
		}

		m_bWskOpened = workspace.open(resPathCenter
				.getWorkspacePath());
		if (m_bWskOpened) {
			if(isFirst){
				isFirst = false;
			}
		}

		return m_bWskOpened;
	}

	private void initCoordinateSet() {

		String sys = acache.getAsString(SysConstant.CACHE_RELATE.SYSTME);
		if (sys != null) {
			Log.v("gis", sys + "systttttttttttttt");
			Constant.COOREDINATE_SYS = Integer.valueOf(sys);
		} else {
			Constant.COOREDINATE_SYS = 1;
		}

		sys = acache.getAsString(SysConstant.CACHE_RELATE.COORD_SET);
		if (sys != null) {
			Constant.COOREDINATE_SET = Integer.valueOf(sys);
		} else {
			Constant.COOREDINATE_SET = 1;
		}

		sys = acache.getAsString(SysConstant.CACHE_RELATE.CIRE_TYPE);
		if (sys != null) {
			Constant.CIRE_TYPE_SET = Integer.valueOf(sys);
		} else {
			Constant.CIRE_TYPE_SET = 1;
		}

		sys = acache.getAsString(SysConstant.CACHE_RELATE.UNIT);
		if (sys != null) {
			Constant.UNIT_SET = Integer.valueOf(sys);
		} else {
			Constant.UNIT_SET = 1;
		}

		sys = acache.getAsString(SysConstant.CACHE_RELATE.SPEED);
		if (sys != null) {
			Constant.SPEED_SET = Integer.valueOf(sys);
		} else {
			Constant.SPEED_SET = 1;
		}

		sys = acache.getAsString(SysConstant.CACHE_RELATE.LENGTH);
		if (sys != null) {
			Constant.LENGTH_SET = Integer.valueOf(sys);
		} else {
			Constant.LENGTH_SET = 1;
		}

		sys = acache.getAsString(SysConstant.CACHE_RELATE.AREA);
		if (sys != null) {
			Constant.AREA_SET = Integer.valueOf(sys);
		} else {
			Constant.AREA_SET = 1;
		}

		sys = acache.getAsString(SysConstant.CACHE_RELATE.TIME);
		if (sys != null) {
			Constant.TIME_SET = Integer.valueOf(sys);
		} else {
			Constant.TIME_SET = 1;
		}

		sys = acache.getAsString(SysConstant.CACHE_RELATE.TIMEZONE);
		if (sys != null) {
			Constant.TIMEZONE_SET = Integer.valueOf(sys);
		} else {
			Constant.TIMEZONE_SET = 3;
		}

	}

	/** 添加 GPS变化的观察者 **/
	public void addGPSObserver(Observer observer) {
		if (naviGPS != null) {
			naviGPS.addObserver(observer);
		}
	}

	/**
	 * 删除一个gps观察者对象
	 * 
	 * @param observer
	 */
	public void deleteGPSObserver(Observer observer) {
		if (naviGPS != null) {
			naviGPS.deleteObserver(observer);
		}
	}

	public boolean isGPSEnable() {
		boolean flag = false;
		if (naviGPS != null) {
			flag = naviGPS.isGPSEnable();
		}
		return flag;
	}

	public void onDestory() {
		if (isOpen) {
			Calendar dt = Calendar.getInstance();
			Long time = dt.getTimeInMillis() / 1000;// 这就是距离1970年1月1日0点0分0秒的毫秒数
			trailEntity.setEndtime(time);
			trailDAO.Add(trailEntity);
		}
		if (naviGPS != null) {
			naviGPS.closeGPSLocation();
			naviGPS.deleteObservers();
			naviGPS = null;
		}
		// 关闭工作空间
		if (workspace != null) {
			workspace.close();
			workspace = null;
		}
		// application = null;
		flag = false;
	}

	public static EApplication getInstance() {
		return application;
	}

	public boolean isWskOpened(){
		return m_bWskOpened;
	}
	
	public Workspace getWorkspace() {
		return workspace;
	}

//	public void setWorkspace(Workspace workspace) {
//		this.workspace = workspace;
//	}

	// /**
	// * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
	// */
	// public class MyLocationListenner implements BDLocationListener {
	// @Override
	// public void onReceiveLocation(BDLocation location) {
	// if (location == null)
	// return;
	// if (location.getLocType() == 161 || location.getLocType() == 66
	// || location.getLocType() == 61) {
	// mCurrentLoction = location;
	// if (isOpen) {
	// saveToDB();
	// curlocPt = new Point2D(location.getLongitude(),
	// location.getLatitude());
	// points_trail.add(curlocPt);
	// maxspeed = CalculateUtils.getMaxSpeed(maxspeed,
	// location.getSpeed());
	// avespeed = CalculateUtils.getAveSpeed(avespeed,
	// location.getSpeed());
	// }
	// watched.setData(location);
	// }
	// }
	//
	// public void onReceivePoi(BDLocation poiLocation) {
	//
	// }
	// }

	public void setLocationValue(Location location) {
		if (isOpen) {
			saveToDB(location);
			curlocPt = new Point2D(location.getLongitude(),
					location.getLatitude());
			points_trail.add(curlocPt);
			maxspeed = CalculateUtils
					.getMaxSpeed(maxspeed, location.getSpeed());
			avespeed = CalculateUtils
					.getAveSpeed(avespeed, location.getSpeed());
		}
	}

	private void saveToDB(Location location) {
		Calendar dt = Calendar.getInstance();
		Long time = dt.getTimeInMillis() / 1000;// 这就是距离1970年1月1日0点0分0秒的毫秒数
		PositionEntity entity = new PositionEntity();
		entity.setLat(location.getLatitude());
		entity.setLon(location.getLongitude());
		entity.setSpeed(location.getSpeed());
		entity.setDatetime(time);
		dao.Add(entity);
	}

	// private void saveToDB() {
	// Calendar dt = Calendar.getInstance();
	// Long time = dt.getTimeInMillis() / 1000;// 这就是距离1970年1月1日0点0分0秒的毫秒数
	// PositionEntity entity = new PositionEntity();
	// entity.setLat(mCurrentLoction.getLatitude());
	// entity.setLon(mCurrentLoction.getLongitude());
	// entity.setSpeed(mCurrentLoction.getSpeed());
	// entity.setDatetime(time);
	// dao.Add(entity);
	// }
	//
	// private void startBDLocService() {
	// mLocationClient = new LocationClient(this);
	// myListener = new MyLocationListenner();
	// mLocationClient.setAK(SysConstant.GPS.LOCATE_AK);
	//
	// mLocationClient.registerLocationListener(myListener);
	//
	// LocationClientOption option = new LocationClientOption();
	// option.setScanSpan(5000);
	// option.setOpenGps(true); // 打开gps
	// option.setCoorType("gcj02"); // 设置坐标类型（国测局经纬度坐标系）
	// option.setServiceName("com.baidu.location.service_v2.9");
	// option.setPoiExtraInfo(true);
	// option.setAddrType("all");
	// option.setPoiNumber(0);
	// option.disableCache(true);
	//
	// mLocationClient.setLocOption(option);
	// mLocationClient.start();
	//
	// }
	//
	// public BDLocation getCurrentLoction() {
	// if (mCurrentLoction.getLocType() == 161
	// || mCurrentLoction.getLocType() == 66
	// || mCurrentLoction.getLocType() == 61) {
	// return mCurrentLoction;
	// } else {
	// return null;
	// }
	//
	// }

	// 获取旅行的最大速度
	public double getMaxspeed() {
		return maxspeed;
	}

	// 获取旅行平均速度
	public double getAvespeed() {
		return avespeed;
	}

	// 添加Activity 到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	/**
	 * 退出程序
	 */
	public void exitApp() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
	}
	
	public static void showToast(String content) {
		Toast.makeText(application, content, Toast.LENGTH_LONG).show();
	}
}
