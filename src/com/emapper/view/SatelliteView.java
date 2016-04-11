package com.emapper.view;

import java.util.ArrayList;
import java.util.List;

import com.emapper.activity.R;
import com.emapper.activity.R.drawable;
import com.emapper.util.DimenUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


public class SatelliteView extends View {
	private static final float GPS_COMMON_SNR = 20f; // 图片高度代表的信噪比数字
	private Context context;
	private Bitmap u_bm;
	private Bitmap d_bm;
	private boolean destory = false;
	
	private List<SatelliteSig> satelliteSigs;
	
	SatelliteSigResProperty sigProperty;
	
	NinePatch upatch;  //上面信号的9。png图片
	NinePatch dpatch;  //底部的9.png图片
	private RectF dst;
	Paint paint;
	Rect rect;
	
	private static final int line_color = Color.rgb(195,204,190);
	private static final int o_color = Color.rgb(235,97,0);  //橙色
	private static final int y_color = Color.rgb(255,241,0);  //黄色
	private static final int r_color=Color.rgb(255, 0, 0);//红色

	public SatelliteView(Context context) {
		this(context, null);
	}

	public SatelliteView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SatelliteView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
		this.destory = false;
		this.satelliteSigs = new ArrayList<SatelliteView.SatelliteSig>();
		
		Resources res = context.getResources();
		this.u_bm = BitmapFactory.decodeResource(res, R.drawable.u_gps_sig);
		this.d_bm = BitmapFactory.decodeResource(res, R.drawable.d_gps_sig);
		this.sigProperty = new SatelliteSigResProperty(26, 260);
		
		this.dst = new RectF();
		this.paint = new Paint();
		this.rect = new Rect();
		this.upatch = new NinePatch(this.u_bm, this.u_bm.getNinePatchChunk(), null);
		this.dpatch = new NinePatch(this.d_bm, this.d_bm.getNinePatchChunk(), null);
		
//		//test
//		this.satelliteSigs.add(new SatelliteSig(8, 20));
//		this.satelliteSigs.add(new SatelliteSig(15, 50));
//		this.satelliteSigs.add(new SatelliteSig(4, 30));
//		this.satelliteSigs.add(new SatelliteSig(4, 10));
//		this.satelliteSigs.add(new SatelliteSig(4, 40));
//		this.satelliteSigs.add(new SatelliteSig(4, 35));

	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(this.destory){
			return;
		}
		
		int width = this.getWidth();
		int height = this.getHeight();
		
		// 绘制顶部图片
		int diff = DimenUtils.dipToPixel(this.context, 8);
		int diffSide = DimenUtils.dipToPixel(this.context, 15);
		/** 计算调整绘制dip值 */
		int diffDipH = 60, totalDipH = 260;
		int visiableH = DimenUtils.dipToPixel(this.context, totalDipH);
		if(visiableH > height){
			diffDipH = 40 ; totalDipH = 170;
			visiableH = DimenUtils.dipToPixel(this.context, totalDipH);
			if (visiableH > height) {
				diffDipH = 30;totalDipH = 130;
				visiableH = DimenUtils.dipToPixel(this.context, totalDipH);
				if (visiableH > height) {
					diffDipH = 20;totalDipH = 90;
					visiableH = DimenUtils.dipToPixel(this.context, totalDipH);
					if(visiableH > height){
						diffDipH = 15;totalDipH = 70;
						//再小无法绘制了  
					}
				}
			}
		}
		
		visiableH = DimenUtils.dipToPixel(this.context, diffDipH); // 每个分割的高度值
		float textSize = visiableH / 2f;

		int bottom = DimenUtils.dipToPixel(this.context,(int) (3.5f * diffDipH)) + diff;
		this.rect.setEmpty();
		this.rect.left = diffSide;
		this.rect.right = width - diffSide;
		this.rect.top = diff;
		this.rect.bottom = bottom;
		//this.upatch.draw(canvas, rect);
		
		//绘制分割线
		resetPaint();
		this.paint.setStrokeWidth(1f);
		this.paint.setColor(line_color);
		float startX, startY, stopX, stopY;
		startX = diffSide;
		stopX = width - diffSide;
		startY = diff + visiableH / 2f;
		stopY = startY;
		this.paint.setColor(r_color);
		this.paint.setTextSize(textSize);
		canvas.drawText("60", startX-20, startY, paint);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
		startY = startY + visiableH;
		stopY = startY;
		this.paint.setColor(o_color);
		canvas.drawText("40", startX-20, startY, paint);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
		startY = startY + visiableH;
		stopY = startY;
		this.paint.setColor(y_color);
		canvas.drawText("20", startX-20, startY, paint);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
		startY = startY + visiableH;
		stopY = startY;
		this.paint.setColor(line_color);
		canvas.drawText("0", startX-20, startY, paint);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
		
		// 绘制底部图片
		this.rect.setEmpty();
		this.rect.left = diffSide;
		this.rect.right = width - diffSide;
		this.rect.top = bottom;
		this.rect.bottom = bottom + DimenUtils.dipToPixel(this.context, diffDipH)/2;
		//this.dpatch.draw(canvas, rect);
		
		
		diffSide = diffSide + DimenUtils.dipToPixel(this.context, 5);
		drawSignal(canvas, this.satelliteSigs, bottom, 50, visiableH, textSize , visiableH/2);
	}
	
	private void setPaint(float textSize, int color) {
		this.paint.setTextSize(textSize);
		this.paint.setColor(color);
		this.paint.setAntiAlias(true);
		this.paint.setStrokeCap(Cap.ROUND);
		this.paint.setStrokeJoin(Join.ROUND);
	}

	@SuppressWarnings("unused")
	private void setPaint() {
		this.paint.setAntiAlias(true);
		this.paint.setStrokeCap(Cap.ROUND);
		this.paint.setStrokeJoin(Join.ROUND);
	}

	private void resetPaint() {
		this.paint.reset();
	}

	/**
	 * 
	 * @param canvas
	 * @param sigs
	 *            信号信息
	 * @param bottom
	 *            顶部图片的top值
	 * @param farleft
	 *            第一个信号柱的左边界值
	 * @param preH
	 *            信噪比为20的  高度值
	 * @param textSize
	 *            显示的自豪值
	 * @param bottom_bm_h
	 *            底部图片的height
	 */
	void drawSignal(Canvas canvas, List<SatelliteSig> sigs, float bottom,
			float farleft, int preH, float textSize, int bottom_bm_h) {
		if (sigs == null || sigs.size() == 0) {
			return;
		}
		
		int w = this.sigProperty.sig_w, h = preH, gap = this.sigProperty.sig_gap;
		int prn;
		float snr, scale, x, y;
		int n = 0;
		String text = null;
		for (SatelliteSig sig : sigs) {
			snr = sig.mSnr;
			prn = sig.mPrn;
			
			if (snr > 65) {
				snr = 65;
			}
			
			// 绘制信号柱
			scale = snr / GPS_COMMON_SNR;
			this.dst.setEmpty();
			dst.left = farleft + n * (w + gap);
			dst.right = dst.left + w;
			dst.top = bottom - h * scale;
			dst.bottom = bottom;
			
			if(snr > GPS_COMMON_SNR){
				this.paint.setColor(y_color);
			}else{
				this.paint.setColor(o_color);
			}
			
			canvas.drawRect(this.dst, paint);
			
			// 绘制卫星信噪比
			text = String.valueOf((int)sig.mSnr);
			resetPaint();
			setPaint(textSize, Color.WHITE);
			x = dst.left + (w - getTextLen(text) - 1) / 2f;
			y = dst.top - 3;
			canvas.drawText(text, x, y, paint);
			
			//绘制卫星编号
			text = convertToStr((int) prn);
			resetPaint();
			setPaint(textSize, Color.WHITE);
			x = dst.left + (w - getTextLen(text) - 1) / 2f;
			y = dst.bottom + bottom_bm_h * 3 / 4f;
			canvas.drawText(text, x, y, paint);
			
			n++;
		}
	}	
	
	/**
	 * 返回绘制数字的长度值
	 * 
	 * @param text
	 * @return
	 */
	float getTextLen(String text) {
		return this.paint.measureText(text);
	}

	String convertToStr(int value) {
		String text = (value < 10) ? ("0" + String.valueOf(value)) : String
				.valueOf(value);
		return text;
	}
	
	/**
	 * 刷新卫星信号信息
	 * 
	 * @param sigs
	 */
	public void refreshSatelliteSigs(SatelliteSig... sigs) {
		if (sigs == null) {
			return;
		}

		this.satelliteSigs.clear();
		for (SatelliteSig sig : sigs) {
			if(sig == null||sig.mSnr==0){
				continue;
			}
			this.satelliteSigs.add(sig);
		}
		this.invalidate();
	}	
	
	/**
	 * 设置信息柱间距
	 * 
	 * @param gap
	 */
	public void setSiGap(int gap) {
		if (this.sigProperty != null) {
			this.sigProperty.setSiGap(gap);
		}
	}	
	
	public void destory(){
		if (this.destory) {
			return;
		}

		this.destory = true;
		this.upatch = null;
		this.dpatch = null;
		recycleBitmap(this.u_bm);
		recycleBitmap(this.d_bm);
		this.dst.setEmpty();
		this.dst = null;
		
		if(this.satelliteSigs != null){
			this.satelliteSigs.clear();
			this.satelliteSigs = null;
		}
		
		this.sigProperty = null;
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
	 * 信号图片属性类
	 * 
	 * @author lijun.xu
	 * 
	 */
	private class SatelliteSigResProperty {
		private static final int DEFALUT_GAP = 10;  //默认间距
		
		int sig_w; // 信号图片width
		int sig_h; // 信号图片height

		int sig_gap; // 信号图片之间间距

		
		SatelliteSigResProperty(int sig_bitmap_w, int sig_bitmap_h) {
			super();
			this.sig_w = sig_bitmap_w;
			this.sig_h = sig_bitmap_h;
			this.sig_gap = DEFALUT_GAP;
		}

		/**
		 * 设置信号图片之间间距
		 * 
		 * @param sig_bitmap_gap
		 */
		void setSiGap(int sig_bitmap_gap) {
			this.sig_gap = sig_bitmap_gap;
		}		
		
	}

	
	/**
	 * 卫星信号类
	 * 
	 * @author lijun.xu
	 * 
	 */
	public static class SatelliteSig {
		int mPrn; // 卫星编号
		float mSnr; // 卫星信噪比

		public SatelliteSig() {
		}

		public SatelliteSig(int mPrn, float mSnr) {
			super();
			this.mPrn = mPrn;
			this.mSnr = mSnr;
		}

		public void setmPrn(int mPrn) {
			this.mPrn = mPrn;
		}

		public void setmSnr(float mSnr) {
			this.mSnr = mSnr;
		}
	}	
}
