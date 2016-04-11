package com.emapper.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.emapper.entity.MeasureLineEntity;
import com.emapper.util.Constant;
import com.emapper.util.Files;
import com.emapper.util.MapHelper;
import com.emapper.util.SysConstant;
import com.emapper.util.XmlParser;
/**
 * 面积测量中 对已存记录的管理界面
 * 
 */
public class ExitingNoteActivity extends BaseActivity implements
		OnClickListener {
	private Button btn_back;
	private TextView text_look;
	private TextView text_delect;
	private TextView text_delete_all;
	private TextView text_save_sd;
	private MeasureLineEntity entity;
	private XmlParser xmlParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		entity = (MeasureLineEntity) getIntent().getSerializableExtra("entity");
//		entity.geoline=MapHelper.getMeasureLineGeo(EApplication.getInstance().getWorkspace(),
//				entity.smid);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_exiting_note);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_exiting_note));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(Constant.RESULT_EXIT_NOTE_BACK);
				finish();
			}
		});
		text_look = (TextView) findViewById(R.id.text_look);
		text_look.setOnClickListener(this);
		text_delect = (TextView) findViewById(R.id.text_delect);
		text_delect.setOnClickListener(this);
		text_delete_all = (TextView) findViewById(R.id.text_delete_all);
		text_delete_all.setOnClickListener(this);
		text_save_sd = (TextView) findViewById(R.id.text_save_sd);
		text_save_sd.setOnClickListener(this);
		xmlParser=new XmlParser();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == text_look) {
			Intent intent =new Intent();
			intent.setClass(this, MeasureActivity.class);
			intent.putExtra("entity", entity);
			Log.v("gis", entity.smid + "entity.smid");
			intent.putExtra(SysConstant.SHIPLINE_TYPE.TYPE, SysConstant.AREA_TYPE.EXITING);
			startActivity(intent);
		} else if (v == text_delect) {
			showDialog(getString(R.string.title),
					getString(R.string.content13),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							boolean a = MapHelper.deleteMeasureLine(
									EApplication.getInstance().getWorkspace(),
									entity.smid);
							setResult(Constant.RESULT_EXIT_NOTE_BACK);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							dialog.dismiss();
						}
					});

		} else if (v == text_delete_all) {
			showDialog(getString(R.string.title),
					getString(R.string.content14),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							boolean a = MapHelper.clearMeasureLine(EApplication
									.getInstance().getWorkspace());
							setResult(Constant.RESULT_EXIT_NOTE_BACK);
							finish();
						}
					}, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							dialog.dismiss();
						}
					});
		} else if (v == text_save_sd) {
			try {
				String text=xmlParser.serialize(entity);
				Files.saveToSD(text, entity.name);
				Toast.makeText(ExitingNoteActivity.this, getString(R.string.content15), Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
