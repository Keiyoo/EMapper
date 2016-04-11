package com.emapper.util;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.android.gis.API;
import com.android.gis.DataSource;
import com.android.gis.Dataset;
import com.android.gis.DatasetVector;
import com.android.gis.GeoLine;
import com.android.gis.GeoPoint;
import com.android.gis.GeoRegion;
import com.android.gis.Layer;
import com.android.gis.LayerSet;
import com.android.gis.Point2D;
import com.android.gis.PrjCoordSys;
import com.android.gis.Recordset;
import com.android.gis.Workspace;
import com.emapper.activity.EApplication;
import com.emapper.entity.MeasureAreaEntity;
import com.emapper.entity.MeasureLineEntity;
import com.emapper.entity.ShippingLineEntity;
import com.emapper.entity.ShippingPointEntity;
import com.emapper.entity.TrailEntity;
import com.piespace.mapping.MapView;
import com.piespace.mapping.Projection;

public class MapHelper {
	public interface OperateItemClickListener {
		void onItemClick(String itemId);
	}

	/**
	 * 获取图层对象
	 * **/
	public static Layer getLayer(MapView mapView, String layerName) {
		Layer layer = null;
		int count = mapView.getLayerSetCount();
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				LayerSet layerSet = mapView.getLayerSet(i);
				layer = layerSet.getLayer(layerName);
				if (layer != null) {
					return layer;
				}
			}
		}
		return layer;
	}

	/**
	 * 获取数据集对象 dsName 数据源名称 dtName 数据集名称
	 * **/
	public static Dataset getDataset(Workspace workSpace, String dsName,
			String dtName) {
		Dataset dt = null;
		DataSource ds = workSpace.GetDataSource(dsName);
		if (ds != null) {
			dt = ds.GetDataset(dtName);
		}
		return dt;
	}

	/**
	 * 判断一个数据源中该名字的数据集是否存在
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param dsName
	 *            数据源名称
	 * @param dtName
	 *            数据集名称
	 * @return
	 */
	public static boolean isDatasetExist(Workspace workSpace, String dsName,
			String dtName) {
		boolean isExist = false;
		return isExist;
	}

	/**
	 * 根据传入的几何对象唯一标示ID和所在的数据集名称，在数据集中删除该几何对象
	 * 
	 * @param geometryId
	 *            几何对象唯一标示
	 * @param dtName
	 *            数据集名称
	 */
	public static boolean delete(Workspace workSpace, Integer geoId,
			String dtName) {
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME, dtName);
		if (dt == null) {
			return false;
		}
		DatasetVector dVector = (DatasetVector) dt;
		int[] ids = { geoId };
		Recordset recordset = dVector.QueryByID(ids);
		if (recordset == null) {
			return false;
		}
		return recordset.delete();
	}

	/**
	 * 临时添加删除数据集中所有几何对象
	 * 
	 * @param workSpace
	 * @param dtName
	 *            要清清除的数据集名称
	 * @return
	 */
	public static boolean clearRecordset(Workspace workSpace, String dtName) {
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME, dtName);
		if (dt == null) {
			return false;
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral("");
		int nCount = recordset.getRecordCount();
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			recordset.delete();
			// recordset.moveNext();
		}
		return true;
	}

	/**
	 * 经纬度点转跟踪层一个点图形
	 * 
	 * @param lon
	 * @param lat
	 * @return
	 */
	public static GeoPoint pointToGeoPoint(MapView mapView, double lon,
			double lat) {
		Projection proj = mapView.getProjection();
		Point2D mapPnt = proj.forward(new Point2D(lon, lat));
		GeoPoint geoP = new GeoPoint();
		geoP.make(mapPnt.x, mapPnt.y);
		return geoP;
	}

	public static Point2D mapPntToWGS84LonLat(Point2D mapPnt) {
		Workspace workspace = EApplication.getInstance().getWorkspace();
		Dataset dt = MapHelper.getDataset(workspace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.BORDERPT_DT_NAME);
		// 把几何对象从地图坐标转换为数据集坐标系
		PrjCoordSys projSysDataSet = dt.getPrjCoordSys();
		// 设置 数据集的投影为源投影
		API.LS_MW_SetPrjCoordSysSrc(projSysDataSet.getHandle());
		GeoPoint geoPoint = new GeoPoint();
		geoPoint.make(mapPnt.x, mapPnt.y);
		// false 表示 从目标投影转到 源投影
		API.LS_MW_PrjConvert(geoPoint.getHandle(), false);
		Point2D lonLat = geoPoint.getPoint();
		return lonLat;
	}

	/**
	 * 删除一条记录
	 * 
	 * @param workSpace
	 * @param id
	 * @return
	 */
	public static boolean deleteRecord(Workspace workSpace, int id,
			String dsname) {
		boolean isdelete = false;
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME, dsname);
		if (dt == null) {
			return false;
		}
		DatasetVector dVector = (DatasetVector) dt;
		int[] ids = { id };
		Recordset recordset = dVector.QueryByID(ids);
		if (recordset == null) {
			return false;
		}
		isdelete = recordset.delete();
		dVector.ReleaseRecordset(recordset);
		return isdelete;
	}

	/**
	 * 获取数据集中最后一个点的ID
	 * 
	 * @param workSpace
	 * @return
	 */
	public static int getLastRecordID(Workspace workSpace, String dsname) {
		int id = 0;
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME, dsname);
		if (dt == null) {
			return 0;
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		int count = recordset.getRecordCount();
		boolean ismove = recordset.moveTo(count - 1);
		if (ismove) {
			id = recordset.getID();
		}
		dVector.ReleaseRecordset(recordset);
		return id;
	}

	/**
	 * 清空所有记录
	 * 
	 * @param workSpace
	 * @return
	 */
	public static boolean clearRecord(Workspace workSpace, String dsname) {
		boolean isclear = false;
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME, dsname);
		if (dt == null) {
			return false;
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			isclear = recordset.delete();
		}
		dVector.ReleaseRecordset(recordset);
		return isclear;
	}

	/** 航点管理 **/

	/**
	 * 传入一个航点对象，添加到数据集中
	 * 
	 * @param entity
	 * @return
	 */
	public static int addShippingPt(Workspace workSpace,
			ShippingPointEntity entity) {
		if (entity == null) {
			return 0;
		}
		GeoPoint geopoint = new GeoPoint();
		geopoint.make(entity.lon, entity.lat);

		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINPT_DT_NAME);
		if (dt == null) {
			return 0;
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		int ngeoId = recordset.addNew(geopoint);
		if (ngeoId == 0) {
			return 0;
		}
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTNAME_FIELD_NAME,
				entity.name);
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTHEIGHT_FIELD_NAME,
				entity.high);
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTPRECISION_FIELD_NAME,
				entity.precision);
		recordset.setFieldValueBool(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTIMAGE_FIELD_ISUSERED,
				entity.isUsered);
		// 设置时间——当前时间设置的为long,后面改成String
//		recordset.setFieldValueString(
//				SysConstant.MAP_DATA_RELATE.BORDERPTNAME_FIELD_NAME,
//				entity.time+"");
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTBAK_FIELD_NAME,
				entity.remark);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTIMAGE_FIELD_NAME,
				entity.image);
		ngeoId = recordset.update();
		dVector.ReleaseRecordset(recordset);
		return ngeoId;
	}

	/**
	 * 编辑航点
	 * 
	 * @param geoId
	 *            记号点对象id
	 * @param entity
	 *            记号点实体对象
	 * @return
	 */
	public static boolean editShippingPt(Workspace workSpace,
			ShippingPointEntity entity) {
		if (entity == null) {
			return false;
		}
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINPT_DT_NAME);
		if (dt == null) {
			return false;
		}
		DatasetVector dVector = (DatasetVector) dt;
		int[] ids = { entity.smid };
		Recordset recordset = dVector.QueryByID(ids);
		if (recordset == null) {
			return false;
		}
		recordset.edit();
		GeoPoint geopoint = new GeoPoint();
		geopoint.make(entity.lon, entity.lat);
		recordset.setGeometry(geopoint);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTNAME_FIELD_NAME,
				entity.name);
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTHEIGHT_FIELD_NAME,
				entity.high);
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTPRECISION_FIELD_NAME,
				entity.precision);
		recordset.setFieldValueBool(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTIMAGE_FIELD_ISUSERED,
				entity.isUsered);
		// 设置时间——当前时间设置的为long,后面改成String
		// recordset.setFieldValueInt(
		// SysConstant.MAP_DATA_RELATE.BORDERPTNAME_FIELD_NAME,
		// entity.time);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTBAK_FIELD_NAME,
				entity.remark);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGPTIMAGE_FIELD_NAME,
				entity.image);
		int ngeoId = recordset.update();
		if (ngeoId < 0) {
			return false;
		}
		dVector.ReleaseRecordset(recordset);
		return true;
	}

	/**
	 * 根据关键字查询航点信息
	 * 
	 * @param workSpace
	 * @param keyword
	 * @return
	 */
	public static List<ShippingPointEntity> queryShippingPts(
			Workspace workSpace, String keyword) {
		List<ShippingPointEntity> entitys = new ArrayList<ShippingPointEntity>();
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINPT_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		String filter = SysConstant.MAP_DATA_RELATE.SHIPPINGPTNAME_FIELD_NAME
				+ " like '%" + keyword + "%'";
		Recordset recordset = dVector.QueryByGeneral(filter);
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			ShippingPointEntity entity = new ShippingPointEntity();
			// 获取存储的属性信息
			String name = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGPTNAME_FIELD_NAME);
			double height = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGPTHEIGHT_FIELD_NAME);
			double precision = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGPTPRECISION_FIELD_NAME);
			String image = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGPTIMAGE_FIELD_NAME);
			String remark = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGPTBAK_FIELD_NAME);
			int smid = recordset.getID();
			// 获取空间信息
			GeoPoint gp = (GeoPoint) recordset.getGeometry();
			entity.smid = smid;
			entity.lon = gp.getPoint().x;
			entity.lat = gp.getPoint().y;
			entity.name = name;
			entity.high = height;
			entity.precision = precision;
			entity.image = image;
			entity.remark = remark;
			entitys.add(entity);
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return entitys;
	}

	/**
	 * 根据关键字查询航点信息
	 * 
	 * @param workSpace
	 * @param keyword
	 * @return
	 */
	public static ShippingPointEntity queryShippingPtByID(Workspace workSpace,
			String keyword) {
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINPT_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		String filter = SysConstant.MAP_DATA_RELATE.SMID + "=" + keyword;
		Recordset recordset = dVector.QueryByGeneral(filter);
		recordset.moveFirst();
		ShippingPointEntity entity = new ShippingPointEntity();

		while (!recordset.isEOF()) {
			// 获取存储的属性信息
			String name = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGPTNAME_FIELD_NAME);
			double height = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGPTHEIGHT_FIELD_NAME);
			double precision = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGPTPRECISION_FIELD_NAME);
			String image = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGPTIMAGE_FIELD_NAME);
			String remark = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGPTBAK_FIELD_NAME);
			int smid = recordset.getID();
			// 获取空间信息
			GeoPoint gp = (GeoPoint) recordset.getGeometry();
			entity.smid = smid;
			entity.lon = gp.getPoint().x;
			entity.lat = gp.getPoint().y;
			entity.name = name;
			entity.high = height;
			entity.precision = precision;
			entity.image = image;
			entity.remark = remark;
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return entity;
	}

	/**
	 * 清除所有航点
	 * 
	 * @param workSpace
	 * @return
	 */
	public static boolean clearShippingPt(Workspace workSpace) {
		return clearRecord(workSpace,
				SysConstant.MAP_DATA_RELATE.SHIPPINPT_DT_NAME);
	}

	/**
	 * 获取航点数据集中最后一个点的ID
	 * 
	 * @param workSpace
	 * @return
	 */
	public static int getLastShippingPtID(Workspace workSpace) {
		return getLastRecordID(workSpace,
				SysConstant.MAP_DATA_RELATE.SHIPPINPT_DT_NAME);
	}

	/**
	 * 删除航点
	 * 
	 * @param workSpace
	 * @param id
	 * @return
	 */
	public static boolean deleteShippingPt(Workspace workSpace, int id) {
		return deleteRecord(workSpace, id,
				SysConstant.MAP_DATA_RELATE.SHIPPINPT_DT_NAME);
	}

	/**
	 * 获取所有航点
	 * 
	 * @param workSpace
	 * @return
	 */
	public static List<ShippingPointEntity> getShippingPtList(
			Workspace workSpace) {

		List<ShippingPointEntity> entitys = new ArrayList<ShippingPointEntity>();
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINPT_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			ShippingPointEntity entity = new ShippingPointEntity();
			// 获取存储的属性信息
			String name = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGPTNAME_FIELD_NAME);
			double height = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGPTHEIGHT_FIELD_NAME);
			double precision = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGPTPRECISION_FIELD_NAME);
			String image = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGPTIMAGE_FIELD_NAME);
			String remark = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGPTBAK_FIELD_NAME);
			boolean isUsered = recordset
					.getFieldValueBool(SysConstant.MAP_DATA_RELATE.SHIPPINGPTIMAGE_FIELD_ISUSERED);
			int smid = recordset.getID();
			// 获取空间信息
			GeoPoint gp = (GeoPoint) recordset.getGeometry();
			entity.smid = smid;
			entity.lon = gp.getPoint().x;
			entity.lat = gp.getPoint().y;
			entity.name = name;
			entity.high = height;
			entity.precision = precision;
			entity.image = image;
			entity.remark = remark;
			entity.isUsered = isUsered;
			entitys.add(entity);
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return entitys;
	}

	/******** 航点管理结束 *********/

	/****** 航线管理开始 **********/

	/**
	 * 添加航线
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param entity
	 *            航线实体类
	 * @return
	 */
	public static int addShippingLine(Workspace workSpace,
			ShippingLineEntity entity) {
		if (entity == null) {
			return 0;
		}
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINLINE_DT_NAME);
		if (dt == null) {
			return 0;
		}

		int count = entity.list.size();
		if (count < 2) {
			return 0;
		}

		Point2D[] points = new Point2D[count];
		for (int i = 0; i < count; i++) {
			Point2D pt2d = new Point2D(entity.list.get(i).lon,
					entity.list.get(i).lat);
			points[i] = dt.getPrjCoordSys().forward(pt2d);
		}
		// 构造线对象
		GeoLine geoline = new GeoLine();
		geoline.make(points);

		entity.distance = geoline.getLength();
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		int ngeoId = recordset.addNew(geoline);
		if (ngeoId == 0) {
			return 0;
		}
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINENAME_FIELD_NAME,
				entity.name);
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINEDIS_FIELD_NAME,
				entity.distance);
		recordset.setFieldValueInt(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINEPCOUNT_FIELD_NAME,
				entity.num);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINEPTS_FIELD_NAME,
				entity.strpoints);
		recordset.setFieldValueInt(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINETYPE_FIELD_NAME,
				entity.linetype);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINECOLOR_FIELD_NAME,
				entity.color);
		ngeoId = recordset.update();
		return ngeoId;
	}

	/**
	 * 编辑航线
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param entity
	 *            航线实体类
	 * @return
	 */
	public static boolean editShippingLine(Workspace workSpace,
			ShippingLineEntity entity) {
		if (entity == null) {
			return false;
		}
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINLINE_DT_NAME);
		if (dt == null) {
			return false;
		}
		DatasetVector dVector = (DatasetVector) dt;
		int[] ids = { entity.smid };
		Recordset recordset = dVector.QueryByID(ids);
		if (recordset == null) {
			return false;
		}
		int count = entity.list.size();
		Point2D[] points = new Point2D[count];
		for (int i = 0; i < count; i++) {
			points[i] = new Point2D(entity.list.get(i).lon,
					entity.list.get(i).lat);
		}
		// 构造线对象
		GeoLine geoline = new GeoLine();
		geoline.make(points);

		entity.distance = geoline.getLength();
		recordset.edit();
		recordset.setGeometry(geoline);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINENAME_FIELD_NAME,
				entity.name);
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINEDIS_FIELD_NAME,
				entity.distance);
		recordset.setFieldValueInt(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINEPCOUNT_FIELD_NAME,
				entity.num);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINEPTS_FIELD_NAME,
				entity.strpoints);
		recordset.setFieldValueInt(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINETYPE_FIELD_NAME,
				entity.linetype);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGLINECOLOR_FIELD_NAME,
				entity.color);
		int ngeoId = recordset.update();
		if (ngeoId < 0) {
			return false;
		}
		dVector.ReleaseRecordset(recordset);
		return true;
	}

	/**
	 * 查询航线信息
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param keyword
	 *            关键字
	 * @return
	 */
	public static List<ShippingLineEntity> queryShippingLines(
			Workspace workSpace, String keyword) {
		List<ShippingLineEntity> entitys = new ArrayList<ShippingLineEntity>();
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINLINE_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		String filter = SysConstant.MAP_DATA_RELATE.SHIPPINGLINENAME_FIELD_NAME
				+ " like '%" + keyword + "%'";
		Recordset recordset = dVector.QueryByGeneral(filter);
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			ShippingLineEntity entity = new ShippingLineEntity();
			// 获取存储的属性信息
			String name = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGLINENAME_FIELD_NAME);
			double distance = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGLINEDIS_FIELD_NAME);
			int pcount = recordset
					.getFieldValueInt(SysConstant.MAP_DATA_RELATE.SHIPPINGLINEPCOUNT_FIELD_NAME);
			String color = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGLINECOLOR_FIELD_NAME);
			int linetype = recordset
					.getFieldValueInt(SysConstant.MAP_DATA_RELATE.SHIPPINGLINETYPE_FIELD_NAME);
			String pts = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGLINEPTS_FIELD_NAME);
			int smid = recordset.getID();
			// 获取空间信息
			entity.smid = smid;
			entity.color = color;
			entity.num = pcount;
			entity.name = name;
			entity.strpoints = pts;
			entity.distance = distance;
			entity.linetype = linetype;
			entitys.add(entity);
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return entitys;
	}

	/**
	 * 清理航线
	 * 
	 * @param workSpace
	 * @return
	 */
	public static boolean clearShippingLine(Workspace workSpace) {
		return clearRecord(workSpace,
				SysConstant.MAP_DATA_RELATE.SHIPPINLINE_DT_NAME);
	}

	/**
	 * 获取航线数据集最后一条记录的id
	 * 
	 * @param workSpace
	 * @return
	 */
	public static int getLastShippingLineID(Workspace workSpace) {
		return getLastRecordID(workSpace,
				SysConstant.MAP_DATA_RELATE.SHIPPINLINE_DT_NAME);
	}

	/**
	 * 删除航线
	 * 
	 * @param workSpace
	 * @param id
	 * @return
	 */
	public static boolean deleteShippingLine(Workspace workSpace, int id) {
		return deleteRecord(workSpace, id,
				SysConstant.MAP_DATA_RELATE.SHIPPINLINE_DT_NAME);
	}

	/**
	 * 获取所有航线信息
	 * 
	 * @param workSpace
	 * @return
	 */
	public static List<ShippingLineEntity> getShippingLineList(
			Workspace workSpace) {
		List<ShippingLineEntity> entitys = new ArrayList<ShippingLineEntity>();
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINLINE_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			ShippingLineEntity entity = new ShippingLineEntity();
			// 获取存储的属性信息
			String name = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGLINENAME_FIELD_NAME);
			double distance = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGLINEDIS_FIELD_NAME);
			int pcount = recordset
					.getFieldValueInt(SysConstant.MAP_DATA_RELATE.SHIPPINGLINEPCOUNT_FIELD_NAME);
			String color = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGLINECOLOR_FIELD_NAME);
			int linetype = recordset
					.getFieldValueInt(SysConstant.MAP_DATA_RELATE.SHIPPINGLINETYPE_FIELD_NAME);
			String pts = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGLINEPTS_FIELD_NAME);
			int smid = recordset.getID();
			// 获取空间信息
			entity.smid = smid;
			entity.color = color;
			entity.num = pcount;
			entity.name = name;
			entity.strpoints = pts;
			entity.distance = distance;
			entity.linetype = linetype;
			entitys.add(entity);
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return entitys;
	}

	public static GeoLine getShippingLineGeo(Workspace workSpace, int keyword) {
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINLINE_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		String filter = SysConstant.MAP_DATA_RELATE.SMID + "=" + keyword;
		Recordset recordset = dVector.QueryByGeneral(filter);
		recordset.moveFirst();
		GeoLine geoline = null;
		while (!recordset.isEOF()) {
			// 获取空间信息
			geoline = (GeoLine) recordset.getGeometry();
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return geoline;
	}

	/****** 航线管理结束 **********/

	/****** 航迹管理 ************/

	/**
	 * 保存航迹线
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param entity
	 *            航迹线
	 * @return
	 */
	public static int addShippingTrail(Workspace workSpace, TrailEntity entity) {
		if (entity == null) {
			return 0;
		}

		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
		if (dt == null) {
			return 0;
		}

		int count = entity.list.size();
		if (count < 2) {
			return 0;
		}

		Point2D[] points = new Point2D[count];
		for (int i = 0; i < count; i++) {
			Point2D pt2d = new Point2D(entity.list.get(i).getLon(), entity.list
					.get(i).getLat());
			points[i] = dt.getPrjCoordSys().forward(pt2d);
		}
		// 构造线对象
		GeoLine geoline = new GeoLine();
		geoline.make(points);

		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		int ngeoId = recordset.addNew(geoline);
		if (ngeoId == 0) {
			return 0;
		}
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILNAME_FIELD_NAME,
				entity.getName());
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILDIS_FIELD_NAME,
				entity.getLength());
		recordset.setFieldValueInt(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILPCOUNT_FIELD_NAME,
				entity.icount);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILST_FIELD_NAME,
				String.valueOf(entity.getStarttime()));
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILET_FIELD_NAME,
				String.valueOf(entity.getEndtime()));
		ngeoId = recordset.update();
		return ngeoId;
	}

	/**
	 * 编辑航迹线
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param entity
	 *            轨迹线
	 * @return
	 */
	public static boolean editShippingTrail(Workspace workSpace,
			TrailEntity entity) {
		if (entity == null) {
			return false;
		}
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
		if (dt == null) {
			return false;
		}
		DatasetVector dVector = (DatasetVector) dt;
		int[] ids = { entity.getId() };
		Recordset recordset = dVector.QueryByID(ids);
		if (recordset == null) {
			return false;
		}
		int count = entity.list.size();
		Point2D[] points = new Point2D[count];
		for (int i = 0; i < count; i++) {
			points[i] = new Point2D(entity.list.get(i).getLon(), entity.list
					.get(i).getLat());
		}
		// 构造线对象
		GeoLine geoline = new GeoLine();
		geoline.make(points);
		recordset.edit();
		recordset.setGeometry(geoline);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILNAME_FIELD_NAME,
				entity.getName());
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILDIS_FIELD_NAME,
				entity.getLength());
		recordset.setFieldValueInt(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILPCOUNT_FIELD_NAME,
				entity.icount);
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILST_FIELD_NAME,
				String.valueOf(entity.getStarttime()));
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILET_FIELD_NAME,
				String.valueOf(entity.getEndtime()));
		int ngeoId = recordset.update();
		if (ngeoId < 0) {
			return false;
		}
		dVector.ReleaseRecordset(recordset);
		return true;
	}

	/**
	 * 查询航迹信息
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param keyword
	 *            关键字
	 * @return
	 */
	public static List<TrailEntity> queryShippingTrails(Workspace workSpace,
			String keyword) {
		List<TrailEntity> entitys = new ArrayList<TrailEntity>();
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
		if (dt == null) {
			return null;
		}

		DatasetVector dVector = (DatasetVector) dt;
		String filter = SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILNAME_FIELD_NAME
				+ " like '%" + keyword + "%'";
		Recordset recordset = dVector.QueryByGeneral(filter);
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			TrailEntity entity = new TrailEntity();
			// 获取存储的属性信息
			String name = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILNAME_FIELD_NAME);
			double distance = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILDIS_FIELD_NAME);
			int pcount = recordset
					.getFieldValueInt(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILPCOUNT_FIELD_NAME);
			String stime = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILST_FIELD_NAME);
			String etime = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILET_FIELD_NAME);

			int smid = recordset.getID();
			// 获取空间信息
			entity.setId(smid);
			entity.setName(name);
			entity.setLength(distance);
			entity.icount = pcount;
			if (!stime.equals("")) {
				entity.setStarttime(Long.parseLong(stime));
			}
			if (!etime.equals("")) {
				entity.setEndtime(Long.parseLong(etime));
			}
			entitys.add(entity);
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return entitys;
	}

	/**
	 * 清空航迹
	 * 
	 * @param workSpace
	 *            工作空间
	 * @return
	 */
	public static boolean clearShippingTrail(Workspace workSpace) {
		return clearRecord(workSpace,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
	}

	/**
	 * 获取航迹数据集最后一条记录的id
	 * 
	 * @param workSpace
	 *            工作空间
	 * @return
	 */
	public static int getLastShippingTrailID(Workspace workSpace) {
		return getLastRecordID(workSpace,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
	}

	/**
	 * 删除航迹
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param id
	 *            航迹ID
	 * @return
	 */
	public static boolean deleteShippingTrail(Workspace workSpace, int id) {
		return deleteRecord(workSpace, id,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
	}

	/**
	 * 获取所有航迹信息
	 * 
	 * @param workSpace
	 *            工作空间
	 * @return
	 */
	public static List<TrailEntity> getShippingTrailList(Workspace workSpace) {
		List<TrailEntity> entitys = new ArrayList<TrailEntity>();
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			TrailEntity entity = new TrailEntity();
			// 获取存储的属性信息
			String name = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILNAME_FIELD_NAME);
			double distance = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILDIS_FIELD_NAME);
			int pcount = recordset
					.getFieldValueInt(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILPCOUNT_FIELD_NAME);
			String stime = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILST_FIELD_NAME);
			String etime = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.SHIPPINGTRAILET_FIELD_NAME);

			int smid = recordset.getID();
			// 获取空间信息
			entity.setId(smid);
			entity.setName(name);
			entity.setLength(distance);
			entity.icount = pcount;
			if (!stime.equals("")) {
				entity.setStarttime(Long.parseLong(stime));
			}
			if (!etime.equals("")) {
				entity.setEndtime(Long.parseLong(etime));
			}
			entitys.add(entity);
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return entitys;
	}

	/**
	 * 获取航迹空间对象
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param keyword
	 *            关键字id
	 * @return
	 */
	public static GeoLine getShippingTrailGeo(Workspace workSpace, int keyword) {
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.SHIPPINGTRAIL_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		String filter = SysConstant.MAP_DATA_RELATE.SMID + "=" + keyword;
		Recordset recordset = dVector.QueryByGeneral(filter);
		recordset.moveFirst();
		GeoLine geoline = null;
		while (!recordset.isEOF()) {
			// 获取空间信息
			geoline = (GeoLine) recordset.getGeometry();
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return geoline;
	}

	/********* 航迹管理结束 **********/

	/********* 长度量算管理 **********/
	/**
	 * 添加量算线
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param entity
	 *            量算线实体类
	 * @return
	 */
	public static int addMeasureLine(Workspace workSpace,
			MeasureLineEntity entity) {
		if (entity == null) {
			Log.v("gis", entity +""+"entity == null");
			return 0;
		}
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.MEASURELINE_DT_NAME);
		if (dt == null) {
			Log.v("gis", dt +""+"dt == null");
			return 0;
		}

		// 构造线对象
		
		GeoLine geoline = entity.geoline;
		
		if (geoline == null || geoline.getPointCount() < 2) {
			return 0;
			
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		int ngeoId = recordset.addNew(geoline);
		if (ngeoId == 0) {
			return 0;
		}
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_NAME,entity.name);
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_DISTANCE,
				entity.disatance);
		ngeoId = recordset.update();
		return ngeoId;
	}

	/**
	 * 清理量算线
	 * 
	 * @param workSpace
	 * @return
	 */
	public static boolean clearMeasureLine(Workspace workSpace) {
		return clearRecord(workSpace,
				SysConstant.MAP_DATA_RELATE.MEASURELINE_DT_NAME);
	}

	/**
	 * 删除量算线
	 * 
	 * @param workSpace
	 * @param id
	 * @return
	 */
	public static boolean deleteMeasureLine(Workspace workSpace, int id) {
		return deleteRecord(workSpace, id,
				SysConstant.MAP_DATA_RELATE.MEASURELINE_DT_NAME);
	}

	/**
	 * 获取所有量算线信息
	 * 
	 * @param workSpace
	 * @return
	 */
	public static List<MeasureLineEntity> getMeasureLineList(Workspace workSpace) {
		List<MeasureLineEntity> entitys = new ArrayList<MeasureLineEntity>();
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.MEASURELINE_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			MeasureLineEntity entity = new MeasureLineEntity();
			// 获取存储的属性信息
			String name = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_NAME);
			double distance = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_DISTANCE);
			int smid = recordset.getID();
			// 获取空间信息
			entity.smid = smid;
			entity.name = name;
			entity.disatance = distance;
			// entity.geoline=(GeoLine)recordset.getGeometry();
			entitys.add(entity);
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return entitys;
	}

	/**
	 * 获取量算线对象
	 * 
	 * @param workSpace
	 * @param keyword
	 * @return
	 */
	public static GeoLine getMeasureLineGeo(Workspace workSpace, int keyword) {
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.MEASURELINE_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		String filter = SysConstant.MAP_DATA_RELATE.SMID + "=" + keyword;
		Recordset recordset = dVector.QueryByGeneral(filter);
		recordset.moveFirst();
		GeoLine geoline = null;
		while (!recordset.isEOF()) {
			// 获取空间信息
			geoline = (GeoLine) recordset.getGeometry();
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return geoline;
	}

	/********* 长度量算管理结束 **********/

	/********* 面积量算管理 **********/
	/**
	 * 添加量算面
	 * 
	 * @param workSpace
	 *            工作空间
	 * @param entity
	 *            量算面实体类
	 * @return
	 */
	public static int addMeasureRegion(Workspace workSpace,
			MeasureAreaEntity entity) {
		if (entity == null) {
			return 0;
		}
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.MEASUREAREA_DT_NAME);
		if (dt == null) {
			return 0;
		}

		// 构造面对象
		GeoRegion georegion = entity.georegion;

		if (georegion == null || georegion.getPointCount() < 3) {
			return 0;
		}

		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		int ngeoId = recordset.addNew(georegion);
		if (ngeoId == 0) {
			return 0;
		}
		recordset.setFieldValueString(
				SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_NAME, entity.name);
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_DISTANCE,
				entity.disatance);
		recordset.setFieldValueDouble(
				SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_AREA, entity.area);
		ngeoId = recordset.update();
		return ngeoId;
	}

	/**
	 * 清理量算面
	 * 
	 * @param workSpace
	 * @return
	 */
	public static boolean clearMeasureRegion(Workspace workSpace) {
		return clearRecord(workSpace,
				SysConstant.MAP_DATA_RELATE.MEASUREAREA_DT_NAME);
	}

	/**
	 * 删除量算面
	 * 
	 * @param workSpace
	 * @param id
	 * @return
	 */
	public static boolean deleteMeasureRegion(Workspace workSpace, int id) {
		return deleteRecord(workSpace, id,
				SysConstant.MAP_DATA_RELATE.MEASUREAREA_DT_NAME);
	}

	/**
	 * 获取所有量算面信息
	 * 
	 * @param workSpace
	 * @return
	 */
	public static List<MeasureAreaEntity> getMeasureRegionList(
			Workspace workSpace) {
		List<MeasureAreaEntity> entitys = new ArrayList<MeasureAreaEntity>();
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.MEASUREAREA_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		Recordset recordset = dVector.QueryByGeneral(null);
		recordset.moveFirst();
		while (!recordset.isEOF()) {
			MeasureAreaEntity entity = new MeasureAreaEntity();
			// 获取存储的属性信息
			String name = recordset
					.getFieldValueString(SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_NAME);
			double distance = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_DISTANCE);
			double area = recordset
					.getFieldValueDouble(SysConstant.MAP_DATA_RELATE.MEASURE_FIELD_AREA);
			int smid = recordset.getID();
			// 获取空间信息
			entity.smid = smid;
			entity.name = name;
			entity.area = area;
			entity.disatance = distance;
			entitys.add(entity);
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return entitys;
	}

	/**
	 * 获取量算面对象
	 * 
	 * @param workSpace
	 * @param keyword
	 * @return
	 */
	public static GeoRegion getMeasureRegionGeo(Workspace workSpace, int keyword) {
		Dataset dt = getDataset(workSpace,
				SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME,
				SysConstant.MAP_DATA_RELATE.MEASUREAREA_DT_NAME);
		if (dt == null) {
			return null;
		}
		DatasetVector dVector = (DatasetVector) dt;
		String filter = SysConstant.MAP_DATA_RELATE.SMID + "=" + keyword;
		Recordset recordset = dVector.QueryByGeneral(filter);
		recordset.moveFirst();
		GeoRegion georegion = null;
		while (!recordset.isEOF()) {
			// 获取空间信息
			georegion = (GeoRegion) recordset.getGeometry();
			recordset.moveNext();
		}
		dVector.ReleaseRecordset(recordset);
		return georegion;
	}

	/********* 面积量算管理结束 **********/

}
