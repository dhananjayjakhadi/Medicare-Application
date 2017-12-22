package com.selfcaremonitor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.selfcaremonitor.R;
import com.selfcaremonitor.data_model.TemperatureItem;

import java.util.List;

/**
 * Created by User on 08/10/2017.
 */
public class TemperatureAdapter extends ArrayAdapter<TemperatureItem> {
    Context context;
    class ViewHolder{
        TextView tvReportId;
        TextView tvTemperature;
        TextView tvReportDate;
    }
    public TemperatureAdapter(Context context, List<TemperatureItem> objects) {
        super(context, 0, objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TemperatureItem ti=getItem(position);
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.temperature_item_layout,parent, false);
            holder=new ViewHolder();
            holder.tvReportId=(TextView)convertView.findViewById(R.id.tvReportId);
            holder.tvTemperature=(TextView)convertView.findViewById(R.id.tvTemperature);
            holder.tvReportDate=(TextView)convertView.findViewById(R.id.tvReportDate);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.tvReportId.setText(""+ti.getReport_id());
        holder.tvTemperature.setText(""+ti.getTemperature());
        holder.tvReportDate.setText(""+ti.getReport_date());
        return convertView;
    }
}
