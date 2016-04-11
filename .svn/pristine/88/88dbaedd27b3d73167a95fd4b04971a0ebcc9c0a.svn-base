package com.emapper.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.gis.Point2D;
import com.emapper.adapter.PointDataAdapter;
import com.emapper.entity.ShippingPointEntity;
import com.emapper.util.CalculateUtils;
import com.emapper.util.CharacterParser;
import com.emapper.util.Constant;
import com.emapper.util.MapHelper;
import com.emapper.util.PinyinComparator;

/**
 * 航点管理 的第一个界面 已存航点界面
 * 
 */
public class PointManagerActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	private Button btn_back;
	private ListView listview;
	private List<ShippingPointEntity> list;
	private PointDataAdapter adapter;
	private EditText edit_search;
	private ImageView img_cancel;
	private ImageView img_add;
	private TextView text_search;
	private TextView text_result;
	private String type = null;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		type = getIntent().getStringExtra("type");
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_point_manager);
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_point_exist));
		btn_back = (Button) findViewById(R.id.btn_back);
		// img_add = (ImageView) findViewById(R.id.img_add);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		text_result = (TextView) findViewById(R.id.text_result);
		listview = (ListView) findViewById(R.id.listview);
		listview.setEmptyView(text_result);
		listview.setOnItemClickListener(this);
		// 实例化汉字转拼音类
		list = new ArrayList<ShippingPointEntity>();

		initData();

		adapter = new PointDataAdapter(this, list, null, true);
		listview.setAdapter(adapter);
		edit_search = (EditText) findViewById(R.id.edit_search);
		img_cancel = (ImageView) findViewById(R.id.img_cancel);
		img_cancel.setOnClickListener(this);
		text_search = (TextView) findViewById(R.id.text_search);
		text_search.setOnClickListener(this);
		edit_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence text, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (!text.toString().equals("")) {
					img_cancel.setVisibility(View.VISIBLE);
				} else {
					img_cancel.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void updataList() {
		initData();
		adapter.setList(list, null);
		this.adapter.notifyDataSetChanged();
	}

	private void initData() {
		if (MapHelper.getShippingPtList(EApplication.getInstance()
				.getWorkspace()) != null) {
			list = MapHelper.getShippingPtList(EApplication.getInstance()
					.getWorkspace());
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).distance = CalculateUtils.getDistance(new Point2D(list
					.get(i).lon, list.get(i).lat));
			Log.v("gis", list.get(i).isUsered+"ooooooooooooooo");
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		// TODO Auto-generated method stub
		ShippingPointEntity entity = list.get(position);
		Log.v("gis", entity.smid + entity.name + type + "entity.id11111");
		if (type.equals("newline")) {
			Intent data = new Intent();
			data.putExtra("entity", entity);
//			entity.isUsered = true;
//			MapHelper.editShippingPt(EApplication.getInstance().getWorkspace(),
//					entity);
			setResult(Constant.RESULT_NEWLIINE, data);
			finish();
		} else if (type.equals("main")) {
			Intent intent = new Intent();
			intent.setClass(this, PointActivity.class);
			intent.putExtra("entity", entity);
			startActivityForResult(intent, Constant.RESULT_MANAGER);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.RESULT_MANAGER) {
			if (resultCode == Constant.RESULT_DELETE_ONE
					|| resultCode == Constant.RESULT_BACK) {
				updataList();
			} else if (resultCode == Constant.RESULT_SORT_NAME) {

				List<ShippingPointEntity> newlist = list;

				list = filledData(newlist);
				// 根据a-z进行排序源数据
				Collections.sort(list, pinyinComparator);
				adapter.setList(list, null);
				this.adapter.notifyDataSetChanged();
			} else if (resultCode == Constant.RESULT_SORT_DISTANCE) {
				// 距离排序
				ShippingPointEntity temp;
				for (int i = 0; i < list.size(); i++) {
					for (int j = i + 1; j < list.size(); j++) {
						if (list.get(i).distance < list.get(j).distance) {
							temp = list.get(i);
							list.set(i, list.get(j));
							list.set(j, temp);
						}
					}
				}
				adapter.setList(list, null);
				this.adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == img_cancel) {
			edit_search.setText("");
			img_cancel.setVisibility(View.GONE);
		} else if (v == text_search) {
			list = MapHelper.queryShippingPts(EApplication.getInstance()
					.getWorkspace(), edit_search.getText().toString());
			for (int i = 0; i < list.size(); i++) {
				list.get(i).distance = CalculateUtils.getDistance(new Point2D(
						list.get(i).lon, list.get(i).lat));
			}

			adapter.setList(list, null);
			this.adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */

	private List<ShippingPointEntity> filledData(List<ShippingPointEntity> list) {
		List<ShippingPointEntity> newlist = new ArrayList<ShippingPointEntity>();

		String name;

		ShippingPointEntity entity;

		for (int i = 0; i < list.size(); i++) {
			entity = list.get(i);
			// 汉字转换成拼音
			name = entity.name;
			String pinyin = characterParser.getSelling(name);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				entity.sortLetters = sortString.toUpperCase();
			} else {
				entity.sortLetters = "#";
			}
			newlist.add(entity);
		}
		return newlist;

	}

}
