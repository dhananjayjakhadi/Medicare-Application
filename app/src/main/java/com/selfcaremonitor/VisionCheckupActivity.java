package com.selfcaremonitor;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.selfcaremonitor.utilities.DBHelper;

import java.util.Calendar;
import java.util.Random;

public class VisionCheckupActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper db;
    TextView tvVisionChart,tvAcuityTest,tvColorBlindTest,tvDecimal,tvPoints,tvReport;
    ScrollView svVisionChart,svAcuityTest,svColorBlindTest;
    ImageView[] ivLetter=new ImageView[21];
    ImageView ivLetters,ivUp,ivDown,ivLeft,ivRight;
    ImageView[] ivAns=new ImageView[5];
    ImageView ivColorTest;
    NumberPicker np1,np2;
    TextView tvSkip,tvConfirm;
    ImageView[] ivAnsB=new ImageView[5];
    Random random=new Random();
    int acuity_letter_no=0,progress=0,question_no=0,right=0;
    int color_letter_no=0,color_test_progress=0;
    String first_no="",second_no="";
    Dialog acuity_result_dialog;
    String right_vision="0.01",myopia_status="myopia",myopia="Myopia is more than 650 degrees",precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
    TextView tvTestResult,tvMyopiaStatus,tvResultDetails,tvPrecautions,tvSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision_checkup);
        tvReport=(TextView)findViewById(R.id.tvReport);
        tvVisionChart=(TextView)findViewById(R.id.tvVisionChart);
        tvAcuityTest=(TextView)findViewById(R.id.tvAcuityTest);
        tvColorBlindTest=(TextView)findViewById(R.id.tvColorBlindTest);
        svVisionChart=(ScrollView)findViewById(R.id.svVisionChart);
        svAcuityTest=(ScrollView)findViewById(R.id.svAcuityTest);
        svColorBlindTest=(ScrollView)findViewById(R.id.svColorBlindTest);
        ivLetter[0]=(ImageView)findViewById(R.id.ivLetter1);
        ivLetter[1]=(ImageView)findViewById(R.id.ivLetter2);
        ivLetter[2]=(ImageView)findViewById(R.id.ivLetter3);
        ivLetter[3]=(ImageView)findViewById(R.id.ivLetter4);
        ivLetter[4]=(ImageView)findViewById(R.id.ivLetter5);
        ivLetter[5]=(ImageView)findViewById(R.id.ivLetter6);
        ivLetter[6]=(ImageView)findViewById(R.id.ivLetter7);
        ivLetter[7]=(ImageView)findViewById(R.id.ivLetter8);
        ivLetter[8]=(ImageView)findViewById(R.id.ivLetter9);
        ivLetter[9]=(ImageView)findViewById(R.id.ivLetter10);
        ivLetter[10]=(ImageView)findViewById(R.id.ivLetter11);
        ivLetter[11]=(ImageView)findViewById(R.id.ivLetter12);
        ivLetter[12]=(ImageView)findViewById(R.id.ivLetter13);
        ivLetter[13]=(ImageView)findViewById(R.id.ivLetter14);
        ivLetter[14]=(ImageView)findViewById(R.id.ivLetter15);
        ivLetter[15]=(ImageView)findViewById(R.id.ivLetter16);
        ivLetter[16]=(ImageView)findViewById(R.id.ivLetter17);
        ivLetter[17]=(ImageView)findViewById(R.id.ivLetter18);
        ivLetter[18]=(ImageView)findViewById(R.id.ivLetter19);
        ivLetter[19]=(ImageView)findViewById(R.id.ivLetter20);
        ivLetter[20]=(ImageView)findViewById(R.id.ivLetter21);

        ivAns[0]=(ImageView)findViewById(R.id.ivAns1);
        ivAns[1]=(ImageView)findViewById(R.id.ivAns2);
        ivAns[2]=(ImageView)findViewById(R.id.ivAns3);
        ivAns[3]=(ImageView)findViewById(R.id.ivAns4);
        ivAns[4]=(ImageView)findViewById(R.id.ivAns5);
        ivLetters=(ImageView)findViewById(R.id.ivLetter);
        tvDecimal=(TextView)findViewById(R.id.tvDecimal);
        tvPoints=(TextView)findViewById(R.id.tvPoints);
        ivUp=(ImageView)findViewById(R.id.ivUp);
        ivDown=(ImageView)findViewById(R.id.ivDown);
        ivLeft=(ImageView)findViewById(R.id.ivLeft);
        ivRight=(ImageView)findViewById(R.id.ivRight);

        ivColorTest=(ImageView)findViewById(R.id.ivColorTest);
        np1=(NumberPicker)findViewById(R.id.np1);
        np2=(NumberPicker)findViewById(R.id.np2);
        tvSkip=(TextView)findViewById(R.id.tvSkip);
        tvConfirm=(TextView)findViewById(R.id.tvConfirm);
        ivAnsB[0]=(ImageView)findViewById(R.id.ivAnsB1);
        ivAnsB[1]=(ImageView)findViewById(R.id.ivAnsB2);
        ivAnsB[2]=(ImageView)findViewById(R.id.ivAnsB3);
        ivAnsB[3]=(ImageView)findViewById(R.id.ivAnsB4);
        ivAnsB[4]=(ImageView)findViewById(R.id.ivAnsB5);

        np1.setMinValue(0);
        np1.setMaxValue(9);
        np2.setMinValue(0);
        np2.setMaxValue(9);

        db=new DBHelper(this);

        acuity_result_dialog=new Dialog(this);
        acuity_result_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        acuity_result_dialog.setContentView(R.layout.acuity_test_result);
        tvTestResult=(TextView)acuity_result_dialog.findViewById(R.id.tvTestResult);
        tvMyopiaStatus=(TextView)acuity_result_dialog.findViewById(R.id.tvMyopiaStatus);
        tvResultDetails=(TextView)acuity_result_dialog.findViewById(R.id.tvResultDetails);
        tvPrecautions=(TextView)acuity_result_dialog.findViewById(R.id.tvPrecautions);
        tvSave=(TextView)acuity_result_dialog.findViewById(R.id.tvSave);

        for(int i=0;i<ivLetter.length;i++){
            int no=random.nextInt(4);
            if(no==0){
                ivLetter[i].setImageResource(R.drawable.e);
            }else if(no==1){
                ivLetter[i].setImageResource(R.drawable.w);
            }else if(no==2){
                ivLetter[i].setImageResource(R.drawable.re);
            }else if(no==3){
                ivLetter[i].setImageResource(R.drawable.m);
            }
        }

        acuity_letter_no=random.nextInt(4);
        color_letter_no=random.nextInt(21);
        updateLetter();
        updateBlindLetter();

        tvVisionChart.setOnClickListener(this);
        tvAcuityTest.setOnClickListener(this);
        tvColorBlindTest.setOnClickListener(this);
        ivUp.setOnClickListener(this);
        ivDown.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvReport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==tvVisionChart){
            svVisionChart.setVisibility(View.VISIBLE);
            svAcuityTest.setVisibility(View.GONE);
            svColorBlindTest.setVisibility(View.GONE);
        }else if(v==tvAcuityTest){
            svVisionChart.setVisibility(View.GONE);
            svAcuityTest.setVisibility(View.VISIBLE);
            svColorBlindTest.setVisibility(View.GONE);
        }else if(v==tvColorBlindTest){
            svVisionChart.setVisibility(View.GONE);
            svAcuityTest.setVisibility(View.GONE);
            svColorBlindTest.setVisibility(View.VISIBLE);
        }else if(v==ivUp){
            checkAns(1);
        }else if(v==ivDown){
            checkAns(3);
        }else if(v==ivLeft){
            checkAns(2);
        }else if(v==ivRight){
            checkAns(0);
        }else if(v==tvSave){
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

            if(db.insertVisionReport(db.getId(),right_vision,myopia_status,myopia,precautions,ystr+"-"+mstr+"-"+dstr+" "+hstr+":"+mnstr+":"+sstr)){
                Toast.makeText(this,"Vision report successfully saved",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this,"Failed to save report",Toast.LENGTH_SHORT).show();
            }
        }else if(v==tvReport){
            Intent intent=new Intent(this, VisionReportActivity.class);
            startActivity(intent);
        }
    }

    void checkAns(int ans){
        if(ans==acuity_letter_no){
            ivAns[question_no].setImageResource(R.drawable.ic_right);
            right++;
        }else{
            ivAns[question_no].setImageResource(R.drawable.ic_wrong);
        }
        question_no++;
        if(question_no>4){
            question_no=0;
            right=0;
            ivAns[0].setImageBitmap(null);
            ivAns[1].setImageBitmap(null);
            ivAns[2].setImageBitmap(null);
            ivAns[3].setImageBitmap(null);
            ivAns[4].setImageBitmap(null);
        }
        if(right>=3){
            right=0;
            question_no=0;
            ivAns[0].setImageBitmap(null);
            ivAns[1].setImageBitmap(null);
            ivAns[2].setImageBitmap(null);
            ivAns[3].setImageBitmap(null);
            ivAns[4].setImageBitmap(null);
            progress++;
            updateLetterSize();
        }else if(question_no>=4 && right<3){
            acuity_result_dialog.show();
            tvTestResult.setText("" + right_vision);
            tvResultDetails.setText("Your vision is "+right_vision+", "+myopia);
            tvPrecautions.setText(""+precautions);
        }
        int new_no=random.nextInt(4);
        if(acuity_letter_no==new_no){
            acuity_letter_no=(new_no+1)%4;
        }else {
            acuity_letter_no =new_no;
        }
        updateLetter();
    }

    void updateLetter(){
        if(acuity_letter_no==0){
            ivLetters.setImageResource(R.drawable.e);
        }else if(acuity_letter_no==1){
            ivLetters.setImageResource(R.drawable.w);
        }else if(acuity_letter_no==2){
            ivLetters.setImageResource(R.drawable.re);
        }else if(acuity_letter_no==3){
            ivLetters.setImageResource(R.drawable.m);
        }
    }

    void updateBlindLetter(){
        if(color_letter_no==0){
            ivColorTest.setImageResource(R.drawable.daltonismo10);
            first_no="1";
            second_no="0";
        }else if(color_letter_no==1){
            ivColorTest.setImageResource(R.drawable.daltonismo12);
            first_no="1";
            second_no="2";
        }else if(color_letter_no==2){
            ivColorTest.setImageResource(R.drawable.daltonismo15);
            first_no="1";
            second_no="5";
        }else if(color_letter_no==3){
            ivColorTest.setImageResource(R.drawable.daltonismo16);
            first_no="1";
            second_no="6";
        }else if(color_letter_no==4){
            ivColorTest.setImageResource(R.drawable.daltonismo18);
            first_no="1";
            second_no="8";
        }else if(color_letter_no==5){
            ivColorTest.setImageResource(R.drawable.daltonismo2);
            first_no="2";
            second_no="";
        }else if(color_letter_no==6){
            ivColorTest.setImageResource(R.drawable.daltonismo29);
            first_no="2";
            second_no="9";
        }else if(color_letter_no==7){
            ivColorTest.setImageResource(R.drawable.daltonismo2_2);
            first_no="2";
            second_no="";
        }else if(color_letter_no==8){
            ivColorTest.setImageResource(R.drawable.daltonismo42);
            first_no="4";
            second_no="2";
        }else if(color_letter_no==9){
            ivColorTest.setImageResource(R.drawable.daltonismo42_2);
            first_no="4";
            second_no="2";
        }else if(color_letter_no==10){
            ivColorTest.setImageResource(R.drawable.daltonismo5);
            first_no="5";
            second_no="";
        }else if(color_letter_no==11){
            ivColorTest.setImageResource(R.drawable.daltonismo57);
            first_no="5";
            second_no="7";
        }else if(color_letter_no==12){
            ivColorTest.setImageResource(R.drawable.daltonismo57_2);
            first_no="5";
            second_no="7";
        }else if(color_letter_no==13){
            ivColorTest.setImageResource(R.drawable.daltonismo5_2);
            first_no="5";
            second_no="";
        }else if(color_letter_no==14){
            ivColorTest.setImageResource(R.drawable.daltonismo6);
            first_no="6";
            second_no="";
        }else if(color_letter_no==15){
            ivColorTest.setImageResource(R.drawable.daltonismo6_2);
            first_no="6";
            second_no="";
        }else if(color_letter_no==16){
            ivColorTest.setImageResource(R.drawable.daltonismo7);
            first_no="7";
            second_no="";
        }else if(color_letter_no==17){
            ivColorTest.setImageResource(R.drawable.daltonismo74);
            first_no="7";
            second_no="4";
        }else if(color_letter_no==18){
            ivColorTest.setImageResource(R.drawable.daltonismo74_2);
            first_no="7";
            second_no="4";
        }else if(color_letter_no==19){
            ivColorTest.setImageResource(R.drawable.daltonismo8);
            first_no="8";
            second_no="";
        }else if(color_letter_no==20){
            ivColorTest.setImageResource(R.drawable.daltonismo96);
            first_no="9";
            second_no="6";
        }
    }

    void updateLetterSize(){
        //LinearLayout.LayoutParams layoutParams;
        final float scale = getResources().getDisplayMetrics().density;
        int pixels=(int) (200 * scale + 0.5f);;
        LinearLayout.LayoutParams layoutParams;
        switch (progress){
            case 0:
                pixels = (int) (200 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(1);
                ivLetters.setScaleY(1);*/
                tvDecimal.setText("0.01");
                tvPoints.setText("3.0");
            case 1:
                //layoutParams=new LinearLayout.LayoutParams(166,166);
                pixels = (int) (166.66 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.83f);
                ivLetters.setScaleY(0.83f);*/
                tvDecimal.setText("0.012");
                tvPoints.setText("3.1");
                right_vision="0.01";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 2:
                //layoutParams=new LinearLayout.LayoutParams(133,133);
                pixels = (int) (133.33 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.665f);
                ivLetters.setScaleY(0.665f);*/
                tvDecimal.setText("0.015");
                tvPoints.setText("3.2");
                right_vision="0.012";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 3:
                //layoutParams=new LinearLayout.LayoutParams(100,100);
                pixels = (int) (100 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.5f);
                ivLetters.setScaleY(0.5f);*/
                tvDecimal.setText("0.02");
                tvPoints.setText("3.3");
                right_vision="0.015";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 4:
                //layoutParams=new LinearLayout.LayoutParams(80,80);
                pixels = (int) (80 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.4f);
                ivLetters.setScaleY(0.4f);*/
                tvDecimal.setText("0.025");
                tvPoints.setText("3.4");
                right_vision="0.02";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 5:
                //layoutParams=new LinearLayout.LayoutParams(66,66);
                pixels = (int) (66.66 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.33f);
                ivLetters.setScaleY(0.33f);*/
                tvDecimal.setText("0.03");
                tvPoints.setText("3.5");
                right_vision="0.025";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 6:
                //layoutParams=new LinearLayout.LayoutParams(50,50);
                pixels = (int) (50 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.25f);
                ivLetters.setScaleY(0.25f);*/
                tvDecimal.setText("0.04");
                tvPoints.setText("3.6");
                right_vision="0.03";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 7:
                //layoutParams=new LinearLayout.LayoutParams(40,40);
                pixels = (int) (40 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.2f);
                ivLetters.setScaleY(0.2f);*/
                tvDecimal.setText("0.05");
                tvPoints.setText("3.7");
                right_vision="0.04";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 8:
                //layoutParams=new LinearLayout.LayoutParams(33,33);
                pixels = (int) (33.33 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.165f);
                ivLetters.setScaleY(0.165f);*/
                tvDecimal.setText("0.06");
                tvPoints.setText("3.8");
                right_vision="0.05";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 9:
                //layoutParams=new LinearLayout.LayoutParams(25,25);
                pixels = (int) (25 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.125f);
                ivLetters.setScaleY(0.125f);*/
                tvDecimal.setText("0.08");
                tvPoints.setText("3.9");
                right_vision="0.06";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 10:
                //layoutParams=new LinearLayout.LayoutParams(20,20);
                pixels = (int) (20 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.1f);
                ivLetters.setScaleY(0.1f);*/
                tvDecimal.setText("0.1");
                tvPoints.setText("4.0");
                right_vision="0.08";
                myopia="Myopia is more than 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 11:
                //layoutParams=new LinearLayout.LayoutParams(16,16);
                pixels = (int) (16.66 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.08f);
                ivLetters.setScaleY(0.08f);*/
                tvDecimal.setText("0.12");
                tvPoints.setText("4.1");
                right_vision="0.1";
                myopia="Myopia is 650 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 12:
                //layoutParams=new LinearLayout.LayoutParams(13,13);
                pixels = (int) (13.33 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.065f);
                ivLetters.setScaleY(0.065f);*/
                tvDecimal.setText("0.15");
                tvPoints.setText("4.2");
                right_vision="0.12";
                myopia="Myopia is 600 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 13:
                //layoutParams=new LinearLayout.LayoutParams(10,10);
                pixels = (int) (10 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.05f);
                ivLetters.setScaleY(0.05f);*/
                tvDecimal.setText("0.2");
                tvPoints.setText("4.3");
                right_vision="0.15";
                myopia="Myopia is 500 degrees";
                myopia_status="myopia";
                precautions="Your eyesight is a bit eye-watering, Please ware glasses test corrected Visual Acuity";
                break;
            case 14:
                //layoutParams=new LinearLayout.LayoutParams(8,8);
                pixels = (int) (8 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.04f);
                ivLetters.setScaleY(0.04f);*/
                tvDecimal.setText("0.25");
                tvPoints.setText("4.4");
                right_vision="0.2";
                myopia="Myopia is 450 degrees";
                precautions="Your vision has been severely myopic";
                myopia_status="myopia";
                break;
            case 15:
                //layoutParams=new LinearLayout.LayoutParams(6,6);
                pixels = (int) (6.66 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.03f);
                ivLetters.setScaleY(0.03f);*/
                tvDecimal.setText("0.3");
                tvPoints.setText("4.5");
                right_vision="0.25";
                myopia="Myopia is 400 degrees";
                precautions="Your vision has been severely myopic";
                myopia_status="myopia";
                break;
            case 16:
                //layoutParams=new LinearLayout.LayoutParams(5,5);
                pixels = (int) (5 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.025f);
                ivLetters.setScaleY(0.025f);*/
                tvDecimal.setText("0.4");
                tvPoints.setText("4.6");
                right_vision="0.3";
                myopia="Myopia is 350 degrees";
                precautions="Your vision has been severely myopic";
                myopia_status="myopia";
                break;
            case 17:
                //layoutParams=new LinearLayout.LayoutParams(4,4);
                pixels = (int) (4 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.02f);
                ivLetters.setScaleY(0.02f);*/
                tvDecimal.setText("0.5");
                tvPoints.setText("4.7");
                right_vision="0.4";
                myopia="Myopia is 250 degrees";
                precautions="Your vision has been severely myopic";
                myopia_status="myopia";
                break;
            case 18:
                //layoutParams=new LinearLayout.LayoutParams(3,3);
                pixels = (int) (3.33 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.0165f);
                ivLetters.setScaleY(0.0165f);*/
                tvDecimal.setText("0.6");
                tvPoints.setText("4.8");
                right_vision="0.5";
                myopia="Myopia is 200 degrees";
                precautions="Your vision has been severely myopic";
                myopia_status="myopia";
                break;
            case 19:
                //layoutParams=new LinearLayout.LayoutParams(2,2);
                pixels = (int) (2.5f * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.0125f);
                ivLetters.setScaleY(0.0125f);*/
                tvDecimal.setText("0.8");
                tvPoints.setText("4.9");
                right_vision="0.6";
                myopia="Myopia is 150 degrees";
                precautions="Your eye habits has a little problem, improve your eye habits";
                myopia_status="Normal";
                break;
            case 20:
                //layoutParams=new LinearLayout.LayoutParams(2,2);
                pixels = (int) (2 * scale + 0.5f);
                layoutParams=new LinearLayout.LayoutParams(pixels,pixels);
                ivLetters.setLayoutParams(layoutParams);
                /*ivLetters.setScaleX(0.01f);
                ivLetters.setScaleY(0.01f);*/
                tvDecimal.setText("1.0");
                tvPoints.setText("5.0");
                right_vision="0.8";
                myopia="Myopia is 100 degrees";
                precautions="Your eye habits has a little problem, improve your eye habits";
                myopia_status="Normal";
                break;
            default:
                //layoutParams=new LinearLayout.LayoutParams(200,200);
                right_vision="1.0 Normal";
                myopia="Need not ware glasses";
                precautions="Congratulations, your eyesight is good, keep good eye habits";
                myopia_status="Normal";
                acuity_result_dialog.show();
                tvTestResult.setText("" + right_vision);
                tvResultDetails.setText("Your vision is "+right_vision+", "+myopia);
                tvPrecautions.setText(""+precautions);
                break;
        }
        //ivLetters.setLayoutParams(layoutParams);
    }

}