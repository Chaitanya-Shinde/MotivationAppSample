package com.example.motivationappsample;

import static com.example.motivationappsample.R.id.date_picker;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motivationappsample.Model.ReminderModel;
import com.example.motivationappsample.RemRecyclerAdapter.RemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reminder extends AppCompatActivity {

    private RecyclerView remRecyclerView;

    Button datePicker;
    TextView tvDay,tvMonth,tvYear;
    DatePickerDialog.OnDateSetListener setListener;
    private RemAdapter remAdapter;

    private List<ReminderModel> remList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        getSupportActionBar().hide();

        remList = new ArrayList<>();

        remRecyclerView = findViewById(R.id.remRecyclerView);
        remRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        remAdapter = new RemAdapter(this);
        remRecyclerView.setAdapter(remAdapter);

        ReminderModel reminder = new ReminderModel();
        reminder.setTask("This is a test task");
        reminder.setStatus(0);
        reminder.setId(1);

        remList.add(reminder);
        remList.add(reminder);
        remList.add(reminder);
        remList.add(reminder);
        remList.add(reminder);

        remAdapter.setReminder(remList);



        /*FOR DATE PICKER*/

        datePicker = (Button) findViewById(date_picker);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Reminder.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String dt_day = day+"";
                String dt_month = month+"";
                String dt_year = year+"";
                tvDay.setText(dt_day);
                tvMonth.setText(dt_month);
                tvYear.setText(dt_year);
            }
        };


//        datePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(
//                        Reminder.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        setListener,year,month,day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//            }
//        });
//
//        setListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                month = month+1;
//                String date = day+"/"+month+"/"+year;
//                datePicker.setText(date);
//            }
//        };
    }
}