package com.emapper.adapter;

import java.util.List;

import com.emapper.activity.R;
import com.emapper.adapter.PointDataAdapter.ViewHolder;
import com.emapper.entity.PointEntity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ManagerAdapter extends BaseAdapter{
	private Context context;
	private List<String> list;
	private LayoutInflater inflater;
public ManagerAdapter(Context context, List<String> list){
	this.list = list;
	
	inflater = LayoutInflater.from(context);
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_manager, null);
			holder.text = (TextView) convertView
					.findViewById(R.id.text_text);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String text = list.get(position);
		holder.text .setText(text);
		
		return convertView;
	}
	static class ViewHolder {
		TextView text;

	}
}
