package com.example.motivationappsample;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.motivationappsample.Model.ReminderModel;
import com.example.motivationappsample.Adapter.RemAdapter;
import com.example.motivationappsample.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Reminder extends AppCompatActivity {

    private RecyclerView remRecyclerView;


    int hour, minute;
    String form;
    Button timeButton, dateButton;
    TextView tvDay,tvMonth,tvYear,taskDate;
    DatePickerDialog.OnDateSetListener setListener;
    private RemAdapter remAdapter;
    FrameLayout Drawer;
    ImageView AddRem, SetRem;
    public List<ReminderModel> taskList;
    private DatePickerDialog datePickerDialog;
    private DatabaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        getSupportActionBar().hide();

        timeButton = findViewById(R.id.timeButton);
        dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDatePicker();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });



        Drawer = findViewById(R.id.drawer);
        AddRem = findViewById(R.id.addRem_btn);
        SetRem = findViewById(R.id.set_rem);

        if(Drawer.getVisibility() == View.VISIBLE )
        {
            Drawer.setVisibility(View.INVISIBLE);

        }

        AddRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawer.setVisibility(View.VISIBLE);
            }

        });

        SetRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawer.setVisibility(View.INVISIBLE);
            }
        });






//------FOR RECYCLER VIEW----------------------------------------------------

        taskList = new ArrayList<>();

        remRecyclerView = findViewById(R.id.remRecyclerView);
        remRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        remAdapter = new RemAdapter(this);
        remRecyclerView.setAdapter(remAdapter);

        ReminderModel reminder = new ReminderModel();
        reminder.setTaskTitle("This is a test task");
        reminder.setStatus(0);
        reminder.setId(1);

        taskList.add(reminder);
        taskList.add(reminder);
        taskList.add(reminder);
        taskList.add(reminder);
        taskList.add(reminder);

        remAdapter.setReminder(taskList);




    }

//--FOR DATE PICKER------------------------------------------------------------

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new
                DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int
                            day)
                    {
                        month = month + 1;
                        String date = makeDateString(day, month, year);
                        dateButton.setText(date);

                    }
                };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year,
                month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        return "JAN";
    }


//--FOR TIME PICKER-------------------------------------------------------------

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new
                TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int
                            selectedMinute)
                    {
                        if (selectedHour == 0) {
                            selectedHour += 12;
                            form = "AM";
                        }
                        else if (selectedHour == 12) {
                            form = "PM";
                        }
                        else if (selectedHour > 12) {
                            selectedHour -= 12;
                            form = "PM";
                        }
                        else {
                            form = "AM";
                        }
                        hour = selectedHour;
                        minute = selectedMinute;
                        timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d %s",hour, minute, form));
                    }
                };
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style,
                onTimeSetListener, hour, minute,false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


    protected void onViewCreated(View view, Bundle savedInstanceState) {
    }

//    @Override
//    public void handleDialogClose(DialogInterface dialog){
//        taskList = db.getAllTasks();
//        Collections.reverse(taskList);
//        tasksAdapter.setTasks(taskList);
//        tasksAdapter.notifyDataSetChanged();
//    }
}