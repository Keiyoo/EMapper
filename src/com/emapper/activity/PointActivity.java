package com.emapper.activity;

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
import android.widget.ListView;
import android.widget.TextView;

import com.emapper.adapter.ManagerAdapter;
import com.emapper.entity.ShippingPointEntity;
import com.emapper.util.Constant;
import com.emapper.util.MapHelper;
import com.emapper.util.StrUtils;
import com.emapper.util.SysConstant;

/**
 * 航点管理的 管理界面
 * 
 */
public class PointActivity extends BaseActivity implements OnItemClickListener {
	private Button btn_back;
	private ListView listview;
	private List<String> list;
	private ManagerAdapter adapter;
	private ShippingPointEntity entity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		entity = (ShippingPointEntity) getIntent().getSerializableExtra(
				"entity");
		initView();
	}

	private void initView() {

		setContentView(R.layout.activity_point);
		TextView text_title = (TextView) findViewById(R.id
				.tv_title);
		text_title.setText(getString(R.string.title_activity_point_manager));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
		list.add(getString(R.string.point_add));
		list.add(getString(R.string.point_change));
		list.add(getString(R.string.point_map));
		list.add(getString(R.string.point_navigation));
		list.add(getString(R.string.point_sort_by_diatance));
		list.add(getString(R.string.point_sort_by_name));
		list.add(getString(R.string.point_delete_one));
		list.add(getString(R.string.point_delete_all));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		String text = list.get(position);
		if (text.equals(getString(R.string.point_add))) {
			Intent intent = new Intent();
			intent.setClass(this, CalibrationPointActivity.class);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,
					SysConstant.POSITION_TYPE.MAIN);
			startActivity(intent);
		} else if (text.equals(getString(R.string.point_change))) {
			Intent intent = new Intent();
			intent.setClass(this, CalibrationPointActivity.class);
			intent.putExtra("entity", entity);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,
					SysConstant.POSITION_TYPE.EDIT);
			startActivity(intent);

		} else if (text.equals(getString(R.string.point_map))) {
			Intent intent = new Intent();
			intent.setClass(this, MapActivity.class);
			intent.putExtra("entity", entity);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,
					SysConstant.POSITION_TYPE.POINTMAP);
			startActivity(intent);
		} else if (text.equals(getString(R.string.point_navigation))) {
			Intent intent = new Intent();
			intent.setClass(this, MapActivity.class);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,
					SysConstant.POSITION_TYPE.POINTNAV);
			startActivity(intent);
		} else if (text.equals(getString(R.string.point_sort_by_diatance))) {
			setResult(Constant.RESULT_SORT_DISTANCE);
			finish();
		} else if (text.equals(getString(R.string.point_sort_by_name))) {
			setResult(Constant.RESULT_SORT_NAME);
			finish();

		} else if (text.equals(getString(R.string.point_delete_one))) {
			String title;
			if (entity.isUsered) {
				title = getString(R.string.content2_1);
			} else {
				title = getString(R.string.content2);
			}
			showDialog(getString(R.string.title), title,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							MapHelper.deleteShippingPt(EApplication
									.getInstance().getWorkspace(), entity.smid);
							setResult(Constant.RESULT_DELETE_ONE);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
		} else if (text.equals(getString(R.string.point_delete_all))) {
			// list.clear();
			String title= getString(R.string.content3);
			
			List<ShippingPointEntity> list =new ArrayList<ShippingPointEntity>();
			if (MapHelper.getShippingPtList(EApplication.getInstance()
					.getWorkspace()) != null) {
				
				list = MapHelper.getShippingPtList(EApplication.getInstance()
						.getWorkspace());
			}
			for (int i = 0; i < list.size(); i++) {
				ShippingPointEntity entity=list.get(i);
				if(entity.isUsered){
					title = getString(R.string.content3_1);
					break;
				}
			}
			
			
			showDialog(getString(R.string.title), title,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							MapHelper.clearShippingPt(EApplication
									.getInstance().getWorkspace());
							setResult(Constant.RESULT_DELETE_ONE);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

		}
	}

}
