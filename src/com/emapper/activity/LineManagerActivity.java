package com.emapper.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.emapper.adapter.ManagerAdapter;
import com.emapper.entity.ShippingLineEntity;
import com.emapper.util.Constant;
import com.emapper.util.MapHelper;
import com.emapper.util.SysConstant;

/**
 * 
 * 航线管理类
 * 主要实现了航线的增删改查，以及地图预览和导航
 *
 */
public class LineManagerActivity extends BaseActivity implements
		OnItemClickListener {
	private Button btn_back;
	private ListView listview;
	private List<String> list;
	private ManagerAdapter adapter;
	private ShippingLineEntity shippingLineEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		shippingLineEntity = (ShippingLineEntity) getIntent()
				.getSerializableExtra("entity");
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_line_manager);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.manager_line));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				setResult(Constant.RESULT_BACK);
				finish();
			}
		});
		listview = (ListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(this);
		list = new ArrayList<String>();
		initData();
		adapter = new ManagerAdapter(this, list);
		listview.setAdapter(adapter);
	}

	private void initData() {
		list.add(getString(R.string.line_add));
		list.add(getString(R.string.line_change));
		list.add(getString(R.string.area_length));
		list.add(getString(R.string.point_map));
		list.add(getString(R.string.point_navigation));
		list.add(getString(R.string.opposite_navigation));
		list.add(getString(R.string.copy_line));
		list.add(getString(R.string.point_delete_one));
		list.add(getString(R.string.point_delete_all));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		String text = list.get(position);
		if (text.equals(getString(R.string.line_add))) {
			Intent intent = new Intent();
			intent.setClass(this, NewLineActivity.class);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,
					SysConstant.SHIPLINE_TYPE.NEW);
			startActivity(intent);
		} else if (text.equals(getString(R.string.line_change))) {
			// 编辑
			Intent intent = new Intent();
			intent.setClass(this, NewLineActivity.class);
			intent.putExtra("entity", shippingLineEntity);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,
					SysConstant.SHIPLINE_TYPE.EDIT);
			startActivity(intent);
		}else if (text.equals(getString(R.string.area_length))){
			// 计算长度面积
			showDialog("距离: "+(new BigDecimal(shippingLineEntity.distance).setScale(2, BigDecimal.ROUND_HALF_UP)) , new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							dialog.dismiss();
						}
					});
		} else if (text.equals(getString(R.string.point_map))) {
			// 地图查看
			Intent intent = new Intent();
			intent.setClass(this, MapActivity.class);
			intent.putExtra("entity", shippingLineEntity);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,
					SysConstant.SHIPLINE_TYPE.LINEMAP);
			startActivity(intent);
		} else if (text.equals(getString(R.string.point_navigation))) {
			// 航线导航
			Intent intent = new Intent();
			intent.setClass(this, MapActivity.class);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,
					SysConstant.SHIPLINE_TYPE.LINENAV);
			startActivity(intent);
		} else if (text.equals(getString(R.string.opposite_navigation))) {
			// 反向导航
		} else if (text.equals(getString(R.string.copy_line))) {
			// 拷贝航线
			Intent intent = new Intent();
			intent.setClass(this, NewLineActivity.class);
			intent.putExtra("entity", shippingLineEntity);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,
					SysConstant.SHIPLINE_TYPE.COPYLINE);
			startActivity(intent);
		} else if (text.equals(getString(R.string.point_delete_one))) {
			showDialog(getString(R.string.title), getString(R.string.content6),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							dialog.dismiss();
							MapHelper.deleteShippingLine(EApplication
									.getInstance().getWorkspace(),
									shippingLineEntity.smid);
							setResult(Constant.RESULT_DELETE_ONE);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
		} else if (text.equals(getString(R.string.point_delete_all))) {
			showDialog(getString(R.string.title), getString(R.string.content7),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							dialog.dismiss();
							MapHelper.clearShippingLine(EApplication
									.getInstance().getWorkspace());
							setResult(Constant.RESULT_DELETE_ONE);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							dialog.dismiss();
						}
					});
		}
	}

}
