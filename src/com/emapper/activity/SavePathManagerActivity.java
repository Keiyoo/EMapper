package com.emapper.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emapper.adapter.ManagerAdapter;
import com.emapper.entity.TrailEntity;
import com.emapper.util.Constant;
import com.emapper.util.MapHelper;
/**
 * 对已存航迹的 一个管理界面
 * 
 */
public class SavePathManagerActivity extends BaseActivity implements
		OnItemClickListener {
	private Button btn_back;
	private ListView listview;
	private List<String> list;
	private ManagerAdapter adapter;
	private TrailEntity entity;
	private List<TrailEntity> traillist;
	private EApplication application;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		entity = (TrailEntity) getIntent().getSerializableExtra("entity");

		traillist = (List<TrailEntity>) getIntent()
				.getSerializableExtra("list");
		application = (EApplication) getApplication();
		application.addActivity(this);

		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_save_path_manager);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_path_save));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

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
		list.add(getString(R.string.set));
		list.add(getString(R.string.savenow));
		list.add(getString(R.string.saveall));
		list.add(getString(R.string.point_delete_one));
		list.add(getString(R.string.point_delete_all));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		String text = list.get(position);
		if (text.equals(getString(R.string.set))) {

		} else if (text.equals(getString(R.string.savenow))) {
			showDialog(getString(R.string.title),
					getString(R.string.content10),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							int issuccess = MapHelper.addShippingTrail(
									EApplication.getInstance().getWorkspace(),
									entity);
							if (issuccess == 0 || issuccess == -1) {
								Toast.makeText(SavePathManagerActivity.this,
										getString(R.string.savesuccss),
										Toast.LENGTH_SHORT).show();
							}
							application.trailDAO.delete(entity.getId());
							setResult(Constant.RESULT_PATH_SAVE_BACK);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

		} else if (text.equals(getString(R.string.saveall))) {
			showDialog(getString(R.string.title), getString(R.string.content8),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							int issuccess;
							int success = 1;
							for (int i = 0; i < traillist.size(); i++) {
								issuccess = MapHelper.addShippingTrail(
										EApplication.getInstance()
												.getWorkspace(), traillist
												.get(i));
								if (issuccess == 0 || issuccess == -1) {
									success = issuccess;
								}
							}
							if (success == 0 || success == -1) {
								Toast.makeText(SavePathManagerActivity.this,
										getString(R.string.saveallsuccss),
										Toast.LENGTH_SHORT).show();
							}
							application.trailDAO.clear();
							setResult(Constant.RESULT_PATH_SAVE_BACK);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

		} else if (text.equals(getString(R.string.point_delete_one))) {
			showDialog(getString(R.string.title), getString(R.string.content8),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							application.trailDAO.delete(entity.getId());
							setResult(Constant.RESULT_PATH_SAVE_BACK);
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
							application.trailDAO.clear();
							setResult(Constant.RESULT_PATH_SAVE_BACK);
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
