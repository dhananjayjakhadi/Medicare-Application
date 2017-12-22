package com.selfcaremonitor;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.selfcaremonitor.adapters.VisionAdapter;
import com.selfcaremonitor.data_model.VisionItem;
import com.selfcaremonitor.utilities.DBHelper;

import java.util.ArrayList;

public class VisionReportActivity extends AppCompatActivity {
    DBHelper db;
    VisionAdapter adapter;
    ArrayList<VisionItem> list=new ArrayList<>();
    ListView lvVisionReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_report);
        lvVisionReport=(ListView)findViewById(R.id.lvVisionReport);
        adapter=new VisionAdapter(this,list);
        lvVisionReport.setAdapter(adapter);

        db=new DBHelper(this);
        Cursor cursor=db.getVisionReport(db.getId());
        if(cursor.moveToFirst()){
            do{
                String report_id=cursor.getString(cursor.getColumnIndex("report_id"));
                String vision_decimal=cursor.getString(cursor.getColumnIndex("vision_decimal"));
                String vision_myopia_status=cursor.getString(cursor.getColumnIndex("vision_myopia_status"));
                String vision_myopia_report=cursor.getString(cursor.getColumnIndex("vision_myopia_report"));
                String vision_report=cursor.getString(cursor.getColumnIndex("vision_report"));
                String report_date=cursor.getString(cursor.getColumnIndex("vision_report_date"));
                adapter.add(new VisionItem(report_id,vision_decimal,vision_myopia_status,vision_myopia_report,vision_report,report_date));
            }while (cursor.moveToNext());
        }else{
            Toast.makeText(this, "No report to show", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
