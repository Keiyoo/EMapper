package com.emapper.util;

import java.util.List;

import android.util.Log;

import com.android.gis.Dataset;
import com.android.gis.GeoLine;
import com.android.gis.GeoRegion;
import com.android.gis.Point2D;
import com.android.gis.Workspace;
import com.baidu.location.BDLocation;
import com.emapper.activity.EApplication;
import com.emapper.util.SysConstant.GPS;
import com.piespace.mapping.MapView;
import com.piespace.pienavi.navi.gps.NaviGPS;

public class CalculateUtils {

	/**
	 * 获取最大航行速度
	 * 
	 * @return
	 */
	public static double getMaxSpeed(double speed, double curspeed) {
		double maxspeed = curspeed > speed ? curspeed : speed;
		return maxspeed;
	}

	/**
	 * 获取平均巡航速度
	 * 
	 * @param speed
	 * @param curspeed
	 * @return
	 */
	public static double getAveSpeed(double speed, double curspeed) {
		double avespeed = (speed + curspeed) / 2;
		return avespeed;
	}

	/**
	 * 获取航行距离
	 * 
	 * @return
	 */
	public static double getDistance(Workspace workSpace) {
		Dataset dt = MapHelper.getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
		if (dt == null) {
			return 0;
		}
		int count = EApplication.points_trail.size();
		if (count >= 2) {
			Point2D[] points = new Point2D[count];
			for (int i = 0; i < count; i++) {
				points[i] = EApplication.points_trail.get(i);
				points[i] = dt.getPrjCoordSys().forward(points[i]);
			}
			// 构造线对象
			GeoLine geoline = new GeoLine();
			geoline.make(points);
			return geoline.getLength();
		}
		return 0;
	}

	/**
	 * 获取地图上线的距离
	 * 
	 * @param mapView
	 * @param pts
	 * @return
	 */
	public static double getDistance(MapView mapView, Point2D[] pts) {
		int count = pts.length;
		if (count >= 2) {
			Point2D[] points = new Point2D[count];
			for (int i = 0; i < count; i++) {
				points[i] = mapView.getProjection().forward(pts[i]);
			}
			// 构造线对象
			GeoLine geoline = new GeoLine();
			geoline.make(points);
			return geoline.getLength();
		}
		return 0;
	}

	/**
	 * 获取地图上面的相关信息（面积和周长）
	 * 
	 * @param mapView
	 * @param pts
	 * @return
	 */
	public static double[] getAreaInfo(MapView mapView, Point2D[] pts) {
		double[] info = new double[2];
		int count = pts.length;
		if (count > 2) {
			Point2D[] points = new Point2D[count];
			for (int i = 0; i < count; i++) {
				points[i] = mapView.getProjection().forward(pts[i]);
			}
			// 构造面对象
			GeoRegion georegion = new GeoRegion();
			georegion.make(points);
			info[0] = georegion.getArea();
			info[1] = georegion.getLength();
		}
		return info;
	}

	/**
	 * 获取线对象
	 * 
	 * @param mapView
	 * @param pts
	 * @return
	 */
	public static GeoLine getGeoline(MapView mapView, List<Point2D> pts) {
		int count = pts.size();
		if (count >= 2) {
			Point2D[] points = new Point2D[count];
			for (int i = 0; i < count; i++) {
				Point2D pt = pts.get(i);
				points[i] = mapView.getProjection().forward(pt);
			}
			// 构造线对象
			GeoLine geoline = new GeoLine();
			geoline.make(points);
			return geoline;
		}
		return null;
	}
	
	/**
	 * 获取面对象
	 * 
	 * @param mapView
	 * @param pts
	 * @return
	 */
	public static GeoRegion getGeoregion(MapView mapView, List<Point2D> pts) {
		int count = pts.size();
		if (count >= 2) {
			Point2D[] points = new Point2D[count];
			for (int i = 0; i < count; i++) {
				Point2D pt = pts.get(i);
				points[i] = mapView.getProjection().forward(pt);
			}
			// 构造面对象
			GeoRegion georegion = new GeoRegion();
			georegion.make(points);
			return georegion;
		}
		return null;
	}

	/**
	 * 获取任意点到当前点的距离
	 * 
	 * @return
	 */
	public static double getDistance(Point2D pt2d) {
		Dataset dt = MapHelper.getDataset(EApplication.getInstance()
				.getWorkspace(), SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
		if (dt == null) {
			return -1;
		}
		Point2D[] points = new Point2D[2];
		points[0] = pt2d;
//		BDLocation location = EApplication.getInstance().getCurrentLoction();
//		if (location != null) {
//			//当前位置可以获取
//			points[1] = new Point2D(location.getLongitude(),
//					location.getLatitude());
		NaviGPS naviGPS=EApplication.getInstance().naviGPS;
		if(naviGPS!=null){
			if(naviGPS.dLongitude==0||naviGPS.dLatitude==0){
				return -1;
			}
			Log.v("gis",naviGPS.dLongitude+" naviGPS!=null"+naviGPS.dLatitude);
			points[1] = new Point2D(naviGPS.dLongitude,
					naviGPS.dLatitude);
		}
//		}else{
//			//当前位置不可用获取则使用上次定位的位置
//			double lon=GPS.LON;
//			double lat=GPS.LAT;
//			if(EApplication.getInstance().acache.getAsString(SysConstant.CACHE_RELATE.LON)!=null&&
//					!EApplication.getInstance().acache.getAsString(SysConstant.CACHE_RELATE.LON).equals("")){
//				lon=Double.parseDouble(EApplication.getInstance().acache.getAsString(SysConstant.CACHE_RELATE.LON));
//			}
//			if(EApplication.getInstance().acache.getAsString(SysConstant.CACHE_RELATE.LAT)!=null&&
//					!EApplication.getInstance().acache.getAsString(SysConstant.CACHE_RELATE.LAT).equals("")){
//				lat=Double.parseDouble(EApplication.getInstance().acache.getAsString(SysConstant.CACHE_RELATE.LAT));
//			}
//			
//			points[1] = new Point2D(lon,
//					lat);
//		}
		for (int i = 0; i < 2; i++) {
			points[i] = dt.getPrjCoordSys().forward(points[i]);
		}
		// 构造线对象
		GeoLine geoline = new GeoLine();
		geoline.make(points);
		return geoline.getLength();
	}
	/**
	 * 获取任意点到当前点的距离
	 * 
	 * @return
	 */
	public static double getPointsDistance(Point2D pt2dOne,Point2D pt2dTwo){
		Dataset dt = MapHelper.getDataset(EApplication.getInstance()
				.getWorkspace(), SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
		if (dt == null) {
			return 0;
		}
		Point2D[] points = new Point2D[2];
		points[0] = pt2dOne;
		points[1] = pt2dTwo;
		for (int i = 0; i < 2; i++) {
			points[i] = dt.getPrjCoordSys().forward(points[i]);
		}
		// 构造线对象
				GeoLine geoline = new GeoLine();
				geoline.make(points);
				return geoline.getLength();
	}
}
