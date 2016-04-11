package com.emapper.activity;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.android.gis.API;
import com.android.gis.DataSource;
import com.android.gis.Dataset;
import com.android.gis.Layer;
import com.android.gis.Style;
import com.android.gis.ThemeLabel;
import com.android.gis.Workspace;
import com.emapper.util.MapHelper;
import com.emapper.util.ResPathCenter;
import com.emapper.util.SysConstant;
import com.piespace.mapping.MapController;
import com.piespace.mapping.MapView;

public class MapInitHelper {

	private MapView mapView;
	private MapController controller;
	private EApplication application;
	private Context context;

//	public static String BASE_MAP_NAME = "";

	public MapInitHelper(Context context, MapView mapView) {
		this.application = EApplication.getInstance();
		this.context = context;
		this.mapView = mapView;
		controller = mapView.getController();
	}
	
	public MapInitHelper(Context context){
		this.application = EApplication.getInstance();
		this.context = context;
	}
	

	void onCreate() {
		init();
	}
	
//	void workspaceInit(){
//		workspace = new Workspace();
////		ResPathCenter pathCenter = application.resPathCenter;
////		String path = pathCenter.getWorkspaceFolderPath();
////		Workspace.SetWorkPath(path);
////		API.init(context, path);
//		String workspacePath = application.resPathCenter.getWorkspacePath();
//		boolean isOpen = workspace.open(workspacePath);
//		Log.v("gis", isOpen + "wwwwwwwwwwwwww" + workspacePath);
//		if (isOpen) {
//			application.setWorkspace(workspace);
//			}	
//		}

	void onResume() {

	}
	


	void onDestory() {
		if (controller != null) {
			controller.closeBasemapFromWorkspace();
			controller.closeWorkspace(application.getWorkspace());
		}
		
		controller = null;
		mapView = null;
//		BASE_MAP_NAME = "";
	}

	private void init() {
		if(!application.isWskOpened()){
			application.OpenWorkspace();
		}
		controller.attachWorkspace(application.getWorkspace());
		
		OpenMap();
		initSettings();
		controller.setRotate(false);
		controller.setShove(false);
//		initMinZoom(5);
		if(mapView!=null){
			mapView.viewEntire();//全图显示
			/** 禁用点避让 */
			mapView.setCollision(false);
		}
	}

	/** 切换工作空间 **/
//	public void switchWorkSpace(){
//		if (controller != null) {
//			controller.closeBasemapFromWorkspace();
//			controller.closeWorkspace(ap);
//
//		}
//		init();
//	}
	
	// 设置最小显示比例尺
	public void initMinZoom(int zoomLevel) {
		double[] scaleLevels = mapView.getZoomLevels();
		if (scaleLevels != null) {
			int scale = scaleLevels.length > 5 ? scaleLevels.length - 5
					: scaleLevels.length;
//			controller.setMinScale(scaleLevels[zoomLevel]);
			controller.setZoom(scaleLevels[scale]);

		}
	}
	
	private boolean OpenMap() {

////		if(application.isFirst){
////			application.isFirst=false;
////			controller.attachWorkspace(application.getWorkspace());//加载工作空间
////			Log.v("gis", application.isFirst+"application.isFirst"+workspace+"workspace");
////		}
//		if(application.getWorkspace()==null){
////			ResPathCenter pathCenter = application.resPathCenter;
////			workspace = new Workspace();
////			String path = pathCenter.getWorkspaceFolderPath();
////			Workspace.SetWorkPath(path);
////			
//			String workspacePath = application.resPathCenter.getWorkspacePath();
//			Log.v("gis", workspacePath+"----------");
//			boolean isOpen = workspace.open(workspacePath);
//			if (isOpen) {
//				Log.v("gis", isOpen+"isOpen"+workspace+"343433434");
//				controller.attachWorkspace(workspace);
//
//				//打开google地图
//				String baseMap=workspace.getMapNameAt(0);
//
//				//打开基础地图
//				//controller.openBasemapFromWorkspace(baseMap);
//				controller.closeBasemapFromWorkspace();
//				boolean isOk =controller.openBasemapFromWorkspace(baseMap);
//				if(isOk){
//					
//				}
//				application.setWorkspace(workspace);
//				tempUse();
//			}
//		}else{
//			this.workspace=application.getWorkspace();
//
//
//			String baseMap = workspace.getMapNameAt(0);
//
//	
//			//打开地图之前，需要先关闭地图，以防多次打开地图。
//			controller.closeBasemapFromWorkspace();
//			boolean isOk =controller.openBasemapFromWorkspace(baseMap);
//			tempUse();
//			Log.v("gis", isOk+"isOk"+workspace+"workspace");
//		}
			
		if (!application.isWskOpened()) {
			return false;
		}

		String strMap = application.getWorkspace().getMapNameAt(0);

		// *************特别注意*******************
		// 打开地图之前，需要先关闭地图，以防多次打开地图。
		controller.closeBasemapFromWorkspace();
		return controller.openBasemapFromWorkspace(strMap);
	}

	public void openVectorMap(String basemapName) {
		controller.closeBasemapFromWorkspace();
		boolean flag = controller.openBasemapFromWorkspace(basemapName);
		mapView.invalidate();

		tempUse();
	}

	public void tempUse() {				
		API.LS_MW_SetAutoProjection(true);
		if(!application.isWskOpened()){
			return;
		}

		DataSource dataSource = application.getWorkspace()
				.GetDataSource(SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME);
		if (dataSource != null) {
			int dtCount = dataSource.GetDatasetCount();
			for (int i = dtCount - 1; i >= 0; i--) {

				Dataset dt = dataSource.GetDatasetAt(i);

				if (dt != null) {
					int dtType = dt.getType();
					String nameString = dt.getName();
//					Log.v("gis",nameString+"----------");
//					if(!"groundRegion".equals(nameString) || !"borderPt".equals(nameString) || !"markPt".equals(nameString)){
//						dataSource.DeleteDataset(nameString);
//					}
					
					if (dtType == 1) {
						controller.addLayerToBasemap(dt, 4,
								SysConstant.MAP_DATA_RELATE.POINT_LAYER_SET);
					} else if (dtType == 3) {
						// Toast.makeText(context,"GeoLine",Toast.LENGTH_LONG).show();
					} else if (dtType == 5) {
						controller.addLayerToBasemap(dt, 2,
								SysConstant.MAP_DATA_RELATE.REGION_LAYER_SET);
					} else if (dtType == 15) {
						controller.addLayerToBasemap(dt, 2,
								SysConstant.MAP_DATA_RELATE.REGION_LAYER_SET);
					}
				}

			}
		}
		
		/**
		 * Dataset dataset0 = dataSource.GetDatasetAt(0); Dataset dataset1 =
		 * dataSource.GetDatasetAt(1); Dataset dataset2 =
		 * dataSource.GetDatasetAt(2);
		 * 
		 * 
		 * String name1 = dataset0.getName(); String name2 = dataset1.getName();
		 * String name3 = dataset2.getName();
		 * controller.addLayerToBasemap(dataset2, 2, "RegionLayerSet");
		 * 
		 * // LayerSet layerSet = controller.addLayerToBasemap(dataset0, 4,
		 * "PointLayerSet"); LayerSet layerSet =
		 * controller.addLayerToBasemap(dataset0, 4, "PointLayerSet");
		 * controller.addLayerToBasemap(dataset1, 4, "PointLayerSet"); Layer
		 * layer0 = layerSet.getLayerAt(0);
		 **/

		double[] scales = mapView.getZoomLevels();
		int levelDiff = 5;

		// 设置border layer样式
		Layer borderPtLyr = MapHelper.getLayer(mapView,
				SysConstant.MAP_DATA_RELATE.BORDERPT_DT_NAME + "@"
						+ SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME);
		if (borderPtLyr != null) {
			Style style = new Style();
			style.markerStyle = 400;
			style.markerSize = /* 32 */48;
			style.markerType = 1;
			borderPtLyr.setStyle(style);
			if (scales != null && scales.length > 0) {
				int len = scales.length;
				if (len > levelDiff) {
					borderPtLyr.setVisibleScaleMin(scales[len - levelDiff]);
				}
				borderPtLyr.setVisibleScaleMax(scales[len - 1]);
			}
		}

		// 设置marker layer的样式
		Layer markerPtLyr = MapHelper.getLayer(mapView,
				SysConstant.MAP_DATA_RELATE.MARKPT_DT_NAME + "@"
						+ SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME);
		if (markerPtLyr != null) {
			Style style = new Style();
			style.markerStyle = 403;
			style.markerSize = /* 32 */48;
			style.markerType = 1;
			markerPtLyr.setStyle(style);
			if (scales != null && scales.length > 0) {
				int len = scales.length;
				if (len > levelDiff) {
					markerPtLyr.setVisibleScaleMin(scales[len - levelDiff]);
				}
				markerPtLyr.setVisibleScaleMax(scales[len - 1]);
			}
		}

		// 设置地块面风格
		Layer groundLyrLayer = MapHelper.getLayer(mapView,
				SysConstant.MAP_DATA_RELATE.GROUNDREGION_DT_NAME + "@"
						+ SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME);
		if (groundLyrLayer != null) {
			Style style = new Style();
			style.fillForeColor = Color.argb(100, 104, 112, 132);
			/** 设置图层颜色与地块边界宽度 lijun.xu */
			style.lineColor = Color.argb(255, 0, 0, 200);
			style.lineWidth = 3;
			groundLyrLayer.setStyle(style);
		}

		/** 设置地块显示的专题图 lijun.xu */
		if (dataSource != null) {
			//防治数据集顺序调整，导致索引获取的有问题，获取建议根据数据集名称获取
			//Dataset dt = dataSource.GetDatasetAt(2);
			Dataset dt = MapHelper.getDataset(application.getWorkspace(), SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME, SysConstant.MAP_DATA_RELATE.GROUNDREGION_DT_NAME);

			ThemeLabel theme = new ThemeLabel();
			boolean make = theme.makeDefault(dt,
					SysConstant.MAP_DATA_RELATE.GROUNDNAME_FIELD_NAME);
			// 构造成功
			if (make) {
				// 添加专题图地块名称地图
				controller.addThemeLayerToBasemap(dt, 4,
						SysConstant.MAP_DATA_RELATE.THEME_LAYER_SET, theme);
			}
		}
		/** 设置边界点专题图名称显示 lijun.xu */
//		if (dataSource != null) {
//			//防治数据集顺序调整，导致索引获取的有问题，获取建议根据数据集名称获取
//			//Dataset dt = dataSource.GetDatasetAt(0);
//			Dataset dt = MapHelper.getDataset(workspace, SysConstant.MAP_DATA_RELATE.DATASOURCE_NAME, SysConstant.MAP_DATA_RELATE.BORDERPT_DT_NAME);
//
//			ThemeLabel theme = new ThemeLabel();
//			boolean make = theme
//					.makeDefault(dt,
//							SysConstant.MAP_DATA_RELATE.BORDERPTNAME_FIELD_NAME/* "SMID" */);
//			// 构造成功
//			if (make) {
//				// 添加边界点专题图
//				controller.addThemeLayerToBasemap(dt, 4,
//						SysConstant.MAP_DATA_RELATE.THEME_LAYER_SET, theme);
//			}
//		}

		/**
		 * Style style = new Style(); style.markerStyle = 1; style.markerSize =
		 * 32; style.markerType = 1; layer0.setStyle(style); Layer layer1 =
		 * layerSet.getLayerAt(1); layer1.setStyle(style);
		 * 
		 * 
		 * //mapView.forceInvalidate();
		 * 
		 * 
		 * Projection Prj = mapView.getProjection(); GeoRegion GeoR = new
		 * GeoRegion(); Point2D[] region=new Point2D[4]; Point2D region1=new
		 * Point2D(); region1.x = 110.28; region1.y = 39.96; region[0]=region1;
		 * Point2D region2=new Point2D(); region2.x = 117.30; region2.y = 39.96;
		 * region[1]=region2; Point2D region3=new Point2D(); region3.x = 119.30;
		 * region3.y = 39.94; region[2]=region3; Point2D region4=new Point2D();
		 * region4.x = 116.28; region4.y = 39.94; region[3]=region4; GeoR =
		 * GeoR.make(region);
		 * 
		 * FieldBlockEntity entity = new FieldBlockEntity(GeoR, "", "", "");
		 * MapHelper.addBlock(workspace, entity);
		 * 
		 * GeoPoint geoPt = new GeoPoint(112.333, 24.34); BorderPtEntity entity2
		 * = new BorderPtEntity(geoPt, "", 23.34);
		 * MapHelper.addBorderPt(workspace, entity2);
		 * 
		 * 
		 * //test
		 * 
		 * BlockTrackingLyrManager blockTrackingLyrManager =
		 * BlockTrackingLyrManager.getInstance(mapView); Point2D pt1 = new
		 * Point2D(112.333, 23.34); //pt1 = Prj.forward(pt1);
		 * 
		 * Point2D pt2 = new Point2D(114.333, 23.34); //pt2 = Prj.forward(pt2);
		 * 
		 * Point2D pt3 = new Point2D(114.333, 25.34); //pt3 = Prj.forward(pt3);
		 * 
		 * 
		 * TrackingLyrPointEntity ptEntity = new
		 * TrackingLyrPointEntity(pt1.x,pt1.y, true);
		 * ArrayList<TrackingLyrPointEntity> list = new
		 * ArrayList<TrackingLyrPointEntity>(); list.add(ptEntity); ptEntity =
		 * new TrackingLyrPointEntity(pt2.x,pt2.y, false); list.add(ptEntity);
		 * ptEntity = new TrackingLyrPointEntity(pt3.x,pt3.y, false);
		 * list.add(ptEntity);
		 * 
		 * blockTrackingLyrManager.updateBlock(list);
		 **/

		/**
		 * pt1 = new Point2D(110, 0);
		 * 
		 * BorderPtTrackingLyrManager borderPtTrackingLyrManager = new
		 * BorderPtTrackingLyrManager(mapView);
		 * borderPtTrackingLyrManager.updateBorderPt(new
		 * TrackingLyrPointEntity(pt1.x,pt1.y, true));
		 * 
		 * pt2 = new Point2D(112, 0); MarkPtTrackingLyrManager
		 * markPtTrackingLyrManager = new MarkPtTrackingLyrManager(mapView);
		 * markPtTrackingLyrManager.updateBorderPt(new
		 * TrackingLyrPointEntity(pt2.x,pt2.y, true));
		 * //blockTrackingLyrManager.clear();
		 **/

//		 MapHelper.clearRecordset(workspace,
//		 SysConstant.MAP_DATA_RELATE.GROUNDREGION_DT_NAME);
//		 MapHelper.clearRecordset(workspace,
//		 SysConstant.MAP_DATA_RELATE.BORDERPT_DT_NAME);
//		 MapHelper.clearRecordset(workspace,
//		 SysConstant.MAP_DATA_RELATE.MARKPT_DT_NAME);
		
		
		
		
		// ////////////////////////////////

		API.LS_MW_ViewEntire();

	}

	/** 初始化设置 **/
	private void initSettings() {

	}

//	public Workspace getWorkspace() {
//		return this.workspace;
//	}
}
