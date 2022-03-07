//package com.example.motivationappsample;
//
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.example.motivationappsample.Model.ReminderModel;
//import com.example.motivationappsample.Utils.DatabaseHandler;
//
//import java.util.Objects;
//
//public class AddNewTask extends Reminder {
//
//    public static final String TAG = "drawer";
//    private EditText taskTitle, taskDesc;
//    private Button setTime, setDate;
//    private ImageView setRem;
//    private DatabaseHandler db;
//
//    public static AddNewTask newInstance() {
//        return new AddNewTask();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reminder);
//
//
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        return super.onCreateView(name, context, attrs);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        taskDesc = findViewById(R.id.newTaskDesc);
//        taskTitle = findViewById(R.id.newTaskTitle);
//        setTime = findViewById(R.id.timeButton);
//        setDate = findViewById(R.id.dateButton);
//        setRem = findViewById(R.id.set_rem);
//
//        boolean isUpdate = false;
//
//        final Bundle bundle = getArguments();
//        if (bundle != null) {
//            isUpdate = true;
//            String task = bundle.getString("task");
//            taskTitle.setText(task);
//            assert task != null;
//
//        }
//
//        db = new DatabaseHandler(getActivity());
//        db.openDatabase();
//
//        taskTitle.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.toString().equals("")){
//                    setRem.setEnabled(false);
//                }
//                else{
//                    setRem.setEnabled(true);
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//
//        taskDesc.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.toString().equals("")){
//                    setRem.setEnabled(false);
//                }
//                else{
//                    setRem.setEnabled(true);
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//
//        setDate.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.toString().equals("")){
//                    setRem.setEnabled(false);
//                }
//                else{
//                    setRem.setEnabled(true);
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//
//        setTime.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(s.toString().equals("")){
//                    setRem.setEnabled(false);
//                }
//                else{
//                    setRem.setEnabled(true);
//                }
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//
//        final boolean finalIsUpdate = isUpdate;
//        setRem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = taskTitle.getText().toString();
//                if(finalIsUpdate){
//                    db.updateTaskTitle(bundle.getInt("id"), text);
//                }
//                else {
//                    ReminderModel task = new ReminderModel();
//                    task.setTask(text);
//                    task.setStatus(0);
//                    db.insertTask(task);
//                }
//                dismiss();
//            }
//        });
//
//    }
//
//    @Override
//    public void onDismiss(FrameLayout dialog) {
//        Activity activity = getActivity();
//        if(activity instanceof DrawerCloseListener)
//            ((DrawerCloseListener)activity).handleDialogClose(dialog);
//    }
//
//
//
//
//}
//
//
