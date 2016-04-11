package com.emapper.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.emapper.activity.R;
import com.emapper.adapter.GridViewAdapter.ViewHolder;
import com.emapper.entity.MainEntity;
import com.emapper.entity.PathEntity;
import com.emapper.entity.TrailEntity;

public class PathManagerAdapter extends BaseAdapter {
	private Context context;
	private List<TrailEntity> list;
	private LayoutInflater inflater;

	public PathManagerAdapter(Context context, List<TrailEntity> list) {
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
		return 0;
	}

	public void setList(List<TrailEntity> list) {
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_path_manager, null);
			holder = new ViewHolder();
			holder.text_name = (TextView) convertView
					.findViewById(R.id.text_name);
			holder.text_isShow = (TextView) convertView
					.findViewById(R.id.text_isshow);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TrailEntity entity = list.get(position);
		holder.text_name.setText(entity.getName());
		holder.text_isShow.setText("是");
		return convertView;
	}

	static class ViewHolder {
		TextView text_name;
		TextView text_isShow;

	}
}
