package com.emapper.util;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;

/**
 * 工程目录管理
 * author *** 
 * 2014-6-12 copyright by ***
 */
public class ResPathCenter {

	public static final String STR_SDCARD_ROOT = Environment
			.getExternalStorageDirectory().getAbsolutePath();       		//SDCard根目录
	public static final String STR_PROJECT_FILE = STR_SDCARD_ROOT + "/EMapper";     //工程目录
	public static final String STR_WSK_FILE = STR_PROJECT_FILE + "/data";           //数据目录
	public static final String STR_CONN_FILE = "SurveyRd";					        // 数据关联目录
	public static final String STR_CACHE_FILE = "cache";					   		// 缓存目录

	private static class ResPathCenterHolder {
		private static ResPathCenter instance = new ResPathCenter();
	}
	
	private ResPathCenter() {

	}

	public static ResPathCenter getInstance() {
		return ResPathCenterHolder.instance;
	}

	/**
	 * 获取工作空间文件目录
	 */
	public String getWorkspaceFolderPath() {
		return STR_WSK_FILE;
	}
	
	/**
	 * 获取工作空间workspace.xml的全路径
	 */
	public String getWorkspacePath() {
		return STR_WSK_FILE + "/workspace.xml";
	}

	/** 数据关联目录 **/
	public String getDataConnPath() {
		return STR_PROJECT_FILE + File.separator + STR_CONN_FILE;
	}

	/** 缓存数据存储路径 **/
	public static String getCachePath() {
		return  STR_PROJECT_FILE + File.separator +STR_CACHE_FILE;
	}
}
