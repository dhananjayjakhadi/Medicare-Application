package com.selfcaremonitor.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 06/10/2017.
 */
public class DBHelper extends SQLiteOpenHelper {
    Context context;
    public DBHelper(Context context) {
        super(context, "HealthCareApp", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbl_members ( " +
                "member_id integer primary key autoincrement, " +
                "member_name varchar, " +
                "member_mobile varchar, " +
                "member_email varchar, " +
                "member_age varchar, " +
                "member_gender varchar, "+
                "member_blood_group varchar, " +
                "member_height varchar, " +
                "member_weight varchar," +
                "member_password varchar)");

        db.execSQL("create table tbl_login ( " +
                "member_id varchar, " +
                "member_name varchar, " +
                "member_mobile varchar, " +
                "member_email varchar, " +
                "member_age varchar, " +
                "member_gender varchar, "+
                "member_blood_group varchar, " +
                "member_height varchar, " +
                "member_weight varchar," +
                "member_password varchar)");

        db.execSQL("create table tbl_vision_report (" +
                "report_id integer primary key autoincrement," +
                "member_id integer, " +
                "vision_decimal varchar," +
                "vision_myopia_status varhcar," +
                "vision_myopia_report varchar," +
                "vision_report varchar," +
                "vision_report_date varchar)");

        db.execSQL("create table tbl_temperature_report(" +
                "report_id integer primary key autoincrement," +
                "member_id integer," +
                "temperature varchar," +
                "report_date varchar)");

        db.execSQL("create table tbl_step_report(" +
                "report_id integer primary key autoincrement," +
                "member_id integer," +
                "steps integer," +
                "report_date varchar)");

        db.execSQL("create table tbl_heart_report(" +
                "report_id integer primary key autoincrement," +
                "member_id varchar," +
                "heart_beats varchar," +
                "member_position varchar," +
                "report_date varchar)");

        db.execSQL("create table tbl_hearing_report(" +
                "report_id integer primary key autoincrement," +
                "member_id varchar," +
                "hearing_frequencies varchar," +
                "report_date varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tbl_members");
        onCreate(db);
    }

    public boolean insertMember(String member_name, String member_mobile, String member_email, String member_age, String member_gender, String member_blood_group, String member_height, String member_weight, String member_password){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("member_name",member_name);
        cv.put("member_mobile",member_mobile);
        cv.put("member_email",member_email);
        cv.put("member_age",member_age);
        cv.put("member_gender", member_gender);
        cv.put("member_blood_group", member_blood_group);
        cv.put("member_height", member_height);
        cv.put("member_weight", member_weight);
        cv.put("member_password", member_password);
        long result=db.insert("tbl_members",null,cv);
        if(result!=-1){
            return true;
        }
        return false;
    }

    public boolean insertLogin(String member_id, String member_name, String member_mobile, String member_email, String member_age, String member_gender, String member_blood_group, String member_height, String member_weight, String member_password){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("member_id",member_id);
        cv.put("member_name",member_name);
        cv.put("member_mobile",member_mobile);
        cv.put("member_email",member_email);
        cv.put("member_age",member_age);
        cv.put("member_gender",member_gender);
        cv.put("member_blood_group",member_blood_group);
        cv.put("member_height",member_height);
        cv.put("member_weight",member_weight);
        cv.put("member_password",member_password);
        long result=db.insert("tbl_members", null, cv);
        if(result!=-1){
            return true;
        }
        return false;
    }

    public boolean logIn(String member_mobile,String member_password){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteDatabase db1=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from tbl_members where member_mobile='" + member_mobile + "' and member_password='" + member_password + "'", null);
        if (cursor.moveToFirst()){
            do {
                Log.e("member_id",""+cursor.getString(cursor.getColumnIndex("member_id")));
                Log.e("member_mobile",""+cursor.getString(cursor.getColumnIndex("member_mobile")));
                Log.e("member_password",""+cursor.getString(cursor.getColumnIndex("member_password")));
                String member_id=cursor.getString(cursor.getColumnIndex("member_id"));
                String member_name=cursor.getString(cursor.getColumnIndex("member_name"));
                String member_email=cursor.getString(cursor.getColumnIndex("member_email"));
                String member_age=cursor.getString(cursor.getColumnIndex("member_age"));
                String member_gender=cursor.getString(cursor.getColumnIndex("member_gender"));
                String member_blood_group=cursor.getString(cursor.getColumnIndex("member_blood_group"));
                String member_height=cursor.getString(cursor.getColumnIndex("member_height"));
                String member_weight=cursor.getString(cursor.getColumnIndex("member_weight"));
                ContentValues cv=new ContentValues();
                cv.put("member_id",member_id);
                cv.put("member_name",member_name);
                cv.put("member_mobile",member_mobile);
                cv.put("member_email",member_email);
                cv.put("member_age",member_age);
                cv.put("member_gender",member_gender);
                cv.put("member_blood_group",member_blood_group);
                cv.put("member_height",member_height);
                cv.put("member_weight",member_weight);
                cv.put("member_password",member_password);
                long result=db1.insert("tbl_login",null,cv);
                if(result!=-1){
                    return true;
                }
            }while (cursor.moveToNext());
        }
        return false;
    }

    public boolean isLoggedIn(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from tbl_login",null);
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }

    public String getId(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from tbl_login",null);
        if(cursor.moveToFirst()){
            return cursor.getString(cursor.getColumnIndex("member_id"));
        }
        return "";
    }

    public String getName(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from tbl_login",null);
        if(cursor.moveToFirst()){
            return cursor.getString(cursor.getColumnIndex("member_name"));
        }
        return "";
    }

    public Cursor getProfile(){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("select * from tbl_login",null);
    }

    public boolean insertTemperature(String member_id, String temperature, String report_date){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("member_id",member_id);
        cv.put("temperature",temperature);
        cv.put("report_date",report_date);
        long result=db.insert("tbl_temperature_report", null, cv);
        if(result!=-1){
            return true;
        }
        return false;
    }

    public Cursor getTemperatureReport(String member_id){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("select * from tbl_temperature_report where member_id="+member_id,null);
    }

    public boolean insertHeartReport(String member_id, String heart_beat, String state, String report_date){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("member_id",member_id);
        cv.put("heart_beats",heart_beat);
        cv.put("member_position",state);
        cv.put("report_date",report_date);
        long result=db.insert("tbl_heart_report", null, cv);
        if(result!=-1){
            return true;
        }
        return false;
    }

    public Cursor getHearReport(String member_id){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("select * from tbl_heart_report where member_id="+member_id,null);
    }

    public boolean insertVisionReport(String member_id, String vision_decimal, String vision_myopia_status, String vision_myopia_report, String vision_report, String vision_report_date){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("member_id",member_id);
        cv.put("vision_decimal",vision_decimal);
        cv.put("vision_myopia_status",vision_myopia_status);
        cv.put("vision_myopia_report",vision_myopia_report);
        cv.put("vision_report",vision_report);
        cv.put("vision_report_date",vision_report_date);
        long result=db.insert("tbl_vision_report", null, cv);
        if(result!=-1){
            return true;
        }
        return false;
    }

    public Cursor getVisionReport(String member_id){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("select * from tbl_vision_report where member_id="+member_id,null);
    }

    public boolean insertStep(String member_id, String date){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteDatabase db1=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from tbl_step_report where member_id=" + member_id + " and report_date='" + date + "'", null);
        if(cursor.moveToFirst()){
            String report_id=cursor.getString(cursor.getColumnIndex("report_id"));
            int steps=cursor.getInt(cursor.getColumnIndex("steps"));
            ContentValues cv=new ContentValues();
            cv.put("steps",""+(steps+1));
            db1.update("tbl_step_report", cv, "report_id=?", new String[]{report_id});
        }else{
            ContentValues cv=new ContentValues();
            cv.put("member_id",member_id);
            cv.put("steps","1");
            cv.put("report_date",""+date);
            db1.insert("tbl_step_report",null,cv);
        }
        return false;
    }

    public Cursor getStepReport(String member_id){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("select * from tbl_step_report where member_id="+member_id,null);
    }

    public boolean insertHearingReport(String member_id, String hearing_frequencies, String date){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("member_id",member_id);
        cv.put("hearing_frequencies",hearing_frequencies);
        cv.put("report_date",""+date);
        long result=db.insert("tbl_hearing_report",null,cv);
        if(result!=-1){
            return true;
        }
        return false;
    }

    public Cursor getHearingReport(String member_id){
        SQLiteDatabase db=getReadableDatabase();
        return db.rawQuery("select * from tbl_hearing_report where member_id="+member_id,null);
    }
}