package com.emapper.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emapper.activity.R;
import com.emapper.entity.MainEntity;

public class GridViewAdapter extends BaseAdapter {
	private Context context;
	private List<MainEntity> list;
	private LayoutInflater inflater;

	public GridViewAdapter(Context context, List<MainEntity> list) {
		this.list = list;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.adapter_main, null);
			holder=new ViewHolder();
			holder.img=(ImageView) convertView.findViewById(R.id.img);
			holder.text=(TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
			
			
					
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		MainEntity entity=list.get(position);
		holder.img.setBackgroundResource(entity.icon);
		holder.text.setText(entity.text);
		return convertView;
	}
	static class ViewHolder{
		ImageView img;
		TextView text;
		
	}
}
