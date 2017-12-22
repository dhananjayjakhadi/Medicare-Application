package com.selfcaremonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.selfcaremonitor.Sensors.StepCounterService;
import com.selfcaremonitor.Sensors.StepDetector;
import com.selfcaremonitor.interfaces.StepListener;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener,StepListener, View.OnClickListener {
    private StepDetector simpleStepDetector;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    static TextView tvSteps,tvReport;
    ImageView ivPlayPause;
    static SharedPreferences sp;
    boolean on=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tvSteps=(TextView)findViewById(R.id.tvSteps);
        tvReport=(TextView)findViewById(R.id.tvReport);
        ivPlayPause=(ImageView)findViewById(R.id.ivPlayPause);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
        sp=getSharedPreferences("selfcaremonitor",Context.MODE_PRIVATE);
        Log.e("sensor"," "+mSensor);
//      mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        PackageManager pm = getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)) {
        //    Toast.makeText(this,"Sensor found",Toast.LENGTH_SHORT).show();
        }else{
        //    Toast.makeText(this,"No sensor found",Toast.LENGTH_SHORT).show();
        }
        tvSteps.setText(""+sp.getInt("steps",0));
        on=sp.getBoolean("steps_counting",false);
        ivPlayPause.setOnClickListener(this);
        if(on) {
            ivPlayPause.setImageResource(R.drawable.ic_pause);
            startService(new Intent(this, StepCounterService.class));
        }else{
            ivPlayPause.setImageResource(R.drawable.ic_play);
            stopService(new Intent(this, StepCounterActivity.class));
        }

        tvReport.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
//        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {

        //double steps = event.values[0];
//        simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        //Log.e("step", "step:" + steps);
    }

    @Override
    public void step(long timeNs) {
        //numSteps++;
        //Log.e("steps","steps "+numSteps);
        //Toast.makeText(this, "Step "+numSteps, Toast.LENGTH_SHORT).show();
        //tvSteps.setText(""+numSteps);
    }

    public static void updateTextView(){
        if(tvSteps!=null){
            tvSteps.setText(""+sp.getInt("steps",0));
        }
    }

    @Override
    public void onClick(View v) {
        if(v==ivPlayPause){
            SharedPreferences.Editor editor=sp.edit();
            editor.putBoolean("steps_counting",!on);
            editor.commit();
            if(on){
                ivPlayPause.setImageResource(R.drawable.ic_play);
                stopService(new Intent(this, StepCounterActivity.class));
            }else{
                ivPlayPause.setImageResource(R.drawable.ic_pause);
                startService(new Intent(this, StepCounterService.class));
            }
            on=!on;
        }else if(v==tvReport){
            Intent intent=new Intent(this, StepReportActivity.class);
            startActivity(intent);
        }
    }
}
