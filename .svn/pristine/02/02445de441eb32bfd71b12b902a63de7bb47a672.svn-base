package com.emapper.activity;

import com.emapper.util.Constant;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 地图设置界面
 * 
 */
public class MapSetActivity extends BaseActivity implements OnClickListener {
	private Button btn_back;
	private TextView text_mapfile;
	private ImageView img_mapfile_set;
	private TextView text_off_line;
	private ImageView img_off_line_set;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		initView();
	}

	private void initView(){
		setContentView(R.layout.activity_map_set);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_map_set));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
		text_mapfile = (TextView) findViewById(R.id.text_mapfile);
		img_mapfile_set = (ImageView) findViewById(R.id.img_mapfile_set);
		img_mapfile_set.setOnClickListener(this);
		text_off_line = (TextView) findViewById(R.id.text_off_line);
		img_off_line_set = (ImageView) findViewById(R.id.img_off_line_set);
		img_off_line_set.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==img_mapfile_set){
			Intent intent =new Intent();
			intent.setClass(this, MapSetAddActivity.class);
			intent.putExtra("type", "mapfile");
			startActivityForResult(intent, Constant.REQUEST_MAPSET);
		}else if(v==img_off_line_set){
			Intent intent =new Intent();
			intent.setClass(this, MapSetAddActivity.class);
			intent.putExtra("type", "offline");
			startActivityForResult(intent, Constant.REQUEST_MAPSET);
		}
	}

}
