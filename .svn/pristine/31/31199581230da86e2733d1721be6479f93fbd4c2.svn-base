package com.emapper.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.emapper.adapter.PointDataAdapter;
import com.emapper.entity.ShippingLineEntity;
import com.emapper.util.Constant;
import com.emapper.util.MapHelper;
import com.emapper.util.SysConstant.SHIPLINE_TYPE;

/**
 * 
 * 航线列表界面,查询航线
 * 
 */
public class ExitingLineActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	private Button btn_back;
	private ListView listview;
	private List<ShippingLineEntity> list;
	private PointDataAdapter adapter;
	private TextView text_new;
	private EditText edit_search;
	private ImageView img_cancel;
	private TextView text_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_exiting_line);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_exiting_line));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		listview = (ListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(this);
		list = new ArrayList<ShippingLineEntity>();
		initData();
		adapter = new PointDataAdapter(this, null, list, false);
		listview.setAdapter(adapter);
		text_new = (TextView) findViewById(R.id.img_new);
		text_new.setOnClickListener(this);
		edit_search = (EditText) findViewById(R.id.edit_search);
		img_cancel = (ImageView) findViewById(R.id.img_cancel);
		img_cancel.setOnClickListener(this);
		text_search = (TextView) findViewById(R.id.text_search);
		text_search.setOnClickListener(this);
		edit_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence text, int arg1, int arg2,
					int arg3) {

				if (!text.toString().equals("")) {
					img_cancel.setVisibility(View.VISIBLE);
				} else {
					img_cancel.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}

	private void initData() {

		if (MapHelper.getShippingLineList(EApplication.getInstance()
				.getWorkspace()) != null) {
			list = MapHelper.getShippingLineList(EApplication.getInstance()
					.getWorkspace());
		}
	}

	/**
	 * 更新航线列表
	 */
	private void updateList() {
		list = MapHelper.getShippingLineList(EApplication.getInstance()
				.getWorkspace());
		adapter.setList(null, list);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.REQUEST_EXITELINE) {
			if (resultCode == Constant.RESULT_EXITELINE) {
				String type = data.getStringExtra(SHIPLINE_TYPE.TYPE);
				if (type.equals(SHIPLINE_TYPE.SAVE)) {
					// 刷新列表
					updateList();
				} else if (type.equals(SHIPLINE_TYPE.NOSAVE)) {

				}
			}
		} else if (requestCode == Constant.REQUEST_EXITELINEMANAGER) {
			if (resultCode == Constant.RESULT_BACK) {
				// 刷新列表
				updateList();
			} else if (resultCode == Constant.RESULT_DELETE_ONE) {
				updateList();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		ShippingLineEntity entity = list.get(position);
		Intent intent = new Intent();
		intent.setClass(this, LineManagerActivity.class);
		intent.putExtra(SHIPLINE_TYPE.SHIPLINEENTITY, entity);
		startActivityForResult(intent, Constant.REQUEST_EXITELINEMANAGER);
	}

	@Override
	public void onClick(View v) {
		if (v == img_cancel) {
			edit_search.setText("");
			img_cancel.setVisibility(View.GONE);
		} else if (v == text_search) {
			// 查询航线
			list = MapHelper.queryShippingLines(EApplication.getInstance()
					.getWorkspace(), edit_search.getText().toString());
			adapter.setList(null, list);
			this.adapter.notifyDataSetChanged();
		} else if (v == text_new) {
			// 新建航线
			Intent intent = new Intent();
			intent.setClass(ExitingLineActivity.this, NewLineActivity.class);
			intent.putExtra(SHIPLINE_TYPE.TYPE, SHIPLINE_TYPE.NEW);
			startActivityForResult(intent, Constant.REQUEST_EXITELINE);
		} else if (v == btn_back) {
			// 返回
			finish();
		}
	}

}
