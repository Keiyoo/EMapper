package com.emapper.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import com.emapper.util.Constant;
import com.emapper.util.PictureUtil;
import com.emapper.util.Produce;
import com.emapper.util.SDCard;
import com.emapper.util.SysConstant;

import android.R.color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 拍照界面
 * 
 */
public class TakePictureActivity extends BaseActivity {
	private Button btn_back;
	private String filename;
	private ImageView img_photo;
	private String image;
	private String type;
	private EApplication appContext;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appContext = EApplication.getInstance();
		appContext.addActivity(this);
		type = getIntent().getStringExtra(SysConstant.SHIPLINE_TYPE.TYPE);
		if (type.equals(SysConstant.POSITION_TYPE.CALIBRATION)) {
			image = getIntent().getStringExtra("image");
			if (image == null || image.equals("")) {
				cameraMethod();
				initView();
			} else {
				initView();
				Bitmap bitmap = PictureUtil.getSmallBitmap(image);
				img_photo.setBackgroundColor(color.transparent);
				img_photo.setImageBitmap(bitmap);
			}
		} else if (type.equals(SysConstant.POSITION_TYPE.MAIN)) {
			cameraMethod();
			initView();
		}

	}

	private void initView() {
		setContentView(R.layout.activity_take_picture);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_take_picture));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (type.equals(SysConstant.POSITION_TYPE.CALIBRATION)) {
					Intent data = new Intent();
					data.putExtra("image", image);
					setResult(Constant.REQUEST_BACKPICTURE, data);
				}
				finish();
			}
		});
		img_photo = (ImageView) findViewById(R.id.img_photo);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == SysConstant.RESULT_CAPTURE_IMAGE) {
				try {
					Bitmap bitmap=null;
					if (appContext.naviGPS.dLongitude == 0
							&& appContext.naviGPS.dLatitude == 0) {
						 bitmap = addTextToImage(
								PictureUtil.getSmallBitmap(filename),
								"N/A,N/A", filename);
					} else {
						 bitmap = addTextToImage(
								PictureUtil.getSmallBitmap(filename),
								"("
										+ new BigDecimal(
												appContext.naviGPS.dLongitude)
												.setScale(
														2,
														BigDecimal.ROUND_HALF_UP)
										+ ","
										+ new BigDecimal(
												appContext.naviGPS.dLatitude)
												.setScale(
														2,
														BigDecimal.ROUND_HALF_UP)
										+ ")", filename);
					}
					img_photo.setImageBitmap(bitmap);
					image = filename;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 照相功能
	 */
	public void cameraMethod() {
		String dirpath = SDCard.getSDCardPath() + "/EMapper/image/";
		File file = new File(dirpath);
		if (!file.exists()) {
			file.mkdirs();
		}
		filename = dirpath + Produce.produceFile();
		Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File imageFile = new File(filename);
		Uri u = Uri.fromFile(imageFile);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, u);
		startActivityForResult(imageCaptureIntent,
				SysConstant.RESULT_CAPTURE_IMAGE);
	}

	public Bitmap addTextToImage(Bitmap src, String text, String path) {
		// 创建可以编辑的图片
		Bitmap alertBitmap = Bitmap.createBitmap(src.getWidth(),
				src.getHeight(), src.getConfig());
		Canvas canvas = new Canvas(alertBitmap);
		Paint paint = new Paint();
		/** 设置字体的颜色 */
		paint.setColor(Color.BLACK);
		/** 设置字体的大小 */
		paint.setTextSize(120);
		/** 设置字体的类型 */
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		/** 先画张图片 */
		canvas.drawBitmap(src, new Matrix(), paint);
		/** 添加文字 */
		canvas.drawText(text, 50, 100, paint);
		OutputStream os = null;
		try {
			os = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/** 保存到本地 */
		alertBitmap.compress(CompressFormat.JPEG, 80, os);
		return alertBitmap;
	}
}
