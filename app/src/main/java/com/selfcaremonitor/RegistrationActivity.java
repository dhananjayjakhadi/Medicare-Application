package com.selfcaremonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.selfcaremonitor.utilities.DBHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper db;
    EditText etMemberName,etMemberMobile,etMemberEmail,etMemberPassword,etConfirmPassword,etMemberAge,etMemberHeight,etMemberWeight;
    Spinner spMemberGender,spMemberBloodGroup;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etMemberName=(EditText)findViewById(R.id.etMemberName);
        etMemberMobile=(EditText)findViewById(R.id.etMemberMobile);
        etMemberEmail=(EditText)findViewById(R.id.etMemberEmail);
        etMemberPassword=(EditText)findViewById(R.id.etMemberPassword);
        etConfirmPassword=(EditText)findViewById(R.id.etConfirmPassword);
        etMemberAge=(EditText)findViewById(R.id.etMemberAge);
        spMemberGender=(Spinner)findViewById(R.id.spMemberGender);
        spMemberBloodGroup=(Spinner)findViewById(R.id.spMemberBloodGroup);
        etMemberHeight=(EditText)findViewById(R.id.etMemberHeight);
        etMemberWeight=(EditText)findViewById(R.id.etMemberWeight);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
        db=new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnRegister){
            String member_name=etMemberName.getText().toString();
            String member_mobile=etMemberMobile.getText().toString();
            String member_email=etMemberEmail.getText().toString();
            String member_password=etMemberPassword.getText().toString();
            String confirm_password=etConfirmPassword.getText().toString();
            String member_age=etMemberAge.getText().toString();
            String member_gender= (String) spMemberGender.getSelectedItem();
            String member_blood_group= (String) spMemberBloodGroup.getSelectedItem();
            String member_height=etMemberHeight.getText().toString();
            String member_weight=etMemberWeight.getText().toString();
            if(member_name.trim().length()<=0){
                etMemberName.setError("Enter name");
                etMemberName.requestFocus();
                return;
            }

            if(member_mobile.trim().length()<=0){
                etMemberMobile.setError("Enter mobile no");
                etMemberMobile.requestFocus();
                return;
            }

            if(member_mobile.trim().length()<10){
                etMemberMobile.setError("Enter 10 digit mobile no");
                etMemberMobile.requestFocus();
                return;
            }

            if(member_email.trim().length()<=0){
                etMemberEmail.setError("Enter email address");
                etMemberEmail.requestFocus();
                return;
            }

            Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
            Matcher regMatcher   = regexPattern.matcher(member_email);
            if(!regMatcher.matches()) {
                etMemberEmail.setError("Please enter correct email address");
                etMemberEmail.requestFocus();
                return;
            }

            if(member_password.trim().length()<=0){
                etMemberPassword.setError("Enter password");
                etMemberPassword.requestFocus();
                return;
            }

            if(member_password.trim().length()<5){
                etMemberPassword.setError("Enter at least 5 characters");
                etMemberPassword.requestFocus();
                return;
            }

            if(confirm_password.trim().length()<=0){
                etConfirmPassword.setError("Enter password");
                etConfirmPassword.requestFocus();
                return;
            }

            if(!member_password.equals(confirm_password)){
                etConfirmPassword.setError("Password does not match");
                etConfirmPassword.requestFocus();
                return;
            }

            if(member_age.trim().length()<=0){
                etMemberAge.setError("Enter age");
                etMemberAge.requestFocus();
                return;
            }

            if(Double.parseDouble(member_age)<=0){
                etMemberAge.setError("Enter age greater than 0");
                etMemberAge.requestFocus();
                return;
            }

            if(spMemberGender.getSelectedItemPosition()==0){
                Toast.makeText(this,"Please select gender",Toast.LENGTH_SHORT).show();
                return;
            }

            if(spMemberBloodGroup.getSelectedItemPosition()==0){
                Toast.makeText(this,"Please select blood group",Toast.LENGTH_SHORT).show();
                return;
            }

            if(member_height.trim().length()<=0){
                etMemberHeight.setError("Enter height");
                etMemberHeight.requestFocus();
                return;
            }

            if(Double.parseDouble(member_height)<=0){
                etMemberHeight.setError("Enter height greater than 0");
                etMemberHeight.requestFocus();
                return;
            }

            if(member_weight.trim().length()<=0){
                etMemberWeight.setError("Enter weight");
                etMemberWeight.requestFocus();
                return;
            }

            if(Double.parseDouble(member_weight)<=0){
                etMemberWeight.setError("Enter weight greater 0");
                etMemberWeight.requestFocus();
                return;
            }

            if(db.insertMember(member_name,member_mobile,member_email,member_age,member_gender,member_blood_group,member_height,member_weight,member_password)){
                Toast.makeText(this,"Registrations Successful",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.putExtra("status","success");
                finish();
            }else{
                Toast.makeText(this,"Failed to register",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
