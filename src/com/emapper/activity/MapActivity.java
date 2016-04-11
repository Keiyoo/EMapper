package com.emapper.activity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.gis.GeoLine;
import com.android.gis.Point2D;
import com.android.gis.Rect2D;
import com.android.gis.Style;
import com.baidu.location.BDLocation;
import com.emapper.entity.ShippingLineEntity;
import com.emapper.entity.ShippingPointEntity;
import com.emapper.util.CalculateUtils;
import com.emapper.util.JWD;
import com.emapper.util.MapHelper;
import com.emapper.util.SysConstant;
import com.emapper.util.SysConstant.GPS;
import com.emapper.util.TransferUtils;
import com.emapper.view.LocView;
import com.piespace.mapping.MapController;
import com.piespace.mapping.MapView;
import com.piespace.mapping.MapView.LayoutParams;
import com.piespace.pienavi.navi.gps.NaviGPS;

/**
 * 
 * 地图界面 可以从其他界面进入，也可以单独打开，主要是进行地图的浏览操作
 * 
 */
public class MapActivity extends Activity implements Observer, OnClickListener {
	private MapView mapView;
	private Button btn_back;
	private MapController controller;
	private MapInitHelper mapInitHelper;
	private EApplication appContext;
	private NaviGPS naviGPS;// 通知更新的GPS

	private final String SHIPPINGPPT_TAG = "shippingpt";
	private final String SHIPPINGPLINE_TAG = "shippingline";
	private final String TRAILLINE_TAG = "trailline";
	private final String LOCATION_TAG = "loc";
	private final String NEWSHIPPINGPLINE_TAG = "newshippingline";
	private final double SCALELEVEL = 1.0255396658559988E-6;

	private String type = null;

	private double x;
	private double y;
	private ShippingPointEntity pointEntity;
	private int id;
	private TextView tv_loc_dis_value;
	private TextView tv_loc_speed_value;
	private TextView tv_title;
	private boolean isfirstLoc = true;
	private ShippingLineEntity shippingLineEntity;
	List<Point2D> ptslist;
	private String linecolor;
	private int linetype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		EApplication app = (EApplication) this.getApplication();
		app.addActivity(this);
		if (!app.flag) {
			app.init();
		}
		type = getIntent().getStringExtra(SysConstant.SHIPLINE_TYPE.TYPE);
		if (type.equals(SysConstant.POSITION_TYPE.CALIMAP)) {
			isfirstLoc = false;
			x = getIntent().getDoubleExtra("x", 0.0);
			y = getIntent().getDoubleExtra("y", 0.0);
		} else if (type.equals(SysConstant.POSITION_TYPE.POINTMAP)) {
			isfirstLoc = false;
			pointEntity = (ShippingPointEntity) getIntent()
					.getSerializableExtra("entity");

		} else if (type.equals(SysConstant.SHIPLINE_TYPE.LINEMAP)) {
			isfirstLoc = false;
			shippingLineEntity = (ShippingLineEntity) getIntent()
					.getSerializableExtra("entity");
			id = shippingLineEntity.smid;
		} else if (type.equals(SysConstant.PATH_TYPE.PATHMANAGERMAP)) {
			isfirstLoc = false;
			id = getIntent().getIntExtra("id", 0);
		} else if (type.equals(SysConstant.POSITION_TYPE.MAIN)) {
			// 从主界面入口进入
			isfirstLoc = true;
		} else if (type.equals(SysConstant.SHIPLINE_TYPE.NEWLINEMAP)) {
			// 从新建航线的地图预览入口进入
			isfirstLoc = false;
			ptslist = (List<Point2D>) getIntent().getSerializableExtra(
					SysConstant.SHIPLINE_TYPE.LIST);
			linecolor = getIntent().getStringExtra(
					SysConstant.SHIPLINE_TYPE.LINECOLOR);
			linetype = getIntent().getIntExtra(
					SysConstant.SHIPLINE_TYPE.LINETYPE, 0);

		}
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setUpView();
		init();
		if (type.equals(SysConstant.POSITION_TYPE.CALIMAP)) {
			addPoi(x, y);
		} else if (type.equals(SysConstant.POSITION_TYPE.POINTMAP)) {
			addShippingPt(pointEntity);
			// 定位情况下 更新下面距离速度 速度为当前gps的速度 距离是据当前定位点的距离
//			double length = CalculateUtils.getDistance(new Point2D(
//					pointEntity.lon, pointEntity.lat));
			String length=JWD.getDistatce(pointEntity.lon, pointEntity.lat, EApplication.getInstance().naviGPS.dLongitude, EApplication.getInstance().naviGPS.dLatitude);
			if (EApplication.getInstance().naviGPS.dLongitude == 0||EApplication.getInstance().naviGPS.dLatitude==0) {
				tv_loc_dis_value.setText("N/A");
			} else {
				tv_loc_dis_value
						.setText(length);
			}
			if (tv_title.getText().toString().endsWith("目标指示\nGPS已定位")) {

			}
			if (EApplication.getInstance().naviGPS.dSpeed == 0) {
				tv_loc_speed_value.setText("N/A");
			} else {

				tv_loc_speed_value.setText(TransferUtils.getFormat(
						naviGPS.dSpeed, "0.0"));
			}
		} else if (type.equals(SysConstant.SHIPLINE_TYPE.LINEMAP)) {
			addShippingLine(id, shippingLineEntity.color,
					shippingLineEntity.linetype);
		} else if (type.equals(SysConstant.PATH_TYPE.PATHMANAGERMAP)) {
			addTrailLine(id);
		} else if (type.equals(SysConstant.SHIPLINE_TYPE.NEWLINEMAP)) {
			// 预览航线
			GeoLine geoLine = CalculateUtils.getGeoline(mapView, ptslist);
			if (geoLine != null) {
				addShippingLine(geoLine, linecolor, linetype);
			}

		} else {
			// 定位
			double lon = GPS.LON;
			double lat = GPS.LAT;
			// if (appContext.acache.getAsString(SysConstant.CACHE_RELATE.LON)
			// != null
			// && !appContext.acache.getAsString(
			// SysConstant.CACHE_RELATE.LON).equals("")) {
			// lon = Double.parseDouble(appContext.acache
			// .getAsString(SysConstant.CACHE_RELATE.LON));
			// }
			// if (appContext.acache.getAsString(SysConstant.CACHE_RELATE.LAT)
			// != null
			// && !appContext.acache.getAsString(
			// SysConstant.CACHE_RELATE.LAT).equals("")) {
			// lat = Double.parseDouble(appContext.acache
			// .getAsString(SysConstant.CACHE_RELATE.LAT));
			// }
			mapView.getController()
					.setCenter(new Point2D(lon, lat), SCALELEVEL);
		}
	}

	private void setUpView() {
		setContentView(R.layout.activity_main);
		appContext = EApplication.getInstance();

		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		mapView = (MapView) findViewById(R.id.mapView);
		ImageButton btn_moonstate = (ImageButton) findViewById(R.id.to_moonstate);
		ImageButton btn_compass = (ImageButton) findViewById(R.id.to_compass);
		ImageButton btn_locate = (ImageButton) findViewById(R.id.to_mylocation);
		btn_moonstate.setOnClickListener(this);
		btn_compass.setOnClickListener(this);
		btn_locate.setOnClickListener(this);

		TextView tv_loc_speed_title = (TextView) findViewById(R.id.loc_speed)
				.findViewById(R.id.common_top);
		tv_loc_speed_value = (TextView) findViewById(R.id.loc_speed)
				.findViewById(R.id.common_bottom);
		TextView tv_loc_dis_title = (TextView) findViewById(R.id.loc_dis)
				.findViewById(R.id.common_top);
		tv_loc_dis_value = (TextView) findViewById(R.id.loc_dis).findViewById(
				R.id.common_bottom);

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setTextSize(20);
		tv_title.setText("目标指示\nGPS未定位");

		tv_loc_speed_title.setText("速度:");
		tv_loc_dis_title.setText("距离:");

	}

	private void init() {
		mapInitHelper = new MapInitHelper(this, mapView);
		mapInitHelper.onCreate();
		appContext.addGPSObserver(this); // 添加 gps 监听
		// appContext.watched.addObserver(this);// 添加gps信息 观察者
		this.controller = mapView.getController();
		/** 最后一次的定位点 全图显示 **/
		// appContext.setWorkspace(mapInitHelper.getWorkspace());
	}

	private void openActivity(Class<?> cls) {
		Intent intent = new Intent(MapActivity.this, cls);
		this.startActivity(intent);
	}

	@Override
	public void update(Observable observable, Object data) {
		if (observable instanceof NaviGPS && data != null) {
			// 接受到GPS信息
			naviGPS = (NaviGPS) observable;
			setLocValue(naviGPS);
			addCurrentLocation(naviGPS);
			// addCurrentPoints(naviGPS.dLongitude, naviGPS.dLatitude);
		}
		// if (observable instanceof LocationWatched) {
		// // 接受到GPS信息
		// BDLocation location = ((LocationWatched) observable).getData();
		// if (location != null) {
		// setLocValue(location);
		// addCurrentLocation(location);
		// }
		// }
	}

	// 更新值信息
	private void setLocValue(BDLocation location) {
		tv_title.setText("目标指示\nGPS已定位");
		tv_title.setTextSize(20);
		tv_loc_speed_value.setText(TransferUtils.getFormat(location.getSpeed(),
				"0.0"));
		if (EApplication.isOpen) {
			tv_loc_dis_value.setText(TransferUtils.getFormat(
					CalculateUtils.getDistance(appContext.getWorkspace()),
					"0.0"));
		}
	}

	// 更新值信息
	private void setLocValue(NaviGPS naviGPS) {
		tv_title.setText("目标指示\nGPS已定位");
		tv_title.setTextSize(20);
		tv_loc_speed_value.setText(TransferUtils.getFormat(naviGPS.dSpeed,
				"0.0"));
		if (EApplication.isOpen) {
			tv_loc_dis_value.setText(TransferUtils.getFormat(
					CalculateUtils.getDistance(appContext.getWorkspace()),
					"0.0"));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// appContext.watched.deleteObserver(this);
		appContext.deleteGPSObserver(this);
		mapView.getTrackingLayerFromBasemap().removeAllGeometry();
		controller.closeBasemapFromWorkspace();
	}

	/**
	 * 添加poi点
	 * 
	 * @param x
	 * @param y
	 */
	private void addPoi(double x, double y) {
		Point2D pt = new Point2D(x, y);
		pt = mapView.getProjection().forward(pt);
		LocView locView = new LocView(appContext);
		locView.setImageResource(R.drawable.star);
		MapView.LayoutParams locViewParam = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, pt,
				MapView.LayoutParams.CENTER);
		locView.setTag(SHIPPINGPPT_TAG);
		// mapView.getController().setCenter(pt);// 设置中心点
		mapView.addView(locView, locViewParam);
		mapView.getController().setCenter(new Point2D(x, y), SCALELEVEL);
	}

	/**
	 * 添加航点
	 * 
	 * @param entity
	 */
	private void addShippingPt(ShippingPointEntity entity) {
		Point2D shippingpt = new Point2D(entity.lon, entity.lat);
		shippingpt = mapView.getProjection().forward(shippingpt);
		LocView locView = new LocView(appContext);
		locView.setImageResource(R.drawable.star);
		MapView.LayoutParams locViewParam = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				shippingpt, MapView.LayoutParams.CENTER);
		locView.setTag(SHIPPINGPPT_TAG);
		// mapView.getController().setCenter(shippingpt);// 设置中心点
		mapView.addView(locView, locViewParam);
		mapView.getController().setCenter(new Point2D(entity.lon, entity.lat),
				SCALELEVEL);
	}

	/**
	 * 添加航线
	 * 
	 * @param entity
	 */
	private void addShippingLine(ShippingLineEntity entity) {
		int count = entity.list.size();
		Point2D[] points = new Point2D[count];
		for (int i = 0; i < count; i++) {
			points[i] = new Point2D(entity.list.get(i).lon,
					entity.list.get(i).lat);
		}
		GeoLine geoLine = new GeoLine();
		GeoLine line = geoLine.make(points);
		Style linestyle = new Style();
		linestyle.lineColor = Color.BLUE;
		linestyle.lineWidth = 5;

		line.setStyle(linestyle);
		int index = mapView.getTrackingLayerFromBasemap().addGeometry(
				SHIPPINGPLINE_TAG, line);
		Rect2D bounds = geoLine.getBounds();
		mapView.getController().setViewBounds(bounds);
	}

	/**
	 * 清除地图上poi点（一般清除点）
	 * 
	 * @param tag
	 */
	private void removePoi(String tag) {
		mapView.removeViewByTag(tag);
	}

	/**
	 * 删除跟踪图层上的对象（一般删除线面）
	 * 
	 * @param name
	 */
	private void removeTrackingLayer(String name) {
		mapView.getTrackingLayerFromBasemap().removeGeometryByName(name);
	}

	/**
	 * 地图上添加航线
	 * 
	 * @param id
	 *            航线id
	 * @param color
	 *            颜色
	 * @param type
	 *            线型
	 */
	private void addShippingLine(int id, String color, int type) {
		GeoLine geoLine = MapHelper.getShippingLineGeo(
				appContext.getWorkspace(), id);
		try {
			Style linestyle = new Style();
			if (color == null || color.equals("")) {
				linestyle.lineColor = Color.BLUE;
			} else {
				linestyle.lineColor = Integer.valueOf(color);
			}
			if (type == 0) {
				linestyle.lineWidth = 2;
			} else if (type == 1) {
				linestyle.lineWidth = 5;
			}
			geoLine.setStyle(linestyle);
			int index = mapView.getTrackingLayerFromBasemap().addGeometry(
					SHIPPINGPLINE_TAG, geoLine);

			Rect2D bounds = geoLine.getBounds();
			mapView.getController().setViewBounds(bounds);
			mapView.getController().refresh();
		} catch (Exception e) {

		}
	}

	/**
	 * 地图上添加新建的航线
	 * 
	 * @param geoLine
	 *            航线
	 * @param color
	 *            颜色
	 * @param type
	 *            线型
	 */
	private void addShippingLine(GeoLine geoLine, String color, int type) {
		removeTrackingLayer(NEWSHIPPINGPLINE_TAG);
		Style linestyle = new Style();
		if (color == null || color.equals("")) {
			linestyle.lineColor = Color.BLUE;
		} else {
			linestyle.lineColor = Integer.valueOf(color);
		}
		if (type == 0) {
			linestyle.lineWidth = 2;
		} else if (type == 1) {
			linestyle.lineWidth = 5;
		}
		geoLine.setStyle(linestyle);
		int index = mapView.getTrackingLayerFromBasemap().addGeometry(
				NEWSHIPPINGPLINE_TAG, geoLine);

		Rect2D bounds = geoLine.getBounds();
		mapView.getController().setViewBounds(bounds);
		mapView.getController().refresh();
	}

	/**
	 * 添加航迹线
	 * 
	 * @param id
	 *            轨迹线ID
	 */
	private void addTrailLine(int id) {
		try {
			GeoLine geoLine = MapHelper.getShippingTrailGeo(
					appContext.getWorkspace(), id);
			if (geoLine != null) {
				Style linestyle = new Style();
				linestyle.lineColor = Color.RED;
				linestyle.lineWidth = 2;
				geoLine.setStyle(linestyle);
				int index = mapView.getTrackingLayerFromBasemap().addGeometry(
						TRAILLINE_TAG, geoLine);

				Rect2D bounds = geoLine.getBounds();

				mapView.getController().setViewBounds(bounds);
				mapView.getController().refresh();
			}

		} catch (Exception e) {
		}

	}

	/**
	 * 添加当前坐标位置
	 * 
	 * @param location
	 *            我的位置
	 */
	private void addCurrentLocation(NaviGPS naviGPS) {
		removePoi(LOCATION_TAG);
		Point2D pt = new Point2D(naviGPS.dLongitude, naviGPS.dLatitude);
		pt = mapView.getProjection().forward(pt);
		LocView locView = new LocView(appContext);
		locView.setImageResource(R.drawable.my_location_chevron);
		MapView.LayoutParams locViewParam = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, pt,
				MapView.LayoutParams.CENTER);
		locView.setTag(LOCATION_TAG);

		double levels[] = mapView.getZoomLevels();
		int length = levels.length;
		if (isfirstLoc) {
			// mapView.getController().setZoom(levels[length - 2]);
			// mapView.getController().setCenter(pt);// 设置中心点
			// 传入的位置必须要以经纬度方式的坐标
			mapView.getController().setCenter(
					new Point2D(naviGPS.dLongitude, naviGPS.dLatitude),
					SCALELEVEL);
		}
		mapView.addView(locView, locViewParam);
		if (isfirstLoc) {
			isfirstLoc = false;
		}
	}

	/**
	 * 添加当前坐标位置
	 * 
	 * @param location
	 *            我的位置
	 */
	private void addCurrentLocation(BDLocation location) {
		removePoi(LOCATION_TAG);
		Point2D pt = new Point2D(location.getLongitude(),
				location.getLatitude());
		pt = mapView.getProjection().forward(pt);
		LocView locView = new LocView(appContext);
		locView.setImageResource(R.drawable.my_location_chevron);
		MapView.LayoutParams locViewParam = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, pt,
				MapView.LayoutParams.CENTER);
		locView.setTag(LOCATION_TAG);

		double levels[] = mapView.getZoomLevels();
		int length = levels.length;
		if (isfirstLoc) {
			// mapView.getController().setZoom(levels[length - 2]);
			// mapView.getController().setCenter(pt);// 设置中心点
			// 传入的位置必须要以经纬度方式的坐标
			mapView.getController()
					.setCenter(
							new Point2D(location.getLongitude(),
									location.getLatitude()), SCALELEVEL);
		}
		mapView.addView(locView, locViewParam);
		if (isfirstLoc) {
			isfirstLoc = false;
		}
	}

	/**
	 * 定位到当前位置
	 */
	private void locate() {
		// BDLocation location = appContext.getCurrentLoction();

		if (appContext.naviGPS != null) {
			Point2D pt = new Point2D(appContext.naviGPS.dLongitude,
					appContext.naviGPS.dLatitude);
			pt = mapView.getProjection().forward(pt);
			double levels[] = mapView.getZoomLevels();
			int length = levels.length;
			// mapView.getController().setZoom(levels[length - 2]);
			// mapView.getController().setCenter(pt);// 设置中心点
			// 传入经纬度坐标
			mapView.getController().setCenter(
					new Point2D(appContext.naviGPS.dLongitude,
							appContext.naviGPS.dLatitude), SCALELEVEL);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.to_moonstate:
			openActivity(SatelliteActivity.class);
			break;
		case R.id.to_compass:
			openActivity(CompassActivity.class);
			break;
		case R.id.btn_back:
			finish();
			break;
		case R.id.to_mylocation:
			locate();
			break;
		}
	}

}
