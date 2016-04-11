package com.emapper.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.util.Log;

import com.emapper.activity.R;

public class TransferUtils {

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static long getLongTime() {
		// 将位置信息上传到服务器
		Calendar dt = Calendar.getInstance();
		Long time = dt.getTimeInMillis() / 1000;// 这就是距离1970年1月1日0点0分0秒的毫秒数
		return time;
	}

	/**
	 * 从long型时间获取字符串时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getTime(long time) {
		Calendar dt = Calendar.getInstance();
		dt.setTimeInMillis(time * 1000);

		// int year=dt.get(Calendar.YEAR);
		// int month=dt.get(Calendar.MONTH)+1;
		// int day=dt.get(Calendar.DAY_OF_MONTH);
		int hour = dt.get(Calendar.HOUR_OF_DAY);
		int miniute = dt.get(Calendar.MINUTE);
		// int second=dt.get(Calendar.SECOND);

		return hour + ":" + miniute;
	}

	/**
	 * 从long型时间获取字符串时间
	 * 
	 * @param time
	 * @return
	 */
	// public static String getTime2(long time) {
	// Calendar dt = Calendar.getInstance();
	// dt.setTimeInMillis(time * 1000);
	//
	// int year = dt.get(Calendar.YEAR);
	// int month = dt.get(Calendar.MONTH) + 1;
	// int day = dt.get(Calendar.DAY_OF_MONTH);
	// int hour = dt.get(Calendar.HOUR_OF_DAY);
	// int miniute = dt.get(Calendar.MINUTE);
	// int second = dt.get(Calendar.SECOND);
	//
	// return year + "-" + month + "-" + day + " " + hour + ":" + miniute
	// + ":" + second;
	// }
	public static String getTime2(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date(time);
		return sdf.format(date);
	}

	public static String getTime3(long time) {
		Calendar dt = Calendar.getInstance();
		dt.setTimeInMillis(time * 1000);

		int year = dt.get(Calendar.YEAR);
		int month = dt.get(Calendar.MONTH) + 1;
		int day = dt.get(Calendar.DAY_OF_MONTH);
		int hour = dt.get(Calendar.HOUR_OF_DAY);
		int miniute = dt.get(Calendar.MINUTE);
		int second = dt.get(Calendar.SECOND);

		return year + "" + month + "" + day + "" + hour + "" + miniute + ""
				+ second + "";
	}

	public static String getFormat(double db, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(db);
	}

	/**
	 * 获取时间差
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getTimeSpan(long start, long end) {
		long between = end - start;
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60;
		String timespan = "";
		if (day > 0) {
			timespan = getFormatTime(day) + "天" + getFormatTime(hour) + "小时"
					+ getFormatTime(minute) + "分" + getFormatTime(second) + "秒";
		} else if (hour > 0) {
			timespan = getFormatTime(hour) + "小时" + getFormatTime(minute) + "分"
					+ getFormatTime(second) + "秒";
		} else {
			timespan = getFormatTime(minute) + "分" + getFormatTime(second)
					+ "秒";
		}
		return timespan;
	}

	public static String getFormatTime(long value) {
		if (value < 10) {
			return "0" + value;
		} else {
			return "" + value;
		}
	}

	/*
	 * 用户坐标单位的设置
	 */

	public static String positionParser(int unitType, double num) {
		java.text.DecimalFormat df8 = new java.text.DecimalFormat("#.########");
		Log.v("gis", unitType + "unitType");
		if (unitType == 2) {
			return TransferUtils.convertToSexage(num) + "";
		} else if (unitType == 1) {
			return TransferUtils.convertToSexagesimal(num) + "";
		} else {
			// return new BigDecimal(num).setScale(8, BigDecimal.ROUND_HALF_UP)
			// + "";
			return df8.format(num)+"°";
		}
	}

	/*
	 * 速度的设置
	 */
	public static String speedParser(int type, double num) {
		java.text.DecimalFormat df2 = new java.text.DecimalFormat("#.##");
		if (type == 2) {
			return df2.format(num / 1000);
		} else {
			return df2.format(num);
		}
	}

	/*
	 * 长度的设置
	 */
	public static String lengthParser(int type, double num) {
		java.text.DecimalFormat df2 = new java.text.DecimalFormat("#.##");
		if (type == 2) {
			return df2.format(num / 1000);
		} else {
			return df2.format(num);
		}
	}

	/*
	 * 面积的设置
	 */
	public static BigDecimal areaParser(int type, double num) {
		if (type == 2) {
			return new BigDecimal(num / 1000000).setScale(2,
					BigDecimal.ROUND_HALF_UP);
		} else {
			return new BigDecimal(num);
		}
	}

	/*
	 * 时间的设置
	 */
	public static double timeParser(int type, long currentTime) {
		if (type == 2) {
			Calendar dt = Calendar.getInstance();
			dt.setTimeInMillis(currentTime * 1000);
			int hour = dt.get(Calendar.HOUR_OF_DAY);
			if (hour >= 0 && hour <= 12) {
				return currentTime;
			} else {
				return currentTime - 12;
			}
		} else {
			return currentTime;
		}

	}

	/*
	 * 时区的设置 ？？？有问题
	 */
	public static long timeZoneParser(int type, long currentTime) {
		TimeZone tz = TimeZone.getDefault();
		String nowZoneID = tz.getID();
		TimeZone tz_1;
		Log.v("gis", nowZoneID + "nowZoneID");
		if (type == 3) {
			tz_1 = TimeZone.getTimeZone("GMT+6");
			Log.v("gis", tz_1.getID() + "tz_1.getID()gmt+6");
		} else if (type == 2) {
			tz_1 = TimeZone.getTimeZone("GMT+7");
			Log.v("gis", tz_1.getID() + "tz_1.getID()gmt+7");
		} else {
			tz_1 = TimeZone.getTimeZone("GMT+8");
			Log.v("gis", tz_1.getID() + "tz_1.getID()gmt+8");
		}
		Log.v("gis",
				getTime2(currentTime
						+ (tz_1.getRawOffset() - tz.getRawOffset()))
						+ "gmt+8");
		Log.v("gis", getTime2(currentTime) + "gmt+8");
		return currentTime + (tz_1.getRawOffset() - tz.getRawOffset());
	}

	/*
	 * 把经纬度转化成度分秒
	 */
	public static String convertToSexagesimal(double num) {
		java.text.DecimalFormat df4 = new java.text.DecimalFormat("#.####");
		int du = (int) Math.floor(Math.abs(num)); // 获取整数部分

		double temp = getdPoint(Math.abs(num)) * 60;

		int fen = (int) Math.floor(temp); // 获取整数部分

		// String miao = new BigDecimal(getdPoint(temp) * 60).setScale(6,
		// BigDecimal.ROUND_HALF_UP) + "";
		String miao = df4.format(getdPoint(temp) * 60);
		if (num < 0) {
			return "-" + du + "°" + fen + "′" + miao + "″";
		} else {
			return du + "°" + fen + "′" + miao + "″";
		}
	}

	/*
	 * 把经纬度转化成度分
	 */
	public static String convertToSexage(double num) {
		java.text.DecimalFormat df6 = new java.text.DecimalFormat("#.######");
		int du = (int) Math.floor(Math.abs(num)); // 获取整数部分

		double temp = getdPoint(Math.abs(num)) * 60;
		// String fen = new BigDecimal(temp).setScale(4,
		// BigDecimal.ROUND_HALF_UP)
		// + "";
		String fen = df6.format(temp);
		if (num < 0) {
			return "-" + du + "°" + fen + "′";
		} else {
			return du + "°" + fen + "′";
		}
	}

	// 获取小数部分

	private static double getdPoint(double num) {

		double d = num;

		int fInt = (int) d;

		BigDecimal b1 = new BigDecimal(Double.toString(d));

		BigDecimal b2 = new BigDecimal(Integer.toString(fInt));

		double dPoint = b1.subtract(b2).floatValue();

		return dPoint;

	}

}
