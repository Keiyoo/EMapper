package com.emapper.adapter;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emapper.activity.R;
import com.emapper.entity.TrailEntity;

public class PathSaveAdapter extends BaseAdapter{

	private Context context;
	private List<TrailEntity> list;
	private LayoutInflater inflater;
	public PathSaveAdapter(Context context, List<TrailEntity> list) {
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
	public void setList(List<TrailEntity> list){
		this.list=list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_path_save, null);
			holder.text_star = (TextView) convertView
					.findViewById(R.id.text_star);
			holder.text_end = (TextView) convertView
					.findViewById(R.id.text_end);
			holder.text_num = (TextView) convertView
					.findViewById(R.id.text_n);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		TrailEntity entity = list.get(position);
		holder.text_star.setText(getTimestr(entity.getStarttime()));
		holder.text_end.setText(getTimestr(entity.getEndtime()));
		holder.text_num.setText(entity.getIcount()+"");
		return convertView;
	
	}
	public  String getTimestr(long time){
		Calendar dt = Calendar.getInstance();
		dt.setTimeInMillis(time * 1000);

		int hour = dt.get(Calendar.HOUR_OF_DAY);
		int miniute = dt.get(Calendar.MINUTE);
		int second = dt.get(Calendar.SECOND);

		return  hour + ":" + miniute
				+ ":" + second;
	}
	static class ViewHolder {
		TextView text_star;
		TextView text_end;
		TextView text_num;

	}

}
