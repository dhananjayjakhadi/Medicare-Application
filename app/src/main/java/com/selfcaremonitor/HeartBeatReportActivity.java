package com.selfcaremonitor;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.selfcaremonitor.adapters.HeartBeatAdapter;
import com.selfcaremonitor.data_model.HeartBeatItem;
import com.selfcaremonitor.utilities.DBHelper;

import java.util.ArrayList;

public class HeartBeatReportActivity extends AppCompatActivity {
    DBHelper db;
    HeartBeatAdapter adapter;
    ArrayList<HeartBeatItem> list=new ArrayList<>();
    ListView lvHeartBeatReport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_beat_report);
        lvHeartBeatReport=(ListView)findViewById(R.id.lvHeartBeatReport);
        adapter=new HeartBeatAdapter(this,list);
        lvHeartBeatReport.setAdapter(adapter);

        db=new DBHelper(this);
        Cursor cursor=db.getHearReport(db.getId());
        if(cursor.moveToFirst()){
            do {
                String report_id = cursor.getString(cursor.getColumnIndex("report_id"));
                String heart_beats = cursor.getString(cursor.getColumnIndex("heart_beats"));
                String state = cursor.getString(cursor.getColumnIndex("member_position"));
                String report_date = cursor.getString(cursor.getColumnIndex("report_date"));
                adapter.add(new HeartBeatItem(report_id, heart_beats, state, report_date));
            }while(cursor.moveToNext());
        }else{
            Toast.makeText(this, "No report to show", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
