package com.emapper.adapter;

import java.math.BigDecimal;
import java.util.List;

import com.emapper.activity.R;
import com.emapper.entity.ShippingPointEntity;
import com.emapper.entity.ShippingLineEntity;
import com.emapper.entity.MainEntity;
import com.emapper.entity.PointEntity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PointDataAdapter extends BaseAdapter {
	private Context context;
	private List<ShippingPointEntity> list;
	private List<ShippingLineEntity> list_line;
	private LayoutInflater inflater;
	private boolean isPoint;

	public PointDataAdapter(Context context, List<ShippingPointEntity> list,
			List<ShippingLineEntity> list_line, boolean isPoint) {
		this.list = list;
		this.list_line = list_line;
		this.isPoint = isPoint;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (isPoint) {
			return list.size();
		} else {
			return list_line.size();
		}

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

	public void setList(List<ShippingPointEntity> list,List<ShippingLineEntity> list_line) {
		if (isPoint) {
			this.list = list;
		}else {
			this.list_line=list_line;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_point_data, null);
			holder.text_disance = (TextView) convertView
					.findViewById(R.id.text_distance);
			holder.text_name = (TextView) convertView
					.findViewById(R.id.text_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (isPoint) {
			ShippingPointEntity entity = list.get(position);
			if(entity.distance!=-1){
				holder.text_disance.setText(new BigDecimal(entity.distance).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
			}
			
			holder.text_name.setText(entity.name);
		} else {
			ShippingLineEntity entity_line = list_line.get(position);
			holder.text_disance.setText(entity_line.num+"");
			holder.text_name.setText(entity_line.name);
		}
		return convertView;
	}

	static class ViewHolder {
		TextView text_name;
		TextView text_disance;

	}
}
