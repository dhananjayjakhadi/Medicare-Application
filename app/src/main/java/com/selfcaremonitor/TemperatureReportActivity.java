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

public class TemperatureReportActivity extends AppCompatActivity {
    DBHelper db;
    TemperatureAdapter adapter;
    ArrayList<TemperatureItem> list=new ArrayList<>();
    ListView lvTemperatureReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_report);
        lvTemperatureReport=(ListView)findViewById(R.id.lvTemperature);
        adapter=new TemperatureAdapter(this,list);
        lvTemperatureReport.setAdapter(adapter);

        db=new DBHelper(this);
        Cursor cursor=db.getTemperatureReport(db.getId());
        if(cursor.moveToFirst()){
            do{
                String report_id=cursor.getString(cursor.getColumnIndex("report_id"));
                String temperature=cursor.getString(cursor.getColumnIndex("temperature"));
                String report_date=cursor.getString(cursor.getColumnIndex("report_date"));
                adapter.add(new TemperatureItem(report_id,temperature,report_date));
            }while (cursor.moveToNext());
        }else{
            Toast.makeText(this, "No report to show", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
