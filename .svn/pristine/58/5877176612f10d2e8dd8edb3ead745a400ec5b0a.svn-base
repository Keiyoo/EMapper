package com.emapper.activity;

import com.emapper.view.ColorPickerDialog;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 采线界面
 * 
 */
public class CollectLineActivity extends BaseActivity implements OnClickListener{
	private Button btn_back;
	private TextView text_save;
	private TextView text_color;
	private ColorPickerDialog dialog;
	private TextView text_line;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		initView();
	}

	private void initView(){
		setContentView(R.layout.activity_coollect_line);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_coollect_line));
		text_save=(TextView) findViewById(R.id.text_save);
		text_save.setVisibility(View.VISIBLE);
		text_save.setOnClickListener(this);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		text_color = (TextView) findViewById(R.id.text_color);
		text_color.setOnClickListener(this);
		text_line = (TextView) findViewById(R.id.text_line);
		text_line.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==text_color){
			dialog = new ColorPickerDialog(this, text_color.getTextColors()
					.getDefaultColor(), getString(R.string.color),
					new ColorPickerDialog.OnColorChangedListener() {

						@Override
						public void colorChanged(int color) {
							text_color.setBackgroundColor(color);
						}
					});
			dialog.show();
		}else if(v==text_line){
			showPop(text_line, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_line.setText(R.string.thicklines);
					mPopupWindow.dismiss();
				}
			}, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					text_line.setText(R.string.thinlines);
					mPopupWindow.dismiss();
				}
			});
		}
	}

}
