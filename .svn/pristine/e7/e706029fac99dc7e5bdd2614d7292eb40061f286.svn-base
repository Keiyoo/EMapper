package com.emapper.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gis.Point2D;
import com.emapper.adapter.LineAdapter;
import com.emapper.entity.ShippingLineEntity;
import com.emapper.entity.ShippingPointEntity;
import com.emapper.util.CalculateUtils;
import com.emapper.util.Constant;
import com.emapper.util.JWD;
import com.emapper.util.MapHelper;
import com.emapper.util.SysConstant.SHIPLINE_TYPE;
import com.emapper.view.ColorPickerDialog;

/**
 * 
 * 新建航线、编辑航线、拷贝航线操作
 * 
 */
public class NewLineActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private Button btn_back;
	private TextView text_line;

	private ListView listview;
	private List<ShippingPointEntity> list;
	private LineAdapter adapter;
	private TextView text_color;
	private ColorPickerDialog dialog;
	private ShippingPointEntity nextPointEntity;
	private ShippingLineEntity lineEntity;
	private EditText edit_name;
	private StringBuffer points = new StringBuffer();
	private String name;
	private String type;
	private String strpoints;
	private int id;
	private String editname;
	private TextView text_navigation;
	private TextView text_map;
	private int linetype;
	private String linecolor = null;
	private TextView text_distance;
	private TextView text_azimuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		// 操作类型
		type = getIntent().getStringExtra(SHIPLINE_TYPE.TYPE);
		// 新建航线
		if (type.equals(SHIPLINE_TYPE.NEW)) {
			lineEntity = null;
		} else if (type.equals(SHIPLINE_TYPE.EDIT)) {
			// 编辑航线
			lineEntity = new ShippingLineEntity();
			lineEntity = (ShippingLineEntity) getIntent().getSerializableExtra(
					SHIPLINE_TYPE.SHIPLINEENTITY);
			id = lineEntity.smid;
			strpoints = lineEntity.strpoints;
			editname = lineEntity.name;
			linetype = lineEntity.linetype;
			linecolor = lineEntity.color;
		} else if (type.equals(SHIPLINE_TYPE.COPYLINE)) {
			// 拷贝航线
			lineEntity = new ShippingLineEntity();
			lineEntity = (ShippingLineEntity) getIntent().getSerializableExtra(
					SHIPLINE_TYPE.SHIPLINEENTITY);
			strpoints = lineEntity.strpoints;
			editname = lineEntity.name;
			linetype = lineEntity.linetype;
			linecolor = lineEntity.color;
		}
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_new_line);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_new_line));
		if (type.equals(SHIPLINE_TYPE.EDIT)) {
			text_title.setText(getString(R.string.line_change));
		}
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		text_navigation = (TextView) findViewById(R.id.text_navigation1);
		text_map = (TextView) findViewById(R.id.text_map1);
		text_map.setOnClickListener(this);
		text_navigation.setOnClickListener(this);
		text_color = (TextView) findViewById(R.id.text_color);
		text_color.setOnClickListener(this);
		edit_name = (EditText) findViewById(R.id.edit_linename);
		text_line = (TextView) findViewById(R.id.text_line);
		text_line.setOnClickListener(this);
		listview = (ListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(this);
		text_distance = (TextView) findViewById(R.id.text_distance);
		text_azimuth = (TextView) findViewById(R.id.text_azimuth);
		list = new ArrayList<ShippingPointEntity>();
		int num = MapHelper.getLastShippingLineID(EApplication.getInstance()
				.getWorkspace());
		name = "Route" + (num + 1);
		edit_name.setHint(name);
		lineEntity = new ShippingLineEntity();
		lineEntity.linetype = 0;
		if (type.equals(SHIPLINE_TYPE.EDIT)
				|| type.equals(SHIPLINE_TYPE.COPYLINE)) {
			initEditData();
			Log.v("gis", type + "editeditedit");
		}
		initData();
		adapter = new LineAdapter(this, list);
		listview.setAdapter(adapter);

	}

	/**
	 * 初始化编辑信息
	 */
	private void initEditData() {
		if (type.equals(SHIPLINE_TYPE.EDIT)) {
			edit_name.setText(editname);
		}
		points.append(strpoints);
		String[] pointid = strpoints.split(",");

		for (int i = 0; i < pointid.length; i++) {
			ShippingPointEntity entity = MapHelper.queryShippingPtByID(
					EApplication.getInstance().getWorkspace(), pointid[i]);
			list.add(entity);
		}
		if (linetype == 0) {
			text_line.setText(R.string.thinlines);
		} else {
			text_line.setText(R.string.thinlines);
		}

		if (linecolor != null && !linecolor.equals("")) {
			text_color.setBackgroundColor(Integer.valueOf(linecolor));
		} else {
			Log.v("gis", linecolor + "linecolor");
		}
	}

	private void initData() {
		nextPointEntity = new ShippingPointEntity();
		nextPointEntity.name = getString(R.string.next);
		list.add(nextPointEntity);
	}

	private void updateList() {
		adapter.setList(list);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.REQUEST_NEWLINE) {
			if (resultCode == Constant.RESULT_NEWLIINE) {
				ShippingPointEntity pointEntity = (ShippingPointEntity) data
						.getSerializableExtra("entity");
				list.add(pointEntity);
				list.remove(list.size() - 2);

				list.add(nextPointEntity);
				points.append(pointEntity.smid + ",");
				updateList();
				// 更新
				if(list.size()<3){
					text_distance.setText("N/A");
					text_azimuth.setText("N/A");
				}else{
					setValue();
				}
			}
		}
	}

	private void setValue() {
		Point2D one = new Point2D(list.get(list.size() - 3).lat, list.get(list
				.size() - 3).lon);
		Point2D two = new Point2D(list.get(list.size() - 2).lat, list.get(list
				.size() - 2).lon);
	//	text_distance.setText(CalculateUtils.getPointsDistance(two, two) + "");
		text_distance.setText(JWD.getDistatce(list.get(list.size() - 3).lon, list.get(list
				.size() - 3).lat, list.get(list.size() - 2).lon, list.get(list
				.size() - 2).lat)+"");
		text_azimuth.setText(JWD.angle(
				new JWD(list.get(list.size() - 3).lon,
						list.get(list.size() - 3).lat),
				new JWD(list.get(list.size() - 2).lon,
						list.get(list.size() - 2).lat))
				+ "");
	}

	@Override
	public void onClick(View v) {
		if (v == text_line) {
			// 设置线型
			showPop(text_line, new OnClickListener() {

				@Override
				public void onClick(View v) {
					text_line.setText(R.string.thicklines);
					linetype = 1;
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					text_line.setText(R.string.thinlines);
					linetype = 0;
					mPopupWindow.dismiss();
				}
			});

		} else if (v == text_color) {
			// 颜色设置
			dialog = new ColorPickerDialog(this, text_color.getTextColors()
					.getDefaultColor(), getString(R.string.color),
					new ColorPickerDialog.OnColorChangedListener() {

						@Override
						public void colorChanged(int color) {
							text_color.setBackgroundColor(color);
							linecolor = String.valueOf(color);
						}
					});
			dialog.show();
		} else if (v == text_map) {
			// 地图上预览航线
			Intent intent = new Intent();
			intent.setClass(this, MapActivity.class);
			intent.putExtra(SHIPLINE_TYPE.TYPE, SHIPLINE_TYPE.NEWLINEMAP);
			List<Point2D> ptslist = new ArrayList<Point2D>();
			for (int i = 0; i < list.size() - 1; i++) {
				ptslist.add(new Point2D(list.get(i).lon, list.get(i).lat));
			}
			intent.putExtra(SHIPLINE_TYPE.LIST, (Serializable) ptslist);
			intent.putExtra(SHIPLINE_TYPE.LINETYPE, linetype);
			intent.putExtra(SHIPLINE_TYPE.LINECOLOR, linecolor);
			startActivity(intent);
		} else if (v == text_navigation) {
			// 航线导航
			Intent intent = new Intent();
			intent.setClass(this, MapActivity.class);
			intent.putExtra(SHIPLINE_TYPE.TYPE, SHIPLINE_TYPE.NEWLINENAV);
			startActivity(intent);
		} else if (v == btn_back) {
			// 保存航线
			saveShipLine();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		if (position == list.size() - 1) {
			Intent intent = new Intent();
			intent.setClass(this, PointManagerActivity.class);
			intent.putExtra(SHIPLINE_TYPE.TYPE, SHIPLINE_TYPE.NEWLINE);
			startActivityForResult(intent, Constant.REQUEST_NEWLINE);
		}
	}

	/**
	 * 保存航线
	 */
	private void saveShipLine() {
		if (list.size() <= 2) {
			showDialog(getString(R.string.title), getString(R.string.content5),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}, new DialogInterface.OnClickListener() {

						// 不保存航线
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent data = new Intent();
							data.putExtra(SHIPLINE_TYPE.TYPE,
									SHIPLINE_TYPE.NOSAVE);
							setResult(Constant.RESULT_EXITELINE, data);
							finish();
						}
					});
		} else {
			showDialog(getString(R.string.title), getString(R.string.content4),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							lineEntity.strpoints = points.toString();
							list.remove(list.size() - 1);
							lineEntity.list = list;
							lineEntity.num = list.size();
							lineEntity.linetype = linetype;
							lineEntity.color = linecolor;
							if (edit_name.getText().toString().equals("")) {
								lineEntity.name = name;
							} else {
								lineEntity.name = edit_name.getText()
										.toString();
							}
							// 当前是新建或者拷贝航线操作则存储新航线
							if (type.equals(SHIPLINE_TYPE.NEW)
									|| type.equals(SHIPLINE_TYPE.COPYLINE)) {
								Log.v("gis", linecolor + "linecolornew");
								int success = MapHelper.addShippingLine(
										EApplication.getInstance()
												.getWorkspace(), lineEntity);
								if (success == 0 || success == -1) {
									Toast.makeText(NewLineActivity.this,
											getString(R.string.savelinesuccss),
											Toast.LENGTH_SHORT).show();
								} else {
									for (int i = 0; i < lineEntity.list.size(); i++) {
										lineEntity.list.get(i).isUsered = true;
										MapHelper.editShippingPt(EApplication
												.getInstance().getWorkspace(),
												lineEntity.list.get(i));
									}
									List<ShippingPointEntity> list1 = MapHelper
											.getShippingPtList(EApplication
													.getInstance()
													.getWorkspace());
									for (int i = 0; i < list1.size(); i++) {
										Log.v("gis", list1.get(i).isUsered
												+ "ppppppppppp");
									}
								}
							} else if (type.equals(SHIPLINE_TYPE.EDIT)) {
								// 编辑航线并存储
								lineEntity.smid = id;
								Log.v("gis", linecolor + "linecolor");
								MapHelper.editShippingLine(EApplication
										.getInstance().getWorkspace(),
										lineEntity);
							}

							Intent data = new Intent();
							data.putExtra(SHIPLINE_TYPE.TYPE,
									SHIPLINE_TYPE.SAVE);
							setResult(Constant.RESULT_EXITELINE, data);
							dialog.dismiss();
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent data = new Intent();
							data.putExtra(SHIPLINE_TYPE.TYPE,
									SHIPLINE_TYPE.NOSAVE);
							setResult(Constant.RESULT_EXITELINE, data);
							finish();
						}
					});
		}
	}

}
