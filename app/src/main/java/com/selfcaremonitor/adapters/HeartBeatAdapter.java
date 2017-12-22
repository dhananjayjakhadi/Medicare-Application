package com.selfcaremonitor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.selfcaremonitor.R;
import com.selfcaremonitor.data_model.HeartBeatItem;

import java.util.List;

/**
 * Created by User on 08/10/2017.
 */
public class HeartBeatAdapter extends ArrayAdapter<HeartBeatItem> {
    Context context;
    class ViewHolder{
        TextView tvReportId;
        TextView tvHeartBeats;
        TextView tvState;
        TextView tvReportDate;
    }
    public HeartBeatAdapter(Context context, List<HeartBeatItem> objects) {
        super(context, 0, objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HeartBeatItem ti=getItem(position);
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.heartbeat_item_layout,parent, false);
            holder=new ViewHolder();
            holder.tvReportId=(TextView)convertView.findViewById(R.id.tvReportId);
            holder.tvHeartBeats=(TextView)convertView.findViewById(R.id.tvHeartBeatRate);
            holder.tvState=(TextView)convertView.findViewById(R.id.tvState);
            holder.tvReportDate=(TextView)convertView.findViewById(R.id.tvReportDate);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.tvReportId.setText(""+ti.getReport_id());
        holder.tvHeartBeats.setText(""+ti.getHeart_beats());
        holder.tvState.setText(""+ti.getState());
        holder.tvReportDate.setText(""+ti.getReport_date());
        return convertView;
    }
}
