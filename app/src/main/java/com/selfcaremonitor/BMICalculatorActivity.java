package com.selfcaremonitor;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.selfcaremonitor.utilities.DBHelper;

public class BMICalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper db;
    EditText etWeight,etHeight;
    Button btnCalculate;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);
        etWeight=(EditText)findViewById(R.id.etMemberWeight);
        etHeight=(EditText)findViewById(R.id.etMemberHeight);
        tvResult=(TextView)findViewById(R.id.tvResult);
        btnCalculate=(Button)findViewById(R.id.btnCalculate);

        db=new DBHelper(this);
        Cursor cursor=db.getProfile();
        if(cursor.moveToFirst()){
            String weight=cursor.getString(cursor.getColumnIndex("member_weight"));
            String height=cursor.getString(cursor.getColumnIndex("member_height"));
            etWeight.setText(""+weight);
            etHeight.setText(""+height);
        }
        btnCalculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnCalculate){
            String member_weight=etWeight.getText().toString();
            String member_height=etHeight.getText().toString();
            if(member_height.trim().length()<=0){
                etHeight.setError("Enter height");
                etHeight.requestFocus();
                return;
            }

            if(Double.parseDouble(member_height)<=0){
                etHeight.setError("Enter height greater than 0");
                etHeight.requestFocus();
                return;
            }

            if(member_weight.trim().length()<=0){
                etWeight.setError("Enter weight");
                etWeight.requestFocus();
                return;
            }

            if(Double.parseDouble(member_weight)<=0){
                etWeight.setError("Enter weight greater 0");
                etWeight.requestFocus();
                return;
            }

            float weight=Float.parseFloat(member_weight);
            float height=Float.parseFloat(member_height);
            float bmi=(weight/((height*0.01f)*(height*0.01f)));

            if(bmi<=16){
                tvResult.setText("BMI="+bmi+"\nStatus: Severe Thinness");
            }else if(bmi>16 && bmi<=17){
                tvResult.setText("BMI="+bmi+"\nStatus: Moderate Thinness");
            }else if(bmi>17 && bmi<=18.5){
                tvResult.setText("BMI="+bmi+"\nStatus: Mild Thinness");
            }else if(bmi>18.5 && bmi<=25){
                tvResult.setText("BMI="+bmi+"\nStatus: Normal");
            }else if(bmi>25 && bmi<=30){
                tvResult.setText("BMI="+bmi+"\nStatus: Overweight");
            }else if(bmi>30 && bmi<=35){
                tvResult.setText("BMI="+bmi+"\nStatus: Obese Class I");
            }else if(bmi>35 && bmi<=40){
                tvResult.setText("BMI="+bmi+"\nStatus: Obese Class II");
            }else if(bmi>40){
                tvResult.setText("BMI="+bmi+"\nStatus: Obese Class III");
            }
        }
    }
}
