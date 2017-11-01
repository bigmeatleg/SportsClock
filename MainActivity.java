package com.jinder.sportsclock;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{
    private ProgressBar pb_counter;
    private ProgressBar pb_cycle;
    private TextView    tv_counter;

    private CountDownTimer  countDownTimer;
    private MediaPlayer     mp;
    private Handler         stepHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        pb_counter = (ProgressBar) findViewById(R.id.pb_counter);
        pb_cycle = (ProgressBar) findViewById(R.id.pb_cycle);
        tv_counter = (TextView) findViewById(R.id.txt_counter);
    }


    @Override
    public void onClick(View view) {
        CountDownStart(10, "gong-played1.mp3");
    }

    private void CountDownStart(final int nSec, final String soundEffect){
        pb_counter.setMax(nSec);
        pb_counter.setProgress(0);

        countDownTimer = new CountDownTimer(nSec*1000, 500) {
            @Override
            public void onTick(long l) {
                Integer nCount = (int)(nSec - (l/1000));
                pb_counter.setProgress(nCount);
                if(((l/1000) < 6) && mp == null){
                    try {
                        mp = new MediaPlayer();
                        AssetFileDescriptor descriptor = getAssets().openFd("sound/" + soundEffect);
                        mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                        descriptor.close();
                        mp.prepare();
                        mp.setLooping(true);
                        mp.setVolume(1.0f, 1.0f);
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                tv_counter.setText(String.valueOf(nCount));
            }

            @Override
            public void onFinish() {
                mp.stop();
                mp.release();
                mp = null;
            }
        }.start();
    }
}
