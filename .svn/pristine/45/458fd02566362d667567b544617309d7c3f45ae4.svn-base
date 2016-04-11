package com.emapper.util;

public interface SysConstant {

	public static final int RESULT_CAPTURE_IMAGE = 1;// 照相的requestCode

	/** popupwindow点击的item项相关 **/
	interface POPUP_RELATE {
		String OPEN_PROJECT = "10";// 打开工程
		String DATA_CONN = "11";// 数据关联
		String HUKOU_SET = "12";// 户籍表设置
		String COORDINATE_SET = "13";// 坐标系设置
		String DATA_OUTPUT = "14";// 数据导出
		String OUT = "15";// 退出程序

		String LOCAL_MAP = "Test";// 本地底图
		String GOOGLE_MAP = "googleimage";// google影像
	}

	interface GPS {
		String LOCATE_AK = "F16abc419f6f6b3c914b93d4fb4c1b72";
		double LON = 116.3975;// 默认经度
		double LAT = 39.9082;// 默认纬度
		double SCALELEVEL = 1.0255396658559988E-6;// 默认地图显示比例尺
	}

	/**
	 * 数据源 数据集名称 数据集字段名称
	 * **/
	interface MAP_DATA_RELATE {
		/** 数据源名称 **/
		String DATASOURCE_NAME = "China400";

		/** 点类型图层集合名称 **/
		String POINT_LAYER_SET = "PointLayerSet";

		/** 线类型图层集合名称 **/
		String LINE_LAYER_SET = "LineLayerSet";
		/** 面类型图层集合名称 **/
		String REGION_LAYER_SET = "RegionLayerSet";
		/**
		 * 专题图层集合名称
		 */
		String THEME_LAYER_SET = "ThemeLayerSet";

		/** 地块数据集名称 **/
		String GROUNDREGION_DT_NAME = "groundRegion";

		String BORDERLINE_DT_NAME = "borderLine";

		/** 边界点数据集名称 **/
		String BORDERPT_DT_NAME = "borderPt";
		/** 记号点数据集名称 **/
		String MARKPT_DT_NAME = "markPt";

		/** 航点数据集名称 **/
		String SHIPPINPT_DT_NAME = "ShippingPt";
		/** 航线数据集名称 **/
		String SHIPPINLINE_DT_NAME = "ShippingLine";
		/** 航迹数据集名称 **/
		String SHIPPINGTRAIL_DT_NAME = "TrailLine";
		/** 量算线数据集名称 **/
		String MEASURELINE_DT_NAME = "MeasureLine";
		/** 量算面数据集名称 **/
		String MEASUREAREA_DT_NAME = "MeasureArea";
		// 地块名称字段
		String GROUNDNAME_FIELD_NAME = "GroundName";
		// 地块编码字段
		String GROUNDCODE_FIELD_NAME = "GroundCode";
		// 地块归属字段
		String GROUNDOWNER_FIELD_NAME = "GroundOwner";

		// 边界点名称字段
		String BORDERPTNAME_FIELD_NAME = "PtName";
		// 边界点高程字段
		String BORDERPTELEVATION_FIELD_NAME = "PtElevation";

		// 记号点名称字段
		String MARKPTNAME_FIELD_NAME = "PtName";

		// 边界线对应的

		// 航点名称
		String SHIPPINGPTNAME_FIELD_NAME = "PtName";
		// 航点高度
		String SHIPPINGPTHEIGHT_FIELD_NAME = "PtHight";
		// 航点经度
		String SHIPPINGPTPRECISION_FIELD_NAME = "PtPrecision";
		// 航点时间
		String SHIPPINGPTTIME_FIELD_NAME = "PtTime";
		// 航点备注
		String SHIPPINGPTBAK_FIELD_NAME = "PtBak";
		// 航点图片
		String SHIPPINGPTIMAGE_FIELD_NAME = "PtImage";

		// 航点是否被用
		String SHIPPINGPTIMAGE_FIELD_ISUSERED = "PtIsUsered";

		// 航迹点名称
		String TRAILPTNAME_FIELD_NAME = "PtName";
		// 航迹点高度
		String TRAILPTHEIGHT_FIELD_NAME = "PtHeight";
		// 航迹点经度
		String TRAILPTPRECISION_FIELD_NAME = "PtPrecision";
		// 航迹点时间
		String TRAILPTTIME_FIELD_NAME = "PtTime";
		// 航迹点备注
		String TRAILPTBAK_FIELD_NAME = "PtBak";
		// 航迹点图片
		String TRAILPTIMAGE_FIELD_NAME = "PtImage";

		// 空间对象id
		String SMID = "SMID";

		// 航线名称
		String SHIPPINGLINENAME_FIELD_NAME = "LName";
		// 航线距离
		String SHIPPINGLINEDIS_FIELD_NAME = "LDistance";
		// 航线点个数
		String SHIPPINGLINEPCOUNT_FIELD_NAME = "LPtCount";
		// 航线点
		String SHIPPINGLINEPTS_FIELD_NAME = "LPts";
		// 航线线型
		String SHIPPINGLINETYPE_FIELD_NAME = "LType";
		// 航线颜色
		String SHIPPINGLINECOLOR_FIELD_NAME = "LColor";

		// 航迹名称
		String SHIPPINGTRAILNAME_FIELD_NAME = "LName";
		// 航迹距离
		String SHIPPINGTRAILDIS_FIELD_NAME = "LDistance";
		// 航迹点个数
		String SHIPPINGTRAILPCOUNT_FIELD_NAME = "LPtCount";
		// 航迹点开始时间
		String SHIPPINGTRAILST_FIELD_NAME = "LStartTime";
		// 航迹点结束时间
		String SHIPPINGTRAILET_FIELD_NAME = "LEndTime";

		// 量算距离和面积
		String MEASURE_FIELD_NAME = "Name";
		String MEASURE_FIELD_DISTANCE = "Distance";
		String MEASURE_FIELD_AREA = " Area";
	}

	/** 地图上添加view的tag相关 **/
	interface MAP_VIEW_TAG {
		String GPS = "gps";// GPS地图显示
		String LOCATION_TAG = "measurepoint";// 量算界面我的位置点
		String MEASURE_TAG = "measureline";// 量算界面线
		String MEASURE_REGION_TAG = "measureregion";// 量算面
	}

	/** intent requestCode 相关 **/
	interface REQUEST_CODE {
		int LAYERCONTROL_REQUESTCODE = 10091;// 图层控制requestCode
		int DATACONN_REQUESTCODE = 10092;// 数据关联requestCode 回调后做地图显示
		int SWITCH_WORKSPACE = 10093;// 打开工程的切换工作空间
	}

	/** cache相关 **/
	interface CACHE_RELATE {
		String LON = "lon";// 经度
		String LAT = "lat";// 纬度
		String WORKSPACE_PATH = "workspacepath";// 工作空间的缓存目录
		String BASE_MAP = "basemap";// 地图
		// 坐标设置
		String SYSTME = "systme";// 地理或者是投影系统
		String COORD_SET = "cood_set";// 坐标系统设置
		String CIRE_TYPE = "cire_type";// 椭球类型
		String UNIT = "unit";// 用户坐标设置
		// 单位设置
		String SPEED = "speed";// 速度
		String LENGTH = "length";// 长度
		String AREA = "area";// 面积
		String TIME = "time";// 时间
		String TIMEZONE = "timezone";// 时区
	}

	/**
	 * 
	 * 轨迹点存储空间
	 * 
	 */
	interface TRAIL_RELATE {
		int TOTALCOUNT = 100000;
	}

	/**
	 * 
	 * 航线管理操作类型
	 * 
	 */
	interface SHIPLINE_TYPE {
		String TYPE = "type";// 操作类型
		String NEW = "new";// 添加航线
		String EDIT = "edit";// 编辑航线
		String COPYLINE = "copyline";// 拷贝航线
		String SHIPLINEENTITY = "entity";// 航线实体
		String LINEMAP = "linemap";// 地图查看航线
		String LINENAV = "linenav"; // 航线导航
		String SAVE = "save";// 保存航线
		String NOSAVE = "nosave";// 不保存航线
		String NEWLINEMAP = "newlinemap";// 预览航线
		String NEWLINENAV = "newlinenav";// 预览导航
		String NEWLINE = "newline";// 添加航线点
		String LIST = "list";// 航线点列表
		String LINETYPE = "linetype";// 航线线型
		String LINECOLOR = "linecolor";// 航线线色
	}

	/**
	 * 
	 * 航点操作类型
	 * 
	 */
	interface POSITION_TYPE {
		String MAIN = "main";// 添加航点
		String EDIT = "edit";// 编辑航点
		String CALIMAP = "calimap";// 标定航点 地图预览
		String CALINAV = "calinav";// 标定航点 导航预览
		String POINTMAP = "pointmap";// 地图查看航点
		String POINTNAV = "pointnav";// 航点导航
		String CALIBRATION = "calibration";// 标定航点查看照片
	}

	/**
	 * 
	 * 航迹操作类型
	 * 
	 */
	interface PATH_TYPE {
		String PATHMANAGERMAP = "pathManagerSecondmap";// 地图航迹查看
		String PATHMANAGERNAV = "pathManagerSecondnav";// 航迹导航
	}

	/**
	 * 
	 * 长度面积操作类型
	 * 
	 */
	interface AREA_TYPE {
		String EXITING = "ExitingNoteActivity";// 已存长度面积的详情查看
	}
}
