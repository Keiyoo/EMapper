package com.emapper.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.emapper.util.Constant;
import com.emapper.util.SysConstant;

/**
 * 坐标设置界面 设置里面的
 * 
 */
public class CoordinateSetActivity extends BaseActivity implements
		OnClickListener {
	private Button btn_back;
	private TextView text_geo_sys;
	private TextView text_geo_sys_set;
	private TextView text_ellipse;
	private TextView text_ellipse_set;
	private TextView text_unit;
	private TextView text_unit_set;
	private PopupWindow mPopupWindow;
	private RadioButton radiobtn_geo;
	private RadioButton radiobtn_pro;
	private EApplication appContext;
	private String unitText;
	private RadioGroup radio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appContext = EApplication.getInstance();
		appContext.addActivity(this);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_coordinate_set);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_coordinate_set));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}

		});
		text_geo_sys = (TextView) findViewById(R.id.text_geo_sys);
		text_geo_sys_set = (TextView) findViewById(R.id.text_geo_sys_set);
		text_geo_sys_set.setOnClickListener(this);
		text_ellipse = (TextView) findViewById(R.id.text_ellipse);
		text_ellipse_set = (TextView) findViewById(R.id.text_ellipse_set);
		text_ellipse_set.setOnClickListener(this);
		text_unit = (TextView) findViewById(R.id.text_user);
		text_unit_set = (TextView) findViewById(R.id.text_user_set);
		text_unit_set.setOnClickListener(this);
		radio = (RadioGroup) findViewById(R.id.radio);
		radiobtn_geo = (RadioButton) findViewById(R.id.radiobtn_geo);
		radiobtn_pro = (RadioButton) findViewById(R.id.radiobtn_pro);
		radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkId) {
				// TODO Auto-generated method stub
				if (radiobtn_geo.getId() == checkId) {
					if(!radiobtn_geo.isChecked()){
					appContext.acache.put(SysConstant.CACHE_RELATE.SYSTME,
							1 + "");
					Constant.COOREDINATE_SYS = 1;
					text_geo_sys.setText(getString(R.string.coordinate_1));
					appContext.acache.put(SysConstant.CACHE_RELATE.COORD_SET,
							1 + "");
					Constant.COOREDINATE_SET = 1;
					}
				} else if (radiobtn_pro.getId() == checkId) {
					if(!radiobtn_pro.isChecked()){
					appContext.acache.put(SysConstant.CACHE_RELATE.SYSTME,
							2 + "");
					Constant.COOREDINATE_SYS = 2;
					text_geo_sys.setText(getString(R.string.pro_1));
					appContext.acache.put(SysConstant.CACHE_RELATE.COORD_SET,
							5 + "");
					Constant.COOREDINATE_SET = 5;
					}
				}
				Log.v("gis", Constant.COOREDINATE_SET
						+ "Constant.COOREDINATE_SET33333333333333333333");
			}
		});

		// unitText=appContext.acache.getAsString(SysConstant.CACHE_RELATE.UNIT);
		// Log.v("gis", unitText+"unitText");
		// if(unitText==null){
		// unitText=getString(R.string.unit_1);
		// }
		// text_unit.setText(unitText);
		initText();
	}

	private void initText() {
		Log.v("gis", Constant.COOREDINATE_SYS + "Constant.COOREDINATE_SYS");
		if (Constant.COOREDINATE_SYS == 1) {
			radiobtn_geo.setChecked(true);
		} else {
			radiobtn_pro.setChecked(true);
		}
		Log.v("gis", Constant.COOREDINATE_SET
				+ "Constant.COOREDINATE_SET1111111111111");
		switch (Constant.COOREDINATE_SET) {

		case 1:
			text_geo_sys.setText(getString(R.string.coordinate_1));
			break;
		case 2:
			text_geo_sys.setText(getString(R.string.coordinate_2));
			break;
		case 3:
			text_geo_sys.setText(getString(R.string.coordinate_3));
			break;
		case 4:
			text_geo_sys.setText(getString(R.string.coordinate_4));
			break;
		case 5:
			text_geo_sys.setText(getString(R.string.pro_1));
			break;
		case 6:
			text_geo_sys.setText(getString(R.string.pro_2));
			break;
		case 7:
			text_geo_sys.setText(getString(R.string.pro_3));
			break;

		}
		switch (Constant.CIRE_TYPE_SET) {
		case 1:
			text_ellipse.setText(getString(R.string.ellipse_1));
			break;
		case 2:
			text_ellipse.setText(getString(R.string.ellipse_2));
			break;
		case 3:
			text_ellipse.setText(getString(R.string.ellipse_3));
			break;
		}
		switch (Constant.UNIT_SET) {
		case 1:
			text_unit.setText(getString(R.string.unit_3));
			break;
		case 2:
			text_unit.setText(getString(R.string.unit_2));
			break;
		case 3:
			text_unit.setText(getString(R.string.unit_1));
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == text_geo_sys_set) {
			final int text1, text2, text3, text4, num;
			if (radiobtn_geo.isChecked()) {
				text1 = R.string.coordinate_1;
				text2 = R.string.coordinate_2;
				text3 = R.string.coordinate_3;
				text4 = R.string.coordinate_4;
				Constant.COOREDINATE_SYS = 1;
				num = 4;
			} else {
				text1 = R.string.pro_1;
				text2 = R.string.pro_2;
				text3 = R.string.pro_3;
				text4 = R.string.pro_3;
				num = 3;
				Constant.COOREDINATE_SYS = 2;
			}
			showPop(text_geo_sys, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_geo_sys.setText(getString(text1));
					if (num == 4) {
						appContext.acache.put(
								SysConstant.CACHE_RELATE.COORD_SET, 1 + "");

						Constant.COOREDINATE_SET = 1;
					} else {
						appContext.acache.put(
								SysConstant.CACHE_RELATE.COORD_SET, 5 + "");

						Constant.COOREDINATE_SET = 5;
					}
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_geo_sys.setText(getString(text2));
					if (num == 4) {
						appContext.acache.put(
								SysConstant.CACHE_RELATE.COORD_SET, 2 + "");

						Constant.COOREDINATE_SET = 2;
					} else {
						appContext.acache.put(
								SysConstant.CACHE_RELATE.COORD_SET, 6 + "");

						Constant.COOREDINATE_SET = 6;
					}
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_geo_sys.setText(getString(text3));
					if (num == 4) {
						appContext.acache.put(
								SysConstant.CACHE_RELATE.COORD_SET, 3 + "");

						Constant.COOREDINATE_SET = 3;
					} else {
						appContext.acache.put(
								SysConstant.CACHE_RELATE.COORD_SET, 7 + "");

						Constant.COOREDINATE_SET = 7;
					}
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_geo_sys.setText(getString(text4));
					if (num == 4) {
						appContext.acache.put(
								SysConstant.CACHE_RELATE.COORD_SET, 4 + "");

						Constant.COOREDINATE_SET = 4;
					} else {
						appContext.acache.put(
								SysConstant.CACHE_RELATE.COORD_SET, 5 + "");

						Constant.COOREDINATE_SET = 5;
					}
					mPopupWindow.dismiss();
				}
			}, num, text1, text2, text3, text4);
		} else if (v == text_ellipse_set) {
			showPop(text_ellipse, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_ellipse.setText(getString(R.string.ellipse_1));
					appContext.acache.put(SysConstant.CACHE_RELATE.CIRE_TYPE,
							1 + "");
					Constant.CIRE_TYPE_SET = 1;
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_ellipse.setText(getString(R.string.ellipse_2));
					appContext.acache.put(SysConstant.CACHE_RELATE.CIRE_TYPE,
							2 + "");
					Constant.CIRE_TYPE_SET = 2;
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					text_ellipse.setText(getString(R.string.ellipse_3));
					appContext.acache.put(SysConstant.CACHE_RELATE.CIRE_TYPE,
							3 + "");
					Constant.CIRE_TYPE_SET = 3;
					mPopupWindow.dismiss();
				}
			}, null, 3, R.string.ellipse_1, R.string.ellipse_2,
					R.string.ellipse_3, R.string.ellipse_3);
		} else if (v == text_unit_set) {
			showPop(text_unit,
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							appContext.acache.put(
									SysConstant.CACHE_RELATE.UNIT, 3 + "");
							Constant.UNIT_SET = 3;
							text_unit.setText(getString(R.string.unit_1));
							mPopupWindow.dismiss();
						}
					},
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							text_unit.setText(getString(R.string.unit_2));
							appContext.acache.put(
									SysConstant.CACHE_RELATE.UNIT, 2 + "");
							Constant.UNIT_SET = 2;
							mPopupWindow.dismiss();
						}
					},
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							text_unit.setText(getString(R.string.unit_3));
							appContext.acache.put(
									SysConstant.CACHE_RELATE.UNIT, 1 + "");
							Constant.UNIT_SET = 1;
							mPopupWindow.dismiss();
						}
					}, null, 3, R.string.unit_1, R.string.unit_2,
					R.string.unit_3,
					R.string.unit_3);
		}
		Log.v("gis", Constant.COOREDINATE_SET
				+ "Constant.COOREDINATE_SET2222222222222");
	}

	private void showPop(View view1, OnClickListener click1,
			OnClickListener click2, OnClickListener click3,
			OnClickListener click4, int num, int res1, int res2, int res3,
			int res4) {
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
			TextView text_4 = (TextView) view.findViewById(R.id.text_4);
			Log.v("gis", "dddd" + res1);
			if (num == 4) {
				text_1.setVisibility(View.VISIBLE);
				text_2.setVisibility(View.VISIBLE);
				text_3.setVisibility(View.VISIBLE);
				text_4.setVisibility(View.VISIBLE);
			} else if (num == 3) {
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
			text_4.setText(getString(res4));

			text_1.setOnClickListener(click1);
			text_2.setOnClickListener(click2);
			text_3.setOnClickListener(click3);
			text_4.setOnClickListener(click4);

		}
	}

}
