package com.emapper.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.util.Log;

public class JWD {
	// 赤道半径,极半径
	private static double rc = 6378137;
	private static double rj = 6356725;
	private double mLongitude, mLatitude;
	private double mRadLo, mRadLa;
	private double ec;
	private double ed;

	public JWD() {

	}

	public JWD(double longitude, double latitude) {
		mLongitude = longitude;
		mLatitude = latitude;
		mRadLo = longitude * Math.PI / 180.;
		mRadLa = latitude * Math.PI / 180.;
		ec = rj + (rc - rj) * (90. - mLatitude) / 90.;
		ed = ec * Math.cos(mRadLa);
	}

	// ! 计算点A 和 点B的经纬度，求他们的距离和点B相对于点A的方位
	public static String angle(JWD a, JWD b) {
		double dx = (b.mRadLo - a.mRadLo) * a.ed;
		double dy = (b.mRadLa - a.mRadLa) * a.ec;
		double angle = 0.0;
		angle = Math.atan(Math.abs(dx / dy)) * 180. / Math.PI;
		// 判断象限
		double dLo = b.mLongitude - a.mLongitude;
		double dLa = b.mLatitude - a.mLatitude;

		if (dLo > 0 && dLa <= 0) {
			angle = (90. - angle) + 90.;
		} else if (dLo <= 0 && dLa < 0) {
			angle = angle + 180.;
		} else if (dLo < 0 && dLa >= 0) {
			angle = (90. - angle) + 270;
		}
		return new Double(angle).intValue() + "";
	}

	/**
	 * 经纬度计算距离
	 * **/
	public static double COE_180_PI = 0.017453292519943295769236907684886;
	public static double R_EARTH = 6371118;

	public static String getDistatce(double lon1, double lat1, double lon2,
			double lat2) {
		double dx1 = lat1 * COE_180_PI;
		double dx2 = lat2 * COE_180_PI;
		// 精度弧度
		double dy1 = lon1 * COE_180_PI;
		double dy2 = lon2 * COE_180_PI;
		// {{ mahb 解决类似dTemp=1.000000000000001时，acos(dTemp)求解溢出问题。
		// return R_EARTH*(acosl(sin(dx1)*sin(dx2) +
		// cos(dx1)*cos(dx2)*cos(dy1-dy2)));
		double dTemp = Math.sin(dx1) * Math.sin(dx2) + Math.cos(dx1)
				* Math.cos(dx2) * Math.cos(dy1 - dy2);
		if (dTemp < 1.0) {
			return calculateScale(R_EARTH * (Math.acos(dTemp)));
		}
		return "0.00";
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

	static NumberFormat nf = new DecimalFormat("0.00");

	public static String calculateScale(double scale) {
		if (scale > 1000) {
			return nf.format(scale / 1000) + "km";
		} else {
			return nf.format(scale) + "m";
		}
	}
}
