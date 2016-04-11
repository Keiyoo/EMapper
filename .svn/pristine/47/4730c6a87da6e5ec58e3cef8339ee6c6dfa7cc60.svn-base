package com.emapper.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.emapper.adapter.PathSaveAdapter;
import com.emapper.entity.TrailEntity;
import com.emapper.util.Constant;
import com.emapper.util.TransferUtils;
/**
 * 保存航迹界面  
 * 
 */
public class PathSaveActivity extends BaseActivity implements
		OnItemClickListener, OnItemLongClickListener {
	private Button btn_back;
	private ListView listview;
	private List<TrailEntity> list;
	private PathSaveAdapter adapter;
	private EApplication application;
	private TextView text_stat_time;
	private TextView text_end_time;
	private TextView text_length;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (EApplication) getApplication();
		application.addActivity(this);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_path_save);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_path_save));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(Constant.RESULT_PATH_MANAGER_BACK);
				finish();
			}
		});
		listview = (ListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(this);
		listview.setOnItemLongClickListener(this);
		list = new ArrayList<TrailEntity>();
		initData();
		adapter = new PathSaveAdapter(this, list);
		listview.setAdapter(adapter);
		text_stat_time = (TextView) findViewById(R.id.text_stat_time);
		text_end_time = (TextView) findViewById(R.id.text_end_time);
		text_length = (TextView) findViewById(R.id.text_length);
		if (list.size() >= 1) {
			text_stat_time.setText(getString(R.string.star_time)
					+ TransferUtils.getTime2(list.get(0).getStarttime()));
			text_end_time.setText(getString(R.string.end_time)
					+ TransferUtils.getTime2(list.get(0).getStarttime()));
			text_length.setText(getString(R.string.pathlendth)
					+ list.get(0).getLength());
		}
	}

	private void initData() {
		list = application.trailDAO.getData();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).list = application.dao.Find(list.get(i)
						.getStarttime(), list.get(i).getEndtime());
				list.get(i).setIcount(list.get(i).list.size());
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		TrailEntity entity = list.get(position);
		text_stat_time.setText(getString(R.string.star_time)
				+ TransferUtils.getTime2(entity.getStarttime()));
		text_end_time.setText(getString(R.string.end_time)
				+ TransferUtils.getTime2(entity.getStarttime()));
		text_length
				.setText(getString(R.string.pathlendth) + entity.getLength());
	}

	private void updateData() {
		initData();
		adapter.setList(list);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			int position, long arg3) {
		// TODO Auto-generated method stub
		TrailEntity entity = list.get(position);
		Intent intent = new Intent();

		intent.setClass(this, SavePathManagerActivity.class);
		if (entity != null) {
			Log.v("gis", entity.getName() + "ccccccccccccccccc");
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("entity",
			// entity);
			// intent.putExtras(bundle);
			intent.putExtra("entity", entity);
			intent.putExtra("list", (Serializable) list);
			startActivityForResult(intent, Constant.REQUEST_PATH_SAVE);
		}

		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.REQUEST_PATH_SAVE) {
			if (resultCode == Constant.RESULT_PATH_SAVE_BACK) {
				// 更新列表
				updateData();
			}
		}
	}
	
}
