package com.emapper.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 地图设置里   地图文件设置  离线文件设置  公用一个界面
 * 
 */
public class MapSetAddActivity extends BaseActivity {
	private Button btn_back;
	private String type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		type=getIntent().getStringExtra("type");
		initView();
	}

	private void initView(){
		setContentView(R.layout.activity_map_set_add);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		if(type.equals("mapfile")){
			text_title.setText(getString(R.string.mapadd_set1));
		}else if(type.equals("offline")){
			text_title.setText(getString(R.string.mapadd_set2));
		}
		
		
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
	}

}
