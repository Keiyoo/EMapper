package com.emapper.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.emapper.adapter.ExitNoteAdapter;
import com.emapper.entity.MeasureLineEntity;
import com.emapper.util.Constant;
import com.emapper.util.MapHelper;
/**
 * 面积测量里  已存记录的列表界面
 * 
 */
public class NotesActivity extends BaseActivity implements OnItemClickListener{
private ExitNoteAdapter adapter;
private Button btn_back;
private List<MeasureLineEntity>list;
private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EApplication.getInstance().addActivity(this);

		initView();
	}

	private void initView(){
		setContentView(R.layout.activity_notes);
		TextView text_title = (TextView) findViewById(R.id.tv_title);
		text_title.setText(getString(R.string.title_activity_exiting_note));
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(Constant.RESULT_BACK);
				finish();
			}
		});
		listView=(ListView)findViewById(R.id.listview);
		listView.setOnItemClickListener(this);
		list=new ArrayList<MeasureLineEntity>();
		initDada();
		adapter=new ExitNoteAdapter(this, list);
		listView.setAdapter(adapter);
	}
	private void initDada(){

		if(MapHelper.getMeasureLineList(EApplication.getInstance().getWorkspace())!=null){
			list=MapHelper.getMeasureLineList(EApplication.getInstance().getWorkspace());
		}

		

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==Constant.REQUEST_EXIT_NOTE){
			if(resultCode==Constant.RESULT_EXIT_NOTE_BACK){
				//更新列表
				initDada();
				adapter.setList(list);
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		MeasureLineEntity entity=list.get(position);
		Intent intent=new Intent();
		intent.setClass(this, ExitingNoteActivity.class);
		intent.putExtra("entity", entity);
		startActivityForResult(intent, Constant.REQUEST_EXIT_NOTE);
	}
}
