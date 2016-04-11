package com.emapper.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 长度面积计算菜单页面
 * 
 */
public class AreaMeasurementActivity extends BaseActivity implements
		OnClickListener {
	private Button btn_back;
	private TextView text_measure;
	private TextView text_read;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		EApplication.getInstance().addActivity(this);

	}

	private void initView() {
		setContentView(R.layout.activity_area_measurement);

		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_area_measurement));

		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		text_read = (TextView) findViewById(R.id.text_read);
		text_read.setOnClickListener(this);
		text_measure = (TextView) findViewById(R.id.text_measure);
		text_measure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == text_measure) {
			starActivity(this, MeasureActivity.class, "AreaMeasurementActivity");
		} else if (v == text_read) {
			openActivity(this, NotesActivity.class);
		}
	}

}
