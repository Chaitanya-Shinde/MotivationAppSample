package com.example.motivationappsample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import static android.Manifest.permission.VIBRATE;
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
    TextView timerText, abtPomodoro, mode, sets;
    String text;
    Boolean flag;
    Integer set;
    ImageView start_btn, stop_btn, pause_btn, resume_btn, icon;
    CountDownTimer workTime, breakTime;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        //spinner code
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //timer
        timerText = findViewById(R.id.timer);
        abtPomodoro = findViewById(R.id.abtpomodoro);
        mode = findViewById(R.id.mode);
        start_btn = findViewById(R.id.start);
        stop_btn = findViewById(R.id.stop);
        pause_btn = findViewById(R.id.pause);
        resume_btn = findViewById(R.id.resume);
        icon = findViewById(R.id.icon);
        sets = findViewById(R.id.sets);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set=0;
                sets.setText("No. of sets completed: "+set);

                //code for 25Mins 5Mins Timer
                if(text.equals("Choose your workload")){
                    Toast.makeText(getApplicationContext(), "Please set the timer first", Toast.LENGTH_SHORT).show();
                }
                else if(text.equals("Light")) {
                    sets.setVisibility(View.VISIBLE);
                    mode.setVisibility(View.VISIBLE);
                    mode.setText("25 Minutes of Work \nLets make it happen !");
                    icon.setVisibility(View.VISIBLE);
                    start_btn.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                    pause_btn.setVisibility(View.VISIBLE);
                    stop_btn.setVisibility(View.VISIBLE);
                    long duration = 1500000;
                    long interval = 1000;
                    workTime = new CountDownTimer(duration, interval) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            icon.setImageResource(R.drawable.work_icon);
                            NumberFormat f = new DecimalFormat("00");
//                            long hour = (millisUntilFinished/36000000)%24;
                            long minutes = (millisUntilFinished / 60000) % 60;
                            long sec = (millisUntilFinished / 1000) % 60;
                            timerText.setText(f.format(minutes) + ":" + f.format(sec));
//                            timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                        }

                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onFinish() {
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(5000, 5);
                            vibrator.vibrate(vibrationEffect);
                            mode.setText("5 Minutes of Break \nRelax a bit for now");
                            icon.setImageResource(R.drawable.break_icon);
                            long duration = 300000;
                            long interval = 1000;
                            breakTime = new CountDownTimer(duration, interval) {

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    NumberFormat f = new DecimalFormat("00");
//                                    long hour = (millisUntilFinished/36000000)%24;
                                    long minutes = (millisUntilFinished / 60000) % 60;
                                    long sec = (millisUntilFinished / 1000) % 60;
                                    timerText.setText(f.format(minutes) + ":" + f.format(sec));
//                                    timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                                }

                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onFinish() {
                                    set++;
                                    sets.setText("No. of sets completed: "+set);
                                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    VibrationEffect vibrationEffect = VibrationEffect.createOneShot(5000, 5);
                                    vibrator.vibrate(vibrationEffect);
                                    workTime.start();
                                }
                            }.start();
                        }
                    }.start();
                }


                //code for 45Mins 5Mins timer
                else{
                    sets.setVisibility(View.VISIBLE);
                    mode.setVisibility(View.VISIBLE);
                    mode.setText("45 Minutes of Work \n 15 Minutes of Break");
                    icon.setVisibility(View.VISIBLE);
                    icon.setImageResource(R.drawable.work_icon);
                    start_btn.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);
                    pause_btn.setVisibility(View.VISIBLE);
                    stop_btn.setVisibility(View.VISIBLE);
                    long duration = 2700000;
                    long interval = 1000;
                    workTime = new CountDownTimer(duration, interval){

                        @Override
                        public void onTick(long millisUntilFinished) {
                            icon.setImageResource(R.drawable.work_icon);
                            mode.setText("45 Minutes of Work \nLets make it happen !");
                            NumberFormat f = new DecimalFormat("00");
//                            long hour = (millisUntilFinished/36000000)%24;
                            long minutes = (millisUntilFinished/60000)%60;
                            long sec =(millisUntilFinished/1000)%60;
                            timerText.setText(f.format(minutes)+":"+f.format(sec));
//                            timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                        }

                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onFinish() {
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            VibrationEffect vibrationEffect = VibrationEffect.createOneShot(5000, 5);
                            vibrator.vibrate(vibrationEffect);
                            mode.setText("15 Minutes of Break \nRelax a bit for now");
                            icon.setImageResource(R.drawable.break_icon);
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
                                    set++;
                                    sets.setText("No. of sets completed: "+set);
                                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    VibrationEffect vibrationEffect = VibrationEffect.createOneShot(5000, 5);
                                    vibrator.vibrate(vibrationEffect);
                                    workTime.start();
                                }
                            }.start();
                        }
                    }.start();
                }
            }
        });


        //to stop the timer
        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set=0;
                spinner.setVisibility(View.VISIBLE);
                pause_btn.setVisibility(View.INVISIBLE);
                stop_btn.setVisibility(View.INVISIBLE);
                start_btn.setVisibility(View.VISIBLE);
                icon.setVisibility(View.INVISIBLE);
                sets.setVisibility(View.INVISIBLE);
                if(workTime != null){
                    workTime.cancel();
                    if(text.equals("Light")){
                        timerText.setText("25:00");
                        mode.setText("25 Minutes of Work \n 5 Minutes of Break");
                    }
                    else {
                        timerText.setText("45:00");
                        mode.setText("45 Minutes of Work \n 5 Minutes of Break");
                    }
                }
            }
        });


        //pause button code
        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resume_btn.setVisibility(View.VISIBLE);
                pause_btn.setVisibility(View.INVISIBLE);
                start_btn.setVisibility(View.VISIBLE);
//                if(breakTime){
//                    flag = true;
//                    workTime.cancel();
//                }
//                else{
//                    flag = false;
//                    workTime.cancel();
//                }
            }
        });


        resume_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resume_btn.setVisibility(View.INVISIBLE);
                pause_btn.setVisibility(View.VISIBLE);
                String leftTime = timerText.getText().toString();
                String pauseTime[] = leftTime.split(":",2);
                long pauseMin = Long.parseLong(pauseTime[0]);
                long pauseSec = Long.parseLong(pauseTime[1]);
                long pauseMilli = TimeUnit.MINUTES.toMillis(pauseMin) + TimeUnit.SECONDS.toMillis(pauseSec);
//                if(flag){
//                    breakTime.onTick(pauseMilli);
//                }
//                else{
//                    workTime.onTick(pauseMilli);
//                }
            }
        });
    }






    //Spinner selection code
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        text = adapterView.getItemAtPosition(i).toString();
        abtPomodoro.setVisibility(View.INVISIBLE);
        if (text.equals("Light")) {
            start_btn.setVisibility(View.VISIBLE);
            timerText.setVisibility(View.VISIBLE);
            timerText.setText("25:00");
            mode.setVisibility(View.VISIBLE);
            mode.setText("25 Minutes of Work \n 5 Minutes of Break");
        } else if (text.equals("Choose your workload")) {
            start_btn.setVisibility(View.INVISIBLE);
            timerText.setVisibility(View.INVISIBLE);
            abtPomodoro.setVisibility(View.VISIBLE);
            mode.setVisibility(View.INVISIBLE);
        } else {
            start_btn.setVisibility(View.VISIBLE);
            timerText.setVisibility(View.VISIBLE);
            mode.setVisibility(View.VISIBLE);
            timerText.setText("45:00");
            mode.setText("45 Minutes of Work \n 15 Minutes of Break");
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}