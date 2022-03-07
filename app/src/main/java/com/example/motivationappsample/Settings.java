package com.example.motivationappsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

public class Settings extends AppCompatActivity {

    TextView reportBtn, streak, totalTime, clearData;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch voiceAssistant;
    Slider slider;
    int streaks;
    float hours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        streak = findViewById(R.id.streaks);
        totalTime = findViewById(R.id.timeSpent);
        voiceAssistant = findViewById(R.id.voice_assist_toggle);
        slider  = findViewById(R.id.vol_slider);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2 = preferences.edit();

        if(preferences.getBoolean("VOICE ASSIST", true)){
            voiceAssistant.setChecked(true);
        }
        else{
            voiceAssistant.setChecked(false);
        }

        hours = preferences.getFloat("HOURS", 0);
        if(hours ==1f) {
            totalTime.setText((int)hours + " hr");
        }
        else{
            totalTime.setText((int)hours+" hrs");
        }

        streaks = preferences.getInt("STREAKS",0);
        streak.setText(String.valueOf(streaks));

//        editor2.putBoolean("VOICE ASSIST", false);
//        voiceAssistant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                editor2.putBoolean("VOICE ASSIST", true);
//            }
//        });



        reportBtn=findViewById(R.id.report_Btn);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Settings.this, ReportProblem.class);
//                startActivity(intent);
            }
        });


        slider.setValue(preferences.getFloat("POMO_VOLUME", 0));

        slider.addOnChangeListener(new Slider.OnChangeListener() {
                                       @Override
                                       public void onValueChange(@NonNull Slider slider, float value, boolean fromUser){
                                           editor2.putBoolean("DEF_POMO_VOLUME", false);
                                           editor2.putFloat("POMO_VOLUME", value);
                                           editor2.apply();
                                       }
                                   }
        );

    }

    public  void onPause(){
        super.onPause();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2 = preferences.edit();
        if(voiceAssistant.isChecked()){
            editor2.putBoolean("VOICE ASSIST", true);
            editor2.apply();
        }
        else{
            editor2.putBoolean("VOICE ASSIST",false);
            editor2.apply();
        }
    }

    public void onBackPressed(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2 = preferences.edit();
        if(voiceAssistant.isChecked()){
            editor2.putBoolean("VOICE ASSIST", true);
            editor2.apply();
        }
        else{
            editor2.putBoolean("VOICE ASSIST",false);
            editor2.apply();
        }
        super.onBackPressed();
    }
}