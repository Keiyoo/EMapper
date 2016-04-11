package com.emapper.activity;

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
import com.emapper.entity.TrailEntity;
import com.emapper.util.Constant;
import com.emapper.util.MapHelper;
import com.emapper.util.SysConstant;
/**
 * 航迹管理的 管理界面
 * 
 */
public class PathManagerSecondActivity extends BaseActivity implements
		OnItemClickListener {
	private Button btn_back;
	private ListView listview;
	private List<String> list;
	private ManagerAdapter adapter;
	private TrailEntity entity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		entity = (TrailEntity) getIntent().getSerializableExtra("entity");
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_path_manager_second);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title
				.setText(getString(R.string.title_activity_path_manager_first));

		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(Constant.RESULT_PATH_MANAGER2_BACK);
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
		list.add(getString(R.string.path_change));
		list.add(getString(R.string.point_navigation));
		list.add(getString(R.string.point_map));
		list.add(getString(R.string.point_delete_one));
		list.add(getString(R.string.point_delete_all));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		String text = list.get(position);
		if (text.equals(getString(R.string.path_change))) {

		} else if (text.equals(getString(R.string.point_navigation))) {
			Intent intent = new Intent();
			intent.setClass(this, MapActivity.class);
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE, SysConstant.PATH_TYPE.PATHMANAGERNAV);
			startActivity(intent);
		} else if (text.equals(getString(R.string.point_map))) {
			Intent intent = new Intent();
			intent.setClass(this, MapActivity.class);
			intent.putExtra("id", entity.getId());
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE,SysConstant.PATH_TYPE.PATHMANAGERMAP);
			startActivity(intent);
		} else if (text.equals(getString(R.string.point_delete_one))) {
			showDialog(getString(R.string.title), getString(R.string.content8),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							MapHelper.deleteShippingTrail(EApplication
									.getInstance().getWorkspace(), entity
									.getId());
							setResult(Constant.RESULT_PATH_MANAGER2_BACK);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

		} else if (text.equals(getString(R.string.point_delete_all))) {
			showDialog(getString(R.string.title), getString(R.string.content9),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							MapHelper.clearShippingTrail(EApplication.getInstance()
									.getWorkspace());
							setResult(Constant.RESULT_PATH_MANAGER2_BACK);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			
		}
	}

}
