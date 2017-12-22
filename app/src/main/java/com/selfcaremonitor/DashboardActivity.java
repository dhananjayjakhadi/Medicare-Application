package com.selfcaremonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.selfcaremonitor.utilities.DBHelper;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper db;
    TextView tvUserName;
    Button btnCheckSensors;
    LinearLayout llVision,llHearing,llHeartRate,llBodyTemperature,llStepCounter,llBMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tvUserName=(TextView)findViewById(R.id.tvUserName);
        btnCheckSensors=(Button)findViewById(R.id.btnCheckSensors);
        llVision=(LinearLayout)findViewById(R.id.llVision);
        llHearing=(LinearLayout)findViewById(R.id.llHearing);
        llHeartRate=(LinearLayout)findViewById(R.id.llHeartRate);
        llBodyTemperature=(LinearLayout)findViewById(R.id.llBodyTemperature);
        llStepCounter=(LinearLayout)findViewById(R.id.llStepCounter);
        llBMI=(LinearLayout)findViewById(R.id.llBMI);
        btnCheckSensors.setOnClickListener(this);
        llVision.setOnClickListener(this);
        llHearing.setOnClickListener(this);
        llHeartRate.setOnClickListener(this);
        llBodyTemperature.setOnClickListener(this);
        llStepCounter.setOnClickListener(this);
        llBMI.setOnClickListener(this);
        db=new DBHelper(this);
        tvUserName.setText("Hi "+db.getName());
    }

    @Override
    public void onClick(View v) {
        if(v==btnCheckSensors){
            Intent intent=new Intent(this, CheckSensorsActivity.class);
            startActivity(intent);
        }else if(v==llVision){
            Intent intent=new Intent(this, VisionCheckupActivity.class);
            startActivity(intent);
        }else if(v==llHearing){
            Intent intent=new Intent(this, HearingActivity.class);
            startActivity(intent);
        }else if(v==llHeartRate) {
            Intent intent=new Intent(this, HeartBeatActivity.class);
            startActivity(intent);
        }else if(v==llBodyTemperature){
            Intent intent=new Intent(this, BodyTemperatureActivity.class);
            startActivity(intent);
        }else if(v==llStepCounter){
            Intent intent=new Intent(this, StepCounterActivity.class);
            startActivity(intent);
        }else if(v==llBMI){
            Intent intent=new Intent(this, BMICalculatorActivity.class);
            startActivity(intent);
        }
    }
}
