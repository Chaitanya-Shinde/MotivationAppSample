package com.example.motivationappsample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.concurrent.TimeUnit;

public class Pomodoro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView timerText, abtPomodoro, mode;
    String text;
    ImageView start, stop, pause;
    CountDownTimer workTime, breakTime;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //timer
        timerText = findViewById(R.id.timer);
        abtPomodoro = findViewById(R.id.abtpomodoro);
        mode = findViewById(R.id.mode);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        pause = findViewById(R.id.pause);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //code for 25Mins 5Mins Timer
                if(text.equals("Set the timer")){
                    Toast.makeText(getApplicationContext(), "Please set the timer first", Toast.LENGTH_SHORT).show();
                }
                else if(text.equals("Easy Mode")){
                    mode.setVisibility(View.VISIBLE);
                    mode.setText("25 Minutes of Work \n 5 Minutes of Break");
                    start.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    long duration = 1500000;
                    long interval = 1000;
                    workTime = new CountDownTimer(duration, interval){

                        @Override
                        public void onTick(long millisUntilFinished) {
                            NumberFormat f = new DecimalFormat("00");
//                            long hour = (millisUntilFinished/36000000)%24;
                            long minutes = (millisUntilFinished/60000)%60;
                            long sec =(millisUntilFinished/1000)%60;
                            timerText.setText(f.format(minutes)+":"+f.format(sec));
//                            timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                        }

                        @Override
                        public void onFinish() {
                            long duration = 300000;
                            long interval = 1000;
                            breakTime = new CountDownTimer(duration,interval){

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    NumberFormat f = new DecimalFormat("00");
//                                    long hour = (millisUntilFinished/36000000)%24;
                                    long minutes = (millisUntilFinished/60000)%60;
                                    long sec =(millisUntilFinished/1000)%60;
                                    timerText.setText(f.format(minutes)+":"+f.format(sec));
//                                    timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                                }

                                @Override
                                public void onFinish() {
                                    timerText.setText("00:00:00");
                                }
                            }.start();
                        }
                    }.start();
                }

                //code for 45Mins 5Mins timer
                else{
                    mode.setVisibility(View.VISIBLE);
                    mode.setText("45 Minutes of Work \n 15 Minutes of Break");
                    start.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    long duration = 2700000;
                    long interval = 1000;
                    workTime = new CountDownTimer(duration, interval){

                        @Override
                        public void onTick(long millisUntilFinished) {
                            NumberFormat f = new DecimalFormat("00");
//                            long hour = (millisUntilFinished/36000000)%24;
                            long minutes = (millisUntilFinished/60000)%60;
                            long sec =(millisUntilFinished/1000)%60;
                            timerText.setText(f.format(minutes)+":"+f.format(sec));
//                            timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                        }

                        @Override
                        public void onFinish() {
                            long duration = 900000;
                            long interval = 1000;
                            breakTime = new CountDownTimer(duration,interval){

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    NumberFormat f = new DecimalFormat("00");
//                                    long hour = (millisUntilFinished/36000000)%24;
                                    long minutes = (millisUntilFinished/60000)%60;
                                    long sec =(millisUntilFinished/1000)%60;
                                    timerText.setText(f.format(minutes)+":"+f.format(sec));
//                                    timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                                }

                                @Override
                                public void onFinish() {
                                    timerText.setText("00:00");
                                }
                            }.start();
                        }
                    }.start();
                }
            }
        });

        //to stop the timer
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode.setVisibility(View.INVISIBLE);
                spinner.setVisibility(View.VISIBLE);
                pause.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                if(workTime != null){
                    workTime.cancel();
                    timerText.setText("00:00");
                }
            }
        });

        //pause button code
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                if(workTime!=null){
                    workTime.cancel();
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        text = adapterView.getItemAtPosition(i).toString();
        abtPomodoro.setVisibility(View.INVISIBLE);
        if (text.equals("Easy Mode")) {
            timerText.setVisibility(View.VISIBLE);
            timerText.setText("25:00");
        } else if (text.equals("Set the timer")) {
            timerText.setVisibility(View.INVISIBLE);
            abtPomodoro.setVisibility(View.VISIBLE);
        } else {
            timerText.setVisibility(View.VISIBLE);
            timerText.setText("45:00");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}