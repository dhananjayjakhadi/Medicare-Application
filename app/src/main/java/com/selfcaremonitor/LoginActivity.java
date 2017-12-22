package com.selfcaremonitor;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.selfcaremonitor.utilities.DBHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper db;
    EditText etMobileNo,etPassword;
    Button btnLogin;
    TextView tvStatus,tvCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvStatus=(TextView)findViewById(R.id.tvStatus);
        etMobileNo=(EditText)findViewById(R.id.etMemberMobile);
        etPassword=(EditText)findViewById(R.id.etMemberPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        tvCreateAccount=(TextView)findViewById(R.id.tvCreateAccount);

        db=new DBHelper(this);
        if(db.isLoggedIn()){
            Intent intent=new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnLogin){
            String member_mobile=etMobileNo.getText().toString();
            String member_password=etPassword.getText().toString();
            if(member_mobile.trim().length()<=0){
                etMobileNo.setError("Enter mobile no");
                etMobileNo.requestFocus();
                return;
            }

            if(member_mobile.trim().length()<10){
                etMobileNo.setError("Enter 10 digit mobile no");
                etMobileNo.requestFocus();
                return;
            }

            if(member_password.trim().length()<=0){
                etPassword.setError("Enter password");
                etPassword.requestFocus();
                return;
            }

            if(member_password.trim().length()<5){
                etPassword.setError("Enter at least 5 characters");
                etPassword.requestFocus();
                return;
            }

            if(db.logIn(member_mobile,member_password)){
                Toast.makeText(this,"Login successful",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,"Login failed !",Toast.LENGTH_SHORT).show();
                tvStatus.setText("Login Failed!");
                tvStatus.setTextColor(Color.parseColor("#aa0000"));
                tvStatus.setVisibility(View.VISIBLE);
            }
        }else if(v==tvCreateAccount){
            Intent intent=new Intent(this,RegistrationActivity.class);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==100){
            String status=data.getStringExtra("status");
            if(status!=null && status.equals("success")){
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setText("Registration Successful");
                tvStatus.setTextColor(Color.parseColor("#aa0000"));
            }
        }
    }
}
