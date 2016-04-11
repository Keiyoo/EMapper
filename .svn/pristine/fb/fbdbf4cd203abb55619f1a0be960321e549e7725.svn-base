package com.emapper.activity;

import com.emapper.util.Constant;
import com.emapper.util.SysConstant;
import com.emapper.util.TransferUtils;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
/**
 * 单位设置界面
 * 
 */
public class UnitSetActivity extends BaseActivity implements OnClickListener {
	private Button btn_back;
	private TextView text_speed;
	private TextView text_speed_set;
	private TextView text_length;
	private TextView text_length_set;
	private TextView text_time;
	private TextView text_time_set;
	private TextView text_area;
	private TextView text_area_set;
	private TextView text_timezone;
	private TextView text_timezone_set;
	private PopupWindow mPopupWindow;
	private EApplication appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appContext = EApplication.getInstance();
		appContext.addActivity(this);
		initView();

	}

	private void initView() {
		setContentView(R.layout.activity_unit_set);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_unit_set));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}

		});
		text_speed = (TextView) findViewById(R.id.text_speed_content);
		text_speed_set = (TextView) findViewById(R.id.text_speed_set);
		text_speed_set.setOnClickListener(this);
		text_length = (TextView) findViewById(R.id.text_length_content);
		text_length_set = (TextView) findViewById(R.id.text_length_set);
		text_length_set.setOnClickListener(this);
		text_time = (TextView) findViewById(R.id.text_time_content);
		text_time_set = (TextView) findViewById(R.id.text_time_set);
		text_time_set.setOnClickListener(this);
		text_area = (TextView) findViewById(R.id.text_area_content);
		text_area_set = (TextView) findViewById(R.id.text_area_set);
		text_area_set.setOnClickListener(this);
		text_timezone = (TextView) findViewById(R.id.text_timezone_content);
		text_timezone_set = (TextView) findViewById(R.id.text_timezone_set);
		text_timezone_set.setOnClickListener(this);
		initText();
	}

	private void initText() {
		if(Constant.SPEED_SET==2){
			text_speed.setText(getString(R.string.speed_set2));
		}else{
			text_speed.setText(getString(R.string.speed_set1));
		}
		
		if(Constant.LENGTH_SET==2){
			text_length.setText(getString(R.string.length_set2));
		}else{
			text_length.setText(getString(R.string.length_set1));
		}
		if(Constant.TIME_SET==2){
			text_time.setText(getString(R.string.time_set2));
		}else{
			text_time.setText(getString(R.string.time_set1));
		}
		if(Constant.AREA_SET==2){
			text_area.setText(getString(R.string.area_set2));
		}else{
			text_area.setText(getString(R.string.area_set1));
		}
		if(Constant.TIMEZONE_SET==3){
			text_timezone.setText(getString(R.string.timezone_set1));
		}else if(Constant.TIMEZONE_SET==2){
			text_timezone.setText(getString(R.string.timezone_set2));
		}else{
			text_timezone.setText(getString(R.string.timezone_set3));
		}
		

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == text_speed_set) {
			showPop(text_speed, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_speed.setText(getString(R.string.speed_set1));
					appContext.acache.put(SysConstant.CACHE_RELATE.SPEED,
							1+"");
					Constant.SPEED_SET = 1;
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_speed.setText(getString(R.string.speed_set2));
					appContext.acache.put(SysConstant.CACHE_RELATE.SPEED,
							2+"");
					Constant.SPEED_SET = 2;
					mPopupWindow.dismiss();
				}
			}, null, 2, R.string.speed_set1, R.string.speed_set2,
					R.string.speed_set1);
		} else if (v == text_length_set) {
			showPop(text_length, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_length.setText(getString(R.string.length_set1));
					appContext.acache.put(SysConstant.CACHE_RELATE.LENGTH,
							1+"");
					Constant.LENGTH_SET = 1;
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_length.setText(getString(R.string.length_set2));
					appContext.acache.put(SysConstant.CACHE_RELATE.LENGTH,
							2+"");
					Constant.LENGTH_SET = 2;
					mPopupWindow.dismiss();
				}
			}, null, 2, R.string.length_set1, R.string.length_set2,
					R.string.length_set1);
		} else if (v == text_time_set) {
			showPop(text_time, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_time.setText(getString(R.string.time_set1));
					appContext.acache.put(SysConstant.CACHE_RELATE.TIME,
							1+"");
					Constant.TIME_SET = 1;
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_time.setText(getString(R.string.time_set2));
					appContext.acache.put(SysConstant.CACHE_RELATE.TIME,
							2+"");
					Constant.TIME_SET = 2;
					mPopupWindow.dismiss();
				}
			}, null, 2, R.string.time_set1, R.string.time_set2,
					R.string.time_set1);
		} else if (v == text_area_set) {
			showPop(text_area, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_area.setText(getString(R.string.area_set1));
					appContext.acache.put(SysConstant.CACHE_RELATE.AREA,
							1+"");
					Constant.AREA_SET = 1;
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_area.setText(getString(R.string.area_set2));
					appContext.acache.put(SysConstant.CACHE_RELATE.AREA,
							2+"");
					Constant.AREA_SET =2;
					mPopupWindow.dismiss();
				}
			}, null, 2, R.string.area_set1, R.string.area_set2,
					R.string.area_set1);
		} else if (v == text_timezone_set) {
			showPop(text_timezone, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_timezone.setText(getString(R.string.timezone_set1));
					appContext.acache.put(SysConstant.CACHE_RELATE.TIMEZONE,
							3+"");
					Constant.TIMEZONE_SET = 3;
					mPopupWindow.dismiss();
					TransferUtils.timeZoneParser(3, 1234);
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_timezone.setText(getString(R.string.timezone_set2));
					appContext.acache.put(SysConstant.CACHE_RELATE.TIMEZONE,
							2+"");
					Constant.TIMEZONE_SET = 2;
					TransferUtils.timeZoneParser(2, 1234);
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_timezone.setText(getString(R.string.timezone_set3));
					appContext.acache.put(SysConstant.CACHE_RELATE.TIMEZONE,
							1+"");
					Constant.TIMEZONE_SET = 1;
					TransferUtils.timeZoneParser(1, 1234);
					mPopupWindow.dismiss();
				}
			}, 3, R.string.timezone_set1, R.string.timezone_set2,
					R.string.timezone_set3);
		}
	}

	private void showPop(View view1, OnClickListener click1,
			OnClickListener click2, OnClickListener click3, int num, int res1,
			int res2, int res3) {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		} else {
			View view = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.coordset_pop, null);
			mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			mPopupWindow.showAsDropDown(view1);

			TextView text_1 = (TextView) view.findViewById(R.id.text_1);
			TextView text_2 = (TextView) view.findViewById(R.id.text_2);
			TextView text_3 = (TextView) view.findViewById(R.id.text_3);
			Log.v("gis", "dddd" + res1);
			if (num == 3) {
				text_1.setVisibility(View.VISIBLE);
				text_2.setVisibility(View.VISIBLE);
				text_3.setVisibility(View.VISIBLE);
			} else if (num == 2) {
				text_1.setVisibility(View.VISIBLE);
				text_2.setVisibility(View.VISIBLE);
			}
			text_1.setText(getString(res1));
			text_2.setText(getString(res2));
			text_3.setText(getString(res3));

			text_1.setOnClickListener(click1);
			text_2.setOnClickListener(click2);
			text_3.setOnClickListener(click3);

		}
	}
}
