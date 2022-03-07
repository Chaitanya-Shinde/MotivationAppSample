package com.example.motivationappsample;

import static android.speech.tts.TextToSpeech.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.VolumeAutomation;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import static com.example.motivationappsample.R.raw.*;
import static java.lang.Thread.sleep;

import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;

public class Pomodoro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView timerText, abtPomodoro, mode, sets, streakText, freezerText;
    String text;
    Integer set = 0;
    ImageView start_btn, stop_btn, pause_btn, resume_btn, icon;
    Spinner spinner;
    CountDownTimer workTime, breakTime, resumeTime, backgroundResumeTime;
    Vibrator vibrator;
    VibrationEffect vibrationEffect;
    MediaPlayer beepSound;
    AudioManager audioManager;
    Boolean flag = true;
    long[] vibe_time = {0, 150, 200, 350, 400, 550, 600, 750};
    int selectionCount= 0;
    int streak, freezer, day, prevDay;
    TextToSpeech textToSpeech;
    Boolean startBtnClick, pauseBtnClick, stopBtnClick, whichPhase, timeLeft;
    long currentTimeMillis, currentTimerTime;
    int spinnerSelection;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
        Objects.requireNonNull(getSupportActionBar()).hide();

        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2 = preferences.edit();

        //Updating streaks and freezers on activity launcH
        if (prefs.getBoolean("FIRST_BOOT", true)) {
            editor.putBoolean("FIRST_BOOT", false);
            editor.putBoolean("STREAK NOT UPDATED", true);
            editor.apply();
        } else {
            streak = prefs.getInt("STREAK", 0);
            prevDay = prefs.getInt("DAY", 0);
            freezer = prefs.getInt("FREEZER", 0);
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.MINUTE);
            if (day < prevDay) {
                prevDay += 7;
            }
            int diff = (day - prevDay) - 1;
            if (day == prevDay + 1) {
                editor.putBoolean("STREAK NOT UPDATED", true);
                editor.apply();
            } else if (day > prevDay + 1) {
                editor.putBoolean("STREAK NOT UPDATED", true);
                editor.apply();
                if (freezer >= diff) {
                    freezer = freezer - diff;
                } else {
                    freezer = 0;
                    streak = 0;

                    int addStreak = preferences.getInt("STREAKS", 0);
                    addStreak += 1;
                    editor2.putInt("STREAKS", addStreak);
                    editor.apply();
                }
            }

        }

        //streaks & freezers - setting text
        streakText = findViewById(R.id.streakText);
        freezerText = findViewById(R.id.freezerText);
        streakText.setText(String.valueOf(streak));
        freezerText.setText(String.valueOf(freezer));


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

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrationEffect = VibrationEffect.createWaveform(vibe_time, VibrationEffect.DEFAULT_AMPLITUDE);

        float beepSoundVol = preferences.getFloat("POMO_VOLUME",0);
        beepSoundVol/=200;
        beepSound = new MediaPlayer();
        beepSound.setAudioAttributes(new AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_ALARM)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
        );
        try {
            beepSound.setDataSource(this, Uri.parse("android.resource://com.example.motivationappsample/" + pomo_beep));
            beepSound.setVolume(beepSoundVol, beepSoundVol);
            beepSound.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }


        textToSpeech = new TextToSpeech(getApplicationContext(), new OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        textToSpeech.setSpeechRate(0.8f);
        textToSpeech.setPitch(0.8f);


        //spinner code
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time, R.layout.custom_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        spinnerSelection = prefs.getInt("SELECTED MODE", 0);
//        spinner.setSelection(spinnerSelection);
        spinner.setOnItemSelectedListener(this);

//        //Background running code
//        currentTimeMillis = System.currentTimeMillis();
//        long prevTimeMillis = prefs.getLong("LAST SYSTEM TIME", 0);
//        long prevTimerTime = prefs.getLong("LAST TIMER TIME", 0);
//        long timeDiff = currentTimeMillis - prevTimeMillis;
//        currentTimerTime = prevTimerTime - timeDiff;
//        set = prefs.getInt("SETS", 0);
//
//        if (prefs.getBoolean("START BUTTON CLICK", true)) {
//            sets.setVisibility(View.VISIBLE);
//            mode.setVisibility(View.VISIBLE);
//            icon.setVisibility(View.VISIBLE);
//            pause_btn.setVisibility(View.VISIBLE);
//            stop_btn.setVisibility(View.VISIBLE);
//            start_btn.setVisibility(View.INVISIBLE);
//            spinner.setVisibility(View.INVISIBLE);
//            sets.setText("No. of sets completed: " + set);
//
//            long duration = currentTimerTime;
//            long interval = 1000;
//            backgroundResumeTime = new CountDownTimer(currentTimerTime, interval) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//
//                    if (spinnerSelection == 1) {
//                        if (prefs.getBoolean("TIMER PHASE", true)) {
//                            mode.setText("25 Minutes of Work \nLets make it happen !");
//                            icon.setImageResource(R.drawable.work_icon);
//                            flag = true;
//                        } else {
//                            mode.setText("5 Minutes of Work \nRelax a  bit for now.");
//                            icon.setImageResource(R.drawable.break_icon);
//                            flag = false;
//                        }
//                    } else {
//                        if (prefs.getBoolean("TIMER PHASE", true)) {
//                            mode.setText("45 Minutes of Work \nLets make it happen !");
//                            icon.setImageResource(R.drawable.work_icon);
//                            flag = true;
//                        } else {
//                            mode.setText("15 Minutes of Work \nRelax a  bit for now.");
//                            icon.setImageResource(R.drawable.break_icon);
//                            flag = false;
//                        }
//                    }
//
//                    NumberFormat f = new DecimalFormat("00");
////                            long hour = (millisUntilFinished/36000000)%24;
//                    long minutes = (millisUntilFinished / 60000) % 60;
//                    long sec = (millisUntilFinished / 1000) % 60;
//                    timerText.setText(f.format(minutes) + ":" + f.format(sec));
////                            timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
//                }
//                @Override
//                public void onFinish () {
//                    //Vibration code
//                    vibrator.vibrate(vibrationEffect);
//                    beepSound.start();
//                    {
//                        try {
//                            beepSound.seekTo(0);
//                            sleep(3000);
//                            beepSound.pause();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    start_btn.callOnClick();
//                }
//            }.start();
//        }

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startBtnClick=true;
//                stopBtnClick=false;

                sets.setVisibility(View.VISIBLE);
                mode.setVisibility(View.VISIBLE);
                icon.setVisibility(View.VISIBLE);
                pause_btn.setVisibility(View.VISIBLE);
                stop_btn.setVisibility(View.VISIBLE);
                start_btn.setVisibility(View.INVISIBLE);
                spinner.setVisibility(View.INVISIBLE);

                sets.setText("No. of sets completed: " + set);

                //code for 25Mins 5Mins Timer
                if (text.equals("Light")) {
                    if (preferences.getBoolean("VOICE ASSIST", true)) {
                        textToSpeech.speak("25 Minutes of Work. Lets make it happen !", QUEUE_FLUSH, null);
                    }

                    long duration = 25000;
                    long interval = 1000;

                    workTime = new CountDownTimer(duration, interval) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            mode.setText("25 Minutes of Work \nLets make it happen !");
                            icon.setImageResource(R.drawable.work_icon);
                            flag = true;
//                            if(!flag){
//                                millisUntilFinished=0;
//                                flag = true;
//                            }
                            NumberFormat f = new DecimalFormat("00");
//                            long hour = (millisUntilFinished/36000000)%24;
                            long minutes = (millisUntilFinished / 60000) % 60;
                            long sec = (millisUntilFinished / 1000) % 60;
                            timerText.setText(f.format(minutes) + ":" + f.format(sec));
//                            timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                        }

                        @Override
                        public void onFinish() {
                            //Vibration code
                            vibrator.vibrate(vibrationEffect);
                            beepSound.start();
                            {
                                try {
                                    beepSound.seekTo(0);
                                    sleep(3000);
                                    beepSound.pause();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            long duration = 5000;
                            long interval = 1000;
                            if (preferences.getBoolean("VOICE ASSIST", true)) {
                                textToSpeech.speak("5 Minutes of Break. Relax a bit for now", QUEUE_FLUSH, null);
                            }
                            breakTime = new CountDownTimer(duration, interval) {

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    mode.setText("5 Minutes of Break \nRelax a bit for now");
                                    icon.setImageResource(R.drawable.break_icon);
                                    flag = false;

                                    NumberFormat f = new DecimalFormat("00");
//                                    long hour = (millisUntilFinished/36000000)%24;
                                    long minutes = (millisUntilFinished / 60000) % 60;
                                    long sec = (millisUntilFinished / 1000) % 60;
                                    timerText.setText(f.format(minutes) + ":" + f.format(sec));
//                                    timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                                }

                                @Override
                                public void onFinish() {
                                    set++;
                                    sets.setText("No. of sets completed: " + set);
                                    float hours = preferences.getFloat("HOURS", 0);
                                    hours += 0.5f;
                                    editor2.putFloat("HOURS", hours);
                                    editor2.apply();

                                    Calendar calendar = Calendar.getInstance();
                                    int day2 = calendar.get(Calendar.MINUTE);
                                    if (day2 < prevDay || day2 < day) {
                                        prevDay += 7;
                                    }
                                    int diff = (day2 - prevDay) - 1;
                                    if (day2 > day) {
                                        if (prefs.getBoolean("STREAK NOT UPDATED", true)) {
                                            if (freezer >= diff) {
                                                freezer = freezer - diff;
                                            } else {
                                                freezer = 0;
                                                streak = 0;

                                                int addStreak = preferences.getInt("STREAKS", 0);
                                                addStreak += 1;
                                                editor2.putInt("STREAKS", addStreak);
                                                editor.apply();
                                            }
                                        } else {
                                            streak++;
                                            editor.putBoolean("STREAK NOT UPDATED", false);
                                            editor.apply();
                                        }
                                        day = day2;
                                        freezerText.setText(String.valueOf(freezer));
                                        streakText.setText(String.valueOf(streak));
                                    }

                                    if (prefs.getBoolean("STREAK NOT UPDATED", true)) {
                                        streak++;
                                        editor.putBoolean("STREAK NOT UPDATED", false);
                                        editor.apply();
                                        streakText.setText(String.valueOf(streak));

                                        //To send total lifetime streaks to your activity
                                        if (preferences.getBoolean("FIRST STREAK NOT STARTED", true)) {
                                            editor2.putInt("STREAKS", 1);
                                            editor2.putBoolean("FIRST STREAK NOT STARTED", false);
                                            editor.apply();
                                        }

                                    }

                                    if (set % 4 == 0) {
                                        freezer++;
                                        freezerText.setText(String.valueOf(freezer));
                                    }


                                    vibrator.vibrate(vibrationEffect);

                                    beepSound.start();
                                    {
                                        try {
                                            beepSound.seekTo(0);
                                            sleep(3000);
                                            beepSound.pause();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (preferences.getBoolean("VOICE ASSIST", true)) {
                                        textToSpeech.speak("25 Minutes of Work. Lets make it happen !", QUEUE_FLUSH, null);

                                    }
                                    workTime.start();
                                }
                            }.start();
                        }
                    }.start();
                }


                //code for 45Mins 5Mins timer
                else {
                    mode.setText("45 Minutes of Work \n 15 Minutes of Break");
                    if (preferences.getBoolean("VOICE ASSIST", true)) {
                        textToSpeech.speak("45 Minutes of Work. Lets make it happen !", QUEUE_FLUSH, null);
                    }
                    long duration = 45000;
                    long interval = 1000;
                    workTime = new CountDownTimer(duration, interval) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            icon.setImageResource(R.drawable.work_icon);
                            mode.setText("45 Minutes of Work \nLets make it happen !");
                            flag = true;
//                            if(!flag){
//                                millisUntilFinished=0;
//                                flag = true;
//                            }

                            NumberFormat f = new DecimalFormat("00");
//                            long hour = (millisUntilFinished/36000000)%24;
                            long minutes = (millisUntilFinished / 60000) % 60;
                            long sec = (millisUntilFinished / 1000) % 60;
                            timerText.setText(f.format(minutes) + ":" + f.format(sec));
//                            timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                        }

                        @Override
                        public void onFinish() {
                            vibrator.vibrate(vibrationEffect);

                            beepSound.start();
                            {
                                try {
                                    beepSound.seekTo(0);
                                    sleep(3000);
                                    beepSound.pause();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            long duration = 15000;
                            long interval = 1000;
                            if (preferences.getBoolean("VOICE ASSIST", true)) {
                                textToSpeech.speak("15 Minutes of Break. Relax a bit for now", QUEUE_FLUSH, null);
                            }
                            breakTime = new CountDownTimer(duration, interval) {

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    mode.setText("15 Minutes of Break \nRelax a bit for now");
                                    icon.setImageResource(R.drawable.break_icon);
                                    flag = false;

                                    NumberFormat f = new DecimalFormat("00");
//                                    long hour = (millisUntilFinished/36000000)%24;
                                    long minutes = (millisUntilFinished / 60000) % 60;
                                    long sec = (millisUntilFinished / 1000) % 60;
                                    timerText.setText(f.format(minutes) + ":" + f.format(sec));
//                                    timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                                }

                                @Override
                                public void onFinish() {
                                    set++;
                                    sets.setText("No. of sets completed: " + set);

                                    float hours = preferences.getFloat("HOURS", 0);
                                    hours += 1f;
                                    editor2.putFloat("HOURS", hours);
                                    editor2.apply();

                                    Calendar calendar = Calendar.getInstance();
                                    int day2 = calendar.get(Calendar.MINUTE);
                                    if (day2 < prevDay || day2 < day) {
                                        prevDay += 7;
                                    }
                                    int diff = (day2 - prevDay) - 1;
                                    if (day2 > day) {
                                        if (prefs.getBoolean("STREAK NOT UPDATED", true)) {
                                            if (freezer >= diff) {
                                                freezer = freezer - diff;
                                            } else {
                                                freezer = 0;
                                                streak = 0;
                                                int addStreak = preferences.getInt("STREAKS", 0);
                                                addStreak += 1;
                                                editor2.putInt("STREAKS", addStreak);
                                                editor.apply();
                                            }
                                        } else {
                                            streak++;
                                            editor.putBoolean("STREAK NOT UPDATED", false);
                                            editor.apply();
                                        }
                                        day = day2;
                                        freezerText.setText(String.valueOf(freezer));
                                        streakText.setText(String.valueOf(streak));
                                    }

                                    if (prefs.getBoolean("STREAK NOT UPDATED", true)) {
                                        streak++;
                                        editor.putBoolean("STREAK NOT UPDATED", false);
                                        editor.apply();
                                        streakText.setText(String.valueOf(streak));

                                        //To send total lifetime streaks to your activity
                                        if (preferences.getBoolean("FIRST STREAK NOT STARTED", true)) {
                                            editor2.putInt("STREAKS", 1);
                                            editor2.putBoolean("FIRST STREAK NOT STARTED", false);
                                            editor.apply();
                                        }

                                    }

                                    if (set % 2 == 0) {
                                        freezer++;
                                        freezerText.setText(String.valueOf(freezer));
                                    }

                                    vibrator.vibrate(vibrationEffect);

                                    beepSound.start();
                                    {
                                        try {
                                            beepSound.seekTo(0);
                                            sleep(3000);
                                            beepSound.pause();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (preferences.getBoolean("VOICE ASSIST", true)) {
                                        textToSpeech.speak("45 Minutes of Work. Lets make it happen !", QUEUE_FLUSH, null);
                                    }
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
                set = 0;
                spinner.setVisibility(View.VISIBLE);
                start_btn.setVisibility(View.VISIBLE);
                pause_btn.setVisibility(View.INVISIBLE);
                stop_btn.setVisibility(View.INVISIBLE);
                icon.setVisibility(View.INVISIBLE);
                sets.setVisibility(View.INVISIBLE);

//                startBtnClick = false;
//                pauseBtnClick =false;
//                stopBtnClick=true;

                vibrator.cancel();
                beepSound.pause();
                textToSpeech.stop();

                if (resumeTime != null) {
                    resumeTime.cancel();
                }

                if (workTime != null) {
                    workTime.cancel();
                }

                if (breakTime != null) {
                    breakTime.cancel();
                }

                if (text.equals("Light")) {
                    timerText.setText("25:00");
                    mode.setText("25 Minutes of Work \n 5 Minutes of Break");
                } else {
                    timerText.setText("45:00");
                    mode.setText("45 Minutes of Work \n 5 Minutes of Break");
                }
            }
        });


        //pause button code
        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resume_btn.setVisibility(View.VISIBLE);
                pause_btn.setVisibility(View.INVISIBLE);

//                startBtnClick =false;
//                pauseBtnClick=true;

                vibrator.cancel();
                beepSound.pause();


                if (resumeTime != null) {
                    resumeTime.cancel();
                }

                if (workTime != null) {
                    workTime.cancel();
                }

                if (breakTime != null) {
                    breakTime.cancel();
                }

            }
        });


        resume_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startBtnClick= true;
//                pauseBtnClick = false;

                resume_btn.setVisibility(View.INVISIBLE);
                pause_btn.setVisibility(View.VISIBLE);
                String leftTime = timerText.getText().toString();
                String pauseTime[] = leftTime.split(":", 2);
                long pauseMin = Long.parseLong(pauseTime[0]);
                long pauseSec = Long.parseLong(pauseTime[1]);
                long pauseMilli = TimeUnit.MINUTES.toMillis(pauseMin) + TimeUnit.SECONDS.toMillis(pauseSec) + 1000;
//                if (flag) {
                resumeTime = new CountDownTimer(pauseMilli, 1000) {

                    @Override
                    public void onTick(long l) {
                        NumberFormat f = new DecimalFormat("00");
//                            long hour = (millisUntilFinished/36000000)%24;
                        long minutes = (l / 60000) % 60;
                        long sec = (l / 1000) % 60;
                        timerText.setText(f.format(minutes) + ":" + f.format(sec));
//                            timerText.setText(f.format(hour)+":"+f.format(minutes)+":"+f.format(sec));
                    }

                    @Override
                    public void onFinish() {

                        if(flag) {
                            workTime.onFinish();
                        }
                        else{
                            breakTime.onFinish();
                        }
                    }
                }.start();
            }
        });
    }


    //Spinner selection code
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        text = adapterView.getItemAtPosition(i).toString();

        spinnerSelection = spinner.getSelectedItemPosition();

        abtPomodoro.setVisibility(View.INVISIBLE);
        if (text.equals("Light")) {
            start_btn.setVisibility(View.VISIBLE);
            timerText.setVisibility(View.VISIBLE);
            timerText.setText("25:00");
            mode.setVisibility(View.VISIBLE);
            mode.setText("25 Minutes of Work \n 5 Minutes of Break");
        } else if (text.equals("Choose your workload")) {
            if(selectionCount==0){
                start_btn.setVisibility(View.INVISIBLE);
                timerText.setVisibility(View.INVISIBLE);
                abtPomodoro.setVisibility(View.VISIBLE);
                mode.setVisibility(View.INVISIBLE);
                selectionCount++;
            }
            else{
                start_btn.setVisibility(View.INVISIBLE);
                abtPomodoro.setVisibility(View.INVISIBLE);
                mode.setVisibility(View.INVISIBLE);
            }
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


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Pomodoro.this);
        builder.setMessage("Quitting Pomodoro will stop the timer.");
        builder.setTitle("Are you sure you want to quit ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                set = 0;

                SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                if (streakText.getText().toString().equals(String.valueOf(streak))) {
                    editor.putInt("STREAK", streak);
                    editor.putInt("DAY", day);
                    editor.apply();
                }
                editor.putInt("FREEZER", freezer);
                editor.apply();

                if (resumeTime != null) {
                    resumeTime.cancel();
                }

                if (workTime != null) {
                    workTime.cancel();
                }

                if (breakTime != null) {
                    breakTime.cancel();
                }
                vibrator.cancel();
                beepSound.pause();

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (streakText.getText().toString().equals(String.valueOf(streak))) {
            editor.putInt("STREAK", streak);
            editor.putInt("DAY", day);
            editor.apply();
        }
        editor.putInt("FREEZER", freezer);
        editor.apply();
//        beepSound.release();

//        //Saving values for background running
//        currentTimeMillis = System.currentTimeMillis();
//        currentTimerTime = Long.parseLong(timerText.getText().toString());
//        currentTimeMillis = TimeUnit.SECONDS.toMillis(currentTimerTime);
//        editor.putInt("SELECTED MODE", spinnerSelection);

//        if(!stopBtnClick) {
//            editor.putBoolean("START BUTTON CLICK", startBtnClick);
//            editor.putBoolean("PAUSE BUTTON CLICK", pauseBtnClick);
//            editor.putLong("LAST DEVICE TIME", currentTimeMillis);
//            editor.putLong("LAST TIMER TIME", currentTimerTime);
//            editor.putBoolean("TIMER PHASE", flag);
//            editor.putInt("SETS", set);
//            editor.apply();
//        }


    }

    public void onResume(){
        super.onResume();
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2 = preferences.edit();

        //Updating streaks and freezers on activity launcH
        streak = prefs.getInt("STREAK", 0);
        prevDay = prefs.getInt("DAY", 0);
        freezer = prefs.getInt("FREEZER", 0);
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.MINUTE);
        if(day<prevDay){
            prevDay += 7;
        }
        int diff = (day - prevDay)-1;
        if(day==prevDay+1){
            editor.putBoolean("STREAK NOT UPDATED", true);
            editor.apply();
        }
        else if(day> prevDay + 1){
            editor.putBoolean("STREAK NOT UPDATED",true);
            editor.apply();
            if(freezer>=diff){
                freezer = freezer - diff;
            }
            else {
                freezer=0;
                streak=0;

                int addStreak = preferences.getInt("STREAKS", 0);
                addStreak+=1;
                editor2.putInt("STREAKS",addStreak);
                editor.apply();
            }
        }
    }
}