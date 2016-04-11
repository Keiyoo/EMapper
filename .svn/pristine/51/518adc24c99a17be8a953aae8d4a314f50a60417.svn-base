package com.emapper.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.emapper.adapter.PathManagerAdapter;
import com.emapper.entity.TrailEntity;
import com.emapper.util.Constant;
import com.emapper.util.MapHelper;
import com.emapper.util.TransferUtils;
/**
 * 航迹管理的第一个界面
 * 
 */
public class PathManagerFirstActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	private Button btn_back;
	private ListView listview;
	private List<TrailEntity> list;
	private PathManagerAdapter adapter;
	private TextView text_save;
	private RadioButton radiobtn_open;
	private RadioButton radiobtn_close;
	private RadioGroup radiogroup;
	private ProgressBar progressBar;
	private TextView text_Percentage;
	private EApplication application;
	private boolean statue;
	private TextView text_clear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (EApplication) getApplication();
		application.addActivity(this);
		initView();
	}

	private CountDownTimer timer = new CountDownTimer(1000, 1000) {
		@Override
		public void onFinish() {
			start();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			refreshProgress();
		}
	};

	private void initView() {
		setContentView(R.layout.activity_path_manager_first);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title
				.setText(getString(R.string.title_activity_path_manager_first));
		text_save = (TextView) findViewById(R.id.text_save);
		text_save.setVisibility(View.VISIBLE);
		text_save.setOnClickListener(this);
		text_clear = (TextView) findViewById(R.id.text_clear);
		text_clear.setOnClickListener(this);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		statue = EApplication.isOpen;
		listview = (ListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(this);
		list = new ArrayList<TrailEntity>();
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		text_Percentage = (TextView) findViewById(R.id.text_Percentage);
		initData();
		adapter = new PathManagerAdapter(this, list);
		listview.setAdapter(adapter);
		radiobtn_open = (RadioButton) findViewById(R.id.radiobtn_open);
		radiobtn_close = (RadioButton) findViewById(R.id.radiobtn_close);
		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				// TODO Auto-generated method stub
				if (radiobtn_open.getId() == checkedId) {
					if (EApplication.isOpen == false) {
						EApplication.isOpen = true;
						timer.start();
						EApplication.trailEntity = new TrailEntity();
						Calendar dt = Calendar.getInstance();
						Long time = dt.getTimeInMillis() / 1000;
						EApplication.trailEntity.setStarttime(time);
						EApplication.trailEntity.setName("T"
								+ TransferUtils.getTime3(time));
					} else {
						timer.start();
					}
				} else if (radiobtn_close.getId() == checkedId) {
					if (EApplication.isOpen) {
						timer.cancel();
						EApplication.isOpen = false;
						Calendar dt = Calendar.getInstance();
						Long time = dt.getTimeInMillis() / 1000;
						EApplication.trailEntity.setEndtime(time);
						application.trailDAO.Add(EApplication.trailEntity);
					}

				}
			}
		});
		if (EApplication.isOpen) {
			radiobtn_open.setChecked(true);
		} else {
			radiobtn_close.setChecked(true);
		}
		refreshProgress();
	}

	private void refreshProgress() {
		int percent = application.dao.GetPercent();
		progressBar.setProgress(percent);
		text_Percentage.setText(percent + "%");
	}

	private void initData() {
		if (MapHelper.getShippingTrailList(EApplication.getInstance()
				.getWorkspace()) != null) {
			list = MapHelper.getShippingTrailList(EApplication.getInstance()
					.getWorkspace());
		}

	}

	// 更新列表
	private void updateData() {
		initData();
		adapter.setList(list);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		TrailEntity entity = list.get(position);
		Intent intent = new Intent();
		intent.setClass(this, PathManagerSecondActivity.class);
		intent.putExtra("entity", entity);
		startActivityForResult(intent, Constant.REQUEST_PATH_MANAGER2);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == text_save) {
			Intent intent = new Intent();
			intent.setClass(this, PathSaveActivity.class);
			startActivityForResult(intent, Constant.REQUEST_PATH_MANAGER);
		} else if (v == text_clear) {
			Log.v("gis", "bbbb");
			if (!radiobtn_open.isChecked()) {
				showDialog(getString(R.string.title),
						getString(R.string.clearmag),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								application.dao.clear();
								application.trailDAO.clear();
							}
						}, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}

						});

			} else {
				Toast.makeText(this, getString(R.string.cleartitle),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.REQUEST_PATH_MANAGER) {
			if (resultCode == Constant.RESULT_PATH_MANAGER_BACK) {
				// 更新列表
				updateData();
			}
		} else if (requestCode == Constant.REQUEST_PATH_MANAGER2) {
			if (resultCode == Constant.RESULT_PATH_MANAGER2_BACK) {
				updateData();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();

	}
}
