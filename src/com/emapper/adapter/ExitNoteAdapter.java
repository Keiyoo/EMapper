package com.emapper.adapter;

import java.util.List;

import com.emapper.activity.R;
import com.emapper.adapter.LineAdapter.ViewHolder;
import com.emapper.entity.MeasureLineEntity;
import com.emapper.entity.ShippingPointEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExitNoteAdapter extends BaseAdapter {
	private Context context;
	private List<MeasureLineEntity> list;
	private LayoutInflater inflater;

	public ExitNoteAdapter(Context context, List<MeasureLineEntity> list) {
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
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setList(List<MeasureLineEntity> list) {
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_exite_note, null);
			holder.text = (TextView) convertView
					.findViewById(R.id.text_notename);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MeasureLineEntity entity = list.get(position);
		holder.text.setText(entity.name);
		return convertView;
	}

	static class ViewHolder {
		TextView text;

	}

}
