package com.emapper.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emapper.activity.R;
import com.emapper.entity.ShippingPointEntity;

public class LineAdapter extends BaseAdapter {
	private Context context;
	private List<ShippingPointEntity> list;
	private LayoutInflater inflater;

	public LineAdapter(Context context, List<ShippingPointEntity> list) {
		this.list = list;

		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public void setList(List<ShippingPointEntity> list) {
		this.list = list;
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
			convertView = inflater.inflate(R.layout.adapter_line, null);
			holder.text = (TextView) convertView.findViewById(R.id.text_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String text = (list.get(position)).name;
		holder.text.setText(text);
		return convertView;
	}

	static class ViewHolder {
		TextView text;

	}

}
