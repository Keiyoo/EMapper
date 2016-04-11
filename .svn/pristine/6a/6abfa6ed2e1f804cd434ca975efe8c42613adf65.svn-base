package com.emapper.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gis.GeoLine;
import com.android.gis.GeoRegion;
import com.android.gis.Point2D;
import com.android.gis.Rect2D;
import com.android.gis.Style;
import com.baidu.location.BDLocation;
import com.emapper.entity.MeasureLineEntity;
import com.emapper.entity.ShippingPointEntity;
import com.emapper.entity.TrailEntity;
import com.emapper.util.CalculateUtils;
import com.emapper.util.MapHelper;
import com.emapper.util.SysConstant;
import com.emapper.util.TransferUtils;
import com.emapper.view.LocView;
import com.piespace.mapping.MapController;
import com.piespace.mapping.MapView;
import com.piespace.mapping.MapView.LayoutParams;
import com.piespace.pienavi.navi.gps.NaviGPS;

/**
 * 面积计算界面 通过GPS定位实时计算行走的距离和面积 备注：当前量算的方式虽然能够实现线面同时获取，但是跟当初设计不太一致，
 * 当前实现的比较麻烦（面信息没有存储，而是依附于线计算而来的）， 后面有时间修改为面存储的方式 by-weizm
 */
public class MeasureActivity extends BaseActivity implements Observer,
		OnClickListener {
	private Button btn_back;
	private TextView text_length;// 长度
	private TextView text_satellitecunt;// 卫星数目
	private TextView text_precious;// 精度
	private TextView text_circ;// 周长
	private TextView text_area;// 面积
	private Button btn_stat;// 开始计算
	private Button btn_end;// 停止计算
	private MapView mapView;
	private MapController controller;
	private MapInitHelper mapInitHelper;
	private EApplication appContext;

	private boolean isFirst = true;
	private boolean isOpen = false;
	// 量算轨迹点
	public List<Point2D> points_trail = new ArrayList();
	private double length = 0.0;
	private EApplication application;
	private MeasureLineEntity measureLineEntity;
	private String type;
	private LinearLayout layout1;
	private LinearLayout layout2;
	private boolean isFirstEnter = true;
	private NaviGPS naviGPS;
	private boolean isFinish = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = EApplication.getInstance();
		application.addActivity(this);
		type = getIntent().getStringExtra(SysConstant.SHIPLINE_TYPE.TYPE);
		if (type.equals(SysConstant.AREA_TYPE.EXITING)) {
			measureLineEntity = (MeasureLineEntity) getIntent()
					.getSerializableExtra("entity");
			Log.v("gis", measureLineEntity.smid + "measureLineEntity.smid");
		}
		initView();
		init();
	}

	private void initView() {
		setContentView(R.layout.activity_measure);
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		if (type.equals("ExitingNoteActivity")) {
			layout1.setVisibility(View.GONE);
			layout2.setVisibility(View.GONE);
		}
		appContext = EApplication.getInstance();
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_area_measurement));
		text_length = (TextView) findViewById(R.id.text_length);
		text_satellitecunt = (TextView) findViewById(R.id.text_satellitecunt);
		text_precious = (TextView) findViewById(R.id.text_precious);
		text_circ = (TextView) findViewById(R.id.text_circ);
		text_area = (TextView) findViewById(R.id.text_area);
		btn_stat = (Button) findViewById(R.id.btn_stat);
		btn_end = (Button) findViewById(R.id.btn_end);
		btn_stat.setOnClickListener(this);
		btn_end.setOnClickListener(this);
		mapView = (MapView) findViewById(R.id.mapView);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isOpen) {
					isFinish = true;
					saveMeasure();
				} else {
					finish();
				}
			}
		});

	}

	private void init() {
		mapInitHelper = new MapInitHelper(this, mapView);
		mapInitHelper.onCreate();
		appContext.addGPSObserver(this); // 添加 gps 监听
		// appContext.watched.addObserver(this);// 添加gps信息 观察者
		this.controller = mapView.getController();
		/** 最后一次的定位点 全图显示 **/
		// acache = ACache.get(new File(ResPathCenter.getCachePath()));

		if (type.equals("ExitingNoteActivity")) {
			addMeasureLine(measureLineEntity.smid);
			text_length.setText(TransferUtils.getFormat(
					measureLineEntity.disatance, "0.00"));

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

	@Override
	public void update(Observable observable, Object data) {
		if (observable instanceof NaviGPS && data != null) {
			// 接受到GPS信息
			naviGPS = (NaviGPS) observable;
			if (type.equals("AreaMeasurementActivity")) {
				if (isFirst) {
					Log.v("gis", isFirst+"isFirstqqqqqqqqqqqqqq");
					measureLineEntity = new MeasureLineEntity();
					Calendar dt = Calendar.getInstance();
					Long time = dt.getTimeInMillis() / 1000;
					measureLineEntity.name = TransferUtils.getTime3(time);
					isFirst = !isFirst;
				}
				if (isOpen) {
					points_trail.add(new Point2D(naviGPS.dLongitude,
							naviGPS.dLatitude));
					// ShippingPointEntity entity=new ShippingPointEntity();
					// entity.lon=naviGPS.dLongitude;
					// entity.lat=naviGPS.dLatitude;
					// entity.name=naviGPS.lTime+"";
					// MapHelper.addShippingPt(EApplication
					// .getInstance().getWorkspace(),
					// entity);
					addMeasureLine(points_trail);
					addMeasureRegion(points_trail);
				}

				addCurrentLocation(naviGPS);
			}
		}
		// if (observable instanceof LocationWatched) {
		// // 接受到GPS信息
		// BDLocation location = ((LocationWatched) observable).getData();
		// if (location != null) {
		// if (type.equals("AreaMeasurementActivity")) {
		// if (isFirst) {
		// measureLineEntity = new MeasureLineEntity();
		// Calendar dt = Calendar.getInstance();
		// Long time = dt.getTimeInMillis() / 1000;
		// measureLineEntity.name = TransferUtils.getTime3(time);
		// isFirst = !isFirst;
		// }
		// if (isOpen) {
		// points_trail.add(new Point2D(location.getLongitude(),
		// location.getLatitude()));
		// addMeasureLine(points_trail);
		// addMeasureRegion(points_trail);
		// }
		//
		// addCurrentLocation(location);
		// }
		// }
		// }
	}

	/**
	 * 地图上添加量算线
	 * 
	 * @param id
	 *            量算线id
	 */
	private void addMeasureLine(int id) {
		removeTrackingLayer(SysConstant.MAP_VIEW_TAG.MEASURE_TAG);
		GeoLine geoLine = MapHelper.getMeasureLineGeo(
				appContext.getWorkspace(), id);
		Style linestyle = new Style();
		linestyle.lineColor = Color.RED;
		linestyle.lineWidth = 2;
		geoLine.setStyle(linestyle);
		int index = mapView.getTrackingLayerFromBasemap().addGeometry(
				SysConstant.MAP_VIEW_TAG.MEASURE_TAG, geoLine);

		Rect2D bounds = geoLine.getBounds();
		mapView.getController().setViewBounds(bounds);
		addMeasureRegion(geoLine.getPoints(0));
	}

	/**
	 * 地图上添加量算面
	 * 
	 * @param points_trail
	 *            量算记录点
	 */
	private void addMeasureRegion(Point2D[] points_trail) {
		removeTrackingLayer(SysConstant.MAP_VIEW_TAG.MEASURE_REGION_TAG);
		GeoRegion geoRegion = new GeoRegion();
		geoRegion.make(points_trail);
		if (geoRegion != null) {
			Style regionstyle = new Style();
			regionstyle.fillForeColor = Color.argb(150, 0, 0, 255);
			geoRegion.setStyle(regionstyle);
			int index = mapView.getTrackingLayerFromBasemap().addGeometry(
					SysConstant.MAP_VIEW_TAG.MEASURE_REGION_TAG, geoRegion);
			double circ = geoRegion.getLength();
			double area = geoRegion.getArea();
			setMeasureValue(circ, area);
		}
	}

	/**
	 * 地图上添加量算面
	 * 
	 * @param points_trail
	 *            量算记录点
	 */
	private void addMeasureRegion(List<Point2D> points_trail) {
		removeTrackingLayer(SysConstant.MAP_VIEW_TAG.MEASURE_REGION_TAG);
		GeoRegion geoRegion = CalculateUtils
				.getGeoregion(mapView, points_trail);
		if (geoRegion != null) {
			Style regionstyle = new Style();
			regionstyle.fillForeColor = Color.argb(150, 0, 0, 255);
			geoRegion.setStyle(regionstyle);
			int index = mapView.getTrackingLayerFromBasemap().addGeometry(
					SysConstant.MAP_VIEW_TAG.MEASURE_REGION_TAG, geoRegion);
			if (index > 0) {
				double circ = geoRegion.getLength();
				double area = geoRegion.getArea();
				setMeasureValue(circ, area);
			}
		}
	}

	/**
	 * 地图上添加量算线
	 * 
	 * @param points_trail
	 *            量算记录点
	 */
	private void addMeasureLine(List<Point2D> points_trail) {
		removeTrackingLayer(SysConstant.MAP_VIEW_TAG.MEASURE_TAG);
		GeoLine geoLine = CalculateUtils.getGeoline(mapView, points_trail);
		if (geoLine != null) {
			measureLineEntity.geoline = geoLine;
			Style linestyle = new Style();
			linestyle.lineColor = Color.RED;
			linestyle.lineWidth = 2;
			geoLine.setStyle(linestyle);
			int index = mapView.getTrackingLayerFromBasemap().addGeometry(
					SysConstant.MAP_VIEW_TAG.MEASURE_TAG, geoLine);
			// Rect2D bounds = geoLine.getBounds();
			// mapView.getController().setViewBounds(bounds);
			length = geoLine.getLength();
			measureLineEntity.disatance = length;
		}
	}

	/**
	 * 设置当前卫星信息
	 * 
	 * @param 当前定位信息
	 */
	private void setLocValue(BDLocation location) {
		// 设置卫星数目
		text_satellitecunt
				.setText(String.valueOf(location.getSatelliteNumber()));
		// 设置当前定位精度
		text_precious.setText(String.valueOf((int) location.getRadius()));
	}

	/**
	 * 设置当前卫星信息
	 * 
	 * @param 当前定位信息
	 */
	private void setLocValue(NaviGPS naviGPS) {
		// 设置卫星数目
		text_satellitecunt.setText(String.valueOf(naviGPS.iSatellites));
		// 设置当前定位精度
		text_precious.setText(String.valueOf((int) naviGPS.dAccuracy));
	}

	/**
	 * 更新量算值
	 * 
	 * @param length
	 */
	private void setMeasureValue(double circ, double area) {
		Log.v("gis", circ+"circ"+area+"area"+length+"length");
		text_length.setText(TransferUtils.getFormat(length, "0.00"));
		text_circ.setText(TransferUtils.getFormat(circ, "0.00"));
		text_area.setText(TransferUtils.getFormat(area, "0.00"));
	}

	/**
	 * 添加当前坐标位置
	 * 
	 * @param location
	 *            我的位置
	 */
	private void addCurrentLocation(BDLocation location) {
		removePoi(SysConstant.MAP_VIEW_TAG.LOCATION_TAG);
		Point2D pt = new Point2D(location.getLongitude(),
				location.getLatitude());
		pt = mapView.getProjection().forward(pt);
		LocView locView = new LocView(appContext);
		locView.setImageResource(R.drawable.my_location_chevron);
		MapView.LayoutParams locViewParam = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, pt,
				MapView.LayoutParams.CENTER);
		locView.setTag(SysConstant.MAP_VIEW_TAG.LOCATION_TAG);

		if (isFirstEnter) {
			mapView.getController()
					.setCenter(
							new Point2D(location.getLongitude(),
									location.getLatitude()),
							SysConstant.GPS.SCALELEVEL);
		} else {
			mapView.getController().setCenter(pt);// 设置中心点
		}
		mapView.addView(locView, locViewParam);

		setLocValue(location);
	}

	/**
	 * 添加当前坐标位置
	 * 
	 * @param location
	 *            我的位置
	 */
	private void addCurrentLocation(NaviGPS naviGPS) {
		removePoi(SysConstant.MAP_VIEW_TAG.LOCATION_TAG);
		Point2D pt = new Point2D(naviGPS.dLongitude, naviGPS.dLatitude);
		pt = mapView.getProjection().forward(pt);
		LocView locView = new LocView(appContext);
		locView.setImageResource(R.drawable.my_location_chevron);
		MapView.LayoutParams locViewParam = new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, pt,
				MapView.LayoutParams.CENTER);
		locView.setTag(SysConstant.MAP_VIEW_TAG.LOCATION_TAG);

		if (isFirstEnter) {
			mapView.getController().setCenter(
					new Point2D(naviGPS.dLongitude, naviGPS.dLatitude),
					SysConstant.GPS.SCALELEVEL);
		} else {
			mapView.getController().setCenter(pt);// 设置中心点
		}
		mapView.addView(locView, locViewParam);

		setLocValue(naviGPS);
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
	 * 删除跟踪图层上的对象（一般删除线和面对象）
	 * 
	 * @param name
	 *            tag值
	 */
	private void removeTrackingLayer(String name) {
		mapView.getTrackingLayerFromBasemap().removeGeometryByName(name);
	}

	@Override
	public void onClick(View v) {
		if (v == btn_stat) {
			// 开始计算
			if (!isOpen) {
				isOpen = true;
				if (!EApplication.isOpen) {
					EApplication.isOpen = true;
					EApplication.trailEntity = new TrailEntity();
					Calendar dt = Calendar.getInstance();
					Long time = dt.getTimeInMillis() / 1000;
					EApplication.trailEntity.setStarttime(time);
					EApplication.trailEntity.setName("T"
							+ TransferUtils.getTime3(time));
				}

			}
		} else if (v == btn_end) {
			// 结束计算
			if (isOpen) {
				saveMeasure();
			}
		} else {

		}
	}

	/**
	 * 保存量算结果
	 */
	private void saveMeasure() {
		showDialog(getString(R.string.title), getString(R.string.savemeasure),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						isOpen = false;
						points_trail.clear();
						//measureLineEntity.geoline = geoLine;
						dialog.dismiss();
						isFirst = true;
//						 points_trail.add(new Point2D(116.22,39.95));
//						 points_trail.add(new Point2D(116.23,39.96));
//							GeoLine geoLine1= CalculateUtils.getGeoline(mapView, points_trail);
							//measureLineEntity.geoline=geoLine1;
						int a = MapHelper.addMeasureLine(EApplication
								.getInstance().getWorkspace(),
								measureLineEntity);
						if (a == 0 || a == -1) {
							Toast.makeText(MeasureActivity.this,
									getString(R.string.savenosuccss),
									Toast.LENGTH_SHORT).show();
						}
					}
				}, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (isFinish) {
							MeasureActivity.this.finish();
						}
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (isOpen) {
				saveMeasure();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
