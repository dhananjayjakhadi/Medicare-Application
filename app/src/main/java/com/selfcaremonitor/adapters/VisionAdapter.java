package com.selfcaremonitor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.selfcaremonitor.R;
import com.selfcaremonitor.data_model.VisionItem;

import java.util.List;

/**
 * Created by User on 09/10/2017.
 */
public class VisionAdapter extends ArrayAdapter<VisionItem> {
    Context context;
    class ViewHolder{
        TextView tvReportId;
        TextView tvVisionDecimal;
        TextView tvMyopiaStatus;
        TextView tvMyopiaReport;
        TextView tvVisionReport;
        TextView tvReportDate;
    }
    public VisionAdapter(Context context, List<VisionItem> objects) {
        super(context, 0, objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        VisionItem vi=getItem(position);
        if(convertView==null){
            LayoutInflater inflater= LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.vision_item_layout,parent,false);
            holder=new ViewHolder();
            holder.tvReportId=(TextView)convertView.findViewById(R.id.tvReportId);
            holder.tvVisionDecimal=(TextView)convertView.findViewById(R.id.tvVisionDecimal);
            holder.tvMyopiaStatus=(TextView)convertView.findViewById(R.id.tvMyopiaStatus);
            holder.tvMyopiaReport=(TextView)convertView.findViewById(R.id.tvMyopiaReport);
            holder.tvVisionReport=(TextView)convertView.findViewById(R.id.tvVisionReport);
            holder.tvReportDate=(TextView)convertView.findViewById(R.id.tvReportDate);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tvReportId.setText(""+vi.getReport_id());
        holder.tvVisionDecimal.setText(""+vi.getVision_decimal());
        holder.tvMyopiaStatus.setText(" "+vi.getVision_myopia_status());
        holder.tvMyopiaReport.setText(""+vi.getVision_myopia_report());
        holder.tvVisionReport.setText(""+vi.getVision_report());
        holder.tvReportDate.setText(""+vi.getReport_date());
        return convertView;

    }
}
