package com.emapper.activity;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emapper.entity.ShippingPointEntity;
import com.emapper.util.Constant;
import com.emapper.util.MapHelper;
import com.emapper.util.StrUtils;
import com.emapper.util.SysConstant;
import com.emapper.util.TransferUtils;
/**
 * 标定航点界面
 * 
 */
public class CalibrationPointActivity extends BaseActivity implements
		OnClickListener {
	private Button btn_back;
	private EditText edit_name;
	private EditText edit_lon;
	private EditText edit_lat;
	private EditText edit_high;
	private EditText edit_precision;
	private EditText edit_time;
	private EditText edit_remark;
	private ImageView img_cancel;
	private TextView text_navigation;
	private TextView text_map;
	private TextView text_picture;
	private Calendar calendar;
	private String name;
	private ShippingPointEntity entity;
	private String type;
	private String image;
	private boolean isSave;
	private int id;
	private EApplication appContext;
	private double lon;
	private double lat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		type = getIntent().getStringExtra(SysConstant.SHIPLINE_TYPE.TYPE);

		if (type.equals(SysConstant.POSITION_TYPE.MAIN)) {
			entity = null;
		} else if (type.equals(SysConstant.POSITION_TYPE.EDIT)) {
			entity = (ShippingPointEntity) getIntent().getSerializableExtra(
					"entity");
			image = entity.image;
			id = entity.smid;
		}
		appContext = EApplication.getInstance();
		initView();

	}

	private void initView() {
		setContentView(R.layout.activity_calibration_point);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.calibration_point));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (lon == 0 || lat == 0
						|| (edit_high.getText().toString().equals(""))
						|| (edit_precision.getText().toString().equals(""))) {
					showDialog(getString(R.string.title),
							getString(R.string.content),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							}, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									finish();
								}
							});
				} else {
					showDialog(getString(R.string.title),
							getString(R.string.content1),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									getData();
									if (type.equals(SysConstant.POSITION_TYPE.EDIT)) {
										MapHelper.editShippingPt(EApplication
												.getInstance().getWorkspace(),
												entity);
										finish();
									} else if (type.equals(SysConstant.POSITION_TYPE.MAIN)) {
										MapHelper.addShippingPt(EApplication
												.getInstance().getWorkspace(),
												entity);
									}
									finish();
								}
							}, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									finish();
								}
							});
				}

			}
		});
		edit_name = (EditText) findViewById(R.id.edit_name);
		//
		int num = MapHelper.getLastShippingPtID(EApplication.getInstance()
				.getWorkspace());
		name = "WPT" + StrUtils.convertStr(num + 1);
		edit_name.setHint(name);

		edit_lon = (EditText) findViewById(R.id.edit_lon);
		edit_lat = (EditText) findViewById(R.id.edit_lan);
		edit_high = (EditText) findViewById(R.id.edit_high);
		edit_precision = (EditText) findViewById(R.id.edit_precision);
		edit_time = (EditText) findViewById(R.id.edit_time);
		edit_remark = (EditText) findViewById(R.id.edit_remark);
		img_cancel = (ImageView) findViewById(R.id.img_cancel);
		img_cancel.setOnClickListener(this);
		text_navigation = (TextView) findViewById(R.id.text_navigation);
		text_map = (TextView) findViewById(R.id.text_map);
		text_picture = (TextView) findViewById(R.id.text_picture);
		// text_save = (TextView) findViewById(R.id.text_savepoint);
		text_navigation.setOnClickListener(this);
		text_map.setOnClickListener(this);
		text_picture.setOnClickListener(this);
//		unitType = appContext.acache.getAsString(SysConstant.CACHE_RELATE.UNIT);
//		if (unitType == null||unitType.equals("")) {
//			unitType = getString(R.string.unit_1);
//		}
		if (type.equals(SysConstant.POSITION_TYPE.EDIT)) {
			Log.v("gis", "type" + type);
			initData();
		} else if (type.equals(SysConstant.POSITION_TYPE.MAIN)) {
			if (appContext.naviGPS != null) {
//				lon = EApplication.getInstance().getCurrentLoction()
//						.getLongitude();
//				lat = EApplication.getInstance().getCurrentLoction()
//						.getLatitude();
				lon=appContext.naviGPS.dLongitude;
				lat=appContext.naviGPS.dLatitude;
				// edit_lon.setText(EApplication.getInstance().getCurrentLoction().getLongitude()+"");
				// edit_lat.setText(EApplication.getInstance().getCurrentLoction().getLatitude()+"");
				edit_lon.setText(TransferUtils.positionParser(Constant.UNIT_SET, lon));
				edit_lat.setText(TransferUtils.positionParser(Constant.UNIT_SET, lat));
				edit_high.setText(TransferUtils.speedParser(Constant.LENGTH_SET, appContext.naviGPS.dAltitude)+ "");
				edit_precision.setText(TransferUtils.speedParser(Constant.LENGTH_SET,appContext.naviGPS.dAccuracy)+ "");
				edit_time.setText(TransferUtils.getTime2(appContext.naviGPS.lTime));
			}else{
				Log.v("gis", "naviGPS!=null1111111111111111111");
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.REQUEST_TOPICTURE) {
			if (resultCode == Constant.REQUEST_BACKPICTURE) {
				image = data.getStringExtra("image");

			}
		}
	}

	
	private void initData() {
		Log.v("gis", "typeinit" + type);
		lon = entity.lon;
		lat = entity.lat;
		// edit_lon.setText(String.valueOf(entity.lon));
		// edit_lat.setText(String.valueOf(entity.lat));
		edit_lon.setText(TransferUtils.positionParser(Constant.UNIT_SET, lon));
		edit_lat.setText(TransferUtils.positionParser(Constant.UNIT_SET, lat));
		edit_high.setText(TransferUtils.speedParser(Constant.LENGTH_SET,entity.high));
		edit_precision.setText(TransferUtils.speedParser(Constant.LENGTH_SET,entity.precision));
		edit_time.setText(TransferUtils.getTime(entity.time));
		edit_remark.setText(entity.remark);
		edit_name.setText(entity.name);

	}

	private void getData() {
		entity = new ShippingPointEntity();
		// entity.lon = Double.valueOf(edit_lon.getText().toString());
		// entity.lat = Double.valueOf(edit_lat.getText().toString());
		entity.lon = lon;
		entity.lat = lat;
		if (edit_name.getText().toString().equals("")) {
			entity.name = name;
		} else {
			entity.name = edit_name.getText().toString();
		}
		entity.high = Double.valueOf(edit_high.getText().toString());
		entity.precision = Double.valueOf(edit_precision.getText().toString());
		entity.remark = edit_remark.getText().toString();
		entity.time = TransferUtils.getLongTime();
		entity.image = image;
		entity.smid = id;
		entity.isUsered=false;

	}

	@Override
	public void onClick(View v) {

		if (v == img_cancel) {

		} else if (v == text_picture) {// 查看照片
			Intent intent = new Intent();
			intent.setClass(this, TakePictureActivity.class);
			intent.putExtra("image", image);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE, SysConstant.POSITION_TYPE.CALIBRATION);
			startActivityForResult(intent, Constant.REQUEST_TOPICTURE);

		} else if (v == text_navigation) {// 导航
			starActivity(this, MapActivity.class, SysConstant.POSITION_TYPE.CALINAV);
		} else if (v == text_map) {// 地图
		// if ((edit_lon.getText().toString().equals(""))
		// || (edit_lat.getText().toString().equals(""))
		// || (Double.valueOf(edit_lon.getText().toString()) < -180)
		// || (Double.valueOf(edit_lon.getText().toString()) > 180)
		// || (Double.valueOf(edit_lat.getText().toString()) < -90)
		// || (Double.valueOf(edit_lat.getText().toString()) > 90)) {
		// Toast.makeText(this, "请输入正确的信息", Toast.LENGTH_SHORT).show();
			if (lon == 0.0 || lat == 0.0 || lon < -180 || lon > 180
					|| lat < -90 || lat > 90) {
				Toast.makeText(this, "请输入正确的信息", Toast.LENGTH_SHORT).show();
			} else {
				Intent intent = new Intent();
				intent.setClass(this, MapActivity.class);
				intent.putExtra("x",
						lon);
				intent.putExtra("y",
						lat);
				intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE, SysConstant.POSITION_TYPE.CALIMAP);
				startActivity(intent);
			}
		}
	}

}
