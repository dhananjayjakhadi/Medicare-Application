package com.selfcaremonitor;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.selfcaremonitor.adapters.TemperatureAdapter;
import com.selfcaremonitor.data_model.TemperatureItem;
import com.selfcaremonitor.utilities.DBHelper;

import java.util.ArrayList;

public class StepReportActivity extends AppCompatActivity {
    DBHelper db;
    TemperatureAdapter adapter;
    ArrayList<TemperatureItem> list=new ArrayList<>();
    ListView lvStepReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_report);
        lvStepReport=(ListView)findViewById(R.id.lvStepReport);
        adapter=new TemperatureAdapter(this,list);
        lvStepReport.setAdapter(adapter);

        db=new DBHelper(this);
        Cursor cursor=db.getStepReport(db.getId());
        if(cursor.moveToFirst()){
            do{
                String report_id=cursor.getString(cursor.getColumnIndex("report_id"));
                String steps=""+cursor.getInt(cursor.getColumnIndex("steps"));
                String report_date=cursor.getString(cursor.getColumnIndex("report_date"));
                adapter.add(new TemperatureItem(report_id,steps,report_date));
            }while (cursor.moveToNext());
        }else{
            Toast.makeText(this,"No report to show",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
