package com.emapper.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.emapper.activity.R;

/**
 * 星历图View
 * 
 * @author lijun.xu
 * 
 */
public class EphemerisView extends View {
	private Context context;
	private boolean destory = false;  //资源是否销毁
	
	private Bitmap compass_bm; // 加载地图
	private Bitmap o_bm; // 橙色
	private Bitmap y_bm; // 黄色
	
	private List<SatellitePt> satellitePts;
	
	public EphemerisView(Context context) {
		this(context, null);
	}

	public EphemerisView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EphemerisView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
		this.destory = false;
		this.compass_bm = BitmapFactory.decodeResource(context.getResources(),R.drawable.gps_compass);
		this.o_bm = BitmapFactory.decodeResource(context.getResources(),R.drawable.gps_o_satellite);
		this.y_bm = BitmapFactory.decodeResource(context.getResources(),R.drawable.gps_y_satellite);
		
		this.satellitePts = new ArrayList<EphemerisView.SatellitePt>();
//		this.satellitePts.add(new SatellitePt(90f, 0f, 15));
//		this.satellitePts.add(new SatellitePt(40f, 160f, 15));
//		this.satellitePts.add(new SatellitePt(90f, 180f, 15));
//		this.satellitePts.add(new SatellitePt(0f, 360f, 30));
		
//		this.setBackgroundColor(Color.rgb(244,244,244));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// 资源已经释放
		if (this.destory) {
			return;
		}
		
		int w = this.compass_bm.getWidth(), h = this.compass_bm.getHeight();
		canvas.save();
		canvas.rotate(0, this.getWidth() / 2f, this.getHeight() / 2f);
		canvas.translate((this.getWidth() - w) / 2f,
				(this.getHeight() - h) / 2f);
		canvas.drawBitmap(this.compass_bm, 0, 0, null);
		
		drawSatellite(canvas, this.satellitePts, w / 2f);
		canvas.restore();
	}
	
	
	
	void drawSatellite(Canvas canvas, List<SatellitePt> pts, float radius) {
		if(pts == null || pts.size() == 0){
			return;
		}
		
		float mElevation, mAzimuth;
		float left, top;
		Bitmap bm;
		double hr;
		float bm_w = this.y_bm.getWidth()/2f, bm_h = this.y_bm.getHeight()/2f;
		for (SatellitePt satellitePt : pts) {
			mElevation = satellitePt.mElevation; // 高度角
			mAzimuth = satellitePt.mAzimuth; // 方位角

			hr = radius * Math.sin(Math.toRadians(mElevation));
			left = (float) (Math.abs(hr * Math.sin(Math.toRadians(mAzimuth))));
			top = (float) (Math.abs(hr * Math.cos(Math.toRadians(mAzimuth))));
			if (mAzimuth >= 0 && mAzimuth < 90) { // 第一象限
				left = radius + left - bm_w;
				top = radius - top + bm_h;
			} else if (mAzimuth >= 90 && mAzimuth < 180) {// 第二象限
				left = radius + left - bm_w;
				top = radius - top - bm_h;
			} else if (mAzimuth >= 180 && mAzimuth < 270) {// 第三象限
				left = radius - left + bm_w;
				top = radius + top - bm_h;
			} else {// 第四象限
				left = radius - left + bm_w;
				top = radius + top + bm_h;
			}
			bm = (satellitePt.mSnr >= 20) ? this.y_bm : this.o_bm;
			canvas.drawBitmap(bm, left, top, null);
		}
		bm = null;
	}
	
	public int getCompassHeight(){
		return this.compass_bm.getHeight();
	}
	
	
	/**
	 * 刷新星历图盘view
	 * 
	 * @param pts
	 */
	public void refreshEphemerisView(SatellitePt... pts) {
		if (pts == null) {
			return;
		}
		this.satellitePts.clear();
		for (SatellitePt satellitePt : pts) {
			if (satellitePt == null) {
				continue;
			}
			this.satellitePts.add(satellitePt);
		}
		this.invalidate();
	}	
	
	/**
	 * 资源释放
	 */
	public void destory(){
		if (this.destory) {
			return;
		}
		destory = true;
		
		recycleBitmap(this.compass_bm);
		recycleBitmap(this.o_bm);
		recycleBitmap(this.y_bm);

		if (this.satellitePts != null) {
			this.satellitePts.clear();
			this.satellitePts = null;
		}
	}
	
	private void recycleBitmap(Bitmap bitmap) {
		Bitmap bm = bitmap;
		bitmap = null;
		if (bm != null && !bm.isRecycled()) {
			bm.recycle();
			bm = null;
		}
	}
	
	/**
	 * 
	 * @author lijun.xu
	 * 
	 */
	public static class SatellitePt {
		float mElevation; // 卫星的高度角
		float mAzimuth; // 卫星的方位角
		float mSnr; // 信噪比

		public SatellitePt() {
		}

		public SatellitePt(float mElevation, float mAzimuth , float mSnr) {
			this.mElevation = mElevation;
			this.mAzimuth = mAzimuth;
			this.mSnr = mSnr;
		}

		public void setmElevation(float mElevation) {
			this.mElevation = mElevation;
		}

		public void setmAzimuth(float mAzimuth) {
			this.mAzimuth = mAzimuth;
		}

		public void setmSnr(float mSnr) {
			this.mSnr = mSnr;
		}
	}	

}
