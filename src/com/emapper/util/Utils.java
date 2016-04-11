package com.emapper.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

import com.android.gis.Point2D;
import com.emapper.util.SysConstant.CACHE_RELATE;
import com.piespace.mapping.Projection;

public class Utils {
	/**
	 * 判断GPS数据是否不等于零
	 */
	public static boolean isGpsDataValid(double dx, double dy) {
		boolean bResult = false;
		if (dx >= 0.00000001 || dx <= -0.00000001 || dy >= 0.00000001
				|| dy <= -0.00000001) {
			bResult = true;
		}
		return bResult;
	}

	public static Point2D toLonLat(Projection proj, Point2D mapPnt) {
		Point2D lonLatPnt = proj.inverse(mapPnt);
		return lonLatPnt;
	}

	public static Point2D toMapPnt(Projection proj, Point2D lonLatPnt) {
		Point2D mapPnt = proj.forward(lonLatPnt);
		return mapPnt;
	}

	/** 暂时用的别名 **/
	public static HashMap<String, String> initDataShow() {
		HashMap<String, String> datas = new HashMap<String, String>();
		datas.put("GooleMap", "背景地图");
		datas.put("groundRegion", "地块面");
		datas.put("borderPt", "边界点");
		datas.put("markPt", "记号点");
		return datas;
	}

	/** 创建指定文件夹 **/
	public static String createFile(String filePath) {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		StringBuffer sb = new StringBuffer(filePath);
		sb.append(File.separator).append(
				"Output" + month + "月" + day + "日" + hour + "时" + minute + "分");
		// sb.append(File.separator).append(
		// "Output" + month + "_" + day + "_" + hour + "_" + minute);
		File file = new File(sb.toString());
		if (!file.exists()) {
			file.mkdirs();
		}
		return sb.toString();
	}

	/** 获取存储的GPS信息 **/
	public static double[] getCacheGps(ACache acache) {
		double[] lonLat = new double[2];
		String lon = acache.getAsString(CACHE_RELATE.LON);
		String lat = acache.getAsString(CACHE_RELATE.LAT);

		if (lon != null && !"".equals(lon)) {
			lonLat[0] = Double.parseDouble(lon);
		} else {
			lonLat[0] = 0;
		}

		if (lat != null && !"".equals(lat)) {
			lonLat[1] = Double.parseDouble(lat);
		} else {
			lonLat[1] = 0;
		}
		return lonLat;
	}

	/** 获取工作空间的缓存目录 **/
	public static String getWorkSpacePath() {
		ACache cache = ACache.get(new File(ResPathCenter.getCachePath()));
		String workPath = cache.getAsString(CACHE_RELATE.WORKSPACE_PATH);
		return workPath;
	}

	public static ArrayList<String> filePaths = new ArrayList<String>();

	/** 读取指定文件夹的层级目录 **/
	public static void readfile(String filepath) {
		File file = new File(filepath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					// 到了3级目录就停下来
					String tempPath = filepath + File.separator + filelist[i];
					tempPath = tempPath.replace(ResPathCenter.STR_WSK_FILE, "")
							.trim();
					String[] splits = tempPath.split(File.separator);
					if (splits.length < 4) {
						File readfile = new File(filepath + File.separator
								+ filelist[i]);
						if (readfile.isDirectory()) {
							readfile(filepath + File.separator + filelist[i]);
						}
					} else if (splits.length == 4) {
						Log.v("gis", tempPath);
						filePaths.add(tempPath);
					}
				}
			}
		}
	}


	/** 转换度分秒 **/
	public static String computeAngleValue(double value) {
		int pm = (value >= 0) ? 1 : -1;
		int degree = pm * (int) Math.floor(Math.abs(value));
		double m = (Math.abs(value - degree)) * 60;
		int minute = (int) Math.floor(m);

		double second = ((m - minute) * 60);

		String strTemp = degree + "°" + minute + "′"
				+ String.format("%.2f", second) + "″";
		return strTemp;
	}

	/** openGL截图 **/
	public static Bitmap SavePixels(int x, int y, int w, int h, GL10 gl) {
		int b[] = new int[w * h];
		int bt[] = new int[w * h];
		IntBuffer ib = IntBuffer.wrap(b);
		ib.position(0);
		gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int pix = b[i * w + j];
				int pb = (pix >> 16) & 0xff;
				int pr = (pix << 16) & 0x00ff0000;
				int pix1 = (pix & 0xff00ff00) | pr | pb;
				bt[(h - i - 1) * w + j] = pix1;
			}
		}
		// Bitmap sb=Bitmap.createBitmap(bt, w, h, true);
		Bitmap sb = Bitmap.createBitmap(bt, w, h, Bitmap.Config.RGB_565);
		return sb;
	}

	public static void SavePNG(int x, int y, int w, int h, String fileName,
			GL10 gl) {
		Bitmap bmp = SavePixels(x, y, w, h, gl);
		try {
			FileOutputStream fos = new FileOutputStream("/mnt/sdcard/"
					+ fileName); // android123提示大家，如何2.2或更高的系统sdcard路径为/mnt/sdcard/
			bmp.compress(CompressFormat.PNG, 100, fos); // 保存为png格式，质量100%
			try {
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
