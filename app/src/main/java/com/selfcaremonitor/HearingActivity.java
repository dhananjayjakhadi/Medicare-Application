package com.selfcaremonitor;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.selfcaremonitor.utilities.DBHelper;

import java.util.Calendar;

public class HearingActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper db;
    TextView tvFreq,tvPlay,tvCouldHear,tvSave,tvNext,tvReport;
    TextView tvLowFreq,tvHighFreq;
    SeekBar sbFrequency;
    ImageView ivPrev,ivNext;
    SoundPool.Builder builder;
    SoundPool soundPool;
    int sound_id;

    private final int duration = 3; // seconds
    int sampleRate = 20000;//8000
    int numSamples = duration * sampleRate;
    double[] sample = new double[numSamples];
    double freqOfTone = 20000; // hz
    byte[] generatedSnd=new byte[2 * numSamples];
    double low_freq=0,high_freq=0;
    boolean could_hear=false;
    Handler handler = new Handler();
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing);
        tvFreq=(TextView)findViewById(R.id.tvFreq);
        sbFrequency=(SeekBar)findViewById(R.id.sbFrequency);
        ivPrev=(ImageView)findViewById(R.id.ivPrev);
        ivNext=(ImageView)findViewById(R.id.ivNext);
        tvPlay=(TextView)findViewById(R.id.tvPlay);
        tvCouldHear=(TextView)findViewById(R.id.tvCouldHear);
        tvSave=(TextView)findViewById(R.id.tvSave);
        tvNext=(TextView)findViewById(R.id.tvNext);
        tvLowFreq=(TextView)findViewById(R.id.tvLowFreq);
        tvHighFreq=(TextView)findViewById(R.id.tvHighFreq);
        tvReport=(TextView)findViewById(R.id.tvReport);

        builder=new SoundPool.Builder();
        soundPool=builder.build();
        sound_id=soundPool.load(this, R.raw.in, 1);
        //soundPool.play(sound_id, 10, 10, 1, -1, freq);

        db=new DBHelper(this);

        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        tvPlay.setOnClickListener(this);
        tvCouldHear.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        sbFrequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvFreq.setText(""+(progress*2)+"Hz");
                //sampleRate=progress;
                freqOfTone=progress*2;
                tvCouldHear.setVisibility(View.GONE);
                tvPlay.setText("Play");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onClick(View v) {
        if(v==ivPrev){
            //sampleRate-=3;
            if(freqOfTone>=25) {
                freqOfTone -= 5;
            }
            tvFreq.setText(""+freqOfTone+"Hz");
            sbFrequency.setProgress((int) freqOfTone/2);
            //soundPool.setRate(sound_id, freq);
        }else if(v==ivNext){
            //sampleRate+=3;
            if(freqOfTone<=19995) {
                freqOfTone += 5;
            }
            tvFreq.setText(""+freqOfTone+"Hz");
            sbFrequency.setProgress((int) freqOfTone/2);
            //soundPool.setRate(sound_id,freq);
        }else if(v==tvPlay){
            // Use a new tread as this can take a while
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            AudioTrack track=generateTone(freqOfTone,2000);
                            tvPlay.setVisibility(View.GONE);
                            track.play();
                            tvPlay.setText("Replay");
                            tvPlay.setVisibility(View.VISIBLE);
                            tvCouldHear.setVisibility(View.VISIBLE);
                            //playSound();
                        }
                    });
                }
            });
            thread.start();
        }else if(v==tvCouldHear){
            if(!could_hear){
                low_freq=freqOfTone;
                high_freq=freqOfTone;
                could_hear=true;
                tvHighFreq.setText(""+high_freq+"Hz");
            }else{
                low_freq=freqOfTone;
                tvLowFreq.setText(""+low_freq+"Hz");
            }
            tvPlay.setText("Play");
            tvSave.setVisibility(View.VISIBLE);
            tvCouldHear.setVisibility(View.GONE);
        }else if(v==tvNext){
            if(freqOfTone>=120) {
                freqOfTone -= 100;
            }
            tvFreq.setText(""+freqOfTone+"Hz");
            sbFrequency.setProgress((int) freqOfTone/2);
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

            if(tvHighFreq.getText().toString().trim().length()<=0){
                Toast.makeText(this, "No frequency range found",Toast.LENGTH_SHORT).show();
                return;
            }

            if(tvLowFreq.getText().toString().trim().length()<=0){
                Toast.makeText(this, "No frequency range found",Toast.LENGTH_SHORT).show();
                return;
            }

            String frequencies=tvLowFreq.getText().toString()+" to "+tvHighFreq.getText().toString();
            Log.e("freques",frequencies);
            if(db.insertHearingReport(db.getId(),frequencies,ystr+"-"+mstr+"-"+dstr+" "+hstr+":"+mnstr+":"+sstr)){
                Toast.makeText(this,"Report successfully saved",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this,"Failed to save report",Toast.LENGTH_SHORT).show();
            }
        }else if(v==tvReport){
            Intent intent=new Intent(this, HearingReportActivity.class);
            startActivity(intent);
        }
    }

    private AudioTrack generateTone(double freqHz, int durationMs) {
        int count = (int)(44100.0 * 2.0 * (durationMs / 1000.0)) & ~1;
        short[] samples = new short[count];
        for(int i = 0; i < count; i += 2){
            short sample = (short)(Math.sin(2 * Math.PI * i / (44100.0 / freqHz)) * 0x7FFF);
            samples[i + 0] = sample;
            samples[i + 1] = sample;
        }
        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
                count * (Short.SIZE / 8), AudioTrack.MODE_STATIC);
        track.write(samples, 0, count);
        track.play();
        return track;
    }
}
