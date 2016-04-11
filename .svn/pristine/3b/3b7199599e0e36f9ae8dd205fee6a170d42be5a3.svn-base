package com.emapper.activity;

import java.util.ArrayList;
import java.util.List;

import com.emapper.adapter.GridViewAdapter;
import com.emapper.entity.MainEntity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 设置主界面
 * 
 */
public class SetActivity extends BaseActivity implements OnItemClickListener {
	private GridView gridview;
	private List<MainEntity> list;
	private GridViewAdapter adapter;
	private EApplication appliation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appliation = EApplication.getInstance();
		appliation.addActivity(this);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_set);
		gridview = (GridView) findViewById(R.id.gridview_icon);
		gridview.setOnItemClickListener(this);
		initGridView();
		adapter = new GridViewAdapter(this, list);
		gridview.setAdapter(adapter);
	}

	private void initGridView() {
		list = new ArrayList<MainEntity>();
		// list.add(new MainEntity(R.drawable.set_1, getString(R.string.set1)));
		// list.add(new MainEntity(R.drawable.set_2, getString(R.string.set2)));
		list.add(new MainEntity(R.drawable.set_3, getString(R.string.set3)));
		list.add(new MainEntity(R.drawable.set_4, getString(R.string.set4)));
		list.add(new MainEntity(R.drawable.set_5, getString(R.string.set5)));
		// list.add(new MainEntity(R.drawable.set_6, getString(R.string.set6)));
		// list.add(new MainEntity(R.drawable.set_7, getString(R.string.set7)));
		list.add(new MainEntity(R.drawable.set_8, getString(R.string.set8)));

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		MainEntity entity = list.get(position);
		if (entity.text.equals(getString(R.string.set1))) {

		} else if (entity.text.equals(getString(R.string.set2))) {

		} else if (entity.text.equals(getString(R.string.set3))) {
			// 跳转到坐标设置
			openActivity(this, CoordinateSetActivity.class);
		} else if (entity.text.equals(getString(R.string.set4))) {
			// 跳转到单位设置
			openActivity(this, UnitSetActivity.class);
		} else if (entity.text.equals(getString(R.string.set5))) {
			// 跳转到地图设置
			openActivity(this, MapSetActivity.class);
		} else if (entity.text.equals(getString(R.string.set6))) {

		} else if (entity.text.equals(getString(R.string.set7))) {

		} else if (entity.text.equals(getString(R.string.set8))) {
			// 退出
			appliation.exitApp();
		}
	}
}
