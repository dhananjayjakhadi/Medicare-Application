package com.selfcaremonitor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class CheckSensorsActivity extends AppCompatActivity {
    TextView tvSensors;
    private SensorManager mgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_sensors);
        tvSensors=(TextView)findViewById(R.id.tvSensors);
        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        List<Sensor> list = mgr.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: list){
            tvSensors.append(sensor.getName()+"\n");
        }
    }
}
