package com.emapper.util;

import android.content.Context;

/**
 * android系统各坐标转�?
 */
public class DimenUtils {

	/**
	 * dip转pixel
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dipToPixel(Context context, int dipValue) {
		float pixelFloat = android.util.TypedValue.applyDimension(
				android.util.TypedValue.COMPLEX_UNIT_DIP, dipValue, context
						.getResources().getDisplayMetrics());
		return (int) pixelFloat;
	}

	/**
	 * sp转pixel
	 * 
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static int spToPixel(Context context, int spValue) {
		float pixelFloat = android.util.TypedValue.applyDimension(
				android.util.TypedValue.COMPLEX_UNIT_SP, spValue, context
						.getResources().getDisplayMetrics());
		return (int) pixelFloat;
	}

}
