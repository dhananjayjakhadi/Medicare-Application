package com.selfcaremonitor;

import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.selfcaremonitor.utilities.DBHelper;

import java.util.Calendar;

public class BodyTemperatureActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    DBHelper db;
    private SensorManager mgr;
    private Sensor temp;
    ImageView ivTempAnim;
    TextView tvReport,tvTemperature,tvSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_temperature);
        tvReport=(TextView)findViewById(R.id.tvReport);
        tvTemperature=(TextView)findViewById(R.id.tvTemperature);
        ivTempAnim=(ImageView)findViewById(R.id.ivTempAnim);
        tvSave=(TextView)findViewById(R.id.tvSave);

        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        temp = mgr.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        Intent intent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        float  temp   = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0)) / 10;
        float fahrenheit = temp * 9 / 5 + 32;
        tvTemperature.setText(String.valueOf(temp) + "\nCelsius ("+fahrenheit+" F)");
        ivTempAnim.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale));

        db=new DBHelper(this);
        tvReport.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mgr.registerListener(this, temp, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mgr.unregisterListener(this, temp);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        for(int i=0;i<event.values.length;i++){
            Log.e("Eve "+i,"Event "+i+" :"+event.values[i]);
        }
        float fahrenheit = event.values[0] * 9 / 5 + 32;
        String temperature="" + event.values[0] + "\nCelsius (" + fahrenheit  + " F)\n";
        tvTemperature.setText(temperature);
    }

    @Override
    public void onClick(View v) {
        if(v==tvSave){
            Calendar calendar=Calendar.getInstance();
            int dd=calendar.get(Calendar.DAY_OF_MONTH);
            int mm=calendar.get(Calendar.MONTH)+1;
            int yy=calendar.get(Calendar.YEAR);
            int hh=calendar.get(Calendar.HOUR_OF_DAY);
            int mn=calendar.get(Calendar.MINUTE);
            int ss=calendar.get(Calendar.SECOND);
            String dstr="0",mstr="0",ystr="0",hstr="0",mnstr="0",sstr="0";
            if(dd<10){
                dstr="0"+dd;
            }else{
                dstr=""+dd;
            }
            if(mm<10){
                mstr="0"+mm;
            }else{
                mstr=""+mm;
            }

            if(yy<10){
                ystr="0"+yy;
            }else{
                ystr=""+yy;
            }
            if(hh<10){
                hstr="0"+hh;
            }else{
                hstr=""+hh;
            }
            if(mn<10){
                mnstr="0"+mn;
            }else{
                mnstr=""+mn;
            }
            if(ss<10){
                sstr="0"+ss;
            }else{
                sstr=""+ss;
            }

            if(db.insertTemperature(db.getId(),tvTemperature.getText().toString(),ystr+"-"+mstr+"-"+dstr+" "+hstr+":"+mnstr+":"+sstr)){
                Toast.makeText(this,"Temperature successfully saved",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this,"Failed to save temperature",Toast.LENGTH_SHORT).show();
            }
        }else if(v==tvReport){
            Intent intent=new Intent(this,TemperatureReportActivity.class);
            startActivity(intent);
        }
    }
}
