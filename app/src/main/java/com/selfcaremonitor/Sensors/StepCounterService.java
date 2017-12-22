package com.selfcaremonitor.Sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.selfcaremonitor.StepCounterActivity;
import com.selfcaremonitor.interfaces.StepListener;
import com.selfcaremonitor.utilities.DBHelper;

import java.util.Calendar;

/**
 * Created by User on 27/09/2017.
 */
public class StepCounterService extends Service implements Runnable,SensorEventListener,StepListener {
    private StepDetector simpleStepDetector;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private int numSteps;
    SharedPreferences sp;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sp=getSharedPreferences("selfcaremonitor", Context.MODE_PRIVATE);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);

        return START_STICKY;
    }

    @Override
    public void run() {
        while(sp.getBoolean("running",true)){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDate(){
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
        return ystr+"-"+mstr+"-"+dstr;
    }
    @Override
    public void step(long timeNs) {
        DBHelper db=new DBHelper(getApplicationContext());
        db.insertStep(db.getId(),getDate());
        numSteps=sp.getInt("steps",0)+1;
        Log.e("steps", "" + numSteps);
        boolean on=sp.getBoolean("steps_counting",false);
        SharedPreferences.Editor editor=sp.edit();
        if(on) {
            editor.putInt("steps", numSteps);
            editor.commit();
        }
        StepCounterActivity.updateTextView();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
